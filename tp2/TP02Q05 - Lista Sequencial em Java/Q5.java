import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ArrayList<Game> games = new ArrayList<Game>();
        Lista lista = new Lista();

        // le arquivo games.csv e salva em games
        try {
            String basefile = "tmp/games.csv";
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

        // ler linhas stdin
        String gameIn = sc.nextLine();
        while (isFim(gameIn) == false) {
            int appId = Integer.parseInt(gameIn);
            Game tmp = new Game();
            for (Game gameI : games) {
                if (appId == gameI.getAppId()) {
                    tmp = gameI.clone();
                }
            }
            // ler stdin - novo input
            lista.inserirFim(tmp);
            gameIn = sc.nextLine();
        }
        // SEGUNDA PARTE - LISTA OPERACOES
        gameIn = sc.nextLine();
        // VARIAVEIS
        int operacoes = Integer.parseInt(gameIn);
        int appId = 0;
        String operacao = "";
        int pos = 0;
        // realizar n operacoes
        while (operacoes > 0) {
            gameIn = sc.nextLine();
            Game tmp = new Game();
            // identificar operacao
            operacao = gameIn.substring(0, 2);
            // SE OPERACAO FOR DO TIPO INSERIR --> PEGAR NOME game
            if ((operacao.equals("II")) || (operacao.equals("IF"))) {
                appId = Integer.parseInt(gameIn.substring(6));
                for (Game gameI : games) {
                    if (appId == gameI.getAppId()) {
                        tmp = gameI.clone();
                    }
                }
                if (operacao.equals("II")) {
                    lista.inserirInicio(tmp);
                } else if (operacao.equals("IF")) {
                    lista.inserirFim(tmp);
                }
            } else if (operacao.equals("I*")) {
                pos = Integer.parseInt(gameIn.substring(3, 5));
                appId = Integer.parseInt(gameIn.substring(6));
                for (Game gameI : games) {
                    if (appId == gameI.getAppId()) {
                        tmp = gameI.clone();
                    }
                }
                lista.inserir(tmp, pos);
            } else if (operacao.equals("R*")) {
                pos = Integer.parseInt(gameIn.substring(3));
                tmp = lista.remover(pos);
                System.out.println("(R) " + tmp.getName());
            } else if (operacao.equals("RI")) {
                tmp = lista.removerInicio();
                System.out.println("(R) " + tmp.getName());
            } else if (operacao.equals("RF")) {
                tmp = lista.removerFim();
                System.out.println("(R) " + tmp.getName());
            }

            operacoes--;
        }
        // imprimir lista de games
        lista.imprimir();
        sc.close();
    }

    // ACHA O FIM DO ARQUIVO
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }

}

class Lista {
    // VARIAVEIS
    Game games[] = new Game[61];
    private int contadorLista = 0;

    // INSERIR NO INICIO
    void inserirInicio(Game game) throws Exception {
        if (contadorLista >= games.length) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = contadorLista; i > 0; i--) {
            games[i] = games[i - 1];
        }
        games[0] = game;
        contadorLista++;
    }

    // INSERIR
    void inserir(Game game, int pos) throws Exception {
        if ((contadorLista >= games.length) || (pos > contadorLista) || (pos < 0)) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = contadorLista; i > pos; i--) {
            games[i] = games[i - 1];
        }
        games[pos] = game;
        contadorLista++;
    }

    // INSERIR FIM
    void inserirFim(Game game) throws Exception {
        if (contadorLista >= games.length) {
            throw new Exception("Erro ao inserir!");
        }

        games[contadorLista] = game;
        contadorLista++;
    }

    // REMOVER INICIO
    Game removerInicio() throws Exception {
        if (contadorLista == 0) {
            throw new Exception("Erro ao remover!");
        }
        Game game = games[0];
        contadorLista--;
        for (int i = 0; i < contadorLista; i++) {
            games[i] = games[i + 1];
        }
        return game;
    }

    // REMOVER
    Game remover(int pos) throws Exception {
        if ((contadorLista == 0) || (pos < 0) || (pos >= contadorLista)) {
            throw new Exception("Erro ao remover!");
        }
        Game game = games[pos];
        contadorLista--;
        for (int i = pos; i < contadorLista; i++) {
            games[i] = games[i + 1];
        }
        return game;
    }

    // REMOVER FIM
    Game removerFim() throws Exception {
        if (contadorLista == 0) {
            throw new Exception("Erro ao remover!");
        }
        Game game = games[contadorLista - 1];
        contadorLista--;
        return game;
    }

    public void imprimir() throws Exception {
        String avg_pt = null;
        DecimalFormat df = new DecimalFormat("##");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < contadorLista; i++) {
            if (games[i].getAvgPlaytime() == 0)
                avg_pt = "null ";
            else if (games[i].getAvgPlaytime() < 60)
                avg_pt = games[i].getAvgPlaytime() + "m ";
            else {
                if (games[i].getAvgPlaytime() % 60 == 0)
                    avg_pt = games[i].getAvgPlaytime() / 60 + "h ";
                else
                    avg_pt = (games[i].getAvgPlaytime() / 60) + "h " + (games[i].getAvgPlaytime() % 60) + "m ";
            }
            System.out.print(
                    "[" + i + "] "
                            + games[i].getAppId() + " " + games[i].getName() + " "
                            + sdf.format(games[i].getReleaseDate()) + " "
                            + games[i].getOwners() + " " + games[i].getAge() + " "
                            + String.format(Locale.ENGLISH, "%.2f", games[i].getPrice()) + " "
                            + games[i].getDlcs() + " " + games[i].getLanguages() + " " + games[i].getWebsite() + " "
                            + games[i].getWindows() + " " + games[i].getMac() + " "
                            + games[i].getLinux() + " "
                            + (Float.isNaN(games[i].getUpvotes()) ? "0% " : df.format(games[i].getUpvotes()) + "% ")
                            + avg_pt
                            + games[i].getDevelopers() + " " + games[i].getGenres());
        }
    }
}

class Game {

    static SimpleDateFormat default_dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
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

        char c_search;
        int index = 0, atr_index = 0;

        // ---------------------------------- //

        // Find "AppID"
        while (true) {

            index++;

            if (line.charAt(index) == ',') {

                this.app_id = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // ---------------------------------- //

        // Find "Name"
        if (line.charAt(atr_index) != ',') {

            if (line.charAt(atr_index) == '\"') {
                atr_index++;
                c_search = '\"';
            } else
                c_search = ',';

            while (true) {

                index++;

                if (line.charAt(index) == c_search) {

                    this.name = line.substring(atr_index, index);

                    if (c_search == ',')
                        index++;
                    else if (c_search == '\"')
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
                c_search = '\"';
            } else {

                df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

                c_search = ',';
            }

            while (true) {

                index++;

                if (line.charAt(index) == c_search) {

                    try {
                        this.release_date = df.parse(line.substring(atr_index, index));
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                    if (c_search == ',')
                        index++;
                    else if (c_search == '\"')
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
                c_search = '\"';
            } else
                c_search = ',';

            while (true) {

                index++;

                if (line.charAt(index) == c_search) {

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
                c_search = '\"';
            } else
                c_search = ',';

            while (true) {

                index++;

                if (line.charAt(index) == c_search) {

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

    public void print() {

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

        System.out.println(this.app_id + " " + this.name + " " + default_dateFormat.format(this.release_date) + " "
                + this.owners + " " + this.age + " " + String.format(Locale.ENGLISH, "%.2f", this.price) + " "
                + this.dlcs + " " + this.languages + " " + this.website + " " + this.windows + " " + this.mac + " "
                + this.linux + " " + (Float.isNaN(this.upvotes) ? "0% " : df.format(this.upvotes) + "% ") + avg_pt
                + this.developers + " " + this.genres);
    }


}