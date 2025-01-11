#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <mutex>
#include <string>
#include <stdexcept>
#include <windows.h>

std::string getExecutableDir() {
    char buffer[MAX_PATH];
    GetModuleFileName(NULL, buffer, MAX_PATH);
    std::string::size_type pos = std::string(buffer).find_last_of("\\/");
    return std::string(buffer).substr(0, pos);
}

std::string getDefaultFilePath(const char* defaultFileName) {
    std::string execDir = getExecutableDir();
    return execDir + "\\" + defaultFileName;
}

// Function to read integers from a .dat file
std::vector<int> read_data(const std::string& filename) {
    std::vector<int> data;
    std::ifstream file(filename);
    if (!file.is_open()) {
        throw std::runtime_error("Failed to open file: " + filename);
    }

    int value;
    while (file >> value) {
        data.push_back(value);
    }
    file.close();
    return data;
}

// Factorization function
void factorize(const std::vector<int>& data, int repetitions) {
    for (int r = 0; r < repetitions; ++r) {
        for (const auto& number : data) {
            int n = number;
            std::vector<int> factors;
            for (int i = 2; i <= std::sqrt(n); ++i) {
                while (n % i == 0) {
                    factors.push_back(i);
                    n /= i;
                }
            }
            if (n > 1) {
                factors.push_back(n);
            }

            std::lock_guard<std::mutex> lock(std::mutex);
            std::cout << "Number: " << number << ", Factors: ";
            for (const auto& factor : factors) {
                std::cout << factor << " ";
            }
            std::cout << std::endl;
        }
    }
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cerr << "Usage: " << " <repetitions>" << std::endl;
        return 1;
    }

    std::string filename = getDefaultFilePath("factorize_data.dat");
    int repetitions = std::stoi(argv[1]);

    // Read data from file
    std::vector<int> data;
    try {
        data = read_data(filename);
    } catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return 1;
    }

    factorize(data, repetitions);

    return 0;
}
