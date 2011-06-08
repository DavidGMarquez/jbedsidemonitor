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
public class SerialEventSeriesMark {

    String nameSignal;
    Timer timer;
    int limitOfItIterations;
    int currentIteration;
    int sizeOfIteration;
    double muestraAnterior;
    double muestraAnteriorAnterior;

    public SerialEventSeriesMark(int delayFirstTime, int periodOfTime, int limitOfIterations, String nameSignal, int sizeOfIteration) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), delayFirstTime, periodOfTime);
        this.currentIteration = 0;
        this.limitOfItIterations = limitOfIterations;
        this.nameSignal = nameSignal;
        this.sizeOfIteration = sizeOfIteration;
        this.muestraAnterior = 0;
        this.muestraAnteriorAnterior = 0;
    }

    class RemindTask extends TimerTask {

        public void run() {
            WriterRunnableEventSeries writerRunnableEventSeries = new WriterRunnableEventSeries(nameSignal);
            if (currentIteration >= limitOfItIterations) {
                System.out.println("Time's end!");
                timer.cancel(); //Terminate the timer thread
            } else {
                for (int i = 0; i < sizeOfIteration; i++) {
                    double muestraActual =  Math.sin(((float)currentIteration/10)+((float)i/100));
                    if (muestraActual > muestraAnterior && muestraAnterior < muestraAnteriorAnterior) {
                        //Minimo
                        writerRunnableEventSeries.addEventToWrite(new Event(1000*(int)((currentIteration/10)+((float)i/100)), "Minimo", new HashMap<String, String>()));
                    }
                    if (muestraActual < muestraAnterior && muestraAnterior > muestraAnteriorAnterior) {
                        //Maximo
                        writerRunnableEventSeries.addEventToWrite(new Event(1000*(int)((currentIteration/10)+((float)i/100)), "Maximo", new HashMap<String, String>()));
                    }
                    muestraAnteriorAnterior=muestraAnterior;
                    muestraAnterior=muestraActual;
                }
                SignalManager.getInstance().encueWriteOperation(writerRunnableEventSeries);
                currentIteration++;
            }
        }
    }
}
