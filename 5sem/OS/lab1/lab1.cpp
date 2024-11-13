#include <windows.h>
#include <iostream>
#include <chrono>

void ExecuteCommand(const std::string& command) {
    STARTUPINFO si;
    PROCESS_INFORMATION pi;
    ZeroMemory(&si, sizeof(si));
    si.cb = sizeof(si);
    ZeroMemory(&pi, sizeof(pi));

    // Время начала
    auto start = std::chrono::high_resolution_clock::now();

    // Запуск процесса
    if (CreateProcess(NULL, const_cast<char*>(command.c_str()), NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi)) {
        WaitForSingleObject(pi.hProcess, INFINITE);

        // Время окончания
        auto end = std::chrono::high_resolution_clock::now();
        std::chrono::duration<double> elapsed = end - start;
        std::cout << "Time: " << elapsed.count() << " sec" << std::endl;

        CloseHandle(pi.hProcess);
        CloseHandle(pi.hThread);
    } else {
        std::cerr << "Error proccessing: " << GetLastError() << std::endl;
    }
}

int main() {
    std::string command;
    while (true) {
        std::cout << "Enter command (or 'exit' for exit): ";
        std::getline(std::cin, command);
        if (command == "exit") break;
        ExecuteCommand(command);
    }
    return 0;
}
