typedef struct
{
    Elf64_Word  p_type;   /* Type of segment */
    Elf64_Word  p_flags;  /* Segment attributes */
    Elf64_Off   p_offset; /* Offset in file */
    Elf64_Addr  p_vaddr;  /* Virtual address in memory */
    Elf64_Addr  p_paddr;  /* Reserved */
    Elf64_Xword p_filesz; /* Size of segment in file */
    Elf64_Xword p_memsz;  /* Size of segment in memory */
    Elf64_Xword p_align;  /* Alignment of segment */
} Elf64_Phdr;