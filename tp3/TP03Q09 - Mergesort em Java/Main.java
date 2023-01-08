import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        String[] entrada = new String[1000];
        Scanner sc = new Scanner(new File("/tmp/games.csv"));
        Scanner scanner = new Scanner(System.in, "UTF-8");
        int n = 0, contador = 0;
        Game[] game = new Game[5000];
        Lista lista = new Lista();

        while (sc.hasNext()) {
            game[contador] = new Game();
            String[] lineFilter = sc.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            game[contador].setAppId(Integer.parseInt(lineFilter[0]));
            game[contador].setName(lineFilter[1]);
            String dateFormate = lineFilter[2].replace("\"", "");
            String date = dateFormate.substring(0, 3) + "/"
                    + dateFormate.substring(dateFormate.length() - 4, dateFormate.length());
            game[contador].setDate((new SimpleDateFormat("MMM/yyyy", Locale.US).parse(date)));
            game[contador].setOwners(lineFilter[3]);
            game[contador].setAge(Integer.parseInt(lineFilter[4]));
            game[contador].setPrice(Float.parseFloat(lineFilter[5]));
            game[contador].setDlcs(Integer.parseInt(lineFilter[6]));
            game[contador].setLanguages(lineFilter[7].split("(\"\\[')|(', ')|('\\]\")"));
            game[contador].setWebsite(lineFilter[8]);
            game[contador].setWindows(Boolean.valueOf(lineFilter[9]));
            game[contador].setMac(Boolean.valueOf(lineFilter[10]));
            game[contador].setLinux(Boolean.valueOf(lineFilter[11]));
            game[contador].setUpVotes(Float.parseFloat(lineFilter[12])
                    / (Float.parseFloat(lineFilter[12]) + Float.parseFloat(lineFilter[13])));
            game[contador].setAvgPt(Integer.parseInt(lineFilter[14]));
            game[contador].setDevelopers(lineFilter[15]);
            game[contador].setGenres((lineFilter.length > 16) ? lineFilter[16].split(",") : null);
            contador++;
        }

        do {
            entrada[n] = scanner.nextLine();
        } while (!(isFim(entrada[n++])));
        n--;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < contador; j++) {
                if (Integer.parseInt(entrada[i]) == game[j].getAppId()) {
                    lista.inserirFim(game[j]);
                }
            }
        }
        
        lista.sort();
        lista.mostrar();
        sc.close();
        scanner.close();
    }

    static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }
}

class Game {
    // ATRIBUTOS
    private int appId;
    private int age;
    private int avgPt;
    private int dlcs;
    private Date releaseDate;
    private String name;
    private String owners;
    private String website;
    private String developers;
    private String[] languages;
    private String[] genres;
    private float price;
    private float upvotes;
    private boolean windows;
    private boolean mac;
    private boolean linux;

    // CONSTRUTORES
    Game() {
        appId = 0;
        name = "";
        releaseDate = null;
        owners = "";
        age = 0;
        price = 0;
        dlcs = 0;
        languages = null;
        website = "";
        windows = true;
        mac = true;
        linux = true;
        upvotes = 0;
        avgPt = 0;
        developers = "";
        genres = null;
    }

    // CLONE
    public Game clone(){
        Game temp = new Game();
        temp.appId = this.appId;
        temp.age = this.age;
        temp.avgPt = this.avgPt;
        temp.dlcs = this.dlcs;
        temp.releaseDate = this.releaseDate;
        temp.name = this.name;
        temp.owners = this.owners;
        temp.website = this.website;
        temp.developers = this.developers;
        temp.languages = this.languages;
        temp.genres = this.genres;
        temp.price = this.price;
        temp.upvotes = this.upvotes;
        temp.windows = this.windows;
        temp.mac = this.mac;
        temp.linux = this.linux;
        return temp;
    }

    SimpleDateFormat formatar = new SimpleDateFormat("MMM/yyyy", Locale.US);

    // GETTER'S E SETTER'S
    public int getAppId() {
        return this.appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() throws Exception{
        Date data = formatar.parse(formatar.format(this.releaseDate));
        return data;
    }

    public void setDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOwners() {
        return this.owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDlcs() {
        return this.dlcs;
    }

    public void setDlcs(int dlcs) {
        this.dlcs = dlcs;
    }

    public String[] getLanguages() {
        return this.languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getWindows() {
        return this.windows;
    }

    public void setWindows(Boolean windows) {
        this.windows = windows;
    }

    public Boolean getMac() {
        return this.mac;
    }

    public void setMac(Boolean mac) {
        this.mac = mac;
    }

    public Boolean getLinux() {
        return this.linux;
    }

    public void setLinux(Boolean linux) {
        this.linux = linux;
    }

    public Float getUpVotes() {
        return this.upvotes;
    }

    public void setUpVotes(float upvotes) {
        this.upvotes = upvotes;
    }

    public int getAvgPt() {
        return this.avgPt;
    }

    public void setAvgPt(int avgPt) {
        this.avgPt = avgPt;
    }

    public String getDevelopers() {
        return this.developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String[] getGenres() {
        return this.languages;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    // PRINT'S
    void printPreco() {
        System.out.format(Locale.US, "%.2f ", this.price);
    }

    void printLinguas() {
        System.out.print("[");
        if (languages.length == 1)
            System.out.print(languages[0].replaceAll("[\\[\\]']", "") + "] ");
        else {

            for (int i = 1; i < languages.length - 1; i++)
                System.out.print(languages[i] + ", ");

            System.out.print(languages[languages.length - 1] + "] ");
        }
    }

    void printWebsite() {
        if (this.website.isEmpty()) {
            System.out.print("null ");
        } else {
            System.out.print(this.website + " ");
        }
    }

    void printUpvotes() {
        System.out.print((int) Math.round(this.upvotes * 100) + "% ");
    }

    void printAVG() {
        int horas = this.avgPt / 60, minutos = this.avgPt % 60;
        if (horas == 0 && minutos == 0)
            System.out.print("null ");
        else if (horas > 0) {
            System.out.print(horas + "h ");
        } else {
            System.out.print(horas + "");
        }
        if (minutos > 0) {
            System.out.print("m ");
        } else {
            System.out.print("");
        }
    }

    void printGenero() {
        System.out.print("[");
        if (genres.length == 1)
            System.out.print(genres[0].replace("\"", "") + "]\n");
        else {
            System.out.print(genres[0].replace("\"", "") + ", ");
            for (int i = 1; i < genres.length - 1; i++)
                System.out.print(genres[i] + ", ");
            System.out.print(genres[genres.length - 1].replace("\"", "") + "]\n");
        }
    }

    void print() {
        System.out.printf("%d %s %s %s %d ", this.appId, this.name, formatar.format(this.releaseDate), this.owners,
                this.age);
        printPreco();
        System.out.print(this.dlcs + " ");
        printLinguas();
        printWebsite();
        System.out.printf("%b %b %b ", this.windows, this.mac, this.linux);
        printUpvotes();
        printAVG();
        System.out.print(this.developers.replace("\"", "") + " ");
        printGenero();
    }
}

class Lista {
    // ATRIBUTOS
    private Game[] array;
    private int n;

    // CONSTRUTORES
    public Lista() {
        this(5000);
    }

    public Lista(int tam) {
        n = 0;
        array = new Game[5000];
    }

    // METODOS
    public void inserir(Game s, int pos) throws Exception { // Insere item
        if (n >= array.length) {
            throw new Exception("Tamanho excedido");
        }

        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }

        array[pos] = s;
        n++;
    }

    public void inserirInicio(Game s) throws Exception { // Insere Inicio
        if (n >= array.length) {
            throw new Exception("Tamanho excedido");
        }

        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = s;
        n++;
    }

    public void inserirFim(Game s) throws Exception { // Insere Fim
        if (n >= array.length) {
            throw new Exception("Tamanho excedido");
        }

        array[n] = s;
        n++;
    }

    public String remover(int pos) throws Exception { // Remove de posicao
        if (n == 0 || pos < 0 || pos >= n) {
            throw new Exception("Posicao inexistente da Lista");
        }

        String resp = array[pos].getName();
        n--;

        for (int i = pos; i < n; i++) {
            array[i] = array[i + 1];
        }

        return resp;
    }

    public String removerInicio() throws Exception { // Remove do inicio
        if (n == 0) {
            throw new Exception("Lista vazia");
        }

        String resp = array[0].getName();
        n--;

        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }

        return resp;
    }

    public String removerFim() throws Exception { // Remove do fim
        if (n == 0) {
            throw new Exception("Lista vazia");
        }

        return array[--n].getName();
    }

    public void mostrar() { // Mostra lista
        for (int i = 0; i < n; i++) {
            array[i].print();
        }
    }

    public void sort() {
        
        mergeSort(0, n-1);
    }

    private void mergeSort(int esq, int dir) {
        
        if (esq < dir){
           
            int meio = (esq + dir) / 2;
            mergeSort(esq, meio);
            mergeSort(meio + 1, dir);
            intercalar(esq, meio, dir);
        }
     }
  
     
     public void intercalar(int esq, int meio, int dir) {

        int n1, n2, i, j, k;
  
        //Definir tamanho dos dois subarrays
        n1 = meio-esq+1;
        n2 = dir - meio;
  
        Game[] a1 = new Game[n1+1];
        Game[] a2 = new Game[n2+1];
  

        //Inicializar primeiro subarray
        for(i = 0; i < n1; i++) {
           a1[i] = array[esq+i].clone();
        }

  
        //Inicializar segundo subarray
        for(j = 0; j < n2; j++){
           a2[j] = array[meio+j+1].clone();
        }


        //Sentinela no final dos dois arrays
        int max = 0x7FFFFFFF;
        float f = max;
        a1[i] = new Game();
        a2[j] = new Game();
        a1[i].setUpVotes(f);
        a2[j].setUpVotes(f);

  
        //Intercalacao propriamente dita
        for(i = j = 0, k = esq; k <= dir; k++){

           array[k] = (a1[i].getUpVotes() <= a2[j].getUpVotes()) ? a1[i++] : a2[j++];
        }
     }

    public void swap(int i, int j) { // faz swap dos elementos
        Game temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
