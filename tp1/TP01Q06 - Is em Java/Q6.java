public class Q6 {
    public static boolean isVogais(String string) {
        boolean resp = true;
        char c = ' ';
        for (int i = 0; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')) {
                resp = false;
            }
        }
        return resp;
    }

    public static boolean isConsoantes(String string) {
        boolean resp = true; 
        char c = ' ';
        for (int i = 0; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(((c >= 'b' && c <= 'z') && (c != 'e' && c != 'i' && c != 'o' && c != 'u')) || ((c >= 'B' && c <= 'Z') && (c != 'E' && c != 'I' && c != 'O' && c != 'U')))) {
                resp = false;
            }
        }
        return resp;
    }

    public static boolean isInteiro(String string) {
        boolean resp = true; 
        char c = ' ';
        for (int i = 0; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                resp = false;
            }
        }
        return resp;
    }

    public static boolean isReal(String string) {
        boolean resp = true; 
        char c = ' ';
        for (int i = 0; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9') || (c == '.') || (c == ',')) {
                resp = false;
            }
        }
        return resp;
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
            if (isVogais(str) == true) {
                MyIO.print("SIM ");
            }
            else {
                MyIO.print("NAO ");
            }
            if (isConsoantes(str) == true) {
                MyIO.print("SIM ");
            }
            else {
                MyIO.print("NAO ");
            }
            if (isInteiro(str) == true) {
                MyIO.print("SIM ");
            }
            else {
                MyIO.print("NAO ");
            }
            if(isReal(str) == true) {
                MyIO.print("SIM\n");
            }
            else {
                MyIO.print("NAO\n");
            }

            str = MyIO.readLine();
        }
    }
}
