/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import algorithms.Algorithm;
import algorithms.AlgorithmManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSourceAdapter;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.DefaultInstantAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.DefaultInstantMark;
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
    private JSignalMonitor jSignalMonitor = null;
    private ArrayList<String> availableCategoriesOfAnnotations = new ArrayList<String>();
    private ConcurrentHashMap<String, ArrayList<JSignalMonitorMark>> marksOfSignals;
    private ConcurrentHashMap<String, ArrayList<JSignalMonitorAnnotation>> annotationOfSignals;

    public JSignalAdapter() {
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        signalsLocks = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
        marksOfSignals = new ConcurrentHashMap<String, ArrayList<JSignalMonitorMark>>();
        annotationOfSignals = new ConcurrentHashMap<String, ArrayList<JSignalMonitorAnnotation>>();
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
            marksOfSignals.put(timeSeries.getIdentifier(), new ArrayList<JSignalMonitorMark>());
        }
    }

    public void addEventSeries(EventSeries eventSeries) {
        if (this.eventSeries.put(eventSeries.getIdentifier(), eventSeries) == null) {
            signalsLocks.put(eventSeries.getIdentifier(), new ReentrantReadWriteLock());
            annotationOfSignals.put(eventSeries.getIdentifier(), new ArrayList<JSignalMonitorAnnotation>());
        }
    }

    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        return availableCategoriesOfAnnotations;
    }

    public float[] getChannelData(String signalName, long firstValueInMiliseconds,
            long lastValueInMiliseconds) {
        if (lastValueInMiliseconds < 0) {
            //@pendiente en la documentacion pone algo de las abcisas
            return new float[1];
        }
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo = this.getConsecutiveSamplesAvailableInfo(signalName);
        int firstSample = (int) (firstValueInMiliseconds * getFrecuencySignalTimeSeries(signalName) / 1000);
        int lastSample = (int) (lastValueInMiliseconds * getFrecuencySignalTimeSeries(signalName) / 1000);
        //System.out.println("PedidoGetChannelData" + signalName + "Desde" + firstSample + "Hasta" + lastSample);
        //@pendiente que pasa si me pide datos mas antiguos de los que tengo
        if (lastSample > consecutiveSamplesAvailableInfo.getOlderSampleAvailable() + consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder()) {
            lastSample = consecutiveSamplesAvailableInfo.getOlderSampleAvailable() + consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder();
        }
        //System.out.println("GetChannelData" + signalName + "Desde" + firstSample + "Hasta" + lastSample);
        try {
            if ((lastSample - firstSample) > 0) {
                float[] readFromTimeSeries = this.readFromTimeSeries(signalName, firstSample, lastSample - firstSample);
                return readFromTimeSeries;
            } else {
                return new float[1];
            }
        } catch (IllegalReadException e) {
            System.out.println("Exception" + e.getMessage());
            return new float[1];
        }

    }

    public float getChannelValueAtTime(String signalName, long timeInMiliseconds) {
        int sample = (int) (timeInMiliseconds * getFrecuencySignalTimeSeries(signalName) / 1000);
        if (sample < 0) {
            return 0;
        }
        //System.out.println("GetChannelValue" + signalName + "Sample" + sample);
        try {
            return this.readFromTimeSeries(signalName, sample, 1)[0];
        } catch (IllegalReadException e) {
            return 0;
        }
    }

    public List<JSignalMonitorAnnotation> getAnnotations(long firstValue, long lastValue) {
        ArrayList<JSignalMonitorAnnotation> annotationsToReturn = new ArrayList<JSignalMonitorAnnotation>();
        for (String signalName : availableCategoriesOfAnnotations) {
            // this.signalsLocks.get(signalName).writeLock().lock();
            this.signalsLocks.get(signalName).readLock().lock();
            try {
                annotationsToReturn.addAll(this.getEventsAnnotations(signalName, firstValue, lastValue));
            } finally {
                this.signalsLocks.get(signalName).readLock().unlock();
            }
            //this.signalsLocks.get(signalName).writeLock().unlock();
        }
        return annotationsToReturn;
    }

    public List<JSignalMonitorAnnotation> getEventsAnnotations(String signalName, long firstValue, long lastValue) {
        ArrayList<JSignalMonitorAnnotation> annotationsToReturn = new ArrayList<JSignalMonitorAnnotation>();
        ArrayList<JSignalMonitorAnnotation> signalsAnnotations = this.annotationOfSignals.get(signalName);
        if (signalsAnnotations != null) {
            for (JSignalMonitorAnnotation jSignalMonitorAnnotation : signalsAnnotations) {
                if (jSignalMonitorAnnotation.getAnnotationTime() < lastValue && jSignalMonitorAnnotation.getAnnotationTime() > firstValue) {
                    annotationsToReturn.add(jSignalMonitorAnnotation);
                }
            }
        }
        return annotationsToReturn;
    }

    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue, long lastValue) {
        ArrayList<JSignalMonitorMark> marksToReturn = new ArrayList<JSignalMonitorMark>();
         this.signalsLocks.get(signalName).writeLock().lock();
       // this.signalsLocks.get(signalName).readLock().lock();
        try {
            marksToReturn.addAll(this.getEventsMarks(signalName, firstValue, lastValue));
        } finally {
         //   this.signalsLocks.get(signalName).readLock().unlock();
            this.signalsLocks.get(signalName).writeLock().unlock();
        }
        
        return marksToReturn;
    }

    private List<JSignalMonitorMark> getEventsMarks(String signalName, long firstValue, long lastValue) {
        ArrayList<JSignalMonitorMark> marksToReturn = new ArrayList<JSignalMonitorMark>();
        ArrayList<JSignalMonitorMark> signalsMarks = this.marksOfSignals.get(signalName);
        if (signalsMarks != null) {
            for (JSignalMonitorMark jSignalMonitorMark : signalsMarks) {
                if (jSignalMonitorMark.getMarkTime() < lastValue && jSignalMonitorMark.getMarkTime() > firstValue) {
                    marksToReturn.add(jSignalMonitorMark);
                }
            }
        }
        return marksToReturn;
    }

    public short[] getSignalEmphasisLevels(String signalName, long firstValueInMiliseconds,
            long lastValueInMiliseconds) {
        //@pendientes
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

    private float[] readFromTimeSeries(String identifierSignal, int posSrc, int sizeToRead) {
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

    private DefaultInstantMark convertEvent2DefaultInstantMark(Event e, String category) {
        DefaultInstantMark defaultInstantMark = new DefaultInstantMark();
        defaultInstantMark.setComentary(e.getType());
        defaultInstantMark.setColor(Color.white);
        defaultInstantMark.setMarkTime(e.getLocation());
        defaultInstantMark.setTitle(category);
        return defaultInstantMark;

    }

    private DefaultInstantAnnotation convertEvent2DefaultInstantAnnotation(Event e, String category) {
        DefaultInstantAnnotation defaultInstantAnnotation = new DefaultInstantAnnotation();
        defaultInstantAnnotation.setAnnotationTime(e.getLocation());
        defaultInstantAnnotation.setTitle(e.getType());
        defaultInstantAnnotation.setColor(Color.yellow);
        defaultInstantAnnotation.setCategory(category);
        return defaultInstantAnnotation;

    }

    public LinkedList<String> getAllTimeSeriesNames() {
        return new LinkedList<String>(this.timeSeries.keySet());
    }

    public LinkedList<String> getAllEventSeriesNames() {
        return new LinkedList<String>(this.eventSeries.keySet());
    }

    public float getFrecuencySignalTimeSeries(String signalName) {
        return this.timeSeries.get(signalName).getFrequency();
    }

    public int getDataSizeTimeSeries(String signalName) {
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo =
                this.getConsecutiveSamplesAvailableInfo(signalName);
        //@pendiente revisar si es esto o lo otro
        return consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder();
    }

    public long getOriginTimeSeries(String signalName) {
        return this.timeSeries.get(signalName).getOrigin();
    }

    public float getMaxSignalTimeSeries(String signalName) {
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo =
                this.getConsecutiveSamplesAvailableInfo(signalName);
        int origin = consecutiveSamplesAvailableInfo.getOlderSampleAvailable();
        int length = consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder();
        float[] readFromTimeSeries = this.readFromTimeSeries(signalName, origin, length);
        float max = Float.MIN_VALUE;
        for (int i = 0; i < readFromTimeSeries.length; i++) {
            if (readFromTimeSeries[i] > max) {
                max = readFromTimeSeries[i];
            }
        }
        return max;
    }

    public float getMinSignalTimeSeries(String signalName) {
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo =
                this.getConsecutiveSamplesAvailableInfo(signalName);
        int origin = consecutiveSamplesAvailableInfo.getOlderSampleAvailable();
        int length = consecutiveSamplesAvailableInfo.getSamplesReadyToReadInOrder();
        float[] readFromTimeSeries = this.readFromTimeSeries(signalName, origin, length);
        float min = Float.MAX_VALUE;
        for (int i = 0; i < readFromTimeSeries.length; i++) {
            if (readFromTimeSeries[i] < min) {
                min = readFromTimeSeries[i];
            }
        }
        return min;
    }

    public void switchEventSeriesToAnnotations(String signalName) {
        if (eventSeries.get(signalName) != null) {
            if (availableCategoriesOfAnnotations.contains(signalName)) {
                removeEventSeriesToAnnotations(signalName);
            } else {
                addEventSeriesToAnnotations(signalName);
            }
        }
    }

    private boolean addEventSeriesToAnnotations(String signalName) {
        return availableCategoriesOfAnnotations.add(signalName);
    }

    private boolean removeEventSeriesToAnnotations(String signalName) {
        return availableCategoriesOfAnnotations.remove(signalName);
    }

    public void executeWriterRunnable(WriterRunnable writerRunnable) {
        //@pendiente idea
        //Cambiar esto a despues de haberse ejecutado los writer Runnables
        //Quizás hacer un executor Service para esto
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
        try {
            if (this.eventSeries.get(identifierSignal).getAgent().equals("Simulated")) {
                processWriterRunnableEventSeriesAnnotation(writerRunnableEventSeries);
            } else {
                processWriterRunnableEventSeriesMark(writerRunnableEventSeries);
            }
        } finally {
            this.signalsLocks.get(identifierSignal).writeLock().unlock();
        }
    }

    private void processWriterRunnableEventSeriesMark(WriterRunnableEventSeries writerRunnableEventSeries) {
        String identifierSignal = writerRunnableEventSeries.getIdentifier();
        String agentAlgorithmName = this.eventSeries.get(identifierSignal).getAgent();
        Algorithm algorithm = AlgorithmManager.getInstance().getAlgorithm(agentAlgorithmName);
        if (algorithm != null) {
            if (algorithm.getNotifyPolice().getTimeSeriesTheshold().keySet().size() == 1) {
                String signalName = algorithm.getNotifyPolice().getTimeSeriesTheshold().keySet().iterator().next();
                //@pendiente decidir que lock coger
                this.signalsLocks.get(signalName).writeLock().lock();
                ArrayList<JSignalMonitorMark> marks = marksOfSignals.get(signalName);
                ArrayList<JSignalMonitorMark> marksToAdd = new ArrayList<JSignalMonitorMark>();
                LinkedList<Event> eventsToDelete = new LinkedList<Event>(writerRunnableEventSeries.getEventsToDelete());
                //@pendiente borrar eventos
/*            for (Event eventDelete : eventsToDelete) {
                this.eventSeries.get(identifierSignal).deleteEvent(eventDelete);
                }*/
                LinkedList<Event> eventsToWrite = new LinkedList<Event>(writerRunnableEventSeries.getEventsToWrite());
                for (Event eventWrite : eventsToWrite) {
                    marksToAdd.add(this.convertEvent2DefaultInstantMark(eventWrite, signalName));
                }
                marks.addAll(marksToAdd);
                this.signalsLocks.get(signalName).writeLock().unlock();
            }
        }


    }

    private void processWriterRunnableEventSeriesAnnotation(WriterRunnableEventSeries writerRunnableEventSeries) {
        String identifierSignal = writerRunnableEventSeries.getIdentifier();
        ArrayList<JSignalMonitorAnnotation> annotations = annotationOfSignals.get(identifierSignal);
        ArrayList<JSignalMonitorAnnotation> annotationsToAdd = new ArrayList<JSignalMonitorAnnotation>();
        LinkedList<Event> eventsToDelete = new LinkedList<Event>(writerRunnableEventSeries.getEventsToDelete());
        //@pendiente borrar eventos
/*            for (Event eventDelete : eventsToDelete) {
        this.eventSeries.get(identifierSignal).deleteEvent(eventDelete);
        }*/
        LinkedList<Event> eventsToWrite = new LinkedList<Event>(writerRunnableEventSeries.getEventsToWrite());
        for (Event eventWrite : eventsToWrite) {
            annotationsToAdd.add(this.convertEvent2DefaultInstantAnnotation(eventWrite, identifierSignal));
        }
        annotations.addAll(annotationsToAdd);
    }

    private void notifyWriterRunnableTimeSeries(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        String identifierSignal = writerRunnableTimeSeries.getIdentifier();
        this.signalsLocks.get(identifierSignal).writeLock().lock();
        try {
            this.timeSeries.get(identifierSignal).write(writerRunnableTimeSeries.getDataToWrite(), writerRunnableTimeSeries.getIndexInitToWrite());
        } finally {
            this.signalsLocks.get(identifierSignal).writeLock().unlock();
        }
        this.processWriterRunnableTimeSeries(writerRunnableTimeSeries);
    }

    private void processWriterRunnableTimeSeries(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        String identifierSignal = writerRunnableTimeSeries.getIdentifier();

        if (jSignalMonitor != null) {
            jSignalMonitor.getChannelProperties(identifierSignal).setDataSize(this.getDataSizeTimeSeries(identifierSignal));
            //   jSignalMonitor.setScrollValue(jSignalMonitor.getEndTime());
            //  jSignalMonitor.repaintAll();
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
