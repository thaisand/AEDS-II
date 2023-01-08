#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// Metodo que checa se string de entrada e um palindromo 
bool isPalindromo(char string[], int esq, int dir)
{
    bool resp = true;

    // Tamanho da string desconsiderando o '\0' 
    dir--;

    // Percorre a string recebida como parametro, checando se e um palindromo
    if(esq < dir) {
        if (string[esq] != string[dir])
        {
            resp = false;
        }
        else{
            esq++;
            dir--;
            isPalindromo(string, esq, dir);
        }
    
    }
    // Retorna true se for palindromo
    return resp;
}

// Metodo que checa se a entrada chegou ao fim
bool isFim(char string[])
{
    bool resp = false;
    if (strlen(string) == 3 && string[0] == 'F' && string[1] == 'I' && string[2] == 'M')
    {
        resp = true;
    }
    return resp;
}

// Metodo principal
int main(void)
{
    // Inicializa variaveis
    char string[400];
    int contador = 0;

    // Le a primeira entrada 
    scanf("%[^\n]s", string);
    getchar();

    //Checa se chegou ao FIM
    while (isFim(string) == false)
    {   
        // Checa se eh palindromo e printa saida
        if (isPalindromo(string, 0, strlen(string)) == true)
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }
        // Le proxima entrada
        scanf("%[^\n]s", string);
        getchar();
    }

    return 0;
}