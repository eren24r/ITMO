#include <windows.h>
#include <iostream>
#include <string>
#include <vector>
#include <filesystem>
#include <chrono>

#define BUFFER_SIZE 1024


void execute_ls() {
    try {
        for (const auto& entry : std::filesystem::directory_iterator(std::filesystem::current_path())) {
            std::cout << entry.path().filename().string() << std::endl;
        }
    } catch (const std::exception& e) {
        std::cerr << "Failed to list directory: " << e.what() << std::endl;
    }
}

void execute_pwd() {
    try {
        std::cout << std::filesystem::current_path().string() << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Failed to get current working directory: " << e.what() << std::endl;
    }
}

void execute_cd(const std::string& path) {
    try {
        std::filesystem::current_path(path);
    } catch (const std::exception& e) {
        std::cerr << "Failed to change directory: " << e.what() << std::endl;
    }
}

void execute_command(const std::vector<std::string>& args) {
    if (args.empty()) return;

    // Convert arguments to a single command string
    std::string command;
    for (const auto& arg : args) {
        command += arg + " ";
    }

    // Create a process to execute the command
    STARTUPINFO si = { sizeof(STARTUPINFO) };
    PROCESS_INFORMATION pi;

    std::vector<char> commandBuffer(command.begin(), command.end());
    commandBuffer.push_back('\0');

    auto start = std::chrono::high_resolution_clock::now();

    if (!CreateProcessA(
            NULL,                   // No module name (use command line)
            commandBuffer.data(),   // Command line
            NULL,                   // Process handle not inheritable
            NULL,                   // Thread handle not inheritable
            FALSE,                  // Set handle inheritance to FALSE
            0,                      // No creation flags
            NULL,                   // Use parent's environment block
            NULL,                   // Use parent's starting directory
            &si,                    // Pointer to STARTUPINFO structure
            &pi)                    // Pointer to PROCESS_INFORMATION structure
    ) {
        std::cerr << "Failed to execute command: " << GetLastError() << std::endl;
        return;
    }

    // Wait until child process exits.
    WaitForSingleObject(pi.hProcess, INFINITE);

    auto end = std::chrono::high_resolution_clock::now();
    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();
    std::cout << "Execution time: " << elapsed << " ms" << std::endl;

    // Close process and thread handles.
    CloseHandle(pi.hProcess);
    CloseHandle(pi.hThread);
}

int main() {
    std::string command_line;

    while (true) {
        std::cout << "shell> ";
        if (!std::getline(std::cin, command_line)) {
            std::cout << std::endl;
            break;
        }

        if (command_line.empty()) {
            continue;
        }

        // Split command line into arguments
        std::vector<std::string> args;
        std::string token;
        size_t pos = 0;
        while ((pos = command_line.find(' ')) != std::string::npos) {
            token = command_line.substr(0, pos);
            args.push_back(token);
            command_line.erase(0, pos + 1);
        }
        args.push_back(command_line);

        if (args[0] == "exit") {
            break;
        } else if (args[0] == "ls") {
            execute_ls();
        } else if (args[0] == "pwd") {
            execute_pwd();
        } else if (args[0] == "cd") {
            if (args.size() < 2) {
                std::cerr << "cd: missing argument" << std::endl;
            } else {
                execute_cd(args[1]);
            }
        } else {
            execute_command(args);
        }
    }

    return 0;
}

