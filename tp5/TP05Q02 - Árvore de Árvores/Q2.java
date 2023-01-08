import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ArvoreDaArvore arvore = new ArvoreDaArvore();
        ArrayList<Game> games = new ArrayList<Game>();
        try {
            // le arquivo games.csv e salva em games
            String basefile = "/tmp/games.csv";
            FileInputStream fstream = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;

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
        String line = sc.nextLine();
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
                    if (game.getAppId() == app_id) {
                        tmp = game.clone();
                    }
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
            arvore.caminharPre(line.charAt(0), line);
            line = sc.nextLine();
        }
        sc.close();
    }

    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }
}

// -------- ARVORE BINARIA ---------
// No 1
class No { // --> char
    public char elemento; // Conteudo do no.
    public No esq; // No da esquerda.
    public No dir; // No da direita.
    public No2 no2;

    No(char elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
        this.no2 = null;
    }

    No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.no2 = null;
    }

    // mostrar
}

// No 2
class No2 { // --> string
    public String elemento; // Conteudo do no
    public No2 esq; // No da esquerda
    public No2 dir; // No da direita

    No2(String elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }

    No2(String elemento, No2 esq, No2 dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

// Arvore
class ArvoreDaArvore {
    private No raiz; // Raiz da arvore.
    ArrayList<String> percurso = new ArrayList<String>();
    boolean temporaria_resp = false;

    public ArvoreDaArvore() throws Exception {
        raiz = null;
        inserir('D');
        inserir('R');
        inserir('Z');
        inserir('X');
        inserir('V');
        inserir('B');
        inserir('F');
        inserir('P');
        inserir('U');
        inserir('I');
        inserir('G');
        inserir('E');
        inserir('J');
        inserir('L');
        inserir('H');
        inserir('T');
        inserir('A');
        inserir('W');
        inserir('S');
        inserir('O');
        inserir('M');
        inserir('N');
        inserir('K');
        inserir('C');
        inserir('Y');
        inserir('Q');
        // os outros 23 caracteres.
    }

    public void caminharPre(char x, String game) {
        System.out.println(game);
        percurso.add("raiz");
        caminharPre(raiz, x, game);
        percurso.add("fim");
        for (int i = 0; i < percurso.size() - 1; i++) {
            System.out.print(percurso.get(i) + " ");
            if (percurso.get(i + 1).equals("encontrou")) {
                i = percurso.size();
            }
        }
        if (temporaria_resp) {
            System.out.print(" SIM");
        } else {
            System.out.print(" NAO");
        }
        percurso.clear();
        System.out.println();
        percurso.clear();
        temporaria_resp = false;
    }

    private void caminharPre(No i, char x, String game) {
        boolean t = false;
        if (i != null && t != true) {
            if (pesquisarSegundaArvore(i.no2, game)) {
                t = true;
                percurso.add("encontrou");
                temporaria_resp = true;
            }
            percurso.add(" ESQ");
            caminharPre(i.esq, x, game);
            percurso.add(" DIR");
            caminharPre(i.dir, x, game); // Elementos da direita.
        }
    }

    public void inserir(char x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(char x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }

    public void inserir(String s) throws Exception {
        inserir(s, raiz);
    }

    public void inserir(String s, No i) throws Exception {
        if (i == null) {
            throw new Exception("Erro ao inserir: caractere invalido!");
        } else if (s.charAt(0) < i.elemento) {
            inserir(s, i.esq);
        } else if (s.charAt(0) > i.elemento) {
            inserir(s, i.dir);
        } else {
            i.no2 = inserir(s, i.no2);
        }
    }

    private No2 inserir(String s, No2 i) throws Exception {
        if (i == null) {
            i = new No2(s);
        } else if (s.compareTo(i.elemento) < 0) {
            i.esq = inserir(s, i.esq);
        } else if (s.compareTo(i.elemento) > 0) {
            i.dir = inserir(s, i.dir);
        } else {
            throw new Exception("Erro ao inserir: elemento existente!");
        }
        return i;
    }

    private boolean pesquisarSegundaArvore(No2 no, String x) {
        boolean resp;
        if (no == null) {
            resp = false;
        } else if (x.compareTo(no.elemento) < 0) {
            percurso.add("esq");
            resp = pesquisarSegundaArvore(no.esq, x);
        } else if (x.compareTo(no.elemento) > 0) {
            percurso.add("dir");
            resp = pesquisarSegundaArvore(no.dir, x);
        } else {
            resp = true;
        }
        return resp;
    }

    public void remover(String chave) throws Exception {
        raiz = remover(chave, raiz);
    }

    private No remover(String chave, No no) throws Exception {
        char c = chave.charAt(0);
        if (c == no.elemento) {
            no.no2 = remover(chave, no.no2);
            // i.no;
        } else if (c < no.elemento) {
            no.esq = remover(chave, no.esq);
        } else if (c > no.elemento) {
            no.dir = remover(chave, no.dir);
            // Sem no a direita.
        } else if (no.dir == null) {
            no = no.esq;
            // Sem no a esquerda.
        } else if (no.esq == null) {
            no = no.dir;
            // No a esquerda e no a direita.
        } else {
            no.esq = maiorEsq(no, no.esq);
        }
        return no;
    }

    private No2 remover(String chave, No2 no2) throws Exception {
        if (no2 == null) {
            throw new Exception("Erro ao remover");
        } else if (chave.compareTo(no2.elemento) < 0) {
            remover(chave, no2.esq);
        } else if (chave.compareTo(no2.elemento) > 0) {
            remover(chave, no2.dir);
        } else if (no2.dir == null) {
            no2 = no2.esq;
            // Sem no2 a esquerda.
        } else if (no2.esq == null) {
            no2 = no2.dir;
            // no2 a esquerda e no2 a direita.
        } else {
            no2.esq = maiorEsq(no2, no2.esq);
        }
        return no2;
    }

    private No2 maiorEsq(No2 i, No2 j) {
        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.
            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    private No maiorEsq(No i, No j) {
        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.
            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }
}

class Game {

    // variáveis
    // static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/yyyy",
    // Locale.ENGLISH);
    private String name;
    // owners, website, developers;
    /*
     * private ArrayList<String> languages, genres;
     * private Date release_date;
     */ private int app_id; /*
                             * , age, dlcs, avg_playtime;
                             * private float price, upvotes;
                             * private boolean windows, mac, linux;
                             */

    // construtor vazio
    public Game() {

        this.name = /* this.owners = this.website = this.developers = */ null;
        /*
         * this.languages = new ArrayList<String>();
         * this.genres = new ArrayList<String>();
         * this.release_date = null;
         */ this.app_id /* = this.age = this.dlcs = this.avg_playtime */ = -1;
        /*
         * this.price = this.upvotes = -1;
         * this.windows = this.mac = this.linux = false;
         */
    }

    // construtor com argumentos
    public Game(String name/*
                            * , String owners, String website, String developers, ArrayList<String>
                            * languages,
                            * ArrayList<String> genres, Date release_date, int app_id, int age, int dlcs,
                            * int upvotes, int avg_playtime,
                            * float price, boolean windows, boolean mac, boolean linux
                            */) {

        this.name = name;
        /*
         * this.owners = owners;
         * this.website = website;
         * this.developers = developers;
         * this.languages = languages;
         * this.genres = genres;
         * this.release_date = release_date;
         */ this.app_id = app_id; /*
                                   * this.age = age;
                                   * this.dlcs = dlcs;
                                   * this.upvotes = upvotes;
                                   * this.avg_playtime = avg_playtime;
                                   * this.price = price;
                                   * this.windows = windows;
                                   * this.mac = mac;
                                   * this.linux = linux;
                                   */
    }

    // setter's e getter's
    public void setName(String name) {
        this.name = name;
    }

    /*
     * public void setOwners(String owners) {
     * this.owners = owners;
     * }
     * 
     * public void setWebsite(String website) {
     * this.website = website;
     * }
     * 
     * public void setDevelopers(String developers) {
     * this.developers = developers;
     * }
     * 
     * public void setLanguages(ArrayList<String> languages) {
     * this.languages = languages;
     * }
     * 
     * public void setGenres(ArrayList<String> genres) {
     * this.genres = genres;
     * }
     * 
     * public void setReleaseDate(Date release_date) {
     * this.release_date = release_date;
     * }
     */
    public void setAppId(int app_id) {
        this.app_id = app_id;
    }

    /*
     * public void setAge(int age) {
     * this.age = age;
     * }
     * 
     * public void setDlcs(int dlcs) {
     * this.dlcs = dlcs;
     * }
     * 
     * public void setAvgPlaytime(int avg_playtime) {
     * this.avg_playtime = avg_playtime;
     * }
     * 
     * public void setPrice(float price) {
     * this.price = price;
     * }
     * 
     * public void setUpvotes(float upvotes) {
     * this.upvotes = upvotes;
     * }
     * 
     * public void setWindows(boolean windows) {
     * this.windows = windows;
     * }
     * 
     * public void setMac(boolean mac) {
     * this.mac = mac;
     * }
     * 
     * public void setLinux(boolean linux) {
     * this.linux = linux;
     * }
     */
    public String getName() {
        return this.name;
    }

    /*
     * public String getOwners() {
     * return this.owners;
     * }
     * 
     * public String getWebsite() {
     * return this.website;
     * }
     * 
     * public String getDevelopers() {
     * return this.developers;
     * }
     * 
     * public ArrayList<String> getLanguages() {
     * return this.languages;
     * }
     * 
     * public ArrayList<String> getGenres() {
     * return this.genres;
     * }
     * 
     * public Date getReleaseDate() {
     * return this.release_date;
     * }
     */
    public int getAppId() {
        return this.app_id;
    }

    /*
     * public int getAge() {
     * return this.age;
     * }
     * 
     * public int getDlcs() {
     * return this.dlcs;
     * }
     * 
     * public int getAvgPlaytime() {
     * return this.avg_playtime;
     * }
     * 
     * public float getPrice() {
     * return this.price;
     * }
     * 
     * public float getUpvotes() {
     * return this.upvotes;
     * }
     * 
     * public boolean getWindows() {
     * return this.windows;
     * }
     * 
     * public boolean getMac() {
     * return this.mac;
     * }
     * 
     * public boolean getLinux() {
     * return this.linux;
     * }
     */
    // metodo clone
    public Game clone() {

        Game cloned = new Game();

        cloned.name = this.name; /*
                                  * cloned.owners = this.owners;
                                  * cloned.website = this.website;
                                  * cloned.developers = this.developers;
                                  * cloned.languages = this.languages;
                                  * cloned.genres = this.genres;
                                  * cloned.release_date = this.release_date;
                                  */
        cloned.app_id = this.app_id; /*
                                      * cloned.age = this.age;
                                      * cloned.dlcs = this.dlcs;
                                      * cloned.avg_playtime = this.avg_playtime;
                                      * cloned.price = this.price;
                                      * cloned.upvotes = this.upvotes;
                                      * cloned.windows = this.windows;
                                      * cloned.mac = this.mac;
                                      * cloned.linux = this.linux;
                                      */

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
    /*
     * // release date
     * if (line.charAt(atr_index) != ',') {
     * 
     * SimpleDateFormat df;
     * 
     * if (line.charAt(atr_index) == '\"') {
     * 
     * df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
     * 
     * atr_index++;
     * busca = '\"';
     * } else {
     * 
     * df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
     * 
     * busca = ',';
     * }
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == busca) {
     * 
     * try {
     * this.release_date = df.parse(line.substring(atr_index, index));
     * } catch (java.text.ParseException e) {
     * e.printStackTrace();
     * }
     * 
     * if (busca == ',')
     * index++;
     * else if (busca == '\"')
     * index += 2;
     * 
     * atr_index = index;
     * break;
     * }
     * }
     * } else
     * atr_index = ++index;
     * 
     * // Owners
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.owners = line.substring(atr_index, index);
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Age
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.age = Integer.parseInt(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Price
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.price = Float.parseFloat(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // DLCs
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.dlcs = Integer.parseInt(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Languages
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ']') {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',')
     * index++;
     * else if (line.charAt(index) == '\"')
     * index += 2;
     * 
     * atr_index = index;
     * break;
     * } else if (line.charAt(index) == '\'') {
     * 
     * int wordStart = index + 1;
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == '\'') {
     * 
     * this.languages.add(line.substring(wordStart, index));
     * break;
     * }
     * }
     * }
     * }
     * 
     * // Website
     * if (line.charAt(atr_index) != ',') {
     * 
     * if (line.charAt(atr_index) == '\"') {
     * 
     * atr_index++;
     * busca = '\"';
     * } else
     * busca = ',';
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == busca) {
     * 
     * this.website = line.substring(atr_index, index);
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * } else
     * atr_index = ++index;
     * 
     * // Windows
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.windows = Boolean.parseBoolean(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Mac
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.mac = Boolean.parseBoolean(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Linux
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.linux = Boolean.parseBoolean(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Upvotes
     * int positives, negatives;
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * positives = Integer.parseInt(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * negatives = Integer.parseInt(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * this.upvotes = (float) (positives * 100) / (float) (positives + negatives);
     * 
     * // AVG Playtime
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.avg_playtime = Integer.parseInt(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * 
     * // Developers
     * if (line.charAt(atr_index) != ',') {
     * 
     * if (line.charAt(atr_index) == '\"') {
     * 
     * atr_index++;
     * busca = '\"';
     * } else
     * busca = ',';
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == busca) {
     * 
     * this.developers = line.substring(atr_index, index);
     * 
     * atr_index = ++index;
     * break;
     * }
     * }
     * } else
     * atr_index = ++index;
     * 
     * // Genres
     * if (index < line.length() - 1) {
     * 
     * if (line.charAt(index) == ',')
     * atr_index = ++index;
     * if (line.charAt(atr_index) == '\"') {
     * 
     * atr_index++;
     * 
     * while (true) {
     * 
     * index++;
     * 
     * if (line.charAt(index) == ',') {
     * 
     * this.genres.add(line.substring(atr_index, index));
     * 
     * atr_index = ++index;
     * } else if (line.charAt(index) == '\"') {
     * 
     * this.genres.add(line.substring(atr_index, line.length() - 1));
     * break;
     * }
     * }
     * } else
     * this.genres.add(line.substring(atr_index, line.length()));
     * }
     * 
     * }
     */

    // imprime game
    public String print() {
        String resp = new String();
        /*
         * String avg_pt = null;
         * 
         * if (this.avg_playtime == 0)
         * avg_pt = "null ";
         * else if (this.avg_playtime < 60)
         * avg_pt = this.avg_playtime + "m ";
         * else {
         * 
         * if (this.avg_playtime % 60 == 0)
         * avg_pt = this.avg_playtime / 60 + "h ";
         * else
         * avg_pt = (this.avg_playtime / 60) + "h " + (this.avg_playtime % 60) + "m ";
         * }
         * 
         * DecimalFormat df = new DecimalFormat("##");
         */
        resp = /* this.app_id + " " + */ this.name /*
                                                    * + " " + dateFormat.format(this.release_date) + " " + this.owners +
                                                    * " "
                                                    * + this.age + " " + String.format(Locale.ENGLISH, "%.2f",
                                                    * this.price) + " " + this.dlcs + " "
                                                    * + this.languages + " " + this.website + " " + this.windows + " " +
                                                    * this.mac + " " + this.linux + " "
                                                    * + (Float.isNaN(this.upvotes) ? "0% " : df.format(this.upvotes) +
                                                    * "% ") + avg_pt + this.developers + " "
                                                    * + this.genres
                                                    */;
        return resp;

    }

    // checa se é o fim das entradas
    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }

}
