import java.util.*;

public class Q9 {
    public static void main(String[] args) throws Exception {
        ArvoreBinaria arvoreBinaria = new ArvoreBinaria();
        Scanner sc = new Scanner(System.in);
        String line = new String();

        
        line = sc.nextLine();
        while (sc.hasNext() == true ) {
            char operacao, elemento;
            boolean resp;
            if (isOperacao(line) == true) {
                operacao = line.charAt(0);
                elemento = line.charAt(2);
                if (operacao == 'I') {
                    arvoreBinaria.inserir(elemento);
                } else if (operacao == 'P') {
                    resp = arvoreBinaria.pesquisar(elemento);
                    if (resp == true) {
                        System.out.println(elemento + " existe");
                    } else {
                        System.out.println("nao existe");
                    }
                }
            } else {
                line = line.trim();
                if (line.compareTo("INFIXA") == 0) {
                    arvoreBinaria.caminharCentral();
                } else if (line.compareTo("PREFIXA") == 0) {
                    arvoreBinaria.caminharPre();
                } else if (line.compareTo("POSFIXA") == 0) {
                    arvoreBinaria.caminharPos();
                }
                System.out.print("\n");
            }
            line = sc.nextLine();
        }
        char operacao, elemento;
            boolean resp;
            if (isOperacao(line) == true) {
                operacao = line.charAt(0);
                elemento = line.charAt(2);
                if (operacao == 'I') {
                    arvoreBinaria.inserir(elemento);
                } else if (operacao == 'P') {
                    resp = arvoreBinaria.pesquisar(elemento);
                    if (resp == true) {
                        System.out.println(elemento + " existe");
                    } else {
                        System.out.println("nao existe");
                    }
                }
            } else {
                line = line.trim();
                if (line.compareTo("INFIXA") == 0) {
                    arvoreBinaria.caminharCentral();
                } else if (line.compareTo("PREFIXA") == 0) {
                    arvoreBinaria.caminharPre();
                } else if (line.compareTo("POSFIXA") == 0) {
                    arvoreBinaria.caminharPos();
                }
                System.out.print("\n");
            }

        sc.close();
    }

    public static boolean isOperacao(String line) {
        if (line.charAt(1) == ' ') {
            return true;
        } else {
            return false;
        }
    }

}

class ArvoreBinaria {
    private No raiz; // Raiz da arvore

    public ArvoreBinaria() { // Construtor
        raiz = null;
    }

    public boolean pesquisar(char x) { // Pesquisar elemento
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(char x, No i) { // Pesquisar elemento
        boolean resp;
        if (i == null) {
            resp = false;

        } else if (x == i.elemento) {
            resp = true;

        } else if (x < i.elemento) {
            resp = pesquisar(x, i.esq);

        } else {
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    public void caminharCentral() { // Exibir elementos
        // System.out.print("[ ");
        caminharCentral(raiz);
        // System.out.println("]");
    }

    private void caminharCentral(No i) { // Exibir elementos
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda.
            System.out.print(i.elemento + " "); // Conteudo do no.
            caminharCentral(i.dir); // Elementos da direita.
        }
    }

    public void caminharPre() { // exibir elementos
        // System.out.print("[ ");
        caminharPre(raiz);
        // System.out.println("]");
    }

    private void caminharPre(No i) { // exibir elementos
        if (i != null) {
            System.out.print(i.elemento + " "); // Conteudo do no
            caminharPre(i.esq); // Elementos da esquerda
            caminharPre(i.dir); // Elementos da direita
        }
    }

    public void caminharPos() { // Exibir elementos
        // System.out.print("[ ");
        caminharPos(raiz);
        // System.out.println("]");
    }

    private void caminharPos(No i) { // Exibir elementos
        if (i != null) {
            caminharPos(i.esq); // Elementos da esquerda.
            caminharPos(i.dir); // Elementos da direita.
            System.out.print(i.elemento + " "); // Conteudo do no.
        }
    }

    public void inserir(char x) throws Exception { // Inserir elemento
        raiz = inserir(x, raiz);

    }

    private No inserir(char x, No i) throws Exception { // Inserir elemento
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

}

class No {
    public char elemento; // Conteudo do no
    public No esq, dir; // Filhos da esq e dir

    // construtores
    public No(char elemento) {
        this(elemento, null, null);
    }

    public No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}