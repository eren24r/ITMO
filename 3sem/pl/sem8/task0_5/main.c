//
// Created by eren on 12/19/23.
//

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

pthread_mutex_t m1;

void bad_print(char *s)
{
    for (; *s != '\0'; s++)
    {
        fprintf(stdout, "%c", *s);
        fflush(stdout);
        usleep(100);
    }
}

void* my_thread(void* arg)
{
    for(int i = 0; i < 10; i++ )
    {
        pthread_mutex_lock(&m1);
        bad_print((char *)arg);
        pthread_mutex_unlock(&m1);
        sleep(1);
    }
    return NULL;
}
int main(void) {
    pthread_t t1, t2;
    pthread_mutex_init(&m1, NULL);
    pthread_create(&t1, NULL, my_thread, "girls sometimes\n");
    pthread_create(&t2, NULL, my_thread, "may be beautiful...\n");
    pthread_mutex_destroy(&m1);
    pthread_exit( NULL );
}
