import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Q3 {
    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Game> games = new ArrayList<Game>();

        // ------------------------------------------------------------------------------
        // //

        try {

            // Read CSV file
            String basefile = "/tmp/games.csv";

            FileInputStream fstream = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            // ------------------------------------ //

            // Explode CSV file reading games
            String line;

            while ((line = br.readLine()) != null) {

                Game game = new Game();

                game.read(line);
                games.add(game);
            }

            // Close CSV file
            fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ----------------------------------------------------------------------------------------------
        // //

        // Read .in file
        Scanner scr = new Scanner(System.in);
        Fila fila = new Fila();

        String line = scr.nextLine();

        while (!isFim(line)) {
            Game tmp = new Game();
            int app_id = Integer.parseInt(line);
            
            // Search game with .in id
            for (Game game : games) {
                if (game.getAppId() == app_id) {
                    tmp = game.clone();
                    fila.inserir(tmp);
                    fila.imprimeMedia();
                }
            }
            line = scr.nextLine();
        }

        // ----------------------------------------------------------------------------------------------
       

        line = scr.nextLine();
        int operacoes = Integer.parseInt(line);

        int appId = 0;
        String operacao = "";
        Game tmp = new Game();
        Game removido = new Game();
        int mediaAvg = 0;
        // realizar n operacoes
        for (int i = 0; i < operacoes; i++) {
            line = scr.nextLine();
            System.out.println(line);

            // identificar operacao
            operacao = line.substring(0, 1);
            // SE OPERACAO FOR DO TIPO INSERIR --> PEGAR ID GAME
            if (operacao.equals("I")) {
                appId = Integer.parseInt(line.substring(2));
                // System.out.println(appId);
            }

            // PREENCHER GAME TMP
            for (Game game : games) {
                if (game.getAppId() == appId) {
                    // game.print();
                    tmp = game.clone();
                }
            }
            // REALIZAR OPERACAO
            if (operacao.equals("I")) {
                fila.inserir(tmp);
                fila.imprimeMedia();
            } else if ((operacao.equals("R"))) {
                removido = fila.remover();
                System.out.println("(R) " + removido.getName());
            }
        }
        // imprimir lista de games
        fila.imprimir();
        scr.close();
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

class Fila {
    private Celula primeiro;
    private Celula ultimo;
    private int tam;

    public Fila() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(Game game) throws Exception {
        if (tam == 5) {
            remover();
        }
        ultimo.prox = new Celula(game);
        if (ultimo == primeiro) {
            primeiro.prox = ultimo.prox;
        }
        ultimo = ultimo.prox;
        ultimo.prox = primeiro;
        tam++;
    }

    public Game remover() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("erro: fila vazia!");
        }
        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        Game game = primeiro.game;
        tmp.prox = null;
        tmp = null;
        tam--;
        ultimo.prox = primeiro;
        return game;
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro; i != ultimo; i = i.prox, tamanho++)
            ;
        return tamanho;
    }

    void imprimeMedia() {
        double media = 0.0;
        int contador = 0;
        for (Celula i = primeiro.prox; i != primeiro; i = i.prox) {
            media = media + i.game.getAvgPlaytime();
            contador++;
        }
        media = media / contador;
        System.out.println(Math.round(media));
    }

    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
        DecimalFormat df = new DecimalFormat("##");
        int j = 0;

        for (Celula i = primeiro.prox; i != primeiro; i = i.prox, j++) {

            System.out.println("[" + j + "] " + i.game.getAppId() + " " + i.game.getName() + " "
                    + sdf.format(i.game.getReleaseDate()) + " "
                    + i.game.getOwners() + " " + i.game.getAge() + " " + i.game.getPrice() + " "
                    + i.game.getDlcs() + " " + i.game.getLanguages() + " "
                    + i.game.getWebsite() + " " + i.game.getWindows() + " "
                    + i.game.getMac() + " " + i.game.getLinux() + " "
                    + (Float.isNaN(i.game.getUpvotes()) ? "0% " : df.format(i.game.getUpvotes()) + "% ") + " "
                    + i.game.getAvgPlaytime() + " "
                    + i.game.getDevelopers() + " " + i.game.getGenres() + " ");

        }
        j++;

    }
}

class Game {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);

    private String name, owners, website, developers;
    private ArrayList<String> languages, genres;
    private Date release_date;
    private int app_id, age, dlcs, avg_playtime;
    private float price, upvotes;
    private boolean windows, mac, linux;

    public Game() {

        this.name = this.owners = this.website = this.developers = null;
        this.languages = new ArrayList<String>();
        this.genres = new ArrayList<String>();
        this.release_date = null;
        this.app_id = this.age = this.dlcs = this.avg_playtime = -1;
        this.price = this.upvotes = -1;
        this.windows = this.mac = this.linux = false;
    }

    public Game(String name, String owners, String website, String developers, ArrayList<String> languages,
            ArrayList<String> genres, Date release_date, int app_id, int age, int dlcs, int upvotes, int avg_playtime,
            float price, boolean windows, boolean mac, boolean linux) {

        this.name = name;
        this.owners = owners;
        this.website = website;
        this.developers = developers;
        this.languages = languages;
        this.genres = genres;
        this.release_date = release_date;
        this.app_id = app_id;
        this.age = age;
        this.dlcs = dlcs;
        this.upvotes = upvotes;
        this.avg_playtime = avg_playtime;
        this.price = price;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setReleaseDate(Date release_date) {
        this.release_date = release_date;
    }

    public void setAppId(int app_id) {
        this.app_id = app_id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDlcs(int dlcs) {
        this.dlcs = dlcs;
    }

    public void setAvgPlaytime(int avg_playtime) {
        this.avg_playtime = avg_playtime;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setUpvotes(float upvotes) {
        this.upvotes = upvotes;
    }

    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    public void setMac(boolean mac) {
        this.mac = mac;
    }

    public void setLinux(boolean linux) {
        this.linux = linux;
    }

    public String getName() {
        return this.name;
    }

    public String getOwners() {
        return this.owners;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getDevelopers() {
        return this.developers;
    }

    public ArrayList<String> getLanguages() {
        return this.languages;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public Date getReleaseDate() {
        return this.release_date;
    }

    public int getAppId() {
        return this.app_id;
    }

    public int getAge() {
        return this.age;
    }

    public int getDlcs() {
        return this.dlcs;
    }

    public int getAvgPlaytime() {
        return this.avg_playtime;
    }

    public float getPrice() {
        return this.price;
    }

    public float getUpvotes() {
        return this.upvotes;
    }

    public boolean getWindows() {
        return this.windows;
    }

    public boolean getMac() {
        return this.mac;
    }

    public boolean getLinux() {
        return this.linux;
    }

    public Game clone() {

        Game cloned = new Game();

        cloned.name = this.name;
        cloned.owners = this.owners;
        cloned.website = this.website;
        cloned.developers = this.developers;
        cloned.languages = this.languages;
        cloned.genres = this.genres;
        cloned.release_date = this.release_date;
        cloned.app_id = this.app_id;
        cloned.age = this.age;
        cloned.dlcs = this.dlcs;
        cloned.avg_playtime = this.avg_playtime;
        cloned.price = this.price;
        cloned.upvotes = this.upvotes;
        cloned.windows = this.windows;
        cloned.mac = this.mac;
        cloned.linux = this.linux;

        return cloned;
    }

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

        // ---------------------------------- //

        // Find release date
        if (line.charAt(atr_index) != ',') {

            SimpleDateFormat df;

            if (line.charAt(atr_index) == '\"') {

                df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                atr_index++;
                busca = '\"';
            } else {

                df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

                busca = ',';
            }

            while (true) {

                index++;

                if (line.charAt(index) == busca) {

                    try {
                        this.release_date = df.parse(line.substring(atr_index, index));
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

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

        // ---------------------------------- //

        // Find "Owners"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.owners = line.substring(atr_index, index);

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "Age"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.age = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "Price"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.price = Float.parseFloat(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "DLCs"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.dlcs = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "Languages"
        while (true) {

            index++;

            if (line.charAt(index) == ']') {

                index++;

                if (line.charAt(index) == ',')
                    index++;
                else if (line.charAt(index) == '\"')
                    index += 2;

                atr_index = index;
                break;
            } else if (line.charAt(index) == '\'') {

                int wordStart = index + 1;

                while (true) {

                    index++;

                    if (line.charAt(index) == '\'') {

                        this.languages.add(line.substring(wordStart, index));
                        break;
                    }
                }
            }
        }

        // ---------------------------------- //

        // Find "Website"
        if (line.charAt(atr_index) != ',') {

            if (line.charAt(atr_index) == '\"') {

                atr_index++;
                busca = '\"';
            } else
                busca = ',';

            while (true) {

                index++;

                if (line.charAt(index) == busca) {

                    this.website = line.substring(atr_index, index);

                    atr_index = ++index;
                    break;
                }
            }
        } else
            atr_index = ++index;

        // ---------------------------------- //

        // Find "Windows"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.windows = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Find "Mac"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.mac = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Find "Linux"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.linux = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "Upvotes"
        int positives, negatives;

        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                positives = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                negatives = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        this.upvotes = (float) (positives * 100) / (float) (positives + negatives);

        // ---------------------------------- //

        // Find "AVG Playtime"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.avg_playtime = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "Developers"
        if (line.charAt(atr_index) != ',') {

            if (line.charAt(atr_index) == '\"') {

                atr_index++;
                busca = '\"';
            } else
                busca = ',';

            while (true) {

                index++;

                if (line.charAt(index) == busca) {

                    this.developers = line.substring(atr_index, index);

                    atr_index = ++index;
                    break;
                }
            }
        } else
            atr_index = ++index;

        // ---------------------------------- //

        // Find "Genres"
        if (index < line.length() - 1) {

            if (line.charAt(index) == ',')
                atr_index = ++index;
            if (line.charAt(atr_index) == '\"') {

                atr_index++;

                while (true) {

                    index++;

                    if (line.charAt(index) == ',') {

                        this.genres.add(line.substring(atr_index, index));

                        atr_index = ++index;
                    } else if (line.charAt(index) == '\"') {

                        this.genres.add(line.substring(atr_index, line.length() - 1));
                        break;
                    }
                }
            } else
                this.genres.add(line.substring(atr_index, line.length()));
        }

        // --------------------------------------------------------------------------------
        // //
    }

    public String print() {
        String resp = new String();
        String avg_pt = null;

        if (this.avg_playtime == 0)
            avg_pt = "null ";
        else if (this.avg_playtime < 60)
            avg_pt = this.avg_playtime + "m ";
        else {

            if (this.avg_playtime % 60 == 0)
                avg_pt = this.avg_playtime / 60 + "h ";
            else
                avg_pt = (this.avg_playtime / 60) + "h " + (this.avg_playtime % 60) + "m ";
        }

        DecimalFormat df = new DecimalFormat("##");

        resp = this.app_id + " " + this.name + " " + dateFormat.format(this.release_date) + " " + this.owners + " "
                + this.age + " " + String.format(Locale.ENGLISH, "%.2f", this.price) + " " + this.dlcs + " "
                + this.languages + " " + this.website + " " + this.windows + " " + this.mac + " " + this.linux + " "
                + (Float.isNaN(this.upvotes) ? "0% " : df.format(this.upvotes) + "% ") + avg_pt + this.developers + " "
                + this.genres;
        return resp;

    }

    // --------------------------------------------------------------------------------------
    // //

    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }

}