import java.io.*;
import java.net.*;
import java.nio.charset.*;

public class Q8 {
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String getHtml(String endereco){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 

        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here
        }

        return resp;
    }

    public static int[] tratamento(String html){
        int[] tudo = new int[25];
        boolean tagBrTable = false;
        for (int i = 0; i < html.length(); i++) {
            if (isTable(html,i)) {
                tudo[24]++;
                tagBrTable = true;
            }
            else if (isBr(html, i)) {
                tudo[23]++;
                tagBrTable = true;
               
            }
            if (tagBrTable && html.charAt(i) == '>') {
                tagBrTable = false;
            }
            if (!tagBrTable) {
                if (isConsoante(html.charAt(i))) {
                    tudo[22]++;
                }
                
                else if (indexVogal(html.charAt(i)) > -1) {
                    int index = indexVogal(html.charAt(i));
                    tudo[index]++;
                }
                
                else if ((int)html.charAt(i) == 195) {
                    int prox = (int)html.charAt(i+1);
                    int index = -1;
                    switch (prox) {
                        case 161:
                            index = 5;
                            tudo[index]++;
                            break;
                        case 169:
                            index = 6;
                            tudo[index]++;
                            break;
                        case 173:
                            index = 7;
                            tudo[index]++;
                            break;
                        case 179:
                            index = 8;
                            tudo[index]++;
                            break;
                        case 186:
                            index = 9;
                            tudo[index]++;
                            break;
                        case 160:
                            index = 10;
                            tudo[index]++;
                            break;
                        case 168:
                            index = 11;
                            tudo[index]++;
                            break;
                        case 172:
                            index = 12;
                            tudo[index]++;
                            break;
                        case 178:
                            index = 13;
                            tudo[index]++;
                            break;
                        case 185:
                            index = 14;
                            tudo[index]++;
                            break;
                        case 163:
                            index = 15;
                            tudo[index]++;
                            break;
                        case 181:
                            index = 16;
                            tudo[index]++;
                            break;
                        case 162:
                            index = 17;
                            tudo[index]++;
                            break;
                        case 170:
                            index = 18;
                            tudo[index]++;
                            break;
                        case 174:
                            index = 19;
                            tudo[index]++;
                            break;
                        case 180:
                            index = 20;
                            tudo[index]++;
                            break;
                        case 187:
                            index = 21;
                            tudo[index]++;
                            break;
                        
                        default:
                            break;
                    }
                    
                }
            }
            
        }
        return tudo;
    }

    public static boolean isLetra(char c){
        return((c >= 'a' && c <= 'z'));
    }

    public static int indexVogal(char c){
        int index = -1;
        String vogais = "aeiou";
        for (int i = 0; i < vogais.length(); i++) {
            if (vogais.charAt(i) == c) {
                index = i;
                i = vogais.length();
            }
        }
        return index;
    }
    
    public static boolean isVogal(char c){
        return(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
    }

    public static boolean isConsoante(char c){
        return(isLetra(c) && !isVogal(c));
    }

    public static boolean isTable(String s,int i){
        boolean resp = true;
        String table = "<table>";
        for (int j = 0; j < 7; j++) {
            if (s.charAt(i+j) != table.charAt(j)) {
                resp = false;
                j = 7;
            }
        }
        return resp;
    }
    
    public static boolean isBr(String s,int i){
        boolean resp = true;
        String br = "<br>";
        for (int j = 0; j < 4; j++) {
            if (s.charAt(i+j) != br.charAt(j)) {
                resp = false;
                j = 7;
            }
        }
        return resp;
    }

    public static void printResp(int[] vetor, String nome){
        MyIO.print("a(" + vetor[0] + ") ");
        MyIO.print("e(" + vetor[1] + ") ");
        MyIO.print("i(" + vetor[2] + ") ");
        MyIO.print("o(" + vetor[3] + ") ");
        MyIO.print("u(" + vetor[4] + ") ");
        MyIO.print("á(" + vetor[5] + ") ");
        MyIO.print("é(" + vetor[6] + ") ");
        MyIO.print("í(" + vetor[7] + ") ");
        MyIO.print("ó(" + vetor[8] + ") ");
        MyIO.print("ú(" + vetor[9] + ") ");
        MyIO.print("à(" + vetor[10] + ") ");
        MyIO.print("è(" + vetor[11] + ") ");
        MyIO.print("ì(" + vetor[12] + ") ");
        MyIO.print("ò(" + vetor[13] + ") ");
        MyIO.print("ù(" + vetor[14] + ") ");
        MyIO.print("ã(" + vetor[15] + ") ");
        MyIO.print("õ(" + vetor[16] + ") ");
        MyIO.print("â(" + vetor[17] + ") ");
        MyIO.print("ê(" + vetor[18] + ") ");
        MyIO.print("î(" + vetor[19] + ") ");
        MyIO.print("ô(" + vetor[20] + ") ");
        MyIO.print("û(" + vetor[21] + ") ");
        MyIO.print("consoante(" + vetor[22] + ") ");
        MyIO.print("<br>(" + vetor[23] + ") ");
        MyIO.print("<table>(" + vetor[24] + ") ");
        MyIO.println(nome);
    }

    public static void main(String[] args) {
        String URL = new String();
        String nome = new String();
        int[] i = new int[26];
        nome = MyIO.readLine();
        while(!isFim(nome)){
            URL = MyIO.readLine();
            String html = getHtml(URL);
            i = tratamento(html);
            printResp(i,nome);
            nome = MyIO.readLine();
        }
        
    }
}