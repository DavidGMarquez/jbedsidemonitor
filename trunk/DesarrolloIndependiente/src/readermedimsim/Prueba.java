/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readermedimsim;

import java.util.StringTokenizer;

/**
 *
 * @author USUARIO
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReaderFromMedicSim Lector = new ReaderFromMedicSim(3434);
        //Enviamos el comando C para recibir configuración
        if (Lector.Send('C') != true) {
            System.out.println("No enviado bien");
        }
        System.out.println("Enviado bien");
        int numero = -1;
        byte[] buffentero = new byte[10000];
        int ibuffentero=0;

        int aux = Lector.Recieve();
        buffentero[ibuffentero]=(byte)aux;
        ibuffentero++;
        System.out.println("Recibo un en binario 21 " + aux);
        aux = Lector.Recieve();
        buffentero[ibuffentero]=(byte)aux;
        ibuffentero++;
        if (aux != -1) {
            numero = aux;
        }
        System.out.println(numero + " Longitud");
        byte[] buffer = new byte[numero];
        int i;
        for (i = 0; i < numero; i++) {
            aux = Lector.Recieve();
            buffentero[ibuffentero]=(byte)aux;
            ibuffentero++;
            if (aux != -1) {
                buffer[i] = (byte) aux;
            } else {
                System.out.println("Fichero acabado");
                break;

            }
        }

        System.out.println("Leido" + i);
        int num = i;
        for (i = 0; i < num; i++) {
            System.out.print((char) buffer[i]);
        }

        System.out.println("Fin");
        System.out.println("Inicio Lectura");
        String buff = new String(buffer);
        StringTokenizer tokenizer = new StringTokenizer(buff, "!");
        StringTokenizer tokenizer_sub;
        String comando;
        String dispositivo;
        boolean one = true;
        int ind = 0;
        int indd = 0;
        while (tokenizer.hasMoreElements()) {
            String line = new String(tokenizer.nextToken());
            tokenizer_sub = new StringTokenizer(line, "_");
            System.out.println("Line"+ind+":"+line);
            if (one) {
                comando = new String(tokenizer_sub.nextToken());
                System.out.println("Comando "+comando);
                dispositivo = new String(tokenizer_sub.nextToken());
                System.out.println("Dispositivo "+dispositivo);
                one = false;
            }
            indd = 0;
            while (tokenizer_sub.hasMoreElements()) {
                System.out.println("SubToken"+indd+":" + tokenizer_sub.nextToken());
                indd++;
            }
            ind++;
        }
        System.out.println("Fin Lectura");
        System.out.println(aux);

        System.out.println("Cadena entera");
        System.out.println("POS  DECIMAL  ASCII");
        for(int in=0;in<ibuffentero;in++)
        {
            System.out.println(in+"     "+buffentero[in]+"     "+((char)buffentero[in]));
        }

        //Doy un tiempo para cerrar el programa
        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (10 * 1000));

        // TODO code application logic here
    }
}
