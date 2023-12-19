# CMakeLists.txt
cmake_minimum_required(VERSION 3.10)
project(MyMemoryAllocator C)

set(CMAKE_C_STANDARD 11)

add_library(src/mem.c
        src/util.c
        src/util.h
        src/mem_internals.h)

add_executable(my_program main.c
        src/mem_alloc.h
        src/mem.c
        src/mem.c)
target_link_libraries(my_program mem_alloc)
