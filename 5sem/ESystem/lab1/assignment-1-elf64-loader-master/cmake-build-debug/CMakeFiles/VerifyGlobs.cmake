# CMAKE generated file: DO NOT EDIT!
# Generated by CMake Version 3.29
cmake_policy(SET CMP0009 NEW)

# test_directories at tester/CMakeLists.txt:1 (file)
file(GLOB NEW_GLOB LIST_DIRECTORIES true "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/*")
set(OLD_GLOB
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/.gitignore"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/1"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/2"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/3"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/4"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/5"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/6"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/tester/tests/7"
  )
if(NOT "${NEW_GLOB}" STREQUAL "${OLD_GLOB}")
  message("-- GLOB mismatch!")
  file(TOUCH_NOCREATE "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/cmake-build-debug/CMakeFiles/cmake.verify_globs")
endif()

# sources at solution/CMakeLists.txt:1 (file)
file(GLOB_RECURSE NEW_GLOB LIST_DIRECTORIES false "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/include/*.h")
set(OLD_GLOB
  )
if(NOT "${NEW_GLOB}" STREQUAL "${OLD_GLOB}")
  message("-- GLOB mismatch!")
  file(TOUCH_NOCREATE "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/cmake-build-debug/CMakeFiles/cmake.verify_globs")
endif()

# sources at solution/CMakeLists.txt:1 (file)
file(GLOB_RECURSE NEW_GLOB LIST_DIRECTORIES false "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/*.c")
set(OLD_GLOB
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/m.c"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/main.c"
  )
if(NOT "${NEW_GLOB}" STREQUAL "${OLD_GLOB}")
  message("-- GLOB mismatch!")
  file(TOUCH_NOCREATE "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/cmake-build-debug/CMakeFiles/cmake.verify_globs")
endif()

# sources at solution/CMakeLists.txt:1 (file)
file(GLOB_RECURSE NEW_GLOB LIST_DIRECTORIES false "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/*.h")
set(OLD_GLOB
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/elf.h"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/header.h"
  "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/solution/src/secheader.h"
  )
if(NOT "${NEW_GLOB}" STREQUAL "${OLD_GLOB}")
  message("-- GLOB mismatch!")
  file(TOUCH_NOCREATE "C:/Users/Aorus/Desktop/assignment-1-elf64-loader-master/cmake-build-debug/CMakeFiles/cmake.verify_globs")
endif()