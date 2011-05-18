/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSourceAdapter;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;

/**
 *
 * @author USUARIO
 */
public class JSignalAdapter extends JSignalMonitorDataSourceAdapter {

    private ConcurrentMap<String, TimeSeries> timeSeries;
    private ConcurrentMap<String, EventSeries> eventSeries;
    private ConcurrentMap<String, ReentrantReadWriteLock> signalsLocks;
    private JSignalMonitor jSignalMonitor =null;

    public JSignalAdapter() {
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        signalsLocks = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
            }

    public JSignalMonitor getjSignalMonitor() {
        return jSignalMonitor;
    }

    public void setjSignalMonitor(JSignalMonitor jSignalMonitor) {
        this.jSignalMonitor = jSignalMonitor;
    }

    
    public void addTimeSeries(TimeSeries timeSeries) {

        if (this.timeSeries.put(timeSeries.getIdentifier(), timeSeries) == null) {
            signalsLocks.put(timeSeries.getIdentifier(), new ReentrantReadWriteLock());
        }

    }

    public void addEventSeries(EventSeries eventSeries) {
        if (this.eventSeries.put(eventSeries.getIdentifier(), eventSeries) == null) {
            signalsLocks.put(eventSeries.getIdentifier(), new ReentrantReadWriteLock());
        }
    }

    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        ArrayList<String> availableCategoriesOfAnnotations = new ArrayList<String>();
        return availableCategoriesOfAnnotations;
    }

    public float[] getChannelData(String signalName, long firstValueInMiliseconds,
            long lastValueInMiliseconds) {
        if (lastValueInMiliseconds < 0) {
            return new float[1];
        }
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo = this.getConsecutiveSamplesAvailableInfo(signalName);
        int frequency = 1000;
        int firstSample = (int) firstValueInMiliseconds / frequency;
        int lastSample = (int) lastValueInMiliseconds / frequency;
        System.out.println("PedidoGetChannelData" + signalName + "Desde" + firstSample + "Hasta" + lastSample);
        //@pendiente que pasa si me pide datos mas antiguos de los que tengo
        if (lastSample > consecutiveSamplesAvailableInfo.getOlderSampleAvailable() + consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder()) {
            lastSample = consecutiveSamplesAvailableInfo.getOlderSampleAvailable() + consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder();
        }

        System.out.println("GetChannelData" + signalName + "Desde" + firstSample + "Hasta" + lastSample);
        try {
            if ((lastSample - firstSample) > 0) {
                float[] readFromTimeSeries = this.readFromTimeSeries(signalName, firstSample, lastSample - firstSample);
                return readFromTimeSeries;
            } else {
                //@duda float de 1 o de 0?
                return new float[1];
            }
        } catch (IllegalReadException e) {
            System.out.println("Exception" + e.getMessage());
            return new float[1];
        }

    }

    public float getChannelValueAtTime(String signalName, long timeInMiliseconds) {
        int sample = (int) timeInMiliseconds / 1000;
        if (sample < 0) {
            return 0;
        }
        System.out.println("GetChannelValue" + signalName + "Sample" + sample);
        try {
            return this.readFromTimeSeries(signalName, sample, 1)[0];
        } catch (IllegalReadException e) {
            return 0;
        }
    }

    public List<JSignalMonitorAnnotation> getAnnotations(long firstValue, long lastValue) {
        ArrayList<JSignalMonitorAnnotation> marksToReturn = new ArrayList<JSignalMonitorAnnotation>();
        return marksToReturn;
    }

    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue, long lastValue) {

        ArrayList<JSignalMonitorMark> marksToReturn = new ArrayList<JSignalMonitorMark>();
        return marksToReturn;
    }

    public short[] getSignalEmphasisLevels(String signalName, long firstValueInMiliseconds,
            long lastValueInMiliseconds) {
        //No valido
        if (lastValueInMiliseconds < 0) {
            return new short[1];
        }
        int frequency = 1;
        int firstSample = (int) firstValueInMiliseconds / frequency;
        int lastSample = (int) lastValueInMiliseconds / frequency;
        float[] readFromTimeSeries = SignalManager.getInstance().readSecureFromTimeSeries(signalName, (int) firstSample, (int) lastSample);
        short[] tmp = new short[readFromTimeSeries.length];
        for (int i = 0; i < readFromTimeSeries.length; i++) {
            tmp[i] = (short) readFromTimeSeries[i];
        }
        return tmp;
    }

    public float[] readFromTimeSeries(String identifierSignal, int posSrc, int sizeToRead) {
        this.signalsLocks.get(identifierSignal).readLock().lock();
        float read[] = null;
        try {
            read = this.timeSeries.get(identifierSignal).read(posSrc, sizeToRead);
        } finally {
            this.signalsLocks.get(identifierSignal).readLock().unlock();
        }
        return read;
    }

    public ConsecutiveSamplesAvailableInfo getConsecutiveSamplesAvailableInfo(String identifierSignal) {
        this.signalsLocks.get(identifierSignal).readLock().lock();
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo = null;
        try {
            consecutiveSamplesAvailableInfo = this.timeSeries.get(identifierSignal).getConsecutiveSamplesAvailableInfo();

        } finally {
            this.signalsLocks.get(identifierSignal).readLock().unlock();
        }
        return consecutiveSamplesAvailableInfo;
    }

    public LinkedList<String> getAllTimeSeriesNames() {
        return new LinkedList<String>(this.timeSeries.keySet());
    }

    public LinkedList<String> getAllEventSeriesNames() {
        return new LinkedList<String>(this.eventSeries.keySet());
    }

    public float getFrecuencySignal(String signalName) {
        return this.timeSeries.get(signalName).getFrequency();
    }

    public int getDataSize(String signalName) {
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo =
                this.getConsecutiveSamplesAvailableInfo(signalName);
        //@pendiente revisar si es esto o lo otro
        return consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder();
    }

    public long getOrigin(String signalName) {
        return this.timeSeries.get(signalName).getOrigin();
    }

    void executeWriterRunnable(WriterRunnable writerRunnable) {
        //@pendiente idea
        //Cambiar esto a despues de haberse ejecutado los writer Runnables
        Thread thread = new Thread(new executeJSignalAdapterRunnable(writerRunnable), "executeJSignalAdapterRunnable");
        thread.start();
    }

    void notifyWriterRunnable(WriterRunnable writerRunnable) {
        if (writerRunnable instanceof WriterRunnableEventSeries) {
            this.notifyWriterRunnableEventSeries((WriterRunnableEventSeries) writerRunnable);
        } else {
            if (writerRunnable instanceof WriterRunnableTimeSeries) {
                this.notifyWriterRunnableTimeSeries((WriterRunnableTimeSeries) writerRunnable);
            } else {
                this.notifyWriterRunnableMultiSignal((WriterRunnableMultiSignal) writerRunnable);
            }
        }
    }

    private void notifyWriterRunnableEventSeries(WriterRunnableEventSeries writerRunnableEventSeries) {
        String identifierSignal = writerRunnableEventSeries.getIdentifier();
        this.signalsLocks.get(identifierSignal).writeLock().lock();
        synchronized(writerRunnableEventSeries){
        try {
            LinkedList<Event> eventsToDelete = new LinkedList<Event>(writerRunnableEventSeries.getEventsToDelete());
            for (Event eventDelete : eventsToDelete) {
                this.eventSeries.get(identifierSignal).deleteEvent(eventDelete);
            }
            LinkedList<Event> eventsToWrite = new LinkedList<Event>(writerRunnableEventSeries.getEventsToWrite());
            for (Event eventWrite : eventsToWrite) {
                this.eventSeries.get(identifierSignal).addEvent(eventWrite);
            }
        } finally {
            this.signalsLocks.get(identifierSignal).writeLock().unlock();
        }

    }
    }

    private void notifyWriterRunnableTimeSeries(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        String identifierSignal = writerRunnableTimeSeries.getIdentifier();
        this.signalsLocks.get(identifierSignal).writeLock().lock();
        try {
            this.timeSeries.get(identifierSignal).write(writerRunnableTimeSeries.getDataToWrite(), writerRunnableTimeSeries.getIndexInitToWrite());
        } finally {
            this.signalsLocks.get(identifierSignal).writeLock().unlock();
        }
        if(jSignalMonitor!=null){
                jSignalMonitor.getChannelProperties(identifierSignal).setDataSize(this.getDataSize(identifierSignal));

                jSignalMonitor.setScrollValue(jSignalMonitor.getEndTime());
                jSignalMonitor.repaintAll();
            }
    }

    private void notifyWriterRunnableMultiSignal(WriterRunnableMultiSignal writerRunnableMultiSignal) {
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnableMultiSignal.getWriterRunnables()) {
            this.notifyWriterRunnable(writerRunnableOneSignal);
        }
    }

    public class executeJSignalAdapterRunnable implements Runnable {

        WriterRunnable writerRunnable;

        executeJSignalAdapterRunnable(WriterRunnable writerRunnable) {
            //@pendiente habría que copiarlo para que el de eventos no de problemas
            this.writerRunnable = writerRunnable;
        }

        public void run() {
            notifyWriterRunnable(writerRunnable);
        }
    }
}
