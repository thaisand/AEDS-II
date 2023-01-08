
import java.util.Scanner;

public class Q5 {

    public static char[] getNumEntradas(String s) {

        int num = (int) (s.charAt(0) - 48);
        char[] entradas = new char[num];
        for (int i = 0; i < entradas.length; i++) {
            entradas[i] = s.charAt(2 * i + 2);
        }
        return entradas;
    }

    public static String subString(String s, int min, int max) {
        String resp = new String();
        for (int i = min; i < max; i++) {
            resp += s.charAt(i);
        }
        return resp;
    }

    public static boolean calcula(String expression) {
        boolean res = true;
        boolean primeiro = true;
        int index = 0;
        String aux = new String();
        String aux2 = new String();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                index = i;
            }
            if (expression.charAt(i) == ')') {
                boolean[] specificInput;
                aux = subString(expression, index, i + 1);
                switch (expression.charAt(index - 1)) {
                    case 'd': // Caso and
                        aux2 = subString(expression, index - 3, i + 1);
                        break;

                    case 't': // Caso not
                        aux2 = subString(expression, index - 3, i + 1);
                        break;

                    case 'r': // Caso or
                        aux2 = subString(expression, index - 2, i + 1);
                        break;
                }

                switch (expression.charAt(index - 1)) {
                    case 'd': // Case and
                        if (primeiro) {
                            res = true;
                        }
                        specificInput = converteEntrada(aux);
                        for (int j = 0; j < specificInput.length; j++) {
                            res &= specificInput[j];
                        }
                        break;

                    case 'r': // Case or
                        if (primeiro) {
                            res = false;
                        }
                        specificInput = converteEntrada(aux);
                        for (int j = 0; j < specificInput.length; j++) {
                            res |= specificInput[j];
                        }
                        break;

                    case 't': // Case not
                        specificInput = converteEntrada(aux);
                        res = !specificInput[0];
                        break;

                    default:
                        System.out.println("ERRO");
                        break;
                }

                expression = replace(expression, aux2, (res) ? "1" : "0");

                if (expression.length() > 1) {
                    i = -1;
                }

                if (isIgual(expression, aux2)) {
                    i = expression.length();
                }
            }
        }

        return res;
    }

    public static String substitui(String exp, char[] input) {
        String resp = new String();
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == 'A') {
                resp += input[0];
            } else if (exp.charAt(i) == 'B') {
                resp += input[1];
            } else if (exp.charAt(i) == 'C') {
                resp += input[2];
            } else if (exp.charAt(i) == 'D') {
                resp += input[3];
            } else if (!(exp.charAt(i) == ' ' || (exp.charAt(i) >= '0' && exp.charAt(i) <= '9'))) {
                resp += exp.charAt(i);
            }
        }

        return resp;
    }

    public static boolean isIgual(String s1, String s2) {
        boolean resp = true;
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                resp = false;
                i = s2.length();
            }
        }
        return resp;
    }

    public static boolean[] converteEntrada(String input) {
        boolean[] resp = new boolean[(input.length() - 1) / 2];
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1') {
                resp[index] = true;
                index++;
            } else if (input.charAt(i) == '0') {
                resp[index] = false;
                index++;
            }
        }
        return resp;
    }

    public static String replace(String principal, String procurada, String nova) {
        String resp = new String();
        for (int i = 0; i < principal.length(); i++) {
            if (i + procurada.length() < principal.length()
                    && isIgual(procurada, subString(principal, i, i + procurada.length()))) {

                for (int j = 0; j < nova.length(); j++) {
                    resp += nova.charAt(j);
                }
                i += (procurada.length() - 1);
            } else {
                resp += principal.charAt(i);
            }
        }
        return resp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = new String();
        expression = sc.nextLine(); // Recebe a primeira entrada
        boolean result;

        while (expression.charAt(0) != '0') {
            char[] entradas = getNumEntradas(expression); // Grava os valores em caracteres das entradas
            expression = substitui(expression, entradas); // Retorna a expressão com os valores substituidos
            result = calcula(expression); // Calcula a expressão
            System.out.println(result ? '1' : '0'); // Imprime 1 para verdadeiro e 0 para falso
            expression = sc.nextLine(); // Requisita a proxima expressão
        }
    }
}
