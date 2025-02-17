#include <windows.h>
#include <iostream>
#include <chrono>
#include <fstream>    // для создания тестового файла
#include <cstdio>
#include <cstdlib>    // rand, srand
#include <cstring>    // memcpy

// -----------------------------------------------------
// Определим аналог ssize_t для Windows (32/64):
#ifndef SSIZE_T_DEFINED
#ifdef _WIN64
typedef long long ssize_t;
#else
typedef int ssize_t;
#endif
#define SSIZE_T_DEFINED
#endif

// -----------------------------------------------------
// Настройки для user-space cache:
static const int CACHE_SIZE  = 8;         // число блоков в кэше
static const int BLOCK_SIZE  = 4096;      // размер блока
static const int MAX_FILES   = 16;

// Структуры для user-space кэша:
struct CacheBlock {
    bool    valid;
    bool    dirty;
    int     file_id;       // наш внутренний fd
    long long block_no;    // номер блока
    char    data[BLOCK_SIZE];
};

struct FileDesc {
    HANDLE      handle;
    long long   current_offset;
    bool        is_valid;
};

// Глобальные
static CacheBlock g_cache[CACHE_SIZE];
static FileDesc   g_files[MAX_FILES];
static bool       g_inited = false;

// -----------------------------------------------------
// Инициализация user-space cache
static void init_all()
{
    if (!g_inited) {
        srand(1); // для воспроизводимого Random
        memset(g_cache, 0, sizeof(g_cache));
        memset(g_files, 0, sizeof(g_files));
        g_inited = true;
    }
}

// Поиск свободного индекса среди g_files
static int find_free_fd_slot()
{
    for (int i = 0; i < MAX_FILES; i++) {
        if (!g_files[i].is_valid) {
            return i;
        }
    }
    return -1;
}

// Сбросить (write-back) dirty-блок
static int flush_block_to_disk(int index)
{
    CacheBlock &blk = g_cache[index];
    if (!blk.valid || !blk.dirty) {
        return 0;
    }
    FileDesc &f = g_files[blk.file_id];
    LARGE_INTEGER li;
    li.QuadPart = blk.block_no * BLOCK_SIZE;
    if (!SetFilePointerEx(f.handle, li, nullptr, FILE_BEGIN)) {
        return -1;
    }
    DWORD written = 0;
    BOOL res = WriteFile(f.handle, blk.data, BLOCK_SIZE, &written, NULL);
    if (!res || written != BLOCK_SIZE) {
        return -1;
    }
    blk.dirty = false;
    return 0;
}

// Прочитать блок
static int read_block_from_disk(int cache_index, int fd, long long block_no)
{
    // Если занято dirty-блоком, сбросим
    if (g_cache[cache_index].valid && g_cache[cache_index].dirty) {
        if (flush_block_to_disk(cache_index) < 0) {
            return -1;
        }
    }
    // Считываем
    FileDesc &f = g_files[fd];
    LARGE_INTEGER li;
    li.QuadPart = block_no * BLOCK_SIZE;
    if (!SetFilePointerEx(f.handle, li, NULL, FILE_BEGIN)) {
        return -1;
    }
    DWORD bytes_read = 0;
    BOOL res = ReadFile(f.handle, g_cache[cache_index].data, BLOCK_SIZE, &bytes_read, NULL);
    if (!res) {
        return -1;
    }
    if (bytes_read < BLOCK_SIZE) {
        memset(g_cache[cache_index].data + bytes_read, 0, BLOCK_SIZE - bytes_read);
    }
    g_cache[cache_index].valid    = true;
    g_cache[cache_index].dirty    = false;
    g_cache[cache_index].file_id  = fd;
    g_cache[cache_index].block_no = block_no;
    return 0;
}

// Найти блок в кэше
static int find_block_in_cache(int fd, long long block_no)
{
    for (int i = 0; i < CACHE_SIZE; i++) {
        if (g_cache[i].valid &&
            g_cache[i].file_id == fd &&
            g_cache[i].block_no == block_no) {
            return i;
        }
    }
    return -1;
}

// Выбор жертвы Random
static int select_victim_random()
{
    return rand() % CACHE_SIZE;
}

// Найти или освободить слот для блока
static int get_cache_index_for_block(int fd, long long block_no)
{
    int idx = find_block_in_cache(fd, block_no);
    if (idx >= 0) {
        return idx;
    }
    // Ищем свободный
    for (int i = 0; i < CACHE_SIZE; i++) {
        if (!g_cache[i].valid) {
            return i;
        }
    }
    // Иначе Random
    return select_victim_random();
}

// -----------------------------------------------------
// User-space cache API:

int lab2_open(const char *path)
{
    init_all();
    int slot = find_free_fd_slot();
    if (slot < 0) return -1;

    // Открываем без системного кэша:
    HANDLE h = CreateFileA(
        path,
        GENERIC_READ | GENERIC_WRITE,
        0, // эксклюзив
        NULL,
        OPEN_EXISTING,
        FILE_ATTRIBUTE_NORMAL | FILE_FLAG_NO_BUFFERING | FILE_FLAG_WRITE_THROUGH,
        NULL
    );
    if (h == INVALID_HANDLE_VALUE) {
        return -1;
    }
    g_files[slot].handle          = h;
    g_files[slot].current_offset  = 0;
    g_files[slot].is_valid        = true;
    return slot;
}

int lab2_close(int fd)
{
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].is_valid) {
        return -1;
    }
    for (int i = 0; i < CACHE_SIZE; i++) {
        if (g_cache[i].valid && g_cache[i].file_id == fd && g_cache[i].dirty) {
            flush_block_to_disk(i);
            g_cache[i].valid = false;
        }
    }
    CloseHandle(g_files[fd].handle);
    g_files[fd].is_valid = false;
    return 0;
}

ssize_t lab2_read(int fd, void *buf, size_t count)
{
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].is_valid) return -1;
    char *dst = (char*)buf;
    size_t bytes_read_total = 0;

    while (count > 0) {
        long long offset       = g_files[fd].current_offset;
        long long block_no     = offset / BLOCK_SIZE;
        size_t offset_in_block = (size_t)(offset % BLOCK_SIZE);
        size_t to_read         = BLOCK_SIZE - offset_in_block;
        if (to_read > count) to_read = count;

        int cidx = find_block_in_cache(fd, block_no);
        if (cidx < 0) {
            cidx = get_cache_index_for_block(fd, block_no);
            if (read_block_from_disk(cidx, fd, block_no) < 0) {
                break; // ошибка
            }
        }
        memcpy(dst, g_cache[cidx].data + offset_in_block, to_read);

        g_files[fd].current_offset += to_read;
        dst += to_read;
        bytes_read_total += to_read;
        count -= to_read;
    }
    return (ssize_t)bytes_read_total;
}

ssize_t lab2_write(int fd, const void *buf, size_t count)
{
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].is_valid) return -1;
    const char *src = (const char*)buf;
    size_t bytes_written_total = 0;

    while (count > 0) {
        long long offset       = g_files[fd].current_offset;
        long long block_no     = offset / BLOCK_SIZE;
        size_t offset_in_block = (size_t)(offset % BLOCK_SIZE);
        size_t to_write        = BLOCK_SIZE - offset_in_block;
        if (to_write > count) to_write = count;

        int cidx = find_block_in_cache(fd, block_no);
        if (cidx < 0) {
            cidx = get_cache_index_for_block(fd, block_no);
            // Для частичной записи нужно подгрузить блок
            if (read_block_from_disk(cidx, fd, block_no) < 0) {
                break;
            }
        }
        memcpy(g_cache[cidx].data + offset_in_block, src, to_write);
        g_cache[cidx].dirty = true;

        g_files[fd].current_offset += to_write;
        src += to_write;
        bytes_written_total += to_write;
        count -= to_write;
    }
    return (ssize_t)bytes_written_total;
}

long long lab2_lseek(int fd, long long offset, int whence)
{
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].is_valid) return -1;
    LARGE_INTEGER fsize;
    fsize.QuadPart = 0;
    GetFileSizeEx(g_files[fd].handle, &fsize);

    long long new_off = -1;
    switch (whence) {
    case SEEK_SET: new_off = offset; break;
    case SEEK_CUR: new_off = g_files[fd].current_offset + offset; break;
    case SEEK_END: new_off = fsize.QuadPart + offset; break;
    }
    if (new_off < 0) {
        return -1;
    }
    g_files[fd].current_offset = new_off;
    return new_off;
}

int lab2_fsync(int fd)
{
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].is_valid) return -1;
    for (int i = 0; i < CACHE_SIZE; i++) {
        if (g_cache[i].valid && g_cache[i].file_id == fd && g_cache[i].dirty) {
            if (flush_block_to_disk(i) < 0) {
                return -1;
            }
        }
    }
    // Системный flush
    if (!FlushFileBuffers(g_files[fd].handle)) {
        return -1;
    }
    return 0;
}

// -----------------------------------------------------
// Обычная работа с файлом (Windows cache)
HANDLE os_open(const char* path)
{
    // Без NO_BUFFERING
    HANDLE h = CreateFileA(
        path,
        GENERIC_READ | GENERIC_WRITE,
        0,
        NULL,
        OPEN_EXISTING,
        FILE_ATTRIBUTE_NORMAL, // используем обычный кэш системы
        NULL
    );
    if (h == INVALID_HANDLE_VALUE) return NULL;
    return h;
}

ssize_t os_read(HANDLE h, void *buf, size_t to_read)
{
    DWORD bytes_read = 0;
    BOOL ok = ReadFile(h, buf, (DWORD)to_read, &bytes_read, NULL);
    if (!ok) return -1;
    return (ssize_t)bytes_read;
}

ssize_t os_write(HANDLE h, const void *buf, size_t to_write)
{
    DWORD bytes_written = 0;
    BOOL ok = WriteFile(h, buf, (DWORD)to_write, &bytes_written, NULL);
    if (!ok) return -1;
    return (ssize_t)bytes_written;
}

long long os_lseek(HANDLE h, long long offset, int whence)
{
    // Получим размер
    LARGE_INTEGER fs;
    fs.QuadPart = 0;
    GetFileSizeEx(h, &fs);

    LARGE_INTEGER li;
    long long new_off = -1;
    switch (whence) {
    case SEEK_SET: new_off = offset; break;
    case SEEK_CUR: {
        // Текущую позицию надо получить:
        LARGE_INTEGER zero; zero.QuadPart = 0;
        LARGE_INTEGER curPos;
        SetFilePointerEx(h, zero, &curPos, FILE_CURRENT);
        new_off = curPos.QuadPart + offset;
        break;
    }
    case SEEK_END: new_off = fs.QuadPart + offset; break;
    }

    if (new_off < 0) return -1;

    li.QuadPart = new_off;
    if (!SetFilePointerEx(h, li, NULL, FILE_BEGIN)) {
        return -1;
    }
    return new_off;
}

int os_fsync(HANDLE h)
{
    if (!FlushFileBuffers(h)) {
        return -1;
    }
    return 0;
}

void os_close(HANDLE h)
{
    if (h && h != INVALID_HANDLE_VALUE) {
        CloseHandle(h);
    }
}

// -----------------------------------------------------
// Создание тестового файла (16 MB, для примера)
static void create_test_file(const char* fname, size_t size_in_bytes)
{
    std::ofstream ofs(fname, std::ios::binary | std::ios::trunc);
    if (!ofs.good()) {
        std::cerr << "Failed to create " << fname << std::endl;
        return;
    }
    // Запишем шаблон
    static const size_t PATTERN_SIZE = 4096;
    char pattern[PATTERN_SIZE];
    for (size_t i = 0; i < PATTERN_SIZE; i++) {
        pattern[i] = char(i % 256);
    }
    size_t written = 0;
    while (written < size_in_bytes) {
        size_t to_write = PATTERN_SIZE;
        if (written + to_write > size_in_bytes) {
            to_write = size_in_bytes - written;
        }
        ofs.write(pattern, to_write);
        written += to_write;
    }
    ofs.close();
    std::cout << "Test file created: " << fname << " (" << size_in_bytes << " bytes)\n";
}

// -----------------------------------------------------
int main(int argc, char* argv[])
{
    // Параметры
    const char* filename = "testfile.bin";
    if (argc >= 2) {
        filename = argv[1];
    }
    int iterations = 5;
    if (argc >= 3) {
        iterations = std::atoi(argv[2]);
    }

    const size_t FILE_SIZE = 16ULL * 1024 * 1024; // 16 MB
    create_test_file(filename, FILE_SIZE);

    // -------------------------------------------------
    // Тест 1: ЧТЕНИЕ через user-space cache (lab2_*) против чтения с обычным кэшем ОС

    std::cout << "\n=== Compare READ performance ===\n";
    const size_t READ_CHUNK = 64 * 1024; // 64 KB буфер

    // 1) User-space cache
    long long total_time_user_ms = 0;
    {
        int fd = lab2_open(filename);
        if (fd < 0) {
            std::cerr << "Error: lab2_open() failed\n";
        } else {
            char *buffer = new char[READ_CHUNK];
            for (int iter = 0; iter < iterations; iter++) {
                // Ставим offset=0
                lab2_lseek(fd, 0, SEEK_SET);

                auto t1 = std::chrono::high_resolution_clock::now();
                size_t left = FILE_SIZE;
                while (left > 0) {
                    size_t to_read = (left > READ_CHUNK) ? READ_CHUNK : left;
                    ssize_t got = lab2_read(fd, buffer, to_read);
                    if (got <= 0) break;
                    left -= got;
                }
                auto t2 = std::chrono::high_resolution_clock::now();
                long long ms = std::chrono::duration_cast<std::chrono::milliseconds>(t2 - t1).count();
                total_time_user_ms += ms;

                std::cout << "User-cache READ iteration " << (iter+1)
                          << " => " << ms << " ms\n";
            }
            // закрываем
            lab2_close(fd);
            delete[] buffer;
        }
    }

    // 2) Обычная работа с файлом (OS cache)
    long long total_time_os_ms = 0;
    {
        HANDLE h = os_open(filename);
        if (!h) {
            std::cerr << "Error: os_open() failed\n";
        } else {
            char *buffer = new char[READ_CHUNK];
            for (int iter = 0; iter < iterations; iter++) {
                // offset=0
                os_lseek(h, 0, SEEK_SET);

                auto t1 = std::chrono::high_resolution_clock::now();
                size_t left = FILE_SIZE;
                while (left > 0) {
                    size_t to_read = (left > READ_CHUNK) ? READ_CHUNK : left;
                    ssize_t got = os_read(h, buffer, to_read);
                    if (got <= 0) break;
                    left -= got;
                }
                auto t2 = std::chrono::high_resolution_clock::now();
                long long ms = std::chrono::duration_cast<std::chrono::milliseconds>(t2 - t1).count();
                total_time_os_ms += ms;

                std::cout << "OS-cache READ iteration " << (iter+1)
                          << " => " << ms << " ms\n";
            }
            os_close(h);
            delete[] buffer;
        }
    }

    std::cout << "\nAverage user-cache READ time: "
              << (double)total_time_user_ms / iterations << " ms\n";
    std::cout << "Average OS-cache   READ time: "
              << (double)total_time_os_ms / iterations << " ms\n";

    // -------------------------------------------------
    // Тест 2: ЗАПИСЬ (небольшого объёма, например 1 MB)

    std::cout << "\n=== Compare WRITE performance ===\n";
    static const size_t WRITE_SIZE = 1024 * 1024; // 1 MB
    char* wbuf = new char[WRITE_SIZE];
    for (size_t i = 0; i < WRITE_SIZE; i++) {
        wbuf[i] = char(i % 256);
    }

    // 1) User-space cache WRITE
    long long user_write_ms = 0;
    {
        int fd = lab2_open(filename);
        if (fd >= 0) {
            // Пишем в начало
            lab2_lseek(fd, 0, SEEK_SET);
            auto t1 = std::chrono::high_resolution_clock::now();
            lab2_write(fd, wbuf, WRITE_SIZE);
            lab2_fsync(fd); // сброс
            auto t2 = std::chrono::high_resolution_clock::now();
            user_write_ms = std::chrono::duration_cast<std::chrono::milliseconds>(t2 - t1).count();

            lab2_close(fd);
        }
    }

    // 2) OS cache WRITE
    long long os_write_ms = 0;
    {
        HANDLE h = os_open(filename);
        if (h) {
            // Пишем в начало
            os_lseek(h, 0, SEEK_SET);
            auto t1 = std::chrono::high_resolution_clock::now();
            os_write(h, wbuf, WRITE_SIZE);
            os_fsync(h); // flush
            auto t2 = std::chrono::high_resolution_clock::now();
            os_write_ms = std::chrono::duration_cast<std::chrono::milliseconds>(t2 - t1).count();

            os_close(h);
        }
    }

    std::cout << "User-cache WRITE 1MB => " << user_write_ms << " ms\n";
    std::cout << "OS-cache   WRITE 1MB => " << os_write_ms << " ms\n";

    delete[] wbuf;
    return 0;
}
