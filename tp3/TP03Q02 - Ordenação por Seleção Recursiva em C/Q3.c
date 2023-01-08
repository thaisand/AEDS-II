#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TAM 100

typedef struct
{
  int year, month;
  char mes[4];
} Date;

typedef struct
{
  char name[TAM];
  char owners[TAM];
  char website[TAM];
  char developers[TAM];
  char languages[TAM][TAM];
  int numLanguages;
  char genres[TAM][TAM];
  int numGenres;
  Date releaseDate;
  int appId;
  int age;
  int dlcs;
  int upvotes;
  int avgPlaytime;
  float price;
  bool windows, mac, llinux;
} Game;

// Variaveis
Game games[TAM];
int x = 0;
Game lista[TAM];
int contador = 0;

// Funcoes
bool isFim(char *s)
{
  return s[0] == 'F' && s[1] == 'I' && s[2] == 'M';
}

void substring(char *string, char *string_start, int length)
{
  strncpy(string, string_start, length);
  string[length] = '\0';
}

char *getMonthName(int month)
{
 

  switch (month)
  {

  case 1:
    char* resp =  "Jan";
    return resp;
    break;
  case 2:
    return "Feb";
    break;
  case 3:
    return "Mar";
    break;
  case 4:
    return "Apr";
    break;
  case 5:
    return "May";
    break;
  case 6:
    return "Jun";
    break;
  case 7:
    return "Jul";
    break;
  case 8:
    return "Aug";
    break;
  case 9:
    return "Sep";
    break;
  case 10:
    return "Oct";
    break;
  case 11:
    return "Nov";
    break;
  case 12:
    return "Dec";
    break;

  default:
    return "N/A";
    break;
  }
}

int getMonthNumber(char *month)
{

  if (!strcmp(month, "Jan"))
    return 1;
  else if (!strcmp(month, "Feb"))
    return 2;
  else if (!strcmp(month, "Mar"))
    return 3;
  else if (!strcmp(month, "Apr"))
    return 4;
  else if (!strcmp(month, "May"))
    return 5;
  else if (!strcmp(month, "Jun"))
    return 6;
  else if (!strcmp(month, "Jul"))
    return 7;
  else if (!strcmp(month, "Aug"))
    return 8;
  else if (!strcmp(month, "Sep"))
    return 9;
  else if (!strcmp(month, "Oct"))
    return 10;
  else if (!strcmp(month, "Nov"))
    return 11;
  else if (!strcmp(month, "Dec"))
    return 12;
}

void ler(Game *game, char *line)
{

  char busca, sub[TAM];
  int index = 0, atr_index = 0;

  // ------------------------------------------------------------ //

  // Find "AppID"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      game->appId = atoi(sub);

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "Name"
  if (line[atr_index] != ',')
  {

    if (line[atr_index] == '\"')
    {

      atr_index++;
      busca = '\"';
    }
    else
      busca = ',';

    while (true)
    {

      index++;

      if (line[index] == busca)
      {

        substring(sub, &line[atr_index], index - atr_index);
        strcpy(game->name, sub);

        if (busca == ',')
          index++;
        else if (busca == '\"')
          index += 2;

        atr_index = index;
        break;
      }
    }
  }
  else
  {

    strcpy(game->name, "null");

    atr_index = ++index;
  }

  // ------------------------------------------------------------ //

  // Find release date
  if (line[atr_index] != ',')
  {

    if (line[atr_index] == '\"')
    {

      atr_index++;
      busca = '\"';
    }
    else
      busca = ',';

    while (true)
    {

      index++;

      if (line[index] == busca)
      {

        substring(sub, &line[atr_index], index - atr_index);

        char subDate[10];

        substring(subDate, &sub[0], 3);

        game->releaseDate.month = getMonthNumber(subDate);

        if (busca == ',')
        {

          substring(subDate, &sub[4], 4);
          game->releaseDate.year = atoi(subDate);

          index++;
        }
        else if (busca == '\"')
        {

          int nmbSpace = 0;

          for (int i = 0;; i++)
          {

            if (sub[i] == ' ')
              nmbSpace++;

            if (nmbSpace == 2)
            {

              i++;

              substring(subDate, &sub[i], 4);

              game->releaseDate.year = atoi(subDate);
              break;
            }
          }

          index += 2;
        }

        atr_index = index;
        break;
      }
    }
  }
  else
  {

    game->releaseDate.month = 0;
    game->releaseDate.year = 0;

    atr_index = ++index;
  }

  // ------------------------------------------------------------ //

  // Find "Owners"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);
      strcpy(game->owners, sub);

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "Age"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      game->age = atoi(sub);

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "Price"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      game->price = atof(sub);

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "DLCs"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      game->dlcs = atoi(sub);

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "Languages"
  while (true)
  {

    index++;

    if (line[index] == ']')
    {

      index++;

      if (line[index] == ',')
        index++;
      else if (line[index] == '\"')
        index += 2;

      atr_index = index;
      break;
    }
    else if (line[index] == '\'')
    {

      int wordStart = index + 1;

      while (true)
      {

        index++;

        if (line[index] == '\'')
        {

          substring(sub, &line[wordStart], index - wordStart);
          strcpy(game->languages[game->numLanguages++], sub);
          break;
        }
      }
    }
  }

  // ------------------------------------------------------------ //

  // Find "Website"
  if (line[atr_index] != ',')
  {

    if (line[atr_index] == '\"')
    {

      atr_index++;
      busca = '\"';
    }
    else
      busca = ',';

    while (true)
    {

      index++;

      if (line[index] == busca)
      {

        substring(sub, &line[atr_index], index - atr_index);
        strcpy(game->website, sub);

        atr_index = ++index;
        break;
      }
    }
  }
  else
  {

    strcpy(game->website, "null");

    atr_index = ++index;
  }

  // ------------------------------------------------------------ //

  // Find "Windows"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      if (!strcmp(sub, "True"))
        game->windows = true;

      atr_index = ++index;
      break;
    }
  }

  // Find "Mac"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      if (!strcmp(sub, "True"))
        game->mac = true;

      atr_index = ++index;
      break;
    }
  }

  // Find "Linux"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      if (!strcmp(sub, "True"))
        game->llinux = true;

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "Upvotes"
  int positives, negatives;

  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      positives = atoi(sub);
      atr_index = ++index;
      break;
    }
  }

  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      negatives = atoi(sub);
      atr_index = ++index;
      break;
    }
  }

  game->upvotes = (float)(positives * 100) / (float)(positives + negatives);

  // ------------------------------------------------------------ //

  // Find "AVG Playtime"
  while (true)
  {

    index++;

    if (line[index] == ',')
    {

      substring(sub, &line[atr_index], index - atr_index);

      game->avgPlaytime = atoi(sub);

      atr_index = ++index;
      break;
    }
  }

  // ------------------------------------------------------------ //

  // Find "Developers"
  if (line[atr_index] != ',')
  {

    if (line[atr_index] == '\"')
    {

      atr_index++;
      busca = '\"';
    }
    else
      busca = ',';

    while (true)
    {

      index++;

      if (line[index] == busca)
      {

        substring(sub, &line[atr_index], index - atr_index);
        strcpy(game->developers, sub);

        atr_index = ++index;
        break;
      }
    }
  }
  else
  {

    strcpy(game->developers, "null");

    atr_index = ++index;
  }

  // ------------------------------------------------------------ //

  // Find "Genres"
  if (index < strlen(line) - 1)
  {

    if (line[index] == ',')
      atr_index = ++index;

    if (line[atr_index] == '\"')
    {

      atr_index++;

      while (true)
      {

        index++;

        if (line[index] == ',')
        {

          substring(sub, &line[atr_index], index - atr_index);
          strcpy(game->genres[game->numGenres++], sub);

          atr_index = ++index;
        }
        else if (line[index] == '\"')
        {

          substring(sub, &line[atr_index], strlen(line) - 1 - atr_index);

          if (sub[strlen(sub) - 2] == '\"')
            sub[strlen(sub) - 2] = '\0';

          strcpy(game->genres[game->numGenres++], sub);
          break;
        }
      }
    }
    else
    {

      substring(sub, &line[atr_index], strlen(line) - 1 - atr_index);

      sub[strlen(line) - 2 - atr_index] = '\0';

      strcpy(game->genres[game->numGenres++], sub);
    }
  }
}

void imprimir(Game games[], int contador)
{
  /* app id name release date owners age price dlcs [languages]
  website windows mac linux upvotes avg_pt developers [genres] */

  for (int j = 0; j < contador; j++)
  {
    printf("%d %s %d %s %d %.2f %d [", games[j].appId, games[j].name,
           games[j].releaseDate,
           games[j].owners, games[j].age, games[j].price, games[j].dlcs);

    for (int i = 0; i < games[j].numLanguages; i++)
    {
      printf("%s, ", games[j].languages[i]);
    }
    printf("%s]\n", games[j].languages[games[j].numLanguages]);

    printf("%s %d %d %d %d %d %s [", games[j].website, games[j].windows,
           games[j].mac, games[j].llinux, games[j].upvotes,
           games[j].avgPlaytime, games[j].developers);
    for (int i = 0; i < games[j].numGenres; i++)
    {
      printf("%s, ", games[j].genres[i]);
    }
    printf("%s]\n", games[j].genres[games[j].numGenres]);
  }
}

Game pesquisar(int app_id)
{

  for (int i = 0; i < x; i++)
  {

    if (games[i].appId == app_id)
      return games[i];
  }

  Game game;
  game.appId = -1;
  return game;
}

// Lista
void inserirInicio(Game g)
{

  if (x >= TAM)
  {

    printf("Insert error: MAX_GAMES reached");
    exit(1);
  }

  for (int i = x; i > 0; i--)
    games[i] = games[i - 1];

  games[0] = g;
  x++;
}

void inserirFim(Game g)
{

  if (x >= TAM)
  {

    printf("Insert error: MAX_GAMES reached");
    exit(1);
  }

  games[x++] = g;
}

void inserir(Game g, int pos)
{

  if (x >= TAM || (pos < 0 || pos > x))
  {

    printf("Insert error: %s",
           x >= TAM ? "MAX_GAMES reached" : "Invalid position");
    exit(1);
  }

  for (int i = x; i > pos; i--)
    games[i] = games[i - 1];

  games[pos] = g;
  x++;
}

Game removerInicio()
{

  Game resp;

  if (x == 0)
  {

    printf("Remove error: Empty list");
    exit(1);
  }

  resp = games[0];
  x--;

  for (int i = 0; i < x; i++)
    games[i] = games[i + 1];
  return resp;
}

Game removerFim()
{

  if (x == 0)
  {

    printf("Remove error: Empty list");
    exit(1);
  }
  return games[--x];
}

Game remover(int pos)
{

  Game resp;

  if (x >= TAM || (pos < 0 || pos > x))
  {

    printf("Insert error: %s!", x == 0 ? "Empty list" : "Invalid position");
    exit(1);
  }

  resp = games[pos];
  x--;

  for (int i = pos; i < x; i++)
    games[i] = games[i + 1];
  return resp;
}

void construir(Game *game)
{

  strcpy(game->name, "");
  strcpy(game->owners, "");
  strcpy(game->website, "");
  strcpy(game->developers, "");

  for (int i = 0; i < TAM; i++)
  {
    strcpy(game->languages[i], "");
    strcpy(game->genres[i], "");
  }

  game->releaseDate.month = -1;
  game->releaseDate.year = -1;
  game->appId = -1;
  game->age = -1;
  game->dlcs = -1;
  game->avgPlaytime = -1;
  game->price = -1;
  game->upvotes = -1;
  game->windows = false;
  game->mac = false;
  game->llinux = false;

  game->numLanguages = 0;
  game->numGenres = 0;
}

int main()
{
  FILE *fp;
  char *line = NULL;
  size_t len = 0;
  size_t read;

  fp = fopen("/tmp/games.csv", "r");

  if (fp == NULL)
  {
    exit(EXIT_FAILURE);
  }
  // -------------------------------------- //
  while ((read = getline(&line, &len, fp)) != -1)
  {
    Game game;

    construir(&game);
    ler(&game, line);

    games[x++] = game;
  }

  fclose(fp);

  if (line)
    free(line);
  // -------------------------------------- //

  char line_in[100];

  // Fill production games list
  scanf(" %[^\n]", line_in);

  while (true)
  {

    if (isFim(line_in))
      break;
    // -------------------------------------- //
    int app_id = atoi(line_in);

    Game found = pesquisar(app_id);

    if (found.appId != -1)
      inserirFim(found);

    // -------------------------- //

    scanf(" %[^\n]", line_in);
  }

  // ----------------------------------------------------------------------------------------
  // //

  // Execute operations
  int n_ops;
  scanf("%i", &n_ops);

  for (int i = 0; i < n_ops; i++)
  {

    scanf(" %[^\n]", line_in);

    Game game;
    char params[10];

    if (line_in[0] == 'I')
    {

      substring(params, &line_in[3], strlen(line_in) - 3);

      if (line_in[1] == 'I')
        inserirInicio(pesquisar(atoi(params)));
      else if (line_in[1] == 'F')
        inserirFim(pesquisar(atoi(params)));
      else if (line_in[1] == '*')
      {

        char appId[10], pos[10];
        int i = 0;

        while (true)
        {

          if (params[i] == ' ')
          {

            substring(pos, &params[0], i);
            substring(appId, &params[i + 1], strlen(params) - i - 1);
            break;
          }
          else
            i++;
        }

        inserir(pesquisar(atoi(appId)), atoi(pos));
      }
    }
    else if (line_in[0] == 'R')
    {

      if (line_in[1] == 'I')
        printf("(R) %s\n", removerInicio().name);
      else if (line_in[1] == 'F')
        printf("(R) %s\n", removerFim().name);
      else if (line_in[1] == '*')
      {

        substring(params, &line_in[3], strlen(line_in) - 3);

        printf("(R) %s\n", remover(atoi(params)).name);
      }
    }
  }
  imprimir(games, contador);
  return 0;
}