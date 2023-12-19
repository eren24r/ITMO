
#include <unistd.h>
#include <stdio.h>
int main(void) {
    pid_t pid = fork();
    if (pid == 0) {
        printf("I am a child, pid was %d\n", pid);
        printf("Child's pid is %d\n", getpid());
    }
    else {
        printf("I am a parent, pid was %d\n", pid);
        printf("Parent's pid is %d\n", getpid());
    }
}
