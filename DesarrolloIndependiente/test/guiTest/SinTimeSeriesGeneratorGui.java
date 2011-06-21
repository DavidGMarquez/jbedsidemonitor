/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTest;

import completeTestsTimeSeries.*;
import datasource.DataSourceDefault;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;

public class SinTimeSeriesGeneratorGui extends DataSourceDefault{

    String nameSignal;
    Timer timer;
    int limitOfItIterations;
    int currentIteration;
    int sizeOfIteration;
    float frecuency;
    long index;
    double multiplier;

    public SinTimeSeriesGeneratorGui(int delayFirstTime, float frecuency, int limitOfIterations, String nameSignal, int sizeOfIteration, double multiplier) {

        timer = new Timer();
        float periodOfTime = (1 / frecuency) * 1000 * sizeOfIteration;
        timer.scheduleAtFixedRate(new RemindTask(), delayFirstTime, (long) periodOfTime);
        this.sizeOfIteration = sizeOfIteration;
        this.currentIteration = 0;
        this.frecuency = frecuency;
        this.limitOfItIterations = limitOfIterations;
        this.nameSignal = nameSignal;
        this.index = 0;
        this.multiplier = multiplier;
        this.registerThis();
    }
    public ArrayList<String> getSeriesGenerated() {
        ArrayList seriesGenerated;
        seriesGenerated=new ArrayList();
        seriesGenerated.add(nameSignal);
        return seriesGenerated;
    }
    public String getIdentifier(){
        return "SinTimeSeriesGenerator";
    }
    class RemindTask extends TimerTask {

        public void run() {
            float[] dataToWrite = new float[sizeOfIteration];
            for (int i = 0; i < dataToWrite.length; i++) {
                dataToWrite[i] = (float) Math.sin(index * multiplier);
                index++;
            }
            WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(nameSignal, dataToWrite, currentIteration * 10);
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
