package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import signals.ReadResult;

public class AlgorithmDefaultImplementation implements Algorithm {

    private String identifier;
    private String identifierSignalToWrite;
    private AlgorithmNotifyPolice algorithmNotifyPolice;

    public AlgorithmDefaultImplementation(String identifier, String identifierSignalToWrite, AlgorithmNotifyPolice algorithmNotifyPolice) {
        this.identifier = identifier;
        this.identifierSignalToWrite = identifierSignalToWrite;
        this.algorithmNotifyPolice = algorithmNotifyPolice;
    }
    public AlgorithmDefaultImplementation(String identifier, String identifierSignalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries) {
        this.identifier = identifier;
        this.identifierSignalToWrite = identifierSignalToWrite;
        HashMap<String, Integer> eventSeriesHold = new HashMap<String, Integer>();
        for (String eventSerieName : eventSeries) {
            eventSeriesHold.put(eventSerieName, new Integer(10));
        }
        HashMap<String, Integer> timeSeriesHold = new HashMap<String, Integer>();
        for (String timeSerieName : timeSeries) {
            timeSeriesHold.put(timeSerieName, new Integer(100));
        }
        this.algorithmNotifyPolice = new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ALL);
    }

    public AlgorithmNotifyPolice getNotifyPolice() {
        return this.algorithmNotifyPolice;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getIdentifierSignalToWrite() {
        return this.identifierSignalToWrite;
    }

    public boolean execute(ReadResult readResult) {
        throw new UnsupportedOperationException("Not supported yet.");
        //Implementar aqui el código del algoritmo
    }
}
