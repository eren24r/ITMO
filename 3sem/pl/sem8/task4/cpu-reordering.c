#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <inttypes.h>
#include <stdlib.h>

sem_t sem_begin0, sem_begin1, sem_end;

int x, y, read0, read1;

void *thread0_impl( void *param )
{
  for (;;) {
    sem_wait( &sem_begin0 );

      __asm__ __volatile__ ("" ::: "memory");
    x = 1;
      __asm__ __volatile__ ("" ::: "memory");
    read0 = y;
      __asm__ __volatile__ ("" ::: "memory");

    sem_post( &sem_end );
  }
  return NULL;
}

void *thread1_impl( void *param )
{
  for (;;) {
    sem_wait( &sem_begin1 );

      __asm__ __volatile__ ("" ::: "memory");
    y = 1;
      __asm__ __volatile__ ("" ::: "memory");
    read1 = x;
      __asm__ __volatile__ ("" ::: "memory");

      sem_post( &sem_end );
  }
  return NULL;
}

int main( void ) {
  sem_init( &sem_begin0, 0, 0);
  sem_init( &sem_begin1, 0, 0);
  sem_init( &sem_end, 0, 0);

  pthread_t thread0, thread1;
  pthread_create( &thread0, NULL, thread0_impl, NULL);
  pthread_create( &thread1, NULL, thread1_impl, NULL);

  for (uint64_t i = 0; i < 1000000; i++)
  {
    x = 0;
    y = 0;
    sem_post( &sem_begin0 );
    sem_post( &sem_begin1 );

    sem_wait( &sem_end );
    sem_wait( &sem_end );

    if (read0 == 0 && read1 == 0 ) {
      printf( "reordering happened on iteration %" PRIu64 "\n", i );
      exit(0);
    }
  }
  puts("No reordering detected during 1000000 iterations");
  return 0;
}
