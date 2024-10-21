#include <elf.h>
#include <errno.h>
#include <fcntl.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <sys/mman.h>
#include <unistd.h>

#include "header.h"
#include "elf.h"
#include "secheader.h"


#define SUCCESS 0
#define EINVAL 22
#define ENOENT 2
#define EIO 5
#define SEGMENT_SIZE 4096


int map_program_headers_segments(const int64_t num_segments, const Elf64_Off offset, const int file_desc) {
    if (lseek(file_desc, (off_t) offset, SEEK_SET) == -1) {
        close(file_desc);
        return EIO;
    }

    Elf64_Phdr program_headers[num_segments];
    if (read(file_desc, program_headers, sizeof(Elf64_Phdr) * num_segments) != sizeof(Elf64_Phdr) * num_segments) {
        close(file_desc);
        return EIO;
    }

    for (int64_t i = 0; i < num_segments; i++) {
        if (program_headers[i].p_type == PT_LOAD) {
            Elf64_Xword memory_size = program_headers[i].p_memsz;
            Elf64_Addr virtual_addr = program_headers[i].p_vaddr;
            if (virtual_addr % SEGMENT_SIZE != 0) {
                memory_size += (virtual_addr % SEGMENT_SIZE);
                virtual_addr -= (virtual_addr % SEGMENT_SIZE);
            }

            if (mmap((void *) virtual_addr, memory_size, PROT_READ | PROT_WRITE | PROT_EXEC, MAP_FIXED | MAP_PRIVATE | MAP_ANONYMOUS, -1, 0) == MAP_FAILED) { //NOLINT
                return EIO;
            }

            if (lseek(file_desc, (off_t) program_headers[i].p_offset, SEEK_SET) == -1) {
                return EIO;
            }

            size_t seg_file_size = program_headers[i].p_filesz;
            if (read(file_desc, (void *) program_headers[i].p_vaddr, seg_file_size) != seg_file_size) { //NOLINT
                return EIO;
            }

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

            if (mprotect((void *) virtual_addr, memory_size, protection) == -1) { //NOLINT
                return EIO;
            }
        }
    }
    return SUCCESS;
}


int open_file(const char *filename) {
    int file_desc = open(filename, O_RDONLY);
    return file_desc;
}

int map_section_header(int fd, Elf64_Shdr* shdx_hdr, char** shstrtab) {
    off_t upper = (off_t)(shdx_hdr->sh_offset & ~(off_t)(SEGMENT_SIZE - 1));
    size_t lower = shdx_hdr->sh_offset - upper;
    char *shstrtab_data = mmap(NULL, shdx_hdr->sh_size + lower, PROT_READ, MAP_PRIVATE, fd, upper);

    if (shstrtab_data == MAP_FAILED) {
        return EIO;
    }

    *shstrtab = shstrtab_data + lower;

    return SUCCESS;
}


int load_elf64(const char *filename, const char *section_name) {
    int file_desc = open_file(filename);
    if (file_desc < 0) {
        return ENOENT;
    }

    Elf64_Ehdr file_header;
    if (read(file_desc, &file_header, sizeof(Elf64_Ehdr)) != sizeof(Elf64_Ehdr)) {
        close(file_desc);
        return EIO;
    }

    if (file_header.e_ident[0] != ELFMAG0 ||
        file_header.e_ident[1] != ELFMAG1 ||
        file_header.e_ident[2] != ELFMAG2 ||
        file_header.e_ident[3] != ELFMAG3) {
        close(file_desc);
        return EIO;
    }


    int res = map_program_headers_segments(file_header.e_phnum,file_header.e_phoff, file_desc);
    if (res != SUCCESS) {
        close(file_desc);
        return res;
    }

    if (lseek(file_desc, (off_t) file_header.e_shoff, SEEK_SET) == -1) {
        close(file_desc);
        return EIO;
    }

    Elf64_Shdr section_header[file_header.e_shnum];
    if (read(file_desc, section_header, sizeof(Elf64_Shdr) * file_header.e_shnum) != sizeof(Elf64_Shdr) * file_header.e_shnum) {
        close(file_desc);
        return EIO;
    }

    Elf64_Shdr* shdx_hdr = &section_header[file_header.e_shstrndx];
    char* shstrtab = NULL;

    int status_code = map_section_header(file_desc, shdx_hdr, &shstrtab);
    if (status_code != SUCCESS) {
        close(file_desc);
        return status_code;
    }

    Elf64_Shdr find_s;
    int flag = 0;
    for (uint64_t i = 0; i < file_header.e_shnum; i++) {
        if (flag == 0 && strcmp(shstrtab + section_header[i].sh_name, section_name) == 0) {
            find_s = section_header[i];
            flag = 1;
        }
    }

    if (flag == 0 || !(find_s.sh_flags & SHF_EXECINSTR)) {
        close(file_desc);
        return EINVAL;
    }

    void (*start_program)(void) = (void (*)(void))find_s.sh_addr; //NOLINT
    if (start_program != NULL) {
        start_program();
    } else {
        return EINVAL;
    }

    close(file_desc);
    return SUCCESS;
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        return EINVAL;
    }

    const char *elf_file = argv[1];
    const char *section_name = argv[2];
    if (elf_file == NULL || section_name == NULL) {
        return EINVAL;
    }

    int result = load_elf64(elf_file, section_name);

    if (result != SUCCESS) {
        return result;
    }

    return SUCCESS;
}
