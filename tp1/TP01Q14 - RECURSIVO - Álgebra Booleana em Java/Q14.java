import java.io.*;
import java.nio.charset.*;
import java.util.Scanner;

public class Q14 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = new String();
        int numEntradas; // Quantidade de entradas
        char[] entradas; // Valores de entradas

        line = sc.nextLine();
        while (line.charAt(0) != '0') {
            numEntradas = (int) (line.charAt(0)) - 48; 
            entradas = recuperaInput(line, numEntradas); // Recupera as entradas em caracteres
            line = replaceInput(line, entradas);
            line = calcline(line);
            System.out.println(line);
            line = sc.nextLine();
        }

    }
    
    public static boolean[] getLogicInputs(String input) {
        // cria o vetor com o tamanho certo de variáveis
        boolean[] logicInput = new boolean[(input.length() - 2) / 2];
        int index = 0; // index do input lógico
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1') {
                logicInput[index] = true;
                index++;
            } else if (input.charAt(i) == '0') {
                logicInput[index] = false;
                index++;
            }
        }
        return logicInput;
    }

    public static String replace(String principal, String procurada, String substituta) {
        String result = new String();
        String aux = new String();

        for (int i = 0; i < principal.length(); i++) {
            aux = substring(principal, i, procurada.length() + i);
            if (equals(aux, procurada)) {
                for (int j = 0; j < substituta.length(); j++) {
                    result += substituta.charAt(j);
                }
                i += procurada.length() - 1;
            } else {
                result += principal.charAt(i);
            }
        }
        return result;
    }

    public static String substring(String s, int min, int max) {
        String resp = new String();
        for (int i = min; i < s.length() && i < max; i++) {
            resp += s.charAt(i);
        }
        return resp;
    }

    public static char[] recuperaInput(String line, int quanti) {
        char[] entradas = new char[quanti];
        for (int i = 0; i < quanti; i++) {
            entradas[i] = line.charAt((i + 1) * 2);
        }
        return entradas;
    }

    public static void printChar(char[] in) {
        System.out.print("[ ");
        for (int i = 0; i < in.length; i++) {
            System.out.print(in[i] + " ");
        }
        System.out.println("]");
    }

    public static String replaceInput(String line, char[] entradas) {
        String newExpress = new String();
        // For iniciado a partir da expressão, ignorando as entradas
        for (int i = (entradas.length + 1) * 2; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case 'A':
                    newExpress += entradas[0];
                    break;

                case 'B':
                    newExpress += entradas[1];
                    break;

                case 'C':
                    newExpress += entradas[2];
                    break;

                case 'D':
                    newExpress += entradas[3];
                    break;
                case ' ':
                    break;
                default:
                    newExpress += line.charAt(i);
                    break;
            }
        }
        return newExpress;
    }

    public static boolean equals(String s1, String s2) {
        boolean result = true;
        if (s1.length() != s2.length()) {
            result = false;
        } else
            for (int i = 0; i < s1.length() && i < s2.length(); i++) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    result = false;
                    i = s1.length();
                }
            }
        return result;
    }

    public static String calcline(String exp) {
        return calcline(exp, 0);
    }

    public static String calcline(String exp, int pos) {
        String resp = new String();
        if (exp.length() > 1) {
            int abreIndex = 0;
            String contaAtual = new String();
            for (int i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') {
                    abreIndex = i;
                } else if (exp.charAt(i) == ')') {
                    contaAtual = substring(exp, abreIndex - 1, i + 1);
                    boolean logicResp = false;
                    boolean[] logicInput = getLogicInputs(contaAtual);
                    switch (contaAtual.charAt(0)) {
                        case 'r':
                            // MyIO.println("entrou na or");
                            logicResp = false;
                            for (int j = 0; j < logicInput.length; j++) {
                                logicResp |= logicInput[j];
                            }
                            contaAtual = substring(exp, abreIndex - 2, i + 1);
                            resp = replace(exp, contaAtual, (logicResp) ? "1" : "0");
                            break;

                        case 'd':
                            logicResp = true;
                            for (int j = 0; j < logicInput.length; j++) {
                                logicResp &= logicInput[j];
                            }
                            contaAtual = substring(exp, abreIndex - 3, i + 1);
                            resp = replace(exp, contaAtual, (logicResp) ? "1" : "0");
                            break;

                        case 't':
                            logicResp = !logicInput[0];
                            contaAtual = substring(exp, abreIndex - 3, i + 1);
                            resp = replace(exp, contaAtual, (logicResp) ? "1" : "0");
                            break;
                    }
                    i = exp.length();
                }
            }
            if (resp.length() > 1) {
                resp = calcline(resp, pos + 1);
            }
        }
        return resp;
    }

}