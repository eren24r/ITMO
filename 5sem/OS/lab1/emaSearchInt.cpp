#include <iostream>
#include <vector>
#include <cstdlib>

// Функция для поиска целых чисел в массиве
std::vector<int> searchInts(const std::vector<int>& data, int threshold) {
    std::vector<int> results;
    for (int val : data) {
        if (val > threshold) {
            results.push_back(val);
        }
    }
    return results;
}

// Функция для расчёта экспоненциального скользящего среднего
double calculateEMA(const std::vector<int>& data, double alpha) {
    if (data.empty()) return 0.0;
    double ema = data[0];  // Начальное значение EMA
    for (size_t i = 1; i < data.size(); i++) {
        ema = alpha * data[i] + (1 - alpha) * ema;
    }
    return ema;
}

int main(int argc, char* argv[]) {
    int repetitions = (argc > 1) ? atoi(argv[1]) : 10;
    int threshold = 50;  // Пороговое значение для поиска
    double alpha = 0.1;  // Параметр сглаживания для EMA

    // Создаём массив данных
    std::vector<int> data(1000);
    for (int& val : data) {
        val = rand() % 100;  // Заполняем случайными числами от 0 до 99
    }

    // Нагрузка: повторяем поиск и расчет EMA
    for (int i = 0; i < repetitions; i++) {
        std::vector<int> results = searchInts(data, threshold);
        double ema = calculateEMA(results, alpha);
        std::cout << "EMA for repetition " << i + 1 << ": " << ema << std::endl;
    }

    return 0;
}
