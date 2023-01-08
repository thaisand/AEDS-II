import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        ArvoreAlvinegra arvore = new ArvoreAlvinegra();
        ArrayList<Game> games = new ArrayList<Game>();

        try {
            // le arquivo games.csv e salva em games
            String basefile = "/tmp/games.csv";
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
            arvore.inserir(tmp);
            line = sc.nextLine();
        }
        // operacoes
        line = sc.nextLine();
        int numOp = Integer.parseInt(line);
        while (numOp > 0) {
            line = sc.nextLine();
            numOp--;
            char operacao = line.charAt(0);
            line = line.substring(2);
            if (operacao == 'I') {
                int app_id = Integer.parseInt(line);
                Game tmp = new Game();
                // PREENCHER GAME TMP
                for (Game game : games) {
                    if (game.getAppId() == app_id)
                        tmp = game.clone();
                }
                arvore.inserir(tmp);
            } else if (operacao == 'R') {
                //arvore.remover(line);
            }
        }
        // pesquisar na arvore
        line = sc.nextLine();
        while (!isFim(line)) {
            // pesquisar
            arvore.pesquisar(line);
            line = sc.nextLine();
        }
        sc.close();
    }

    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }
}

// ---------------------------- NO ARVORE ---------------------
class No {
    public boolean cor;
    public Game game;
    public No esq, dir;

    public No() {
        this(null);
    }

    public No(Game game) {
        this(game, false, null, null);
    }

    public No(Game game, boolean cor) {
        this(game, cor, null, null);
    }

    public No(Game game, boolean cor, No esq, No dir) {
        this.cor = cor;
        this.game = game;
        this.esq = esq;
        this.dir = dir;
    }
}

// --------------------------- ARVORE -----------------------
class ArvoreAlvinegra {
    private No raiz; // Raiz da arvore.

    public ArvoreAlvinegra() {
        raiz = null;
    }

    // PESQUISA E IMPRIME CAMINHAMENTO
    public boolean pesquisar(String chave) {
        //System.out.println();
        System.out.println(chave);
        System.out.print("raiz");
        return pesquisar(chave, raiz);
    }

    private boolean pesquisar(String chave, No i) {
        boolean resp;
        if (i == null) {
            resp = false;
            System.out.println(" NAO");
        } else if (chave.equals(i.game.getName())) {
            resp = true;
            System.out.println(" SIM");
        } else if (chave.compareTo(i.game.getName()) < 0) {
            System.out.print(" esq");
            resp = pesquisar(chave, i.esq);
        } else {
            System.out.print(" dir");
            resp = pesquisar(chave, i.dir);
        }
        return resp;
    }

    public void inserir(Game game) throws Exception {
        String titulogame = game.getName();
        // Se a arvore estiver vazia
        if (raiz == null) {
            raiz = new No(game);

            // Senao, se a arvore tiver um game
        } else if (raiz.esq == null && raiz.dir == null) {
            if (titulogame.compareTo(raiz.game.getName()) < 0) {
                raiz.esq = new No(game);
            } else {
                raiz.dir = new No(game);
            }

            // Senao, se a arvore tiver dois elementos (raiz e dir)
        } else if (raiz.esq == null) {
            if (titulogame.compareTo(raiz.game.getName()) < 0) {
                raiz.esq = new No(game);
            } else if (titulogame.compareTo(raiz.dir.game.getName()) < 0) {
                raiz.esq = new No(raiz.game);
                raiz.game = game;
            } else {
                raiz.esq = new No(raiz.game);
                raiz.game = raiz.dir.game;
                raiz.dir.game = game;
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, se a arvore tiver dois elementos (raiz e esq)
        } else if (raiz.dir == null) {
            if (titulogame.compareTo(raiz.game.getName()) > 0) {
                raiz.dir = new No(game);
            } else if (titulogame.compareTo(raiz.esq.game.getName()) > 0) {
                raiz.dir = new No(raiz.game);
                raiz.game = game;
            } else {
                raiz.dir = new No(raiz.game);
                raiz.game = raiz.esq.game;
                raiz.esq.game = game;
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, a arvore tem tres ou mais elementos
        } else {
            inserir(game, null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void balancear(No bisavo, No avo, No pai, No i) {
        // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
        if (pai.cor == true) {
            // 4 tipos de reequilibrios e acoplamento
            if (pai.game.getName().compareTo(avo.game.getName()) > 0) { // rotacao a esquerda ou direita-esquerda
                if (i.game.getName().compareTo(pai.game.getName()) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { // rotacao a direita ou esquerda-direita
                if (i.game.getName().compareTo(pai.game.getName()) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.game.getName().compareTo(bisavo.game.getName()) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }
            // reestabelecer as cores apos a rotacao
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        } // if(pai.cor == true)
    }

    private void inserir(Game game, No bisavo, No avo, No pai, No i) throws Exception {
        String titulogame = game.getName();
        if (i == null) {
            if (titulogame.compareTo(pai.game.getName()) < 0) {
                i = pai.esq = new No(game, true);
            } else {
                i = pai.dir = new No(game, true);
            }
            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }
        } else {
            // Achou um 4-no: eh preciso fragmenta-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }
            if (titulogame.compareTo(i.game.getName()) < 0) {
                inserir(game, avo, pai, i, i.esq);
            } else if (titulogame.compareTo(i.game.getName()) > 0) {
                inserir(game, avo, pai, i, i.dir);
            } else {
                throw new Exception("Erro inserir (game repetido)!");
            }
        }
    }

    private No rotacaoDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private No rotacaoEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }

    private No rotacaoDirEsq(No no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private No rotacaoEsqDir(No no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }
}

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
