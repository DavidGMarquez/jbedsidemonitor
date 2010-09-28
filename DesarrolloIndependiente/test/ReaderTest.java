/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import signals.SignalManager;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import readermedimsim.ReaderFromMedicSim;
import signals.*;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class ReaderTest {

    SignalManager signalManager = SignalManager.getInstance();
    ReaderFromMedicSim readerFromMedicSim = null;
    WriteToDisk WTD = null;
    Thread threadWriteToDisk = null;

    public ReaderTest() {
        this.readerFromMedicSim = new ReaderFromMedicSim(3434);
        if (readerFromMedicSim.Send('C') != true) {
            System.out.println("No enviado bien la peticion de configuración");
        }
        //System.out.println("Enviado bien petición configuración");
        int lengthOfConfiguration = -1;

        int receivedByte = readerFromMedicSim.Recieve();

        //System.out.println("El 21 " + aux);

        if (receivedByte != -1) {
            lengthOfConfiguration = receivedByte;
        }
        System.out.println(receivedByte + " Longitud");
        byte[] configurationBuffer = new byte[lengthOfConfiguration];
        int indexToRead;
        for (indexToRead = 0; indexToRead < lengthOfConfiguration; indexToRead++) {
            receivedByte = readerFromMedicSim.Recieve();
            if (receivedByte != -1) {
                configurationBuffer[indexToRead] = (byte) receivedByte;
            } else {
                System.out.println("File Finish");
                break;

            }
        }
        System.out.println("Configuration Read" + configurationBuffer);
        String buff = new String(configurationBuffer);
        this.signalManager.addAllTimeSeries(processReadConfiguration(buff));

        //System.out.println("Leido" + indexToRead);
       /* int num = indexToRead;
        for (indexToRead = 0; indexToRead < num; indexToRead++) {
        System.out.print((char) configurationBuffer[indexToRead]);
        }*/

        //System.out.println("Fin");
        //System.out.println("Inicio Lectura");

        //System.out.println(aux);

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public ArrayList<TimeSeries> processReadConfiguration(String bufferConfiguration) {
        ArrayList<TimeSeries> signalsCreated = new ArrayList<TimeSeries>();
        StringTokenizer separatorLine = new StringTokenizer(bufferConfiguration, "!");
        String comando;
        String dispositivo;
        boolean firstLineofConfiguration = true;
        int numberOfLine = 0;
        int temporalIndexOfToken = 0;
        while (separatorLine.hasMoreElements()) {
            String oneLineToProcess = new String(separatorLine.nextToken());
            String[] tokensOfOneLine = oneLineToProcess.split("_");
            for (int x = 0; x < tokensOfOneLine.length; x++) {
                System.out.println(tokensOfOneLine[x]);
            }
            System.out.println("Line" + numberOfLine + ":" + oneLineToProcess);
            temporalIndexOfToken = 0;
            if (firstLineofConfiguration) {
                //Solo la primera señal que tiene el dispositivo y el C
                comando = new String(tokensOfOneLine[temporalIndexOfToken]);
                temporalIndexOfToken++;
                //System.out.println("Comando " + comando);
                dispositivo = new String(tokensOfOneLine[temporalIndexOfToken]);
                temporalIndexOfToken++;
                //System.out.println("Dispositivo " + dispositivo);
                firstLineofConfiguration = false;
            } else {
                //Para las siguientes señales que comienzan por _
                temporalIndexOfToken++;
            }

            int samplesframe = 0;
            int adcGain = 0;
            String units = null;
            int adcResolution = 0;
            String description = null;
            int typeOfToken = 0;
            while (temporalIndexOfToken < tokensOfOneLine.length) {
                System.out.println("Token:" + temporalIndexOfToken + ":" + tokensOfOneLine[temporalIndexOfToken]);
                switch (typeOfToken) {
                    case 0: {
                        samplesframe = Integer.parseInt(tokensOfOneLine[temporalIndexOfToken]);
                        //  System.out.println("Samples " + temporalIndexOfToken + ":" + samplesframe);
                    }
                    break;
                    case 1: {
                        adcGain = Integer.parseInt(tokensOfOneLine[temporalIndexOfToken]);
                        //System.out.println("AdcGain " + temporalIndexOfToken + ":" + adcGain);

                    }
                    break;
                    case 2: {
                        units = new String(tokensOfOneLine[temporalIndexOfToken]);
                        //System.out.println("Units " + temporalIndexOfToken + ":" + units);

                    }
                    break;
                    case 3: {
                        adcResolution = Integer.parseInt(tokensOfOneLine[temporalIndexOfToken]);
                        //System.out.println("adcResolution " + temporalIndexOfToken + ":" + adcResolution);

                    }
                    break;
                    case 4: {
                        description = new String(tokensOfOneLine[temporalIndexOfToken]);
                        //System.out.println("description " + temporalIndexOfToken + ":" + description);

                    }
                    break;
                    default: {
                        System.out.println("Error token no reconocido");
                    }
                    break;

                }
                ;
                temporalIndexOfToken++;
                typeOfToken++;
            }
            signalsCreated.add(new TimeSeries(description, units, adcGain, adcGain, units));
            numberOfLine++;
        }
        //System.out.println("Fin Lectura");
        return signalsCreated;



    }

    @Test
    public void TestLecturaOneSignalToFile() {

        WriteTimeSeriesInDisk();
        //Abrimos un fichero para guardar las muestras leidas
        FileWriter fileOutput = null;
        PrintWriter pw = null;
        try {
            fileOutput = new FileWriter("outputOneSignaltoFile.txt");
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

        //Enviamos el comando de recibir los datos
        readerFromMedicSim.Send('S');
        System.out.println("Esperamos Datos");
        int oneByteRead;
        int numberOfBytesRead = 0;
        //Ahora vamos a recibir la señal en concreto
        ByteBuffer temporalTwoBytesToShort = ByteBuffer.allocate(2);
        temporalTwoBytesToShort.order(ByteOrder.BIG_ENDIAN);
        short shortValue;
        boolean firstByte = true;
//
        //int temporal21=readerFromMedicSim.Recieve();
        //System.out.println("El 21" + temporal21);
        while ((oneByteRead = readerFromMedicSim.Recieve()) != -1) {
            numberOfBytesRead++;
            //System.out.println(numberOfBytesRead + " " + oneByteRead);
            if (firstByte) {
                temporalTwoBytesToShort.put(0, (byte) oneByteRead);
                firstByte = false;
            } else {
                temporalTwoBytesToShort.put(1, (byte) oneByteRead);
                shortValue = temporalTwoBytesToShort.getShort(0);
                pw.println(shortValue);
                float floatValue[] = new float[1];
                floatValue[0] = shortValue;
                System.out.println("Recibo" + shortValue);
                this.signalManager.writeToTimeSeries(0, floatValue);
                firstByte = true;

            }
        }
        try {
            fileOutput.close();

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        long t0, t1;
        try {
            WTD.terminado();
            threadWriteToDisk.join(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (10 * 1000));

        // TODO code application logic here
    }

    public void WriteTimeSeriesInDisk() {
        WTD = new WriteToDisk("Output1.txt", signalManager);
        threadWriteToDisk = new Thread(WTD, "threadWriteToDisk");

        threadWriteToDisk.start();

    }
}
