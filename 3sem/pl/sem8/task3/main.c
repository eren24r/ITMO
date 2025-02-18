#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/wait.h>

#define ARR_SIZE 10

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}

void print_array(int* arr, size_t size) {
    for (size_t i = 0; i < size; i++) {
        printf("%d ", arr[i]);
    }
    puts("\n");
}

void change_array_element(size_t index, int new_value, int* arr, size_t size) {
    if (index < size) {
        arr[index] = new_value;
    }
}

int main(void) {
    int nums[] = {1,2,3,4,5,6,7,8,9,10};

    int* shmem = create_shared_memory(sizeof(int) * 10);
    for (size_t i = 0; i < ARR_SIZE; i++) {
        shmem[i] = nums[i];
    }

    sem_t *sem;
    sem = sem_open("/my_semaphore", O_CREAT, 0666, 0);

    int pipe_fd[2];
    if (pipe(pipe_fd) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    printf("Shared memory at: %p\n" , (void*)shmem);
    int pid = fork();
    if (pid == 0) {
        close(pipe_fd[0]);

        while (1) {
            int index;
            int num;
            printf("[child] enter index and new value (or -1 to exit): ");
            scanf("%d %d", &index, &num);

            if (index < 0) {
                close(pipe_fd[1]);
                sem_post(sem);
                sem_close(sem);
                exit(EXIT_SUCCESS);
            }

            change_array_element(index, num, shmem, ARR_SIZE);
            write(pipe_fd[1], shmem, sizeof(int) * ARR_SIZE);

            sem_post(sem);
        }
    } else {
        close(pipe_fd[1]);

        printf("[parent] Child's pid is: %d\n", pid);
        while (1) {
            sem_wait(sem);

            puts("[parent] array before: ");
            print_array(shmem, ARR_SIZE);
            puts("[parent] waiting for child process to send data...");

            ssize_t bytesRead = read(pipe_fd[0], shmem, sizeof(int) * ARR_SIZE);

            if (bytesRead <= 0) {
                waitpid(pid, NULL, 0);
                sem_close(sem);
                sem_unlink("/my_semaphore");
                break;
            }

            puts("[parent] array after: ");
            print_array(shmem, ARR_SIZE);
        }
    }

    return 0;
}
