import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

 public class Q6 {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        Lista lista = new Lista();
        int contador = -1;
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

        // ler linhas stdin
        String line = sc.nextLine();
        while (isFim(line) == false) {
            int app_id = Integer.parseInt(line);
            Game tmp = new Game();
            // PREENCHER GAME TMP
            for (Game game : games) {
                if (game.getAppId() == app_id)
                    tmp = game.clone();
            }
            lista.inserirFim(tmp);
            contador++;
            line = sc.nextLine();
        }

        // imprimir lista de games ORDENADOS
        lista.quicksort(0, contador);
        lista.imprimir();
        sc.close();
    }

    public static boolean isFim(String line) {
        return line.compareTo("FIM") == 0;
    }
}

class Game {

    // variáveis
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
    private String name, owners, website, developers;
    private ArrayList<String> languages, genres;
    private Date release_date;
    private int app_id, age, dlcs, avg_playtime;
    private float price, upvotes;
    private boolean windows, mac, linux;

    // construtor vazio 
    public Game() {

        this.name = this.owners = this.website = this.developers = null;
        this.languages = new ArrayList<String>();
        this.genres = new ArrayList<String>();
        this.release_date = null;
        this.app_id = this.age = this.dlcs = this.avg_playtime = -1;
        this.price = this.upvotes = -1;
        this.windows = this.mac = this.linux = false;
    }

    //construtor com argumentos
    public Game(String name, String owners, String website, String developers, ArrayList<String> languages, ArrayList<String> genres, Date release_date, int app_id, int age, int dlcs, int upvotes, int avg_playtime, float price, boolean windows, boolean mac, boolean linux) {

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

    // setter's e getter's
    public void setName(String name) { this.name = name; }
    public void setOwners(String owners) { this.owners = owners; }
    public void setWebsite(String website) { this.website = website; }
    public void setDevelopers(String developers) { this.developers = developers; }
    public void setLanguages(ArrayList<String> languages) { this.languages = languages; }
    public void setGenres(ArrayList<String> genres) { this.genres = genres; }
    public void setReleaseDate(Date release_date) { this.release_date = release_date; }
    public void setAppId(int app_id) { this.app_id = app_id; }
    public void setAge(int age) { this.age = age; }
    public void setDlcs(int dlcs) { this.dlcs = dlcs; }
    public void setAvgPlaytime(int avg_playtime) { this.avg_playtime = avg_playtime; }
    public void setPrice(float price) { this.price = price; }
    public void setUpvotes(float upvotes) { this.upvotes = upvotes; }
    public void setWindows(boolean windows) { this.windows = windows; }
    public void setMac(boolean mac) { this.mac = mac; }
    public void setLinux(boolean linux) { this.linux = linux; }

    public String getName() { return this.name; }
    public String getOwners() { return this.owners; }
    public String getWebsite() { return this.website; }
    public String getDevelopers() { return this.developers; }
    public ArrayList<String> getLanguages() { return this.languages; }
    public ArrayList<String> getGenres() { return this.genres; }
    public Date getReleaseDate() { return this.release_date; }
    public int getAppId() { return this.app_id; }
    public int getAge() { return this.age; }
    public int getDlcs() { return this.dlcs; }
    public int getAvgPlaytime() { return this.avg_playtime; }
    public float getPrice() { return this.price; }
    public float getUpvotes() { return this.upvotes; }
    public boolean getWindows() { return this.windows; }
    public boolean getMac() { return this.mac; }
    public boolean getLinux() { return this.linux; }
    
    // metodo clone
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
    
    // metodo que le linha e formata infos
    public void read(String line) {
        

        char busca;
        int index = 0, atr_index = 0;

        // AppID
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.app_id = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Name
        if(line.charAt(atr_index) != ',') {

            if(line.charAt(atr_index) == '\"') {
                
                atr_index++;
                busca = '\"';
            }
            else busca = ',';
            
            while(true) {

                index++;

                if(line.charAt(index) == busca) {
                    
                    this.name = line.substring(atr_index, index);

                    if(busca == ',') index++;
                    else if(busca == '\"') index += 2;
                    
                    atr_index = index;
                    break;
                }
            }
        }
        else atr_index = ++index;

        // release date
        if(line.charAt(atr_index) != ',') {

            SimpleDateFormat df;
            
            if(line.charAt(atr_index) == '\"') {
                
                df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                atr_index++;
                busca = '\"';
            }
            else {
                
                df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

                busca = ',';
            }

            while(true) {

                index++;

                if(line.charAt(index) == busca) {
                    
                    try { this.release_date = df.parse(line.substring(atr_index, index)); } 
                    catch (java.text.ParseException e) { e.printStackTrace(); }

                    if(busca == ',') index++;
                    else if(busca == '\"') index += 2;
                    
                    atr_index = index;
                    break;
                }
            }
        }
        else atr_index = ++index;

        // Owners
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.owners = line.substring(atr_index, index);

                atr_index = ++index;
                break;
            }
        }

        // Age
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.age = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Price
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.price = Float.parseFloat(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // DLCs
        while(true) {

            index++;

            if(line.charAt(index) == ',') {
                
                this.dlcs = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Languages
        while(true) {

            index++;

            if(line.charAt(index) == ']') {

                index++;
                
                if(line.charAt(index) == ',') index++;
                else if(line.charAt(index) == '\"') index += 2;

                atr_index = index;
                break;
            }
            else if(line.charAt(index) == '\'') {

                int wordStart = index + 1;

                while(true) {

                    index++;

                    if(line.charAt(index) == '\'') {
                        
                        this.languages.add(line.substring(wordStart, index));
                        break;
                    }
                }
            }
        }

        // Website
        if(line.charAt(atr_index) != ',') {

            if(line.charAt(atr_index) == '\"') {
                
                atr_index++;
                busca = '\"';
            }
            else busca = ',';
            
            while(true) {

                index++;

                if(line.charAt(index) == busca) {
                    
                    this.website = line.substring(atr_index, index);

                    atr_index = ++index;
                    break;
                }
            }
        }
        else atr_index = ++index;

        // Windows
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.windows = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Mac
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.mac = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Linux
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.linux = Boolean.parseBoolean(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Upvotes
        int positives, negatives;

        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                positives = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                negatives = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        this.upvotes = (float)(positives * 100) / (float)(positives + negatives);

        // AVG Playtime
        while(true) {

            index++;

            if(line.charAt(index) == ',') {

                this.avg_playtime = Integer.parseInt(line.substring(atr_index, index));

                atr_index = ++index;
                break;
            }
        }

        // Developers
        if(line.charAt(atr_index) != ',') {

            if(line.charAt(atr_index) == '\"') {
                
                atr_index++;
                busca = '\"';
            }
            else busca = ',';
            
            while(true) {

                index++;

                if(line.charAt(index) == busca) {
                    
                    this.developers = line.substring(atr_index, index);

                    atr_index = ++index;
                    break;
                }
            }
        }
        else atr_index = ++index;
       
        // Genres
        if(index < line.length() - 1) {

            if(line.charAt(index) == ',') atr_index = ++index;                    
            if(line.charAt(atr_index) == '\"') {

                atr_index++;
                
                while(true) {

                    index++;

                    if(line.charAt(index) == ',') {
                        
                        this.genres.add(line.substring(atr_index, index));

                        atr_index = ++index;
                    }
                    else if(line.charAt(index) == '\"') {

                        this.genres.add(line.substring(atr_index, line.length() - 1));
                        break;
                    }
                }
            }
            else this.genres.add(line.substring(atr_index, line.length()));
        }

    }

    // imprime game 
    public String print() {
        String resp = new String();
        String avg_pt = null;

        if(this.avg_playtime == 0) avg_pt = "null ";
        else if(this.avg_playtime < 60) avg_pt = this.avg_playtime + "m ";
        else {

            if(this.avg_playtime % 60 == 0) avg_pt = this.avg_playtime / 60 + "h ";
            else avg_pt = (this.avg_playtime / 60) + "h " + (this.avg_playtime % 60) + "m ";
        }

        DecimalFormat df = new DecimalFormat("##");

        resp = this.app_id + " " + this.name + " " + dateFormat.format(this.release_date) + " " + this.owners + " " + this.age + " " + String.format(Locale.ENGLISH, "%.2f", this.price) + " " + this.dlcs + " " + this.languages + " " + this.website + " " + this.windows + " " + this.mac + " " + this.linux + " " + (Float.isNaN(this.upvotes) ? "0% " : df.format(this.upvotes) + "% ") + avg_pt + this.developers + " " + this.genres;
        return resp;

    }

    // checa se é o fim das entradas
    public static boolean isFim(String line) { 
        return line.compareTo("FIM") == 0; 
    }

}

class Lista {
    Game games[] = new Game[100];
    private int contador = 0; 

    // INSERIR NO INICIO
    void inserirInicio(Game game) throws Exception {
        if (contador >= games.length) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = contador; i > 0; i--) {
            games[i] = games[i - 1];
        }
        games[0] = game;
        contador++;
    }
    // INSERIR NO FIM
    void inserirFim(Game game) throws Exception {
        if (contador >= games.length) {
            throw new Exception("Erro ao inserir!");
        }
        games[contador] = game;
        contador++;
    }
    // INSERIR EM POSICAO 
    void inserir(Game game, int pos) throws Exception {
        if (contador >= games.length) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = contador; i > pos; i--) {
            games[i] = games[i - 1];
        }
        games[pos] = game;
        contador++;
    }

    // REMOVER DO INICIO
    Game removerInicio() throws Exception {
        if (contador == 0) {
            throw new Exception("Erro ao remover!");
        }
        Game game = games[0];
        for (int i = 0; i < contador; i++) {
            games[i] = games[i + 1];
        }
        contador--;
        return game;
    }

    // REMOVER DO FIM
    Game removerFim() throws Exception {
        if (contador == 0) {
            throw new Exception("Erro ao remover!");
        }
        Game game = games[contador - 1];
        contador--;
        return game;
    }

    // REMOVER DE POSICAO 
    Game remover(int pos) throws Exception {
        if ((contador == 0) || (pos < 0) || (pos >= contador)) {
            throw new Exception("Erro ao remover!");
        }
        Game game = games[pos];
        contador--;
        for (int i = pos; i < contador; i++) {
            games[i] = games[i + 1];
        }
        return game;
    }

    // ORDENAR POR RELEASE DATE --> ORDENAR POR NOME
    public void quicksort(int esq, int dir) {
        int i = esq;
        int j = dir;
        int pivo = (esq+dir)/2;
        while (i <= j) {
            while((games[i].getReleaseDate().compareTo(games[pivo].getReleaseDate()) < 0) || ((games[i].getReleaseDate().compareTo(games[pivo].getReleaseDate()) == 0) && (games[i].getName().compareTo(games[pivo].getName()) < 0))) {
                i++;
            }
            while((games[j].getReleaseDate().compareTo(games[pivo].getReleaseDate()) > 0) || ((games[j].getReleaseDate().compareTo(games[pivo].getReleaseDate()) == 0) && (games[j].getName().compareTo(games[pivo].getName()) > 0))) {
                j--;
            }
            if (i<=j) {
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
        Game tmp = new Game();
        tmp = games[posA];
        games[posA] = games[posB];
        games[posB] = tmp;
    }

    public void imprimir() {
        String avg_pt = null;
        DecimalFormat df = new DecimalFormat("##");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
        
        for (int i = 0; i < contador; i++) {
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
            System.out.println(
                    games[i].getAppId() + " " +
                            games[i].getName() + " " + sdf.format(games[i].getReleaseDate())
                            + " "
                            + games[i].getOwners() + " " + games[i].getAge() + " "
                            + String.format(Locale.ENGLISH, "%.2f", games[i].getPrice()) + " "
                            + games[i].getDlcs() + " " + games[i].getLanguages() + " " + games[i].getWebsite() + " "
                            + games[i].getWindows()
                            + " " + games[i].getMac() + " " + games[i].getLinux() + " "
                            + (Float.isNaN(games[i].getUpvotes()) ? "0% " : df.format(games[i].getUpvotes()) + "% ")
                            + avg_pt + games[i].getDevelopers() + " " + games[i].getGenres());
        }
    }
}

