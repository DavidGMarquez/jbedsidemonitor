/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

import guiTest.*;
import completeTestsTimeSeries.*;
import datasource.DataSourceDefault;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;

public class DataSourceCosTimeSeries extends DataSourceDefault {

    String nameSignal;
    Timer timer;
    int limitOfItIterations;
    int currentIteration;
    int sizeOfIteration;
    float frecuency;
    long index;
    double multiplier;
    int delayFirstTime;

    public DataSourceCosTimeSeries(int delayFirstTime, float frecuency, int limitOfIterations, String nameSignal, int sizeOfIteration, double multiplier) {
        this.delayFirstTime = delayFirstTime;
        this.sizeOfIteration = sizeOfIteration;
        this.currentIteration = 0;
        this.frecuency = frecuency;
        this.limitOfItIterations = limitOfIterations;
        this.nameSignal = nameSignal;
        this.index = 0;
        this.multiplier = multiplier;
        this.registerThis();
    }

    public boolean start() {
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
        return "DataSourceCoshTimeSeries";
    }

    public boolean hasConfigurationGui() {
        return true;
    }

    public void showConfigurationGui(JFrame jframe) {
        DataSourceCosTimeSeriesGui dataSourceCoshTimeSeriesGui = new DataSourceCosTimeSeriesGui(jframe, true, this);
        dataSourceCoshTimeSeriesGui.setLocationRelativeTo(jframe);
        dataSourceCoshTimeSeriesGui.setVisible(true);
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

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
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
                dataToWrite[i] = (float) Math.cos(index * multiplier);
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
