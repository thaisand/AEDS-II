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

public class Q4 {
    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Game> games = new ArrayList<Game>();

        // ------------------------------------------------------------------------------

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
        ListaDupla listaDupla = new ListaDupla();
        int contador = -1;

        String line = scr.nextLine();

        while (isFim(line) == false) {
            int appId = Integer.parseInt(line);
            Game tmp = new Game();

            // Search game with .in id
            for (Game game : games) {
                if (game.getAppId() == appId) {
                    tmp = game.clone();
                    listaDupla.inserirFim(tmp);
                }
            }
            line = scr.nextLine();
            contador++;
        }

        // imprime lista de games ordenada pela data
        listaDupla.quicksort(0, contador);
        listaDupla.imprimir();
        scr.close();

    }
}

class CelulaDupla {
    public Game elemento; // Elemento inserido na celula.
    public CelulaDupla ant; // Aponta a celula prox.
    public CelulaDupla prox; // Aponta a celula prox.

    public CelulaDupla() {

    }

    public CelulaDupla(Game elemento) {
        this.elemento = elemento;
        this.ant = this.prox = null;
    }
}

class ListaDupla {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;

    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

    public void inserirInicio(Game x) {
        CelulaDupla tmp = new CelulaDupla(x);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;

        if (primeiro == ultimo) {
            ultimo = tmp;
        } else {
            tmp.prox.ant = tmp;
        }
        tmp = null;
    }

    public void inserirFim(Game x) {
        CelulaDupla tmp = new CelulaDupla(x);
        ultimo.prox = tmp;
        tmp.ant = ultimo;
        ultimo = tmp;
        tmp = null;
    }

    public void inserir(Game x, int pos) throws Exception {
        if (pos < 0 || pos > tamanho()) {
            throw new Exception("Erro! Posição inválida.");
        } else if (pos == 0) {
            inserirInicio(x);
        } else if (pos == tamanho()) {
            inserirFim(x);
        } else {
            // Caminhar ate a posicao anterior a insercao
            CelulaDupla i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;

            CelulaDupla tmp = new CelulaDupla(x);
            tmp.ant = i;
            tmp.prox = i.prox;
            tmp.ant.prox = tmp.prox.ant = tmp;
            tmp = i = null;
        }
    }

    public Game removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro! Lista vazia.");
        }

        CelulaDupla tmp = primeiro;
        primeiro = primeiro.prox;
        Game resp = primeiro.elemento;
        tmp.prox = primeiro.ant = null;
        tmp = null;
        return resp;
    }

    public Game removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover lista!");
        }
        Game resp = ultimo.elemento;
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
        return resp;
    }

    public Game remover(int pos) throws Exception {
        Game resp;
        int tamanho = tamanho();

        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover lista!");

        } else if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro! Posicao invalida");
        } else if (pos == 0) {
            resp = removerInicio();
        } else if (pos == tamanho - 1) {
            resp = removerFim();
        } else {
            CelulaDupla i = primeiro.prox;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;
            // i == pos
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            resp = i.elemento;
            i.prox = i.ant = null;
            i = null;
        }

        return resp;
    }

    public int tamanho() {
        int tamanho = 0;
        for (CelulaDupla i = primeiro; i != ultimo; i = i.prox) {
            tamanho++;
        }
        return tamanho;
    }

    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
        DecimalFormat df = new DecimalFormat("##");
        CelulaDupla tmp = primeiro;
        String avg_pt = null;

        for (tmp = tmp.prox; tmp != null; tmp = tmp.prox) {
            if (tmp.elemento.getAvgPlaytime() == 0)
                avg_pt = "null ";
            else if (tmp.elemento.getAvgPlaytime() < 60)
                avg_pt = tmp.elemento.getAvgPlaytime() + "m ";
            else {

                if (tmp.elemento.getAvgPlaytime() % 60 == 0)
                    avg_pt = tmp.elemento.getAvgPlaytime() / 60 + "h ";
                else
                    avg_pt = (tmp.elemento.getAvgPlaytime() / 60) + "h " + (tmp.elemento.getAvgPlaytime() % 60) + "m ";
            }

            System.out.println(tmp.elemento.getAppId() + " "
                    + tmp.elemento.getName() + " " + sdf.format(tmp.elemento.getReleaseDate()) + " "
                    + tmp.elemento.getOwners() + " " + tmp.elemento.getAge() + " " + tmp.elemento.getPrice() + " "
                    + tmp.elemento.getDlcs() + " " + tmp.elemento.getLanguages() + " "
                    + tmp.elemento.getWebsite() + " " + tmp.elemento.getWindows() + " "
                    + tmp.elemento.getMac() + " " + tmp.elemento.getLinux() + " "
                    + (Float.isNaN(tmp.elemento.getUpvotes()) ? "0% " : df.format(tmp.elemento.getUpvotes()) + "%")
                    + " "
                    + avg_pt + " " + tmp.elemento.getDevelopers() + " " + tmp.elemento.getGenres() + " ");

        }

    }

    public CelulaDupla posCelula(int pos) {
        CelulaDupla resp = primeiro.prox;
        for (int j = 0; j < pos; j++, resp = resp.prox)
            ;
        return resp;
    }

    void quicksort(int esq, int dir) {
        // VARIAVEIS
        int i = esq;
        int j = dir;
        int pivo = (esq + dir) / 2;
        while (i <= j) {
            while ((posCelula(i).elemento.getReleaseDate().compareTo(posCelula(pivo).elemento.getReleaseDate()) < 0)
                    || ((posCelula(i).elemento.getReleaseDate()
                            .compareTo(posCelula(pivo).elemento.getReleaseDate()) == 0)
                            && (posCelula(i).elemento.getName()
                                    .compareTo(posCelula(pivo).elemento.getName()) < 0))) {
                i++;
            }
            while ((posCelula(j).elemento.getReleaseDate().compareTo(posCelula(pivo).elemento.getReleaseDate()) > 0)
                    || ((posCelula(j).elemento.getReleaseDate()
                            .compareTo(posCelula(pivo).elemento.getReleaseDate()) == 0)
                            && (posCelula(j).elemento.getName()
                                    .compareTo(posCelula(pivo).elemento.getName()) > 0))) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j) {
            quicksort(esq, j);
        }
        if (i < dir) {
            quicksort(i, dir);
        }
    }

    void swap(int posA, int posB) {
        Game tmp = posCelula(posA).elemento;
        posCelula(posA).elemento = posCelula(posB).elemento;
        posCelula(posB).elemento = tmp;
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
            ArrayList<String> genres, Date release_date, int app_id, int age, int dlcs, int upvotes,
            int avg_playtime,
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
                + (Float.isNaN(this.upvotes) ? "0% " : df.format(this.upvotes) + "% ") + avg_pt + this.developers
                + " "
                + this.genres;
        return resp;

    }

    // --------------------------------------------------------------------------------------
    // //

    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }

}
