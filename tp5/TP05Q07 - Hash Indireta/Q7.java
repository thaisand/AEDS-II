import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Q7 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        Hash hashR = new Hash(21);
        ArrayList<Game> games = new ArrayList<Game>();

        try {
            // le arquivo games.csv e salva em games
            String basefile = "tmp/games.csv";
            FileInputStream fstream = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            while ((line = br.readLine()) != null) {
                Game game = new Game();
                game.read(line);
                games.add(game);
            }
            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ler linhas stdin - inserir na arvore
        line = sc.nextLine();
        while (isFim(line) == false) {
            int app_id = Integer.parseInt(line);
            Game tmp = new Game();
            // PREENCHER GAME TMP
            for (Game game : games) {
                if (game.getAppId() == app_id)
                    tmp = game.clone();
            }
            hashR.inserirInicio(tmp);
            line = sc.nextLine();
        }
        // pesquisar na arvore
        line = sc.nextLine();
        while (!isFim(line)) {
            hashR.pesquisar(line);
            line = sc.nextLine();
        }
        sc.close();
    }

    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }
}
// ---------------------------- HASH ---------------------
class Hash {
    Lista tabela[];
    int tamanho;
    final String NULO = "-1";

    public Hash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new Lista[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new Lista();
        }
    }

    public int h(String titulo) {
        int soma = 0;

        for (int i = 0; i < titulo.length(); i++) {
            soma += titulo.charAt(i);
        }
        return soma % tamanho;
    }

    public void inserirInicio(Game game) {
        int pos = h(game.getName());
        tabela[pos].inserirInicio(game);
    }

    void pesquisar(String titulo) {
        int pos = h(titulo);
        boolean retorno = tabela[pos].pesquisar(titulo);
        System.out.println("=> " + titulo);
        if (!retorno) {
            System.out.println("NAO");
        } else {
            System.out.println("Posicao: " + pos);
        }
    }
}

class Celula {
    // VARIAVEIS
    public Game game; // Elemento inserido na celula.
    public Celula prox; // Aponta a celula prox.

    // Construtor da classe.
    public Celula() {
    }

    // Construtor da classe.
    public Celula(Game game) {
        this.game = game;
        this.prox = null;
    }
}

class Lista {
    private Celula primeiro;
    private Celula ultimo;

    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirInicio(Game game) {
        Celula tmp = new Celula(game);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;
    }


    public boolean pesquisar(String titulo) {
        boolean retorno = false;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            if (i.game.getName().equals(titulo)) {
                retorno = true;
                i = ultimo;
            }
        }
        return retorno;
    }

    void imprimir() {
        int j = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox, j++) {
            System.out.print(
                    "[" + j + "] "
                            + i.game.getName() );

        }
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro; i != ultimo; i = i.prox, tamanho++)
            ;
        return tamanho;
    }
}
// ----------------------------GAME------------------------------
class Game {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
    private String name;
    private int app_id;

    // construtor vazio
    public Game() {
        this.name = null;
        this.app_id = -1;
    }

    // construtor 
    public Game(String name, int app_id) {
        this.name = name;
        this.app_id = app_id;
    }
    // setter's e getter's
    public void setName(String name) {
        this.name = name;
    }

    public void setAppId(int app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return this.name;
    }

    public int getAppId() {
        return this.app_id;
    }

    // metodo clone
    public Game clone() {

        Game cloned = new Game();
        cloned.name = this.name;
        cloned.app_id = this.app_id;
        return cloned;
    }

    // metodo que le linha e formata infos
    public void read(String line) {

        char busca;
        int index = 0, atr_index = 0;

        // AppID
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.app_id = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Name
        if (line.charAt(atr_index) != ',') {

            if (line.charAt(atr_index) == '\"') {

                atr_index++;
                busca = '\"';
            } else
                busca = ',';

            while (true) {

                index++;

                if (line.charAt(index) == busca) {

                    this.name = line.substring(atr_index, index);

                    if (busca == ',')
                        index++;
                    else if (busca == '\"')
                        index += 2;

                    atr_index = index;
                    break;
                }
            }
        } else
            atr_index = ++index;

    }

    // imprime game
    public String print() {
        String resp = new String();
        resp = this.app_id + " " + this.name ;
        return resp;
    }

    // checa se é o fim das entradas
    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }

}
