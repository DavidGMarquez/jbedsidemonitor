/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import integration.SignalManager;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.*;

/**
 *
 * @author USUARIO
 */
class WriteToDisk implements Runnable {

    boolean escribiendo = true;
    boolean ejecutar = true;
    FileWriter fileOutput = null;
    PrintWriter pw = null;
    SignalManager signalManager = null;
    int indexToRead = 0;

    WriteToDisk(String fileName, SignalManager signalManager) {
 //       System.out.println("HOLA SOY TINO");
        this.signalManager = signalManager;

        try {
            fileOutput = new FileWriter(fileName);
            pw = new PrintWriter(fileOutput);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                if (null != fileOutput) {
                    fileOutput.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void terminado() {
        System.out.println("-->Ok el fichero va terminado tmb");
        escribiendo = false;
    }

    public void run() {
        int numBytesWrite = 0;
        while (ejecutar) {

            float[] dataRead = this.signalManager.readNewFromTimeSeries(0, indexToRead);
            if (dataRead.length == 0 && escribiendo == false) {
                System.out.println("-->Mando que se pare");
                ejecutar = false;
            }
            if (dataRead.length > 0) {
                //    System.out.println("Leido"+dataRead.length);
                indexToRead = indexToRead + dataRead.length;
                for (int i = 0; i < dataRead.length; i++) {
                    pw.println((short) dataRead[i]);
                }
                numBytesWrite = numBytesWrite + dataRead.length;
                System.out.println("Numero de Bytes escritos" + numBytesWrite);
                pw.flush();

            } else {             
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WriteToDisk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        pw.close();
        try {
            fileOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteToDisk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
