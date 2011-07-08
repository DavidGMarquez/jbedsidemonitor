/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testJade;

import completeTestsTimeSeries.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class JADEGenerator implements Runnable {

    String nameSignal;
    Timer timer;
    int currentIteration;
    int dataSize;
    FileReader fr;
    BufferedReader input;
    int enviados;

    public JADEGenerator(int dataSize, String nameSignal) {
        timer = new Timer();
        this.currentIteration = 0;
        this.nameSignal = nameSignal;
        this.dataSize = dataSize;
        try {
            fr = new FileReader("4_A1.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JADEGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        input = new BufferedReader(fr);
    }


    public  void run() {
        try {
            float[] dataToSend = new float[dataSize];
            int indexSend = 0;
            String temp = "";
            String line;
            StringTokenizer st;
            int leidos = 0;
            while (((line = input.readLine()) != null)) {
                st = new StringTokenizer(line, "; \t");
                while (st.hasMoreTokens()) {
                    temp = st.nextToken();
                    dataToSend[indexSend] = Float.parseFloat(temp);
                    indexSend++;
                    if (indexSend == dataSize) {
                        this.sendRunnable(dataToSend, leidos);
                        indexSend = 0;
                    }
                    leidos++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JADEGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendRunnable(float[] datos, int leidos) {
        if(leidos>4600000){
        System.out.println("Generate" + leidos);
        }
        WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(nameSignal, datos, currentIteration * dataSize);
        SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
        currentIteration++;

    }
}
