#ifndef LAB2_CACHE_H
#define LAB2_CACHE_H

#ifdef __cplusplus
extern "C" {
#endif

#include <stddef.h>  // или #include <cstddef> - чтобы был виден size_t

    __declspec(dllimport)
    int lab2_open(const char* path);

    __declspec(dllimport)
    int lab2_close(int fd);

    __declspec(dllimport)
    long long lab2_lseek(int fd, long long offset, int whence);

    __declspec(dllimport)
    long long lab2_read(int fd, void* buf, size_t count);

    __declspec(dllimport)
    long long lab2_write(int fd, const void* buf, size_t count);

    __declspec(dllimport)
    int lab2_fsync(int fd);

#ifdef __cplusplus
}
#endif

#endif // LAB2_CACHE_H
