#include <iostream>
#include <cstring>
#include <chrono>
#include <cstdint>
#include "lab2_cache.h"

using namespace std::chrono;

/*int main(int argc, char* argv[])
{
    if (argc < 2) {
        std::cerr << "Usage: " << argv[0] << " <filename>\n";
        return 1;
    }
    const char* path = argv[1];

    auto totalStart = high_resolution_clock::now();

    int fd = lab2_open(path);
    if (fd < 0) {
        std::cerr << "Error: lab2_open(" << path << ") failed.\n";
        return 1;
    }
    std::cout << "File opened successfully (fd=" << fd << ")\n";

    {
        char buffer[1024];
        std::memset(buffer, 0, sizeof(buffer));

        long long bytesRead = lab2_read(fd, buffer, 1024);
        if (bytesRead < 0) {
            std::cerr << "Error: lab2_read() failed.\n";
            lab2_close(fd);
            return 1;
        }
        std::cout << "Read " << bytesRead << " bytes: \"" << buffer << "\"\n";
    }

    long long newPos = lab2_lseek(fd, 0, SEEK_SET);
    if (newPos < 0) {
        std::cerr << "Error: lab2_lseek() failed.\n";
        lab2_close(fd);
        return 1;
    }
    std::cout << "lseek -> newPos = " << newPos << "\n";

    // 4. Запишем новую строку
    {
        const char* text = "Hello from Lab2 Cache!\n";
        size_t textLen = std::strlen(text);

        long long bytesWritten = lab2_write(fd, text, textLen);
        if (bytesWritten < 0) {
            std::cerr << "Error: lab2_write() failed.\n";
            lab2_close(fd);
            return 1;
        }
        std::cout << "Wrote " << bytesWritten << " bytes.\n";
    }

    if (lab2_fsync(fd) != 0) {
        std::cerr << "Error: lab2_fsync() failed.\n";
        lab2_close(fd);
        return 1;
    }
    std::cout << "Data fsync'ed to disk.\n";

    if (lab2_close(fd) != 0) {
        std::cerr << "Error: lab2_close() failed.\n";
        return 1;
    }
    std::cout << "File closed successfully.\n";

    auto totalEnd = high_resolution_clock::now();
    auto totalDuration = duration_cast<microseconds>(totalEnd - totalStart);

    double totalDurationMs = totalDuration.count() / 1000.0;
    std:: cout << "\nTotal time " << totalDurationMs << " ms" << std:: endl;

    std::cout << "Done.\n";


    return 0;
}*/
