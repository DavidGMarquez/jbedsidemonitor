/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsEventSeries;

import completeTestsTimeSeries.*;
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
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmStupid2XMultiSignalsEventImplementation extends AlgorithmDefaultImplementation {

    private Map<String, Integer> indexOfWrite;

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
                    this.proccess((ReadResultEventSeries)readResultOne);
                }
            }
        }
          if (readResult instanceof ReadResultEventSeries) {
              this.proccess((ReadResultEventSeries)readResult);
                }
        return true;
    }

    private void proccess(ReadResultEventSeries readResultEventSeries) {
    WriterRunnableEventSeries writerRunnableEventSeries= new WriterRunnableEventSeries(readResultEventSeries.getIdentifierSignal() + "_" + this.getIdentifier());
    readResultEventSeries.getEventsReadWritten();
    }
}
