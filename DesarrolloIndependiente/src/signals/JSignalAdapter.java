/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import algorithms.Algorithm;
import algorithms.AlgorithmExecutionInfo;
import algorithms.AlgorithmManager;
import datasource.DataSource;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSourceAdapter;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.DefaultInstantAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.DefaultInstantMark;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.DefaultIntervalAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.DefaultIntervalMark;
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
    private List<String> availableCategoriesOfAnnotations;
    private ConcurrentHashMap<String, ArrayList<JSignalMonitorMark>> marksOfSignals;
    private ConcurrentHashMap<String, ArrayList<JSignalMonitorAnnotation>> annotationOfSignals;
    private ConcurrentHashMap<String, ArrayList<String>> channelsToMarks;
    private ConcurrentHashMap<String, ArrayList<String>> channelsToMarksNotShow;
    private ConcurrentHashMap<String, ReentrantReadWriteLock> lockChannelsToMarks;
    private ExecutorServiceJSignalAdapterRunnable executorServiceJSignalAdapterRunnable;
    private boolean annotationInterval = true;
    private boolean markInterval = true;
    private int annotationIntervalSize = 250;
    private int markIntervalSize = 250;

    public JSignalAdapter() {
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        signalsLocks = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
        marksOfSignals = new ConcurrentHashMap<String, ArrayList<JSignalMonitorMark>>();
        annotationOfSignals = new ConcurrentHashMap<String, ArrayList<JSignalMonitorAnnotation>>();
        channelsToMarks = new ConcurrentHashMap<String, ArrayList<String>>();
        channelsToMarksNotShow = new ConcurrentHashMap<String, ArrayList<String>>();
        lockChannelsToMarks = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
        availableCategoriesOfAnnotations = Collections.synchronizedList(new ArrayList<String>());
        executorServiceJSignalAdapterRunnable = new ExecutorServiceJSignalAdapterRunnable();
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
            lockChannelsToMarks.put(timeSeries.getIdentifier(), new ReentrantReadWriteLock());
        }
    }

    public void addEventSeries(EventSeries eventSeries) {
        if (this.eventSeries.put(eventSeries.getIdentifier(), eventSeries) == null) {
            signalsLocks.put(eventSeries.getIdentifier(), new ReentrantReadWriteLock());
            if (isEventSeriesLikeMarks(eventSeries)) {
                marksOfSignals.put(eventSeries.getIdentifier(), new ArrayList<JSignalMonitorMark>());
                String channelName = eventSeries.getSeriesIsGeneratedFrom().get(0);
                anotateEventSeriesMark(channelName, eventSeries.getIdentifier());
            } else {
                annotationOfSignals.put(eventSeries.getIdentifier(), new ArrayList<JSignalMonitorAnnotation>());
                                availableCategoriesOfAnnotations.add(eventSeries.getIdentifier());
            }
        }
    }

    private void anotateEventSeriesMark(String channelName, String identifierSignal) {
        if (!existMarkToChannel(channelName, identifierSignal)) {
            createIfNoExistMarkToChannel(channelName, identifierSignal);
        }
    }

    private boolean existMarkToChannel(String channelName, String identifierSignal) {
        boolean response = false;
        lockChannelsToMarks.get(channelName).readLock().lock();
        try {
            ArrayList<String> signalsAnnotations = channelsToMarks.get(channelName);
            if (signalsAnnotations == null) {
                response = false;
            } else {
                if (signalsAnnotations.contains(identifierSignal)) {
                    response = true;
                }
                response = false;
            }
        } finally {
            lockChannelsToMarks.get(channelName).readLock().unlock();
        }
        return response;
    }

    private void createIfNoExistMarkToChannel(String channelName, String identifierSignal) {
        lockChannelsToMarks.get(channelName).writeLock().lock();
        try {
            if (!existMarkToChannel(channelName, identifierSignal)) {
                ArrayList<String> signalsAnnotations = channelsToMarks.get(channelName);
                if (signalsAnnotations == null) {
                    signalsAnnotations = new ArrayList<String>();
                    signalsAnnotations.add(identifierSignal);
                    channelsToMarks.put(channelName, signalsAnnotations);
                } else {
                    if (!signalsAnnotations.contains(identifierSignal)) {
                        signalsAnnotations.add(identifierSignal);
                        channelsToMarks.put(channelName, signalsAnnotations);
                    }
                }
            }
        } finally {
            lockChannelsToMarks.get(channelName).writeLock().unlock();
        }
    }

    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        return new ArrayList<String>(availableCategoriesOfAnnotations);
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
        lockChannelsToMarks.get(signalName).readLock().lock();
        ArrayList<String> signalsWithMarks = channelsToMarks.get(signalName);
        if (signalsWithMarks != null) {
            try {
                signalsWithMarks = new ArrayList<String>(channelsToMarks.get(signalName));
            } finally {
                lockChannelsToMarks.get(signalName).readLock().unlock();
            }
            for (String signalWithMarks : signalsWithMarks) {
                if (channelsToMarksNotShow.get(signalName) == null) {
                    try {
                        this.signalsLocks.get(signalWithMarks).readLock().lock();
                        marksToReturn.addAll(this.getIntervalMarksForSignal(signalWithMarks, firstValue, lastValue));
                    } finally {
                        this.signalsLocks.get(signalWithMarks).readLock().unlock();
                    }
                } else if (!channelsToMarksNotShow.get(signalName).contains(signalWithMarks)) {
                    try {
                        this.signalsLocks.get(signalWithMarks).readLock().lock();
                        marksToReturn.addAll(this.getIntervalMarksForSignal(signalWithMarks, firstValue, lastValue));
                    } finally {
                        this.signalsLocks.get(signalWithMarks).readLock().unlock();
                    }
                }
            }
        } else {
            lockChannelsToMarks.get(signalName).readLock().unlock();
        }
        return marksToReturn;
    }

    private List<JSignalMonitorMark> getIntervalMarksForSignal(String signalName, long firstValue, long lastValue) {
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

    public boolean isAnnotationInterval() {
        return annotationInterval;
    }

    public void setAnnotationInterval(boolean annotationInterval) {
        this.annotationInterval = annotationInterval;
    }

    public int getAnnotationIntervalSize() {
        return annotationIntervalSize;
    }

    public void setAnnotationIntervalSize(int annotationIntervalSize) {
        this.annotationIntervalSize = annotationIntervalSize;
    }

    public boolean isMarkInterval() {
        return markInterval;
    }

    public void setMarkInterval(boolean markInterval) {
        this.markInterval = markInterval;
    }

    public int getMarkIntervalSize() {
        return markIntervalSize;
    }

    public void setMarkIntervalSize(int markIntervalSize) {
        this.markIntervalSize = markIntervalSize;
    }

    private DefaultInstantMark convertEvent2DefaultInstantMark(Event e, String category) {
        DefaultInstantMark defaultInstantMark = new DefaultInstantMark();
        defaultInstantMark.setComentary(e.getType());
        defaultInstantMark.setColor(Color.yellow);
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

    private DefaultIntervalAnnotation convertEvent2DefaultIntervalAnnotation(Event e, String category) {
        DefaultIntervalAnnotation defaultIntervalAnnotation = new DefaultIntervalAnnotation();
        defaultIntervalAnnotation.setAnnotationTime(e.getLocation() - annotationIntervalSize);
        defaultIntervalAnnotation.setEndTime(e.getLocation() + annotationIntervalSize);
        defaultIntervalAnnotation.setTitle(e.getType());
        defaultIntervalAnnotation.setColor(Color.yellow);
        defaultIntervalAnnotation.setCategory(category);
        return defaultIntervalAnnotation;
    }

    private DefaultIntervalMark convertEvent2DefaultIntervalMark(Event e, String category) {
        DefaultIntervalMark defaultIntervalMark = new DefaultIntervalMark();
        defaultIntervalMark.setComentary(e.getType());
        defaultIntervalMark.setColor(Color.red);
        defaultIntervalMark.setMarkTime(e.getLocation() - markIntervalSize);
        defaultIntervalMark.setEndTime(e.getLocation() + markIntervalSize);
        defaultIntervalMark.setTitle(category);
        return defaultIntervalMark;
    }

    public LinkedList<String> getAllTimeSeriesNames() {
        return new LinkedList<String>(this.timeSeries.keySet());
    }

    public TimeSeries getTimeSeries(String signalName) {
        return this.timeSeries.get(signalName);
    }

    public LinkedList<String> getAllEventSeriesNames() {
        return new LinkedList<String>(this.eventSeries.keySet());
    }

    public EventSeries getEventSeries(String signalName) {
        return this.eventSeries.get(signalName);
    }

    public LinkedList<String> getAllAlgorithmNames() {
        return new LinkedList<String>(AlgorithmManager.getInstance().getAllAlgorithmNames());
    }

    public Algorithm getAlgorithm(String signalName) {
        return AlgorithmManager.getInstance().getAlgorithm(signalName);
    }

    public AlgorithmExecutionInfo getAlgorithmExecutionInfo(String signalName) {
        return AlgorithmManager.getInstance().getAlgorithmExecutionInfo(signalName);
    }

    public DataSource getDataSource(String dataSourceName) {
        return SignalManager.getInstance().getDataSource(dataSourceName);
    }

    public boolean getStateOfDataSource(String dataSourceName) {
        return SignalManager.getInstance().getStateOfDataSource(dataSourceName);
    }

    public LinkedList<String> getAllDataSourceNames() {
        return new LinkedList<String>(SignalManager.getInstance().getAllDataSourcesNames());
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

    public ArrayList<String> getSignalMarksForSignal(String signalName) {
        ArrayList<String> signalsMark = new ArrayList<String>();
        lockChannelsToMarks.get(signalName).readLock().lock();
        try {
            if (channelsToMarks.get(signalName) != null) {
                signalsMark = channelsToMarks.get(signalName);
            }
        } finally {
            lockChannelsToMarks.get(signalName).readLock().unlock();
        }
        return signalsMark;
    }

    public void switchMarkSignalShow(String signalName, String markSignal) {
        lockChannelsToMarks.get(signalName).writeLock().lock();
        try {
            ArrayList<String> marksSignalsNotShow = channelsToMarksNotShow.get(signalName);
            if (marksSignalsNotShow != null) {
                if (marksSignalsNotShow.contains(markSignal)) {
                    marksSignalsNotShow.remove(markSignal);
                } else {
                    marksSignalsNotShow.add(markSignal);
                }
            } else {
                marksSignalsNotShow = new ArrayList<String>();
                marksSignalsNotShow.add(markSignal);
            }
            channelsToMarksNotShow.put(signalName, marksSignalsNotShow);

        } finally {
            lockChannelsToMarks.get(signalName).writeLock().unlock();
        }
    }

    public boolean isEventSeriesLikeMarks(String signalName) {
        boolean response = false;
        this.signalsLocks.get(signalName).readLock().lock();
        try {
            EventSeries eventSerieName = this.eventSeries.get(signalName);
            if (eventSerieName.getSeriesIsGeneratedFrom().size() == 1) {
                String signalImputName = eventSerieName.getSeriesIsGeneratedFrom().get(0);
                if (timeSeries.get(signalImputName) != null) {
                    response = true;
                }
            }
        } finally {
            this.signalsLocks.get(signalName).readLock().unlock();
        }
        return response;
    }

    public boolean isEventSeriesLikeMarks(EventSeries eventSeries) {
        boolean response = false;
        if (eventSeries.getSeriesIsGeneratedFrom().size() == 1) {
            String signalImputName = eventSeries.getSeriesIsGeneratedFrom().get(0);
            if (timeSeries.get(signalImputName) != null) {
                response = true;
            }
        }
        return response;
    }

    public void switchStateAlgorithm(String algorithmName) {
        if (AlgorithmManager.getInstance().isActiveAlgorithm(algorithmName)) {
            AlgorithmManager.getInstance().desactiveAlgorithm(algorithmName);
        } else {
            AlgorithmManager.getInstance().activeAlgorithm(algorithmName);
        }
    }

    public void switchStateSignalManager() {
        if (SignalManager.getInstance().isRunning()) {
            SignalManager.getInstance().pause();
        } else {
            SignalManager.getInstance().resume();
        }
    }

    public boolean startSignalManager() {
        return SignalManager.getInstance().start();
    }

    public void executeWriterRunnable(WriterRunnable writerRunnable) {
        executorServiceJSignalAdapterRunnable.executeRunnable(new JSignalAdapterRunnable(writerRunnable));
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
            if (isEventSeriesLikeMarks(identifierSignal)) {
                processWriterRunnableEventSeriesMark(writerRunnableEventSeries);
            } else {
                processWriterRunnableEventSeriesAnnotation(writerRunnableEventSeries);
            }
        } finally {
            this.signalsLocks.get(identifierSignal).writeLock().unlock();
        }
    }

    private void processWriterRunnableEventSeriesMark(WriterRunnableEventSeries writerRunnableEventSeries) {
        String identifierSignal = writerRunnableEventSeries.getIdentifier();
        this.signalsLocks.get(identifierSignal).writeLock().lock();
        try {
            ArrayList<JSignalMonitorMark> marks = marksOfSignals.get(identifierSignal);
            ArrayList<JSignalMonitorMark> marksToAdd = new ArrayList<JSignalMonitorMark>();
            LinkedList<Event> eventsToDelete = new LinkedList<Event>(writerRunnableEventSeries.getEventsToDelete());
            //@pendiente borrar eventos
/*            for (Event eventDelete : eventsToDelete) {
            this.eventSeries.get(identif++++++ierSignal).deleteEvent(eventDelete);
            }*/
            LinkedList<Event> eventsToWrite = new LinkedList<Event>(writerRunnableEventSeries.getEventsToWrite());
            for (Event eventWrite : eventsToWrite) {
                if (markInterval) {
                    marksToAdd.add(this.convertEvent2DefaultIntervalMark(eventWrite, identifierSignal));
                } else {
                    marksToAdd.add(this.convertEvent2DefaultInstantMark(eventWrite, identifierSignal));
                }
            }
            marks.addAll(marksToAdd);
        } finally {
            this.signalsLocks.get(identifierSignal).writeLock().unlock();
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
            if (annotationInterval) {
                annotationsToAdd.add(this.convertEvent2DefaultIntervalAnnotation(eventWrite, identifierSignal));
            } else {
                annotationsToAdd.add(this.convertEvent2DefaultInstantAnnotation(eventWrite, identifierSignal));
            }

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
        this.updateDataTimeSeries(identifierSignal);
    }

    private void updateDataTimeSeries(String identifierSignal) {
        if (jSignalMonitor != null) {
            if (jSignalMonitor.hasChannel(identifierSignal)) {
                jSignalMonitor.getChannelProperties(identifierSignal).setDataSize(this.getDataSizeTimeSeries(identifierSignal));
            }
        }
    }

    private void notifyWriterRunnableMultiSignal(WriterRunnableMultiSignal writerRunnableMultiSignal) {
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnableMultiSignal.getWriterRunnables()) {
            this.notifyWriterRunnable(writerRunnableOneSignal);
        }
    }

    public class JSignalAdapterRunnable implements Runnable {

        WriterRunnable writerRunnable;

        JSignalAdapterRunnable(WriterRunnable writerRunnable) {
            this.writerRunnable = writerRunnable;
        }

        public void run() {
            notifyWriterRunnable(writerRunnable);
        }
    }
}
