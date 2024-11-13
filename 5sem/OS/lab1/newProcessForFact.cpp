#include <iostream>
#include <vector>
#include <cstdlib>

std::vector<int> factorize(int n) {
    std::vector<int> factors;
    for (int i = 2; i * i <= n; i++) {
        while (n % i == 0) {
            factors.push_back(i);
            n /= i;
        }
    }
    if (n > 1) factors.push_back(n);
    return factors;
}

int main(int argc, char* argv[]) {
    int repetitions = (argc > 1) ? atoi(argv[1]) : 10;
    for (int i = 0; i < repetitions; i++) {
        auto factors = factorize(98765431); // произвольное большое число для нагрузки
    }
    std::cout << "Factorise is finished!" << std::endl;
    return 0;
}

/*Без оптимизации: g++ -O0 -o factorize factorize.cpp
С оптимизацией: g++ -O3 -o factorize factorize.cpp*/