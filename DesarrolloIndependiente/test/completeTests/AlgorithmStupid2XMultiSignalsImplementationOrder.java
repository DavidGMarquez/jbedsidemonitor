/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

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
import signals.TimeSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmStupid2XMultiSignalsImplementationOrder extends AlgorithmDefaultImplementation {

    private Map<String, Integer> indexOfWrite;

    public AlgorithmStupid2XMultiSignalsImplementationOrder(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
        indexOfWrite = new HashMap<String, Integer>();
    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();

            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                    ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResultOne;
                    float[] data = readResultTimeSeries.getData();
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i] * 2;
                    }
                    
                        if (indexOfWrite.get(readResultTimeSeries.getIdentifierSignal()) == null) {
                            indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), new Integer(0));
                        }

                        int auxIndexOfWrite = indexOfWrite.get(readResultTimeSeries.getIdentifierSignal()).intValue();
                        WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(readResultTimeSeries.getIdentifierSignal() + "_" + this.getIdentifier(), data,readResultTimeSeries.getPosInitToRead());
                        indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), auxIndexOfWrite + data.length);
                        SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
                    


                }
                if (readResultOne instanceof ReadResultEventSeries) {
                }
            }
        }
        return true;
    }
}
