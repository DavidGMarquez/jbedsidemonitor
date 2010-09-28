/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource;

import vehicleclass.WriteOperation;
import integration.ThreadManager;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.StringTokenizer;

import readermedimsim.ReaderFromMedicSim;
import signals.*;

/**
 *
 * @author USUARIO
 */
public class DriverReaderJSignal implements Runnable{

    SignalManager signalManager =null;
    ReaderFromMedicSim readerFromMedicSim = null;
    ThreadManager generalManager=null;
    int sizeOfBufferToWrite=100;
    float[] bufferToWrite;
    int indexBufferToWrite=0;

    public DriverReaderJSignal() {
        this.signalManager = SignalManager.getInstance();
        this.generalManager=ThreadManager.getInstance();
        this.bufferToWrite=new float[sizeOfBufferToWrite];
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
        ArrayList<TimeSeries> signalsTimeSeries = processReadConfiguration(buff);
        for(int i=0;i<signalsTimeSeries.size();i++){
            this.signalManager.addTimeSeries(signalsTimeSeries.get(i));
        }
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
    public void run() {


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
        //int temporal21=readerFromMedicSim.Recieve();
        //System.out.println("El 21" + temporal21);
        while ((oneByteRead = readerFromMedicSim.Recieve()) != -1) {
            numberOfBytesRead++;
            System.out.println("De "+numberOfBytesRead + " Leido " + oneByteRead);
            if (firstByte) {
                temporalTwoBytesToShort.put(0, (byte) oneByteRead);
                firstByte = false;
            } else {
                temporalTwoBytesToShort.put(1, (byte) oneByteRead);
                shortValue = temporalTwoBytesToShort.getShort(0);
                float floatValue[] = new float[1];
                floatValue[0] = shortValue;
                System.out.println("Recibo" + shortValue);
                //this.signalManager.writeToTimeSeries(0, floatValue);
                addValueToBuffer(floatValue[0]);
                firstByte = true;
            }
        }
    }


    private void addValueToBuffer(float floatValue) {
      //  System.out.println("index "+indexBufferToWrite);
        bufferToWrite[indexBufferToWrite]=floatValue;
        indexBufferToWrite++;
        if(indexBufferToWrite==sizeOfBufferToWrite)
        {     
            float[] bufferToWriteCopy=new float[sizeOfBufferToWrite];
            System.arraycopy(bufferToWrite, 0, bufferToWriteCopy, 0, sizeOfBufferToWrite);
            createWriteOperation(bufferToWriteCopy);
            indexBufferToWrite=0;

    }
}

    private void createWriteOperation(float[] bufferToWriteCopy) {
        System.out.println("DEBUG creada operacion de copia");
/*        System.out.println(ThreadManager.getInstance());
        System.out.println(generalManager);
        generalManager=ThreadManager.getInstance();
        System.out.println(generalManager);*/
        this.generalManager.addWriteOperation(new WriteOperation(bufferToWriteCopy, null));
    }

}
