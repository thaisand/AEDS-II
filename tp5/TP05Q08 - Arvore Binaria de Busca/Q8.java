import java.util.Scanner;

public class Q8 {
    public static void main(String[] args) throws Exception {
        int numTestes = 0; // numero de casos de teste com 2 linhas cada
        int n = 0; // quant de numeros que deve compor cada arvore 
        int elemento = 0; 
        Scanner sc = new Scanner(System.in);
        
        numTestes = sc.nextInt();
        for(int i = 1; i <= numTestes; i++) {
            ArvoreBinaria arvoreBinaria = new ArvoreBinaria();
            n = sc.nextInt();
            for (int j = 0; j < n; j++) {
                elemento = sc.nextInt();
                arvoreBinaria.inserir(elemento);
            }
            System.out.println("Case " + i + ":");
            System.out.print("Pre.: " );
            arvoreBinaria.caminharPre();
            System.out.print("\n");
            System.out.print("In..: " );
            arvoreBinaria.caminharCentral();
            System.out.print("\n");
            System.out.print("Post: " );
            arvoreBinaria.caminharPos();
            System.out.println("\n");
        }
        sc.close();
    }
}

class ArvoreBinaria {
	private No raiz; // Raiz da arvore.


	//Construtor 
	public ArvoreBinaria() {
		raiz = null;
	}

    // caminha em ordem
	public void caminharCentral() {
		//System.out.print("[ ");
		caminharCentral(raiz);
		//System.out.println("]");
	}

	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
			System.out.print(i.elemento + " "); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

    // caminha pré-ordem
	public void caminharPre() {
		//System.out.print("[ ");
		caminharPre(raiz);
		//System.out.println("]");
	}

	private void caminharPre(No i) {
		if (i != null) {
			System.out.print(i.elemento + " "); // Conteudo do no.
			caminharPre(i.esq); // Elementos da esquerda.
			caminharPre(i.dir); // Elementos da direita.
		}
	}

    // caminha pós-ordem
	public void caminharPos() {
		//System.out.print("[ ");
		caminharPos(raiz);
		//System.out.println("]");
	}

	private void caminharPos(No i) {
		if (i != null) {
			caminharPos(i.esq); // Elementos da esquerda.
			caminharPos(i.dir); // Elementos da direita.
			System.out.print(i.elemento + " "); // Conteudo do no.
		}
	}

    // insere elemento
	public void inserir(int x) throws Exception {
		raiz = inserir(x, raiz);

	}

	private No inserir(int x, No i) throws Exception {
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
    public int elemento; // Conteudo do no
    public No esq, dir;  // Filhos da esq e dir.

    // construtores
    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}
