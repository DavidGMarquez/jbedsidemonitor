/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readermedimsim;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Reader Lector = new Reader(3434);
        if (Lector.Send('C') != true) {
            System.out.println("No enviado bien");
        }
        System.out.println("Enviado bien");
        int numero = -1;
        int aux = Lector.Recieve();
        System.out.println("El 21 " + aux);
        aux = Lector.Recieve();
        if (aux != -1) {
            numero = aux;
        }
        System.out.println(numero + " Longitud");
        byte[] buffer = new byte[numero];
        int i;
        for (i = 0; i < numero; i++) {
            aux = Lector.Recieve();
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
        String comando;
        String dispositivo;
        boolean one = true;
        int ind = 0;
        int indd = 0;
        while (tokenizer.hasMoreElements()) {
            String line = new String(tokenizer.nextToken());
            String[] result = line.split("_");
            for (int x = 0; x < result.length; x++) {
                System.out.println(result[x]);
            }

            System.out.println("Line" + ind + ":" + line);
            indd = 0;
            if (one) {
                //Solo la primera señal que tiene el dispositivo y el C
                comando = new String(result[indd]);
                indd++;
                System.out.println("Comando " + comando);
                dispositivo = new String(result[indd]);
                indd++;
                System.out.println("Dispositivo " + dispositivo);
                one = false;
            }
            else
            {
                //Para las siguientes señales que comienzan por _
                indd++;
            }

            int samplesframe = 0;
            int adcGain = 0;
            String units = null;
            int adcResolution = 0;
            String description = null;
            int inddd = 0;
            while (indd < result.length) {
                switch (inddd) {
                    case 0: {
                        samplesframe = Integer.parseInt(result[indd]);
                        System.out.println("Samples " + indd + ":" + samplesframe);
                    }
                    break;
                    case 1: {
                        adcGain = Integer.parseInt(result[indd]);
                        System.out.println("AdcGain " + indd + ":" + adcGain);

                    }
                    break;
                    case 2: {
                        units = new String(result[indd]);
                        System.out.println("Units " + indd + ":" + units);

                    }
                    break;
                    case 3: {
                        adcResolution = Integer.parseInt(result[indd]);
                        System.out.println("adcResolution " + indd + ":" + adcResolution);

                    }
                    break;
                    case 4: {
                        description = new String(result[indd]);
                        System.out.println("description " + indd + ":" + description);

                    }
                    break;
                    default: {
                        System.out.println("Error token no reconocido");
                    }
                    break;

                }
                ;
                indd++;
                inddd++;
            }
            ind++;
        }
        System.out.println("Fin Lectura");
        System.out.println(aux);
        /*

        if (Lector.Send(67) != true) {
        System.out.println("No enviado bien");
        }
        System.out.println("Enviado bien");

        numero = -1;
        aux = Lector.Recieve();
        aux = Lector.Recieve();
        System.out.println(aux + "Second Round");
        if (aux != -1) {
        numero = aux;
        }
        System.out.println(numero + " Longitud");
        buffer = new byte[numero];

        for (i = 0; i < numero; i++) {
        aux = Lector.Recieve();
        if (aux != -1) {
        buffer[i] = (byte) aux;
        } else {
        System.out.println("Fichero acabado");
        break;

        }
        }
         */
        System.out.println("Leido" + i);
        num = i;
        for (i = 0; i < num; i++) {
            System.out.print((char) buffer[i]);
        }

//Abrimos un fichero para guardar las muestras leidas
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("s.txt");
            pw = new PrintWriter(fichero);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Enviamos el comando de recibir los datos
        Lector.Send('S');
        int L;
        int C = 0;
        //Ahora vamos a recibir la señal en concreto
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.BIG_ENDIAN);
        short shortVal;
        boolean first = true;
        System.out.println("El 21" + Lector.Recieve());
        while ((L = Lector.Recieve()) != -1) {
            C++;
            System.out.println(C + " " + L);
            if (first) {
                bb.put(0, (byte) L);
                first = false;
            } else {
                bb.put(1, (byte) L);
                shortVal = bb.getShort(0);
                pw.println(shortVal + " ");
                first = true;
            }
        }
        try {
            fichero.close();

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (10 * 1000));

        // TODO code application logic here
    }
}
