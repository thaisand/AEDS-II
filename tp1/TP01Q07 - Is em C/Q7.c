#include <stdbool.h>
#include <stdio.h>
#include <string.h>

bool isFim(char entrada[]) {
  bool resp = false;
  if (entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M') {
    resp = true;
  }
  return resp;
}

bool isVogais(char string[]) {
  bool resp = true;
  char c = ' ';
  for (int i = 0; i < strlen(string); i++) {
    c = string[i];
    if (!(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' ||
          c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')) {
      resp = false;
    }
  }
  return resp;
}

bool isConsoantes(char string[]) {
  bool resp = true;
  char c = ' ';
  for (int i = 0; i < strlen(string); i++) {
    c = string[i];
    if (!(((c >= 'b' && c <= 'z') &&
           (c != 'e' && c != 'i' && c != 'o' && c != 'u')) ||
          ((c >= 'B' && c <= 'Z') &&
           (c != 'E' && c != 'I' && c != 'O' && c != 'U')))) {
      resp = false;
    }
  }
  return resp;
}

bool isInteiro(char string[]) {
  bool resp = true;
  char c = ' ';
  for (int i = 0; i < strlen(string); i++) {
    c = string[i];
    if (!(c >= '0' && c <= '9')) {
      resp = false;
    }
  }
  return resp;
}

bool isReal(char string[]) {
  bool resp = true;
  char c = ' ';
  for (int i = 0; i < strlen(string); i++) {
    c = string[i];
    if (!(c >= '0' && c <= '9') || (c == '.') || (c == ',')) {
      resp = false;
    }
  }
  return resp;
}

int main(void) {
  char entrada[700];

  scanf("%[^\n]s", entrada);
  getchar();

  while (isFim(entrada) == false) {
    if (isVogais(entrada) == true) {
      printf("SIM ");
    } else {
      printf("NAO ");
    }
    if (isConsoantes(entrada) == true) {
      printf("SIM ");
    } else {
      printf("NAO ");
    }
    if (isInteiro(entrada) == true) {
      printf("SIM ");
    } else {
      printf("NAO ");
    }
    if (isReal(entrada) == true) {
      printf("SIM\n");
    } else {
      printf("NAO\n");
    }
    scanf("%[^\n]s", entrada);
    getchar();
  }
  return 0;
}