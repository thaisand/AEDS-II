import java.io.RandomAccessFile;
import javax.swing.plaf.multi.MultiOptionPaneUI;

public class Q9 {

    public static void escreveEntradas(int n) throws Exception {
        double entrada;
        RandomAccessFile raf = new RandomAccessFile("arquivo.txt", "rw");
        for (int i = 0; i < n; i++) {
            entrada = MyIO.readDouble();
            raf.writeDouble(entrada);
        }
        raf.close();
    }

    public static void leFim (String arquivo, int n) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(arquivo, "r");
        int posFim = (8 * (n-1));
        double entrada;
        int entradaInt;
        for (int i = posFim; i >= 0; i = i - 8){
            raf.seek(i);
            entrada = raf.readDouble();
            if(entrada % 1 == 0)
            {
                entradaInt = (int)(entrada); 
                MyIO.println(entradaInt);
            }
            else {
                MyIO.println(entrada);
            }
        }
        raf.close();
    }

    public static void main(String[] args) throws Exception {
        MyIO.setCharset("ISO-8859-1");

        int n = 0;
        
        n = MyIO.readInt();
        escreveEntradas(n);
        leFim("arquivo.txt", n);
    }
}
