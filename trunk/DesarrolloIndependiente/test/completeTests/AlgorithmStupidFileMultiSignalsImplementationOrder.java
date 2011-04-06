/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmDefaultImplementationOneSignal;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AlgorithmStupidFileMultiSignalsImplementationOrder extends AlgorithmDefaultImplementationOneSignal {

    private Map<String, FileWriter> signalFileWriter;
    private Map<String, PrintWriter> signalPrintWriter;

    public AlgorithmStupidFileMultiSignalsImplementationOrder(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
        signalFileWriter = new HashMap<String, FileWriter>();
        signalPrintWriter = new HashMap<String, PrintWriter>();
        Set<String> keySet = this.getNotifyPolice().getTimeSeriesTheshold().keySet();
        for (String key : keySet) {
            try {
                signalFileWriter.put(key, new FileWriter(key));
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmStupidFileImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            signalPrintWriter.put(key, new PrintWriter(signalFileWriter.get(key)));
        }
    }

    public boolean execute(ReadResult readResult) {
//@debug        System.out.println("Ejecutando MultiSignal File");
        if (readResult instanceof ReadResultMultiSignal) {

            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();

            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                    ReadResultTimeSeries readResultTimeSeries = (ReadResultTimeSeries) readResultOne;
         //@debug           System.out.println("Ejecutando MultiSignal File"+readResultTimeSeries.getIdentifierSignal()+"tamano "+readResultTimeSeries.getData().length);
                    int posToRead = readResultTimeSeries.getPosInitToRead();
                    PrintWriter pw=signalPrintWriter.get(readResultTimeSeries.getIdentifierSignal());
                    float[] data = readResultTimeSeries.getData();
                    for (int i = 0; i < data.length; i++) {
                        pw.println(posToRead + i);
                        pw.println(data[i]);
                    }
                    pw.flush();
                    FileWriter file=signalFileWriter.get(readResultTimeSeries.getIdentifierSignal());
                    try {
                        file.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(AlgorithmStupidFileImplementation.class.getName()).log(Level.SEVERE, null, ex);
                    }                 


                }
                if (readResultOne instanceof ReadResultEventSeries) {
                }
            }
        }
        return true;
    }
}
