#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

void* my_thread(void* arg) {
    for (size_t i = 0; i < 10; i++) {
        printf("%s", (char*) arg);
        sleep(1);
    }
    return NULL;
}

int main(void) {
    pthread_t t1, t2;
    pthread_create(&t1, NULL, my_thread, "girls sometimes\n");
    pthread_create(&t2, NULL, my_thread, "may be beautiful...\n");
    pthread_exit(NULL);
    return 0;
}
