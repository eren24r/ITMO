file(GLOB_RECURSE sources CONFIGURE_DEPENDS
        src/*.c
        include/*.h
)

add_executable(image-transformer ${sources})
add_executable(image-transformer ${sources}
        include/status.h
        include/image.h
        src/bmp.c
        src/image.c)
target_include_directories(image-transformer PRIVATE src include)
