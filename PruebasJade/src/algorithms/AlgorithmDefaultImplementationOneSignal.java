package algorithms;

import datasource.DataSourceDefault;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import signals.Series;
import signals.SignalManager;
import signals.WriterRunnable;

public abstract class AlgorithmDefaultImplementationOneSignal implements Algorithm {

    private String identifier;
    private Series signalToWrite;
    private AlgorithmNotifyPolice algorithmNotifyPolice;

    public AlgorithmDefaultImplementationOneSignal(String identifier,
            Series signalToWrite, AlgorithmNotifyPolice algorithmNotifyPolice) {
        this.identifier = identifier;
        this.signalToWrite = signalToWrite;
        this.algorithmNotifyPolice = algorithmNotifyPolice;
    }

    public AlgorithmDefaultImplementationOneSignal(String identifier, Series signalToWrite,
            LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        this.identifier = identifier;
        this.signalToWrite = signalToWrite;
        HashMap<String, Integer> eventSeriesHold = new HashMap<String, Integer>();
        for (String eventSerieName : eventSeries) {
            eventSeriesHold.put(eventSerieName, new Integer(0));
        }
        HashMap<String, Integer> timeSeriesHold = new HashMap<String, Integer>();
        for (String timeSerieName : timeSeries) {
            timeSeriesHold.put(timeSerieName, new Integer(100));
        }
        this.algorithmNotifyPolice = new AlgorithmNotifyPolice(timeSeriesHold,
                eventSeriesHold, AlgorithmNotifyPoliceEnum.ONE);
    }

    public AlgorithmNotifyPolice getNotifyPolice() {
        return this.algorithmNotifyPolice;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Series getSignalToWrite() {
        return this.signalToWrite;
    }

    public boolean hasConfigurationGui() {
        return false;
    }

    public void showConfigurationGui(JFrame jframe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        public boolean waitAndSendWriterRunable(WriterRunnable writerRunnable) {
        while (!SignalManager.getInstance().isRunning()) {
            synchronized (SignalManager.getInstance().getLockWaitRunning()) {
                try {
                    SignalManager.getInstance().getLockWaitRunning().wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlgorithmDefaultImplementationOneSignal.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return sendWriterRunnable(writerRunnable);
    }

    public boolean sendWriterRunnable(WriterRunnable writerRunnable) {
        return SignalManager.getInstance().encueWriteOperation(writerRunnable);
    }
}
