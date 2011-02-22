package algorithms;

import java.util.Map;

/**
 * Must be Immutable
 */
public class AlgorithmNotifyPolice {

    protected Map<String, Integer> timeSeriesTheshold;
    protected Map<String, Integer> eventSeriesTheshold;
    private AlgorithmNotifyPoliceEnum notifyPolice;

    public AlgorithmNotifyPolice(Map<String, Integer> timeSeriesTheshold, Map<String, Integer> eventSeriesTheshold, AlgorithmNotifyPoliceEnum notifyPolice) {
        this.timeSeriesTheshold = timeSeriesTheshold;
        this.eventSeriesTheshold = eventSeriesTheshold;
        this.notifyPolice = notifyPolice;
    }
    
    public Map<String, Integer> getEventSeriesTheshold() {
        return eventSeriesTheshold;
    }

    public AlgorithmNotifyPoliceEnum getNotifyPolice() {
        return notifyPolice;
    }

    public Map<String, Integer> getTimeSeriesTheshold() {
        return timeSeriesTheshold;
    }
}
