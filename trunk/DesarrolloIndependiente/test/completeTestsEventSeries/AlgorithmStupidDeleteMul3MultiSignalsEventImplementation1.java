/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsEventSeries;

import completeTestsTimeSeries.*;
import algorithms.AlgorithmDefaultImplementation;
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
public class AlgorithmStupidDeleteMul3MultiSignalsEventImplementation1 extends AlgorithmDefaultImplementation {

    private Map<String, Integer> indexOfWrite;
    SortedSet<Event> eventsUnmodifiableCopy = new TreeSet<Event>() ;
    LinkedList<Event> eventsNew=new LinkedList<Event>();
    LinkedList<Event> eventsOld=new LinkedList<Event>();

    public AlgorithmStupidDeleteMul3MultiSignalsEventImplementation1(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
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
        eventsOld=new LinkedList<Event>(eventsNew);
        eventsNew=new LinkedList<Event>();
        if(indexOfWrite.get(readResultEventSeries.getIdentifierSignal())==null){
            indexOfWrite.put(readResultEventSeries.getIdentifierSignal(), new Integer(0));
             eventsUnmodifiableCopy = readResultEventSeries.getEventsUnmodifiableCopy();
        }
        WriterRunnableEventSeries writerRunnableEventSeries = new WriterRunnableEventSeries(readResultEventSeries.getIdentifierSignal() + "_" + this.getIdentifier());
        LinkedList<Event> eventsReadWritten = readResultEventSeries.getEventsReadWritten();
        for (Event currentEvent : eventsReadWritten) {
            writerRunnableEventSeries.addEventToWrite(new Event(currentEvent.getLocation(), currentEvent.getType(), currentEvent.getCopyOfAttributes()));
             if (currentEvent.getLocation() % 3 == 0) {
                 eventsNew.add(currentEvent);
             }
        }
        LinkedList<Event> eventsReadDeleted = readResultEventSeries.getEventsReadDeleted();
        for (Event currentEvent : eventsReadDeleted) {
            writerRunnableEventSeries.addEventToDelete(new Event(currentEvent.getLocation(), currentEvent.getType(), currentEvent.getCopyOfAttributes()));

        }
        for(Event event:eventsOld){
            writerRunnableEventSeries.addEventToDelete(event);
        }
        SignalManager.getInstance().encueWriteOperation(writerRunnableEventSeries);
    }
}
