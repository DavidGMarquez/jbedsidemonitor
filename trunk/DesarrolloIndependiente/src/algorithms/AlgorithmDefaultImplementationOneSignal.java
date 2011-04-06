package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import signals.Series;

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
            eventSeriesHold.put(eventSerieName, new Integer(10));
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
}
