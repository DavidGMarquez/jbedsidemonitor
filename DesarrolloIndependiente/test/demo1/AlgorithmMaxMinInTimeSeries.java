/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

import algorithms.AlgorithmDefaultImplementation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import signals.Event;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;
import signals.Series;
import signals.SignalManager;
import signals.WriterRunnableEventSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmMaxMinInTimeSeries extends AlgorithmDefaultImplementation {

    private float sampleolderolder = Float.MAX_VALUE;
    private float sampleolder = Float.MAX_VALUE;
    private float sample = Float.MAX_VALUE;

    public AlgorithmMaxMinInTimeSeries(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                    process((ReadResultTimeSeries) readResultOne);
                }
                if (readResult instanceof ReadResultEventSeries) {
                    process((ReadResultEventSeries) readResultOne);
                }
            }
        }
        if (readResult instanceof ReadResultTimeSeries) {
            process((ReadResultTimeSeries) readResult);
        }
        if (readResult instanceof ReadResultEventSeries) {
            process((ReadResultEventSeries) readResult);
        }
        return true;
    }

    public void process(ReadResultTimeSeries readResultTimeSeries) {
        float frecuency = SignalManager.getInstance().getFrecuencyTimeSeries(readResultTimeSeries.getIdentifierSignal());
        long origin = SignalManager.getInstance().getOriginTimeSeries(readResultTimeSeries.getIdentifierSignal());
        WriterRunnableEventSeries writerRunnableEventSeries = new WriterRunnableEventSeries(this.getSignalToWrite().getIdentifier());
        int posInitToRead = readResultTimeSeries.getPosInitToRead();
        float[] data = readResultTimeSeries.getData();

        for (int i = 0; i < data.length; i++) {
            sampleolderolder = sampleolder;
            sampleolder = sample;
            sample = data[i];
            if (sampleolderolder < sampleolder && sampleolder > sample) {
                writerRunnableEventSeries.addEventToWrite(maxEvent(i, posInitToRead, frecuency, origin));
            }
            if (sampleolderolder > sampleolder && sampleolder < sample) {
                writerRunnableEventSeries.addEventToWrite(minEvent(i, posInitToRead, frecuency, origin));
            }
        }
        this.waitAndSendWriterRunable(writerRunnableEventSeries);
    }

    public Event maxEvent(int index, int posInitToWrite, float frecuency, long origin) {
        long location = (long) (((index + posInitToWrite) * 1000) / frecuency + origin);
        Event event = new Event(location, "Max", new HashMap<String, String>());
        return event;
    }

    public Event minEvent(int index, int posInitToWrite, float frecuency, long origin) {
        long location = (long) (((index + posInitToWrite) * 1000) / frecuency + origin);
        Event event = new Event(location, "Min", new HashMap<String, String>());
        return event;
    }

    public void process(ReadResultEventSeries readResultEventSeries) {
    }
}
