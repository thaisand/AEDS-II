public class Q13 {
    // Metodo que recebe uma string como parametro e retorna outra contendo a
    // entrada de forma cifrada
    public static String ciframento(String str, int i, int fim) {
        String cifrada = "";
        if (i < fim) {
            cifrada += (char)(str.charAt(i) + 3) + ciframento(str, i+1, fim);
        
        }
        else {
            cifrada += (char)(str.charAt(i) + 3);
        }
        return cifrada;
    }

    public static String ciframento(String str) {
        int fim = str.length();
        fim -= 1;
        return ciframento(str, 0, fim); 
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

    public static void main(String[] args) {
        // Seta o charset dos arquivos
        MyIO.setCharset("ISO-8859-1");
        // Inicializa variaveis
        String str = new String();
        
        str = MyIO.readLine();
        while ((isFim(str)) == false) {
            // Para cada linha de entrada, escreve uma linha com a mensagem criptografada
            MyIO.println(ciframento(str));
            str = MyIO.readLine();
        }
    }

}
