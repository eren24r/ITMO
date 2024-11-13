#include <iostream>
#include <fstream>
#include <cstdlib>

int main(int argc, char* argv[]) {
    int repetitions = (argc > 1) ? atoi(argv[1]) : 10;
    std::ofstream file("disk_load_test.txt");

    for (int i = 0; i < repetitions; i++) {
        file << "Writing data for load on disk" << std::endl;
    }
    file.close();
    std::cout << "Writing is finished!" << std::endl;
    return 0;
}

/*Без оптимизации: g++ -O0 -o factorize newProcessForFact.cpp
С оптимизацией: g++ -O3 -o factorize factorize.cpp*/