#include <elf.h>      // Для работы с форматами ELF-файлов
#include <errno.h>    // Определение ошибок, таких как EINVAL, EIO и т.д.
#include <fcntl.h>    // Операции открытия файлов
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <sys/mman.h> // Для работы с отображением файлов в память (mmap)
#include <unistd.h>   // Для системных вызовов, таких как read, lseek, close
#include <header.h>
#include <secheader.h>

// Определение констант для удобства использования
#define SUCCESS 0
#define EINVAL 22
#define ENOENT 2
#define EIO 5
#define SEGMENT_SIZE 4096  // Размер сегмента (обычно размер страницы памяти)

// Функция отображения (mmap) сегментов программы, указанных в заголовках программы ELF-файла
int map_program_headers_segments(const int64_t num_segments, const Elf64_Off offset, const int file_desc) {
    // Устанавливаем смещение на начало заголовков программы
    if (lseek(file_desc, (off_t) offset, SEEK_SET) == -1) {
        close(file_desc);
        return EIO;
    }

    // Чтение всех заголовков программы
    Elf64_Phdr program_headers[num_segments];
    if (read(file_desc, program_headers, sizeof(Elf64_Phdr) * num_segments) != sizeof(Elf64_Phdr) * num_segments) {
        close(file_desc);
        return EIO;
    }

    // Проход по каждому заголовку программы
    for (int64_t i = 0; i < num_segments; i++) {
        if (program_headers[i].p_type == PT_LOAD) { // Проверка, что сегмент предназначен для загрузки
            // Размер сегмента в памяти
            Elf64_Xword memory_size = program_headers[i].p_memsz;
            // Виртуальный адрес начала сегмента
            Elf64_Addr virtual_addr = program_headers[i].p_vaddr;

            // Выравнивание виртуального адреса до границы сегмента (страницы памяти)
            if (virtual_addr % SEGMENT_SIZE != 0) {
                memory_size += (virtual_addr % SEGMENT_SIZE);
                virtual_addr -= (virtual_addr % SEGMENT_SIZE);
            }

            // Отображение сегмента в память (mmap)
            if (mmap((void *) virtual_addr, memory_size, PROT_READ | PROT_WRITE | PROT_EXEC, MAP_FIXED | MAP_PRIVATE | MAP_ANONYMOUS, -1, 0) == MAP_FAILED) { //NOLINT
                return EIO;
            }

            // Устанавливаем смещение на начало сегмента в файле
            if (lseek(file_desc, (off_t) program_headers[i].p_offset, SEEK_SET) == -1) {
                return EIO;
            }

            // Чтение данных сегмента из файла
            size_t seg_file_size = program_headers[i].p_filesz;
            if (read(file_desc, (void *) program_headers[i].p_vaddr, seg_file_size) != seg_file_size) { //NOLINT
                return EIO;
            }

            // Устанавливаем права доступа для сегмента в памяти
            int protection = 0;
            Elf64_Word flags = program_headers[i].p_flags;
            if ((flags & PF_R) == PF_R) {
                protection |= PROT_READ;
            }
            if ((flags & PF_W) == PF_W) {
                protection |= PROT_WRITE;
            }
            if ((flags & PF_X) == PF_X) {
                protection |= PROT_EXEC;
            }

            // Применение прав доступа для сегмента
            if (mprotect((void *) virtual_addr, memory_size, protection) == -1) { //NOLINT
                return EIO;
            }
        }
    }
    return SUCCESS;
}

// Функция для открытия файла по указанному пути
int open_file(const char *filename) {
    int file_desc = open(filename, O_RDONLY); // Открытие файла только для чтения
    return file_desc;
}

// Функция для отображения таблицы строк заголовков секций
int map_section_header(int fd, Elf64_Shdr* shdx_hdr, char** shstrtab) {
    // Вычисляем выравненные границы для отображения секции
    off_t upper = (off_t)(shdx_hdr->sh_offset & ~(off_t)(SEGMENT_SIZE - 1));
    size_t lower = shdx_hdr->sh_offset - upper;

    // Отображаем секцию строк заголовков в память
    char *shstrtab_data = mmap(NULL, shdx_hdr->sh_size + lower, PROT_READ, MAP_PRIVATE, fd, upper);
    if (shstrtab_data == MAP_FAILED) {
        return EIO;
    }

    // Устанавливаем указатель на начало строки в таблице
    *shstrtab = shstrtab_data + lower;

    return SUCCESS;
}

// Основная функция загрузки и выполнения ELF-файла
int load_elf64(const char *filename, const char *section_name) {
    // Открытие ELF файла
    int file_desc = open_file(filename);
    if (file_desc < 0) {
        return ENOENT;
    }

    // Чтение заголовка ELF файла
    Elf64_Ehdr file_header;
    if (read(file_desc, &file_header, sizeof(Elf64_Ehdr)) != sizeof(Elf64_Ehdr)) {
        close(file_desc);
        return EIO;
    }

    // Проверка сигнатуры ELF файла
    if (file_header.e_ident[0] != ELFMAG0 ||
        file_header.e_ident[1] != ELFMAG1 ||
        file_header.e_ident[2] != ELFMAG2 ||
        file_header.e_ident[3] != ELFMAG3) {
        close(file_desc);
        return EIO;
    }

    // Отображение сегментов программы
    int res = map_program_headers_segments(file_header.e_phnum, file_header.e_phoff, file_desc);
    if (res != SUCCESS) {
        close(file_desc);
        return res;
    }

    // Устанавливаем смещение на заголовки секций
    if (lseek(file_desc, (off_t) file_header.e_shoff, SEEK_SET) == -1) {
        close(file_desc);
        return EIO;
    }

    // Чтение заголовков секций
    Elf64_Shdr section_header[file_header.e_shnum];
    if (read(file_desc, section_header, sizeof(Elf64_Shdr) * file_header.e_shnum) != sizeof(Elf64_Shdr) * file_header.e_shnum) {
        close(file_desc);
        return EIO;
    }

    // Получаем строковую таблицу для имен секций
    Elf64_Shdr* shdx_hdr = &section_header[file_header.e_shstrndx];
    char* shstrtab = NULL;
    int status_code = map_section_header(file_desc, shdx_hdr, &shstrtab);
    if (status_code != SUCCESS) {
        close(file_desc);
        return status_code;
    }

    // Поиск нужной секции по имени
    Elf64_Shdr find_s;
    int flag = 0;
    for (uint64_t i = 0; i < file_header.e_shnum; i++) {
        if (flag == 0 && strcmp(shstrtab + section_header[i].sh_name, section_name) == 0) {
            find_s = section_header[i];
            flag = 1;
        }
    }

    // Если секция не найдена или она не исполняемая
    if (flag == 0 || !(find_s.sh_flags & SHF_EXECINSTR)) {
        close(file_desc);
        return EINVAL;
    }

    // Выполнение кода в секции
    void (*start_program)(void) = (void (*)(void))find_s.sh_addr; //NOLINT
    if (start_program != NULL) {
        start_program();
    } else {
        return EINVAL;
    }

    close(file_desc);
    return SUCCESS;
}

// Основная функция программы
int main(int argc, char *argv[]) {
    if (argc != 3) { // Проверка количества аргументов
        return EINVAL;
    }

    // Получаем имена ELF файла и секции из аргументов
    const char *elf_file = argv[1];
    const char *section_name = argv[2];
    if (elf_file == NULL || section_name == NULL) {
        return EINVAL;
    }

    // Загружаем и выполняем указанную секцию ELF файла
    int result = load_elf64(elf_file, section_name);

    if (result != SUCCESS) {
        return result;
    }

    return SUCCESS;
}
