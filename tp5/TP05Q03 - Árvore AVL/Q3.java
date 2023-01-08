import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        ArvoreAVL arvore = new ArvoreAVL();
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
            arvore.inserir(tmp.getName());
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
                arvore.inserir(tmp.getName());
            } else if (operacao == 'R') {
                arvore.remover(line);
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

class No {
    public String elemento; // Conteudo do no
    public No esq, dir; // Filhos da esq e dir
    public int nivel; // Numero de niveis abaixo do no

    public No(String elemento) {
        this(elemento, null, null, 1);
    }

    public No(String elemento, No esq, No dir, int nivel) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = nivel;
    }

    public void setNivel() {
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }

    public static int getNivel(No no) {
        return (no == null) ? 0 : no.nivel;
    }
}

// --------------------------- ARVORE -----------------------
class ArvoreAVL {
    private No raiz; 

    public ArvoreAVL() {
        raiz = null;
    }

    public boolean pesquisar(String x) {
        System.out.println(x);
        System.out.print("raiz");
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(String x, No i) {
        boolean resp;
        if (i == null) {
            resp = false;
            System.out.println(" NAO");
        } else if (x.equals(i.elemento)) {
            resp = true;
            System.out.println(" SIM");
        } else if (x.compareTo(i.elemento) < 0) {
            System.out.print(" esq");
            resp = pesquisar(x, i.esq);
        } else {
            System.out.print(" dir");
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda
            System.out.print(i.elemento + " "); // Conteudo do no
            caminharCentral(i.dir); // Elementos da direita
        }
    }

    public void inserir(String x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(String x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x.compareTo(i.elemento) < 0) {
            i.esq = inserir(x, i.esq);
        } else if (x.compareTo(i.elemento) > 0) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return balancear(i);
    }

    public void remover(String x) throws Exception {
        raiz = remover(x, raiz);
    }

    private No remover(String x, No i) throws Exception {
        if (i == null) {
            System.out.println("Erro ao remover!");
        } else if (x.compareTo(i.elemento) < 0) {
            i.esq = remover(x, i.esq);
        } else if (x.compareTo(i.elemento) > 0) {
            i.dir = remover(x, i.dir);
            // Sem no a direita
        } else if (i.dir == null) {
            i = i.esq;
            // Sem no a esquerda
        } else if (i.esq == null) {
            i = i.dir;
            // No a esquerda e no a direita
        } else {
            i.esq = maiorEsq(i, i.esq);
        }
        return balancear(i);
    }

    private No maiorEsq(No i, No j) {
        // Encontrou o maximo da subarvore esquerda
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j
            j = j.esq; // Substitui j por j.ESQ
            // Existe no a direita
        } else {
            // Caminha para direita
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    private No balancear(No no) throws Exception {
        if (no != null) {
            int fator = No.getNivel(no.dir) - No.getNivel(no.esq);
            // Se balanceada
            if (Math.abs(fator) <= 1) {
                no.setNivel();
                // Se desbalanceada para a direita
            } else if (fator == 2) {
                int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);
                // Se o filho a direita tambem estiver desbalanceado
                if (fatorFilhoDir == -1) {
                    no.dir = rotacionarDir(no.dir);
                }
                no = rotacionarEsq(no);
                // Se desbalanceada para a esquerda
            } else if (fator == -2) {
                int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);
                // Se o filho a esquerda tambem estiver desbalanceado
                if (fatorFilhoEsq == 1) {
                    no.esq = rotacionarEsq(no.esq);
                }
                no = rotacionarDir(no);
            } else {
                throw new Exception(
                        "Erro no No(" + no.elemento + ") com fator de balanceamento (" + fator + ") invalido!");
            }
        }
        return no;
    }

    private No rotacionarDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;
        no.setNivel(); // Atualizar o nivel do no
        noEsq.setNivel(); // Atualizar o nivel do noEsq

        return noEsq;
    }

    private No rotacionarEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        no.setNivel(); // Atualizar o nivel do no
        noDir.setNivel(); // Atualizar o nivel do noDir
        return noDir;
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
