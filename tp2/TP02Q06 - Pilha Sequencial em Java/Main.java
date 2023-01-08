import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    
    public static void main(String [] args) throws Exception {

        String [] entrada = new String [1000];
        String [] entry = new String [1000];
        Scanner sc = new Scanner(new File("/tmp/games.csv")); 
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String comando = "", auxiliar = "";
        int n = 0;
        int contador = 0;
        int m = 0;
        int num = 0;

        Games [] game = new Games[5000]; 
        Lista lista = new Lista();

        while (sc.hasNext()) {
            game[contador] = new Games();
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
            game[contador].setAvg_pt(Integer.parseInt(lineFilter[14]));
            game[contador].setDevelopers(lineFilter[15]);
            game[contador].setGenres((lineFilter.length > 16) ? lineFilter[16].split(",") : null);
            contador++;
        }

        entrada[n] = scanner.nextLine();
        while (!(isFim(entrada[n]))) {
            n++;
            entrada[n] = scanner.nextLine();
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < contador; j++) {
                if(Integer.parseInt(entrada[i]) == game[j].getAppId()){
                    lista.inserirFim(game[j]);
                }
            }
        }
        num = Integer.parseInt(scanner.nextLine());
        do{
            entry[m] = scanner.nextLine();
            m++;
        } while (num > m);

        for(int k = 0; k < m; k++) {
            comando = entry[k].substring(0, 1);
            if(comando.equals("I")) {
                auxiliar = entry[k].substring(2);
                for(int l = 0; l < contador; l++) {
                    if(Integer.parseInt(auxiliar) == game[l].getAppId()) {
                        lista.inserirFim(game[l]);
                    }
                }
            }
            else {
                System.out.println("(R) " + lista.removerFim());
            }

        }
        lista.mostrar();
        sc.close();
        scanner.close();
    }

    static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

}
class Games {
    // Declaracao de atributos
    private int app_id;
    private int age;
    private int avg_pt;
    private int dlcs;
    private Date release_date;
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

    // Construtores vazios
    Games() {
    app_id = 0;
    name = "";
    release_date = null;
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
    avg_pt = 0;
    developers = "";
    genres = null;
    }
    
    SimpleDateFormat formataDate = new SimpleDateFormat("MMM/yyyy", Locale.US);

    //Getter's e setter's

    public int getAppId() {
        return this.app_id;
    }

    public void setAppId(int app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return formataDate.format(this.release_date);
    }

    public void setDate(Date release_date) {
        this.release_date = release_date;
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

    public int getAvg_pt() {
        return this.avg_pt;
    }

    public void setAvg_pt(int avg_pt) {
        this.avg_pt = avg_pt;
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

    // Impressoes
    void imprimirPrice() {
        System.out.format(Locale.US, "%.2f ", this.price);
    }

    void imprimirLinguas() {
        System.out.print("[");
        if (languages.length == 1) {
            System.out.print(languages[0].replaceAll("[\\[\\]']", "") + "] ");
        } else {
            for (int i = 1; i < languages.length - 1; i++)
                System.out.print(languages[i] + ", ");

            System.out.print(languages[languages.length - 1] + "] ");
        }
    }

    void imprimirWebSite() {
        if(this.website.isEmpty()){
            System.out.print("null ");
        }else{
            System.out.print(this.website + " ");
        }
    }

    void imprimirUpVotes() {
        System.out.print((int) Math.round(this.upvotes * 100) + "% ");
    }

    void imprimirAVG() {
        int horasContadas = this.avg_pt / 60, minutosPassados = this.avg_pt % 60;
        if (horasContadas == 0 && minutosPassados == 0)
            System.out.print("null ");

        else
        if(horasContadas > 0){
            System.out.print(horasContadas + "h ");
        }else{
            System.out.print(horasContadas + "");

        }
        if(minutosPassados > 0){
            System.out.print("m ");
        }else{
            System.out.print("");

        }
    }

    void imprimirGeneros() {

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

    void imprimir() {
        System.out.printf("%d %s %s %s %d ", this.app_id, this.name, formataDate.format(this.release_date), this.owners,
                this.age);
        imprimirPrice();
        System.out.print(this.dlcs + " ");
        imprimirLinguas();
        imprimirWebSite();
        System.out.printf("%b %b %b ", this.windows, this.mac, this.linux);
        imprimirUpVotes();
        imprimirAVG();
        System.out.print(this.developers.replace("\"", "") + " ");
        imprimirGeneros();
    }
}
class Lista {
    // Atributos
    private Games [] array;
    private int n;

    // Construtores
    public Lista(){
    this(1000);
    }   

    public Lista(int tam) {
        array = new Games [1000];
        n = 0;
    }

    // Metodos
    public void inserirInicio(Games s) throws Exception {                                  
        if(n >= array.length) {
            throw new Exception("Tamanho da Lista Excedido");
        }
        for(int i = n; i > 0; i--) {
            array[i] = array[i-1];
        }
        array[0] = s;
        n++;
    }

    public void inserirFim(Games s) throws Exception {                                        
        if(n >= array.length) {
            throw new Exception("Tamanho da Lista Excedido");
        }
        array[n] = s;
        n++;
    }

    public void inserir(Games s, int posicao) throws Exception {                                    
        if(n >= array.length) {
            throw new Exception("Tamanho da Lista Excedido");
        }

        for(int i = n; i > posicao; i--) {
            array[i] = array[i-1];
        }

        array[posicao] = s;
        n++;
    }

    public String removerInicio() throws Exception {                                             
        if(n == 0) {
            throw new Exception("Lista vazia");
        }

        String resp = array[0].getName();
        n--;

        for(int i = 0; i < n; i++) {
            array[i] = array[i+1];
        }
        return resp;
    }

    public String removerFim() throws Exception {          
        if(n==0) {
            throw new Exception("Lista vazia");
        }
        return array[--n].getName();
    }

    public String remover(int posicao) throws Exception {                                            // Remover

        if(n == 0 || posicao < 0 || posicao >= n) {
            throw new Exception("posicaoicao inexistente da Lista");
        }

        String resp = array[posicao].getName();
        n--;

        for(int i = posicao; i < n; i++) {
            array[i] = array[i+1];
        }

        return resp;
    }

    public void mostrar() {                                                             
        for(int i = 0; i < n; i++) {
            System.out.print("[" + i + "] ");
            array[i].imprimir();
        }
    }

    public boolean search (String s) {                                                     
        boolean resp = false;
        for(int i = 0; i < n && resp == false; i++) {
            resp = (array[i].getName().equals(s));
        }
        return resp;
    }

}
