/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import algorithms.AlgorithmDefaultImplementation;
import auxiliarTools.AuxTestUtilities;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AlgorithmStupid2XImplementation extends AlgorithmDefaultImplementation {

    public AlgorithmStupid2XImplementation(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
            System.out.println("STUPID2x---Multisignal de " + readResultMultiSignal.getReadResults().size() + " Señáles");
            ReadResult readResultOne = readResultMultiSignal.getReadResults().getFirst();

            if (readResultOne instanceof ReadResultTimeSeries) {
                ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResultOne;
                float[] data = readResultTimeSeries.getData();
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i] * 2;
                }
                if (this.getSignalToWrite() instanceof TimeSeries) {

                    WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(this.getSignalToWrite().getIdentifier(), data);
                    SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
                }


            }
            if (readResultOne instanceof ReadResultEventSeries) {
            }

        }
        return true;
    }
}
