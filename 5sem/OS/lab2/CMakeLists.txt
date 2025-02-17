cmake_minimum_required(VERSION 3.10)

# Название проекта (произвольное)
project(Lab2CacheExe LANGUAGES CXX)

# Устанавливаем стандарт C++ (при необходимости)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Создаём исполняемый файл из одного .cpp
add_executable(lab2_cache_exe
        lab2_cache_exe.cpp
)

# Если нужны дополнительные библиотеки (иногда для WinAPI):
# target_link_libraries(lab2_cache_exe PRIVATE kernel32 user32)

# Опционально, если хотим оптимизацию по умолчанию:
# set(CMAKE_BUILD_TYPE Release)
