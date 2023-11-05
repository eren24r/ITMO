ASM = nasm 
ASMFLAG = -f elf64
LD = ld
PY = python3
	
%.o: %.asm
	${ASM} ${ASMFLAG} -o $@ $^

program: hello_mmap.o
	$(LD) -o $@ $^


clean:
	rm -f *.o program

sum: clean program test

.PHONY: test clean
