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
public class AlgorithmStupidFileImplementation extends AlgorithmDefaultImplementation {

    FileWriter file = null;
    PrintWriter pw = null;
    String nameFile=null;

    public AlgorithmStupidFileImplementation(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries,String nameFile) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
        this.nameFile=nameFile;
        try {
            file = new FileWriter(this.nameFile);
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmStupidFileImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        pw = new PrintWriter(file);

    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
            ReadResult readResultOne = readResultMultiSignal.getReadResults().getFirst();

            if (readResultOne instanceof ReadResultTimeSeries) {
                ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResultOne;
                int posToRead=readResultTimeSeries.getPosInitToRead();
                float[] data = readResultTimeSeries.getData();
                for (int i = 0; i < data.length; i++) 
                {
                    pw.println(posToRead+i);
                    pw.println(data[i]);
                }
                pw.flush();
                try {
                    file.flush();
                } catch (IOException ex) {
                    Logger.getLogger(AlgorithmStupidFileImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (readResultOne instanceof ReadResultEventSeries) {

            }

        }
        return true;
    }
}
