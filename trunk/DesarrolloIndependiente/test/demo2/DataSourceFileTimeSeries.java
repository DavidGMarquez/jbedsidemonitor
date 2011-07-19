/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo2;

import guiTest.*;
import completeTestsTimeSeries.*;
import datasource.DataSourceDefault;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;

public class DataSourceFileTimeSeries extends DataSourceDefault {

    String nameSignal;
    Timer timer;
    int limitOfItIterations;
    int currentIteration;
    int sizeOfIteration;
    float frecuency;
    long index;
    int delayFirstTime;
    FileReader fr;
    BufferedReader input;
    String fileName;

    public DataSourceFileTimeSeries(int delayFirstTime, float frecuency, int limitOfIterations, String nameSignal, int sizeOfIteration, String fileName) {
        this.delayFirstTime = delayFirstTime;
        this.sizeOfIteration = sizeOfIteration;
        this.currentIteration = 0;
        this.frecuency = frecuency;
        this.limitOfItIterations = limitOfIterations;
        this.nameSignal = nameSignal;
        this.index = 0;
        this.registerThis();
        this.fileName=fileName;
    }

    public boolean start() {
           try {
            fr = new FileReader(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataSourceFileTimeSeries.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        input = new BufferedReader(fr);
        timer = new Timer();
        float periodOfTime = (1 / frecuency) * 1000 * sizeOfIteration;
        timer.scheduleAtFixedRate(new RemindTask(), delayFirstTime, (long) periodOfTime);
        return true;
    }

    public ArrayList<String> getSeriesGenerated() {
        ArrayList seriesGenerated;
        seriesGenerated = new ArrayList();
        seriesGenerated.add(nameSignal);
        return seriesGenerated;
    }

    public String getIdentifier() {
        return "FileDataSource";
    }


    public int getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    public float getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(float frecuency) {
        this.frecuency = frecuency;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public int getLimitOfItIterations() {
        return limitOfItIterations;
    }

    public void setLimitOfItIterations(int limitOfItIterations) {
        this.limitOfItIterations = limitOfItIterations;
    }

    public String getNameSignal() {
        return nameSignal;
    }

    public int getSizeOfIteration() {
        return sizeOfIteration;
    }

    public void setSizeOfIteration(int sizeOfIteration) {
        this.sizeOfIteration = sizeOfIteration;
    }

    class RemindTask extends TimerTask {

        public void run() {
            float[] dataToWrite = new float[sizeOfIteration];
            for (int i = 0; i < dataToWrite.length; i++) {
                try {
                    dataToWrite[i] = Float.parseFloat(input.readLine());
                } catch (IOException ex) {
                    Logger.getLogger(DataSourceFileTimeSeries.class.getName()).log(Level.SEVERE, null, ex);
                    timer.cancel();
                    break;
                }
                index++;
            }
            WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(nameSignal, dataToWrite, currentIteration * sizeOfIteration);
            waitAndSendWriterRunable(writerRunnableTimeSeries);
            currentIteration++;
            if (currentIteration > limitOfItIterations) {
                desactivate();
                System.out.println("Time's end!" + nameSignal);
                timer.cancel(); //Terminate the timer thread
            }
        }
    }
}
