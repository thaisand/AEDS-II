public class Q11 {

    // Metodo que recebe string como parametro e retorna true se for palindromo
    public static boolean isPalindromo(String str, int esq, int dir) throws Exception {
        boolean resp = true;

        // Percorre a string recebida como parametro, checando se e um palindromo
        if (esq < dir) {
            if (str.charAt(esq) != str.charAt(dir)) {
                resp = false;
            } else {
                esq++;
                dir--; 
                resp = isPalindromo(str, esq, dir);
            }
        }

        // Retorna true se for palindromo
        return resp;
    }

    public static boolean isPalindromo(String str) throws Exception {
        int dir = str.length();
        dir -= 1;
        return isPalindromo(str, 0, dir);
    }

    // Metodo que checa se o arquivo de entrada chegou ao fim
    public static boolean isFim(String str) {
        boolean resp = false;
        if (str.equals("FIM")) {
            resp = true;
        }

        // Retorna true se a string for igual a 'FIM'
        return resp;
    }

    public static void main(String[] args) throws Exception {

        // Seta o charset dos arquivos
        MyIO.setCharset("ISO-8859-1");

        // Inicializa variaveis
        String[] str = new String[7000];
        int contador = 0;

        // Le cada linha do arquivo de entrada ate encontrar seu fim
        do {
            str[contador] = MyIO.readLine().trim();
        } while ((isFim(str[contador++])) == false);

        // Desconsidera o fim do arquivo de entrada
        contador--;

        // Checa se as strings do array sao palindromos
        for (int j = 0; j < contador; j++) {
            if ((isPalindromo(str[j])) == true) {
                MyIO.println("SIM");
            } else {
                MyIO.println("NAO");
            }
        }
    }

}