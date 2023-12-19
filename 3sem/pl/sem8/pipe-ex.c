#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
int main(void) {
    // Создадим два конвейера
    int pipes[2][2];
    pipe(pipes[0]);
    pipe(pipes[1]);
    // Создадим дочерний процесс
    pid_t pid = fork();
    if (pid == 0) {
        // Сохраним нужные дескпиторы конвейеров
        int to_parent_pipe = pipes[1][1];
        int from_parent_pipe = pipes[0][0];
        // И закроем ненужные
        close(pipes[1][0]);
        close(pipes[0][1]);
        // Отпавим один байт родителю
        char c = 'A';
        write(to_parent_pipe, &c, 1);
        // И будем ждать, пока родитель не пришлет 1 байт в ответ
        while (read(from_parent_pipe, &c, 1) == 0)
            ;
        printf("Recieved from parent: %c\n", c);
        // Закроем дескпиторы
        close(to_parent_pipe);
        close(from_parent_pipe);
        return 0;
    }
    else {
        // Сохраним нужные дескпиторы конвейеров
        int from_child_pipe = pipes[1][0];
        int to_child_pipe = pipes[0][1];
        // И закроем ненужные
        close(pipes[1][1]);
        close(pipes[0][0]);
        // Будем ждать, пока ребенок не пришлет 1 байт
        char c;
        while (read(from_child_pipe, &c, 1) == 0)
            ;
        printf("Recieved from child: %c\n", c);
        // И отправим ответ
        c++;
        write(to_child_pipe, &c, 1);
        // Дождемся завершения ребенка
        waitpid(pid, NULL, 0);
        // Закроем дескпиторы
        close(from_child_pipe);
        close(to_child_pipe);
        return 0;
    }
}
