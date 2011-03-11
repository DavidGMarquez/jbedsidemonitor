/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import algorithms.AlgorithmDefaultImplementation;
import java.util.LinkedList;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmStupidImplementation extends AlgorithmDefaultImplementation {

    public AlgorithmStupidImplementation(String identifier, String identifierSignalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, identifierSignalToWrite, timeSeries, eventSeries);
    }

    public boolean execute(ReadResult readResult) {
        System.out.println("Ejecutandose Algoritmo");
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
            System.out.println("Multisignal de "+readResultMultiSignal.getReadResults().size()+" Señáles");
        }
        if (readResult instanceof ReadResultTimeSeries) {
            ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResult;
            System.out.println("TimeSeries" + readResultTimeSeries.getIdentifierSignal());
            System.out.println("N" + readResultTimeSeries.getData().length);
        }
        if (readResult instanceof ReadResultEventSeries) {
            ReadResultEventSeries readResultEventSeries = (ReadResultEventSeries) readResult;
            System.out.println("EventSeries" + readResultEventSeries.getIdentifierSignal());
            System.out.println("N" + readResultEventSeries.getEventsRead().size());
        }
        return true;
    }
}
