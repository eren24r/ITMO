%macro test 3
    db %1
    db %2 
    db %3
    %endmacro

section .data
    test "hello", ",", " world"

section .text
    global _start


_start: