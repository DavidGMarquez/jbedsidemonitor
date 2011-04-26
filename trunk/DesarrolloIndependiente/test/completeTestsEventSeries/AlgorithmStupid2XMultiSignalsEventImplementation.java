/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsEventSeries;

import completeTestsTimeSeries.*;
import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmDefaultImplementationOneSignal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import signals.Event;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;
import signals.Series;
import signals.SignalManager;
import signals.TimeSeries;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmStupid2XMultiSignalsEventImplementation extends AlgorithmDefaultImplementationOneSignal {

    private Map<String, Integer> indexOfWrite;
    SortedSet<Event> eventsUnmodifiableCopy = new TreeSet<Event>() ;

    public AlgorithmStupid2XMultiSignalsEventImplementation(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
        indexOfWrite = new HashMap<String, Integer>();
    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();

            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                }
                if (readResultOne instanceof ReadResultEventSeries) {
                    this.proccess((ReadResultEventSeries) readResultOne);
                }
            }
        }
        if (readResult instanceof ReadResultEventSeries) {
            this.proccess((ReadResultEventSeries) readResult);
        }
        return true;
    }

    private void proccess(ReadResultEventSeries readResultEventSeries) {

        if(indexOfWrite.get(readResultEventSeries.getIdentifierSignal())==null){
            indexOfWrite.put(readResultEventSeries.getIdentifierSignal(), new Integer(0));
             eventsUnmodifiableCopy = readResultEventSeries.getEventsUnmodifiableCopy();
        }
        WriterRunnableEventSeries writerRunnableEventSeries = new WriterRunnableEventSeries(readResultEventSeries.getIdentifierSignal() + "_" + this.getIdentifier());
        LinkedList<Event> eventsReadWritten = readResultEventSeries.getEventsReadWritten();
        for (Event currentEvent : eventsReadWritten) {
            writerRunnableEventSeries.addEventToWrite(new Event(currentEvent.getLocation() * 2, currentEvent.getType(), currentEvent.getCopyOfAttributes()));
        }
        LinkedList<Event> eventsReadDeleted = readResultEventSeries.getEventsReadDeleted();
        for (Event currentEvent : eventsReadDeleted) {
            writerRunnableEventSeries.addEventToDelete(new Event(currentEvent.getLocation() * 2, currentEvent.getType(), currentEvent.getCopyOfAttributes()));
        }
        SignalManager.getInstance().encueWriteOperation(writerRunnableEventSeries);
    }
}
