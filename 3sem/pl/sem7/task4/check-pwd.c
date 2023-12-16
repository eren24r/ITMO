/* check-pwd.c */
/* pass check by this command: echo -n -e "\x72\x72\x72\x72\x72\x72\x72\x72\x72\x72\x1" | ./check-pwd */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int check_password(FILE *f, const char *password) {
    char buffer[10];
    int res = 0;
    fscanf(f, "%9s", buffer);

    // if (fscanf(f, "%s", buffer) > 10) return 0;

    if (strcmp(buffer, password) == 0){
        res = 1;
    }

  return res;
}

int main(int argc, char **argv) {
  if (check_password(stdin, "password"))
    puts("Access granted.");
  else
    puts("Wrong password.");
}
