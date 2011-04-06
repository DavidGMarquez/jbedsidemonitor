/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import algorithms.AlgorithmDefaultImplementation;
import auxiliarTools.AuxTestUtilities;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import signals.Event;
import signals.EventSeries;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;
import signals.Series;
import signals.SignalManager;
import signals.TimeSeries;
import signals.WriterRunnable;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmStupidImplementation extends AlgorithmDefaultImplementation {

    public AlgorithmStupidImplementation(String identifier, Series signalToWrite,
            LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
    }

    @Override
    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
        //@debug    System.out.println("Multisignal de "+readResultMultiSignal.getReadResults().size()+" Señáles");
            if(this.getSignalToWrite() instanceof TimeSeries){
                float f[]={0};
                WriterRunnableTimeSeries writerRunnableTimeSeries=new WriterRunnableTimeSeries(
                        this.getSignalToWrite().getIdentifier(), f,0);
                SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
            }
            if(this.getSignalToWrite() instanceof EventSeries){
                WriterRunnableEventSeries writerRunnableEventSeries =
                        new WriterRunnableEventSeries(this.getSignalToWrite().getIdentifier());
                writerRunnableEventSeries.addEventToWrite(new Event(0, "Generated", new HashMap<String, String>()));
                SignalManager.getInstance().encueWriteOperation(writerRunnableEventSeries);
            }
        }
        if (readResult instanceof ReadResultTimeSeries) {
            ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResult;
       //@debug  System.out.println("TimeSeries" + readResultTimeSeries.getIdentifierSignal());
 //@debug           System.out.println("N" + readResultTimeSeries.getData().length);
        }
        if (readResult instanceof ReadResultEventSeries) {
            ReadResultEventSeries readResultEventSeries = (ReadResultEventSeries) readResult;
 //@debug           System.out.println("EventSeries" + readResultEventSeries.getIdentifierSignal());
  //@debug          System.out.println("N" + readResultEventSeries.getEventsRead().size());
        }
        return true;
    }
}
