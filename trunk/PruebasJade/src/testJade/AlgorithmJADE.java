/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testJade;

import algorithms.AlgorithmDefaultImplementation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;
import signals.Series;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmJADE extends AlgorithmDefaultImplementation {


    public AlgorithmJADE(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {

            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();

            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                    ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResultOne;
                    this.proccess(readResultTimeSeries);
                }
                if (readResultOne instanceof ReadResultEventSeries) {
                }
            }
        }
        if (readResult instanceof ReadResultTimeSeries) {
            ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResult;
            this.proccess(readResultTimeSeries);
        }
        if (readResult instanceof ReadResultEventSeries) {
        }
        return true;
    }

    public void proccess(ReadResultTimeSeries readResultTimeSeries) {
        float[] data = readResultTimeSeries.getData();
        for (int i = 0; i < data.length; i++) {
            data[i] = (float) (Math.pow(-1, i) * Math.pow(data[i], 2));
            if (Float.compare(data[i], Float.NaN) == 0) {
                data[i] = 0;
            }
        }
                if(readResultTimeSeries.getPosInitToRead()%100000==0){
            System.out.println(this.getIdentifier()+" "+readResultTimeSeries.getPosInitToRead());
        }
        Thread.yield();
  //      WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(this.getSignalToWrite().getIdentifier(), data, readResultTimeSeries.getPosInitToRead());
//        indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), auxIndexOfWrite + data.length);

        //SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
    }
}
