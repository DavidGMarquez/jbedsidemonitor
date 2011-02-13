package algorithms;

import java.util.Map;

/**
 * Must be Immutable
 */
public class AlgorithmNotifyPolice {

    protected Map<String, Long> timeSeriesTheshold;
    protected Map<String, Long> eventSeriesTheshold;
    //1 All 2 Solo una
    //@comentario usa una enumeracion; las inmediaciones son type safe, un entero en este contexto no
    int notifyPolice;

    public AlgorithmNotifyPolice(Map<String, Long> timeSeriesTheshold, Map<String, Long> eventSeriesTheshold, int notifyPolice) {
        this.timeSeriesTheshold = timeSeriesTheshold;
        this.eventSeriesTheshold = eventSeriesTheshold;
        this.notifyPolice = notifyPolice;
    }
    
    public Map<String, Long> getEventSeriesTheshold() {
        return eventSeriesTheshold;
    }

    public int getNotifyPolice() {
        return notifyPolice;
    }

    public Map<String, Long> getTimeSeriesTheshold() {
        return timeSeriesTheshold;
    }
}
