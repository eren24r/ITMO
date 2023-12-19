#include <unistd.h>
#include <stdio.h>
#include <stdint.h>
#include <sys/wait.h>

enum {
    MESSAGE_MAGIC = 0xAFAF, // magic signature value
    MAX_MESSAGE_LEN = 4096  // maximum message length
};
struct __attribute__((packed)) message_header
{
    uint16_t magic;     // magic signature
    uint16_t type;      // type of the message
    uint16_t data_len;  // length of data
}; enum {
    // maximum data length
    MAX_MESSAGE_DATA_LEN = MAX_MESSAGE_LEN - sizeof(struct message_header)
};
struct __attribute__((packed)) message
{
    struct message_header header;
    // payload
    uint8_t data[MAX_MESSAGE_DATA_LEN];
};

int send(int fd, const struct message *msg)
{
    int msg_size;
    /* Check if the input data is not empty */
    if (fd < 0 || msg == NULL)
        return -1;
    /* Calculate the message size to send */
    msg_size = sizeof(struct message_header) + msg->header.data_len;
    /* Check if message payload size is valid */
    if (msg->header.data_len > MAX_MESSAGE_DATA_LEN)
        return -1;
    /* Write data to the output pipe (we assume it is ready) */
    if (write(fd, msg, msg_size) != msg_size)
        return -2;
    return 0;
}

int receive(int fd, struct message *msg)
{
    int msg_size;
    /* Check if the input data is not empty */
    if (fd < 0 || msg == NULL)
        return -1;
    /* Try to read header */
    msg_size = read(fd, &msg->header, sizeof(struct message_header));
    if (msg_size == 0)
        return 0;
    /* Check header magic */
    if (msg->header.magic != MESSAGE_MAGIC)
        return -2;
    /* Check if message has payload */
    if (msg->header.data_len > MAX_MESSAGE_DATA_LEN)
        return -2;
    if (msg->header.data_len > 0)
        msg_size += read(fd, &msg->data, msg->header.data_len);
    /* Return number of bytes read */
    return msg_size;
}

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
        struct message msg = {
                .header = {.magic = MESSAGE_MAGIC, .data_len = 10},
                .data = {42,43,44,45,46,47,48,49,50,51}
        };
        send(to_parent_pipe, &msg);
        // И будем ждать, пока родитель не пришлет 1 байт в ответ
        while (receive(from_parent_pipe, &msg) == 0);
        printf("Recieved from parent: %s\n", msg.data);
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
        struct message msg;
        while (receive(from_child_pipe, &msg) == 0);
        printf("Received from child: %s\n", msg.data);
        // И отправим ответ
        send(to_child_pipe, &msg);
        // Дождемся завершения ребенка
        waitpid(pid, NULL, 0);
        // Закроем дескпиторы
        close(from_child_pipe);
        close(to_child_pipe);
        return 0;
    }
}
