/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo2;

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
public class AlgorithmLatidoInTimeSeries extends AlgorithmDefaultImplementation {


    private float contador = -1;

    public AlgorithmLatidoInTimeSeries(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
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
            System.out.println("Data"+data[i]+"Contao"+contador);
            if(data[i]<-999){
                if(contador<0){
                    System.out.println("Latido");
                writerRunnableEventSeries.addEventToWrite(latidoEvent(i, posInitToRead, frecuency, origin));
                contador=50;}
            }
            contador--;
        }
        this.waitAndSendWriterRunable(writerRunnableEventSeries);
    }

    public Event latidoEvent(int index, int posInitToWrite, float frecuency, long origin) {
        long location = (long) (((index + posInitToWrite) * 1000) / frecuency + origin);
        Event event = new Event(location, "Latido", new HashMap<String, String>());
        return event;
    }


    public void process(ReadResultEventSeries readResultEventSeries) {
    }
}
