package algorithms;

import java.util.Map;

/**
 * Must be Immutable
 */
public class AlgorithmNotifyPolice {

    protected Map<String, Long> timeSeriesTheshold;
    protected Map<String, Long> eventSeriesTheshold;
    private AlgorithmNotifyPoliceEnum notifyPolice;

    public AlgorithmNotifyPolice(Map<String, Long> timeSeriesTheshold, Map<String, Long> eventSeriesTheshold, AlgorithmNotifyPoliceEnum notifyPolice) {
        this.timeSeriesTheshold = timeSeriesTheshold;
        this.eventSeriesTheshold = eventSeriesTheshold;
        this.notifyPolice = notifyPolice;
    }
    
    public Map<String, Long> getEventSeriesTheshold() {
        return eventSeriesTheshold;
    }

    public AlgorithmNotifyPoliceEnum getNotifyPolice() {
        return notifyPolice;
    }

    public Map<String, Long> getTimeSeriesTheshold() {
        return timeSeriesTheshold;
    }
}
