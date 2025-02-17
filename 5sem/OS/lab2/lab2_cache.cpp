#include "lab2_cache.h"
#include <windows.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <random>
#include <ctime>

//--------------------------------------------------
// Настройки кэша
//--------------------------------------------------
#define MAX_FILES   256          // Максимум открытых файлов
#define MAX_BLOCKS  128          // Максимум блоков в кэше
#define BLOCK_SIZE  4096         // Размер блока (кратен сектору)

//--------------------------------------------------
// Структура, описывающая открытый файл
//--------------------------------------------------
struct Lab2File {
    HANDLE      hFile;      // WinAPI HANDLE
    bool        inUse;      // Флаг, что данная ячейка занята
    long long   filePos;    // «Логический» указатель на позицию в файле
};

//--------------------------------------------------
// Структура, описывающая блок в кэше
//--------------------------------------------------
struct CacheBlock {
    bool        inUse;          // Занят ли блок
    int         fd;             // Какому нашему "fd" принадлежит блок
    bool        isDirty;        // Флаг "грязный" (есть несохранённые изменения)
    long long   fileOffset;     // Смещение в файле (в байтах) от начала файла
    char*       data;           // Указатель на буфер (размер BLOCK_SIZE), выровнен
};

//--------------------------------------------------
// Глобальные структуры
//--------------------------------------------------
static Lab2File    g_files[MAX_FILES];     // Массив структур для открытых файлов
static CacheBlock  g_cache[MAX_BLOCKS];    // Массив блоков кэша

// Генератор случайных чисел для Random Replacement
static std::mt19937 g_rng{ static_cast<std::mersenne_twister_engine<unsigned, 32, 624, 397, 31, 2567483615, 11, 4294967295, 7, 2636928640, 15, 4022730752, 18, 1812433253>::result_type>(static_cast<unsigned long>(std::time(nullptr))) };

//--------------------------------------------------
// Вспомогательные функции
//--------------------------------------------------

static void* aligned_alloc_for_disk(size_t alignment, size_t size) {
    // В MSVC используем _aligned_malloc. Не забудьте освободить _aligned_free.
    return _aligned_malloc(size, alignment);
}

static void aligned_free_for_disk(void* ptr) {
    if (ptr) {
        _aligned_free(ptr);
    }
}

// Ищем свободную ячейку в g_files, чтобы получить "fd"
static int allocate_file_slot() {
    for (int i = 0; i < MAX_FILES; i++) {
        if (!g_files[i].inUse) {
            g_files[i].inUse = true;
            return i;
        }
    }
    return -1; // нет места
}

// Ищем свободный блок в кэше
static int find_free_block() {
    for (int i = 0; i < MAX_BLOCKS; i++) {
        if (!g_cache[i].inUse) {
            return i;
        }
    }
    return -1; // кэш заполнен
}

// Случайно выбираем блок для вытеснения
static int select_victim_block() {
    std::uniform_int_distribution<int> dist(0, MAX_BLOCKS - 1);
    return dist(g_rng);
}

// Сбросить (записать на диск) блок, если он "грязный"
static bool write_block_to_disk(int blockIndex) {
    CacheBlock& blk = g_cache[blockIndex];
    if (!blk.isDirty) {
        return true; // нечего сбрасывать
    }

    // Устанавливаем физический указатель файла
    LARGE_INTEGER li;
    li.QuadPart = blk.fileOffset;
    if (!SetFilePointerEx(g_files[blk.fd].hFile, li, NULL, FILE_BEGIN)) {
        return false;
    }

    // Записываем BLOCK_SIZE байт
    DWORD written = 0;
    if (!WriteFile(g_files[blk.fd].hFile, blk.data, BLOCK_SIZE, &written, NULL)) {
        return false;
    }
    if (written != BLOCK_SIZE) {
        return false;
    }

    // Сбрасываем флаг грязности
    blk.isDirty = false;
    return true;
}

// Считываем блок с диска по смещению offset, помещаем его в ячейку blockIndex
static bool read_block_from_disk(int fd, long long offset, int blockIndex) {
    CacheBlock& blk = g_cache[blockIndex];

    // Сдвигаем физический указатель файла
    LARGE_INTEGER li;
    li.QuadPart = offset;
    if (!SetFilePointerEx(g_files[fd].hFile, li, NULL, FILE_BEGIN)) {
        return false;
    }

    // Пытаемся прочитать BLOCK_SIZE
    DWORD bytesRead = 0;
    if (!ReadFile(g_files[fd].hFile, blk.data, BLOCK_SIZE, &bytesRead, NULL)) {
        return false;
    }
    // Если конец файла, дочитываем нулями
    if (bytesRead < BLOCK_SIZE) {
        std::memset(blk.data + bytesRead, 0, BLOCK_SIZE - bytesRead);
    }

    // Инициализация полей
    blk.inUse      = true;
    blk.fd         = fd;
    blk.isDirty    = false;
    blk.fileOffset = offset;
    return true;
}

// Ищем в кэше блок (fd, offset)
static int find_block_in_cache(int fd, long long offset) {
    for (int i = 0; i < MAX_BLOCKS; i++) {
        if (g_cache[i].inUse &&
            g_cache[i].fd == fd &&
            g_cache[i].fileOffset == offset) {
            return i;
        }
    }
    return -1;
}

// Получаем блок из кэша, при необходимости подгружаем с диска (для чтения/записи)
static int get_block_for_offset(int fd, long long offset, bool doReadFromDisk) {
    // Проверяем, есть ли уже нужный блок
    int index = find_block_in_cache(fd, offset);
    if (index >= 0) {
        return index;
    }

    // Ищем свободный блок
    int freeIdx = find_free_block();
    if (freeIdx < 0) {
        // Кэш заполнен, вытесняем случайный
        freeIdx = select_victim_block();
        // Сбрасываем "грязный" блок, если есть
        if (!write_block_to_disk(freeIdx)) {
            return -1;
        }
    }

    // Если действительно нужно загружать данные с диска (например, для чтения или
    // для частичной записи внутри блока), то делаем read_block_from_disk
    // Если вы уверены, что перезаписывается весь блок целиком, можно пропустить чтение,
    // но в общем случае лучше читать.
    if (doReadFromDisk) {
        if (!read_block_from_disk(fd, offset, freeIdx)) {
            return -1;
        }
    } else {
        // Если "не читаем" с диска, сами заполним метаданные
        CacheBlock& blk = g_cache[freeIdx];
        blk.inUse      = true;
        blk.fd         = fd;
        blk.fileOffset = offset;
        blk.isDirty    = false;
        // Обнулим буфер на всякий случай
        std::memset(blk.data, 0, BLOCK_SIZE);
    }

    return freeIdx;
}

//--------------------------------------------------
// Экспортируемые функции (API)
//--------------------------------------------------

extern "C" __declspec(dllexport)
int lab2_open(const char *path) {
    // Ищем свободное место в таблице файлов
    int fd = allocate_file_slot();
    if (fd < 0) {
        // Нет свободных слотов
        return -1;
    }

    // Открываем файл в режиме чтения+записи. Если нужен только read,
    // можно оставить GENERIC_READ.
    HANDLE hFile = CreateFileA(
        path,
        GENERIC_READ | GENERIC_WRITE,
        FILE_SHARE_READ,
        NULL,
        OPEN_EXISTING, // или CREATE_ALWAYS, если нужно создать
        FILE_FLAG_NO_BUFFERING | FILE_FLAG_WRITE_THROUGH,
        NULL
    );

    if (hFile == INVALID_HANDLE_VALUE) {
        g_files[fd].inUse = false;
        return -1;
    }

    // Запоминаем HANDLE
    g_files[fd].hFile   = hFile;
    g_files[fd].filePos = 0;     // Начальная позиция
    return fd;
}

extern "C" __declspec(dllexport)
int lab2_close(int fd) {
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].inUse) {
        return -1;
    }

    // Сбросим все "грязные" блоки, относящиеся к этому fd
    for (int i = 0; i < MAX_BLOCKS; i++) {
        if (g_cache[i].inUse && g_cache[i].fd == fd) {
            if (!write_block_to_disk(i)) {
                // Игнорируем ошибку или обрабатываем
                // Для простоты вернём -1 при ошибке
                return -1;
            }
            g_cache[i].inUse = false;
        }
    }

    // Закрываем HANDLE
    CloseHandle(g_files[fd].hFile);
    g_files[fd].inUse = false;
    return 0;
}

extern "C" __declspec(dllexport)
long long lab2_lseek(int fd, long long offset, int whence) {
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].inUse) {
        return -1;
    }

    long long newPos;
    switch (whence) {
    case SEEK_SET:
        newPos = offset;
        break;
    case SEEK_CUR:
        newPos = g_files[fd].filePos + offset;
        break;
    // Если нужно SEEK_END, можно добавить.
    default:
        return -1;
    }

    if (newPos < 0) {
        return -1;
    }

    g_files[fd].filePos = newPos;
    return newPos;
}

extern "C" __declspec(dllexport)
long long lab2_read(int fd, void *buf, size_t count) {
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].inUse || buf == nullptr) {
        return -1;
    }
    if (count == 0) {
        return 0;
    }

    long long totalRead = 0;

    while (count > 0) {
        long long blockNum   = g_files[fd].filePos / BLOCK_SIZE;
        long long blockOfs   = g_files[fd].filePos % BLOCK_SIZE;
        long long bytesLeft  = BLOCK_SIZE - blockOfs;
        long long toReadNow  = (count < (size_t)bytesLeft) ? count : bytesLeft;

        // Получаем (или загружаем) нужный блок из кэша
        long long offsetInFile = blockNum * BLOCK_SIZE;
        int blockIndex = get_block_for_offset(fd, offsetInFile, true); // true = читаем с диска
        if (blockIndex < 0) {
            // Ошибка чтения
            if (totalRead > 0) {
                return totalRead;
            }
            return -1;
        }

        // Копируем данные из кэша в пользовательский буфер
        CacheBlock &blk = g_cache[blockIndex];
        std::memcpy((char*)buf + totalRead, blk.data + blockOfs, (size_t)toReadNow);

        // Обновляем счётчики
        totalRead            += toReadNow;
        g_files[fd].filePos += toReadNow;
        count               -= toReadNow;
    }

    return totalRead;
}

extern "C" __declspec(dllexport)
long long lab2_write(int fd, const void *buf, size_t count) {
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].inUse || buf == nullptr) {
        return -1;
    }
    if (count == 0) {
        return 0;
    }

    long long totalWritten = 0;

    while (count > 0) {
        long long blockNum   = g_files[fd].filePos / BLOCK_SIZE;
        long long blockOfs   = g_files[fd].filePos % BLOCK_SIZE;
        long long bytesLeft  = BLOCK_SIZE - blockOfs;
        long long toWriteNow = (count < (size_t)bytesLeft) ? count : bytesLeft;

        // Получаем блок в кэше. Надо сначала прочитать с диска, чтобы не затереть
        // часть блока, которую не перезаписываем.
        long long offsetInFile = blockNum * BLOCK_SIZE;
        bool needRead = (toWriteNow < BLOCK_SIZE); // Если перезаписывается весь блок, можно skip.
        int blockIndex = get_block_for_offset(fd, offsetInFile, needRead);
        if (blockIndex < 0) {
            if (totalWritten > 0) {
                return totalWritten;
            }
            return -1;
        }

        // Записываем в буфер кэша
        CacheBlock &blk = g_cache[blockIndex];
        std::memcpy(blk.data + blockOfs, (const char*)buf + totalWritten, (size_t)toWriteNow);
        blk.isDirty = true;

        // Обновляем счётчики
        totalWritten        += toWriteNow;
        g_files[fd].filePos += toWriteNow;
        count              -= toWriteNow;
    }

    return totalWritten;
}

extern "C" __declspec(dllexport)
int lab2_fsync(int fd) {
    if (fd < 0 || fd >= MAX_FILES || !g_files[fd].inUse) {
        return -1;
    }

    // Сбрасываем все грязные блоки для данного fd
    for (int i = 0; i < MAX_BLOCKS; i++) {
        if (g_cache[i].inUse && g_cache[i].fd == fd) {
            if (!write_block_to_disk(i)) {
                return -1;
            }
        }
    }

    return 0;
}

//--------------------------------------------------
// Инициализация кэша при загрузке DLL (необязательно)
//--------------------------------------------------

BOOL APIENTRY DllMain(HMODULE hModule, DWORD ul_reason_for_call, LPVOID lpReserved) {
    switch (ul_reason_for_call) {
    case DLL_PROCESS_ATTACH:
        // Инициализация глобальных структур при загрузке DLL
        std::memset(g_files, 0, sizeof(g_files));
        std::memset(g_cache, 0, sizeof(g_cache));
        // Выделим память под каждый блок (data)
        for (int i = 0; i < MAX_BLOCKS; i++) {
            g_cache[i].data = (char*)aligned_alloc_for_disk(BLOCK_SIZE, BLOCK_SIZE);
            g_cache[i].inUse = false;
        }
        break;
    case DLL_PROCESS_DETACH:
        // Освобождение ресурсов
        for (int i = 0; i < MAX_BLOCKS; i++) {
            if (g_cache[i].data) {
                // Если блок грязный, можно было бы записать, но
                // предполагается, что пользователь закрыл все файлы
                aligned_free_for_disk(g_cache[i].data);
                g_cache[i].data = nullptr;
            }
        }
        break;
    }
    return TRUE;
}
