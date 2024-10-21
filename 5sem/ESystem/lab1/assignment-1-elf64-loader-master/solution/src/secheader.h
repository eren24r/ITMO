typedef struct
{
  Elf64_Word  sh_name;      /* Section name */
  Elf64_Word  sh_type;      /* Section type */
  Elf64_Xword sh_flags;     /* Section attributes */
  Elf64_Addr  sh_addr;      /* Virtual address in memory */
  Elf64_Off   sh_offset;    /* Offset in file */
  Elf64_Xword sh_size;      /* Size of section */
  Elf64_Word  sh_link;      /* Link to other section */
  Elf64_Word  sh_info;      /* Miscellaneous information */
  Elf64_Xword sh_addralign; /* Address alignment boundary */
  Elf64_Xword sh_entsize;   /* Size of entries, if section has table */
} Elf64_Shdr;
