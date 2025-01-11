#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <mutex>
#include <chrono>
#include <cmath>
#include <cstdlib>
#include <atomic>
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


std::mutex print_mutex;
std::atomic<int> completed_tasks{0};

// Exponential Moving Average Task
void ema_task(const std::vector<double>& data, int repetitions) {
    for (int r = 0; r < repetitions; ++r) {
        double alpha = 0.1;
        double ema = data[0];
        for (size_t i = 1; i < data.size(); ++i) {
            ema = alpha * data[i] + (1 - alpha) * ema;
        }
    }
    completed_tasks++;
}

// Factorization Task
void factorize_task(const std::vector<int>& data, int repetitions) {
    for (int r = 0; r < repetitions; ++r) {
        for (int num : data) {
            for (int i = 2; i <= std::sqrt(num); ++i) {
                while (num % i == 0) {
                    num /= i;
                }
            }
        }
    }
    completed_tasks++;
}

// Load data from a file
std::vector<double> load_ema_data(const std::string& file_path) {
    std::ifstream file(file_path);
    std::vector<double> data;
    double value;
    while (file >> value) {
        data.push_back(value);
    }
    return data;
}

std::vector<int> load_fact_data(const std::string& file_path) {
    std::ifstream file(file_path);
    std::vector<int> data;
    int value;
    while (file >> value) {
        data.push_back(value);
    }
    return data;
}

void benchmark(const std::string& double_file, const std::string& int_file, int num_threads, int repetitions) {
    auto double_data = load_ema_data(double_file);
    auto int_data = load_fact_data(int_file);

    std::vector<std::thread> threads;
    auto start_time = std::chrono::high_resolution_clock::now();

    for (int i = 0; i < num_threads; ++i) {
        if (i % 2 == 0) {
            threads.emplace_back(ema_task, std::cref(double_data), repetitions);
        } else {
            threads.emplace_back(factorize_task, std::cref(int_data), repetitions);
        }
    }

    for (auto& t : threads) {
        t.join();
    }

    auto end_time = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> elapsed = end_time - start_time;

    std::lock_guard<std::mutex> lock(print_mutex);
    std::cout << "All tasks completed." << std::endl;
    std::cout << "Total tasks completed: " << completed_tasks << std::endl;
    std::cout << "Elapsed time: " << elapsed.count() << " seconds" << std::endl;
}

int main(int argc, char* argv[]) {
    if (argc != 3) {
        std::cerr << "Usage: " << "  <num_threads> <repetitions>" << std::endl;
        return 1;
    }

    std::string double_file = getDefaultFilePath("ema_search_data.dat");
    std::string int_file = getDefaultFilePath("factorize_data.dat");
    int num_threads = std::stoi(argv[1]);
    int repetitions = std::stoi(argv[2]);

    benchmark(double_file, int_file, num_threads, repetitions);

    return 0;
}
