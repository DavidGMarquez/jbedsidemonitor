/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTest;

import completeTestsEventSeries.*;
import completeTestsTimeSeries.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Event;
import signals.SignalManager;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class SerialEventSeriesGui {

    String nameSignal;
    Timer timer;
    int limitOfItIterations;
    int currentIteration;
    int sizeOfIteration;

    public SerialEventSeriesGui(int delayFirstTime, int periodOfTime, int limitOfIterations, String nameSignal, int sizeOfIteration) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), delayFirstTime, periodOfTime);
        this.currentIteration = 0;
        this.limitOfItIterations = limitOfIterations;
        this.nameSignal = nameSignal;
        this.sizeOfIteration = sizeOfIteration;
    }

    class RemindTask extends TimerTask {

        public void run() {
            WriterRunnableEventSeries writerRunnableEventSeries = new WriterRunnableEventSeries(nameSignal);
            if (currentIteration >= limitOfItIterations) {
                System.out.println("Time's end!");
                timer.cancel(); //Terminate the timer thread
            } else {
                for (int i = 0; i < sizeOfIteration; i++) {
                    writerRunnableEventSeries.addEventToWrite(new Event(10000*((currentIteration * 10) + (i)), "Originated bySerialEventSeriesGenerator", new HashMap<String, String>()));
                    //         System.out.println((i+currentIteration*10)+"Value Serial +"+dataToWrite[i]);
                }
                            while (!SignalManager.getInstance().isRunning()) {
                synchronized (SignalManager.getInstance().getLockWaitRunning()) {
                    try {
                        SignalManager.getInstance().getLockWaitRunning().wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SinTimeSeriesGeneratorGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
                SignalManager.getInstance().encueWriteOperation(writerRunnableEventSeries);
                currentIteration++;
            }
        }
    }
}
