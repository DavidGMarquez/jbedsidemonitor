/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo3;

import guiTest.*;
import completeTestsTimeSeries.*;
import algorithms.AlgorithmDefaultImplementation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JFrame;
import signals.ReadResult;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;
import signals.ReadResultTimeSeries;
import signals.Series;
import signals.Series;
import signals.SignalManager;
import signals.SignalManager;
import signals.TimeSeries;
import signals.WriterRunnableTimeSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmDerivateTimeSeries extends AlgorithmDefaultImplementation {

    private Map<String, Integer> indexOfWrite;

    private float dataold;

    public AlgorithmDerivateTimeSeries(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
        indexOfWrite = new HashMap<String, Integer>();

    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                    process((ReadResultTimeSeries) readResultOne);
                }
            }
        }
        if (readResult instanceof ReadResultTimeSeries) {
            process((ReadResultTimeSeries) readResult);
        }
        if (readResult instanceof ReadResultEventSeries){
            ReadResultEventSeries readResultEventSeries= (ReadResultEventSeries) readResult;

        }
        return true;
    }

    public void process(ReadResultTimeSeries readResultTimeSeries) {
        float[] data = readResultTimeSeries.getData();
        for (int i = 0; i < data.length; i++) {
            float aux= data[i];
            data[i] = (data[i]-dataold);
            dataold=aux;
        }
        if (indexOfWrite.get(readResultTimeSeries.getIdentifierSignal()) == null) {
            indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), new Integer(0));
        }
        int auxIndexOfWrite = indexOfWrite.get(readResultTimeSeries.getIdentifierSignal()).intValue();
        WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(readResultTimeSeries.getIdentifierSignal() + "_" + this.getIdentifier(), data, readResultTimeSeries.getPosInitToRead());
        indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), auxIndexOfWrite + data.length);
        this.waitAndSendWriterRunable(writerRunnableTimeSeries);
    }




}
