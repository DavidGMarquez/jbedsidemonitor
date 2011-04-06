/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package completeTests;

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
public class SumSerial_SinTimeSeriesGeneratorOrder {
    String nameSignal;
    Timer timer;
    int limitOfItIterations;
    int currentIteration;

    public SumSerial_SinTimeSeriesGeneratorOrder(int delayFirstTime, int periodOfTime,int limitOfIterations,String nameSignal) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), delayFirstTime,periodOfTime);
        this.currentIteration = 0;
        this.limitOfItIterations = limitOfIterations;
        this.nameSignal=nameSignal;
    }

    class RemindTask extends TimerTask {

        public void run() {
            float[] dataToWrite=new float[10];
            for(int i=0;i<dataToWrite.length;i++){
                dataToWrite[i]=(float) Math.sin(((float)currentIteration/10)+((float)i/100))+(currentIteration*10)+(i);
        ///        System.out.println((i+currentIteration*10)+"Value Sin +"+dataToWrite[i]+ "  "+(((float)currentIteration/10)+((float)i/100)));
            }
            WriterRunnableTimeSeries writerRunnableTimeSeries=new WriterRunnableTimeSeries(nameSignal, dataToWrite,currentIteration*10);
            SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
            currentIteration++;
            if (currentIteration > limitOfItIterations) {
                System.out.println("Time's end!");
                timer.cancel(); //Terminate the timer thread
            }
        }
    }

}
