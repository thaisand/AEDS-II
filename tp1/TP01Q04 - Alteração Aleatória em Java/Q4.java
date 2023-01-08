import java.util.Random;

public class Q4 {

    public static String alteracao(String str, Random gerador) {
        String alterada = new String();
        char char1 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        char char2 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
    
        for (int i = 0; i < str.length(); i++) {
           if (str.charAt(i) == char1) {
                alterada += char2;
           }
           else {
                alterada += str.charAt(i);
           }
        }
        return alterada;
    }

    public static boolean isFim(String string) {
        boolean resp = false;
        if (string.equals("FIM")) {
            resp = true;
        }

        // Retorna true se a string for igual a 'FIM'
        return resp;
    }
    public static void main(String[] args) { 
        // Seta o charset dos arquivos 
        MyIO.setCharset("ISO-8859-1");
        Random gerador = new Random();
        gerador.setSeed(4);
        
        String string = new String();
        string = MyIO.readLine();
        while (!isFim(string)) {
            MyIO.println(alteracao(string, gerador));
            string = MyIO.readLine();
        }
    }
}