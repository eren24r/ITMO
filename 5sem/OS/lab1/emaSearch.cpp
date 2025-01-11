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

// Exponential Moving Average Search for Integer
void ema_search_int(const std::vector<int>& data, int repetitions) {
    for (int r = 0; r < repetitions; ++r) {
        double ema = 0;
        double alpha = 0.1; // Smoothing factor
        for (size_t i = 0; i < data.size(); ++i) {
            ema = alpha * data[i] + (1 - alpha) * ema;
        }

        // Find the closest integer in the data to the calculated EMA
        int closest = data[0];
        double min_diff = std::abs(ema - data[0]);
        for (const auto& value : data) {
            double diff = std::abs(ema - value);
            if (diff < min_diff) {
                min_diff = diff;
                closest = value;
            }
        }

        std::lock_guard<std::mutex> lock(std::mutex);
        std::cout << "EMA: " << ema << ", Closest: " << closest << std::endl;
    }
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cerr << "Usage: " << " <repetitions>" << std::endl;
        return 1;
    }

    std:: string filename = getDefaultFilePath("ema_search_data.dat");
    int repetitions = std::stoi(argv[1]);

    // Read data from file
    std::vector<int> data;
    try {
        data = read_data(filename);
    } catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return 1;
    }

    ema_search_int(data, repetitions);

    return 0;
}
