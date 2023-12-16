/* printf.c */

#include <stdio.h>

int main(void) {
  char buffer[1024];
  fgets( buffer, 1024, stdin);
  printf( buffer );
  return 0;
}
