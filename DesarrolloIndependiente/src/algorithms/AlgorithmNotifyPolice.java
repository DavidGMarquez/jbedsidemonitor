package algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * Must be Immutable
 */
public class AlgorithmNotifyPolice {

    protected Map<String, Integer> timeSeriesTheshold;
    protected Map<String, Integer> eventSeriesTheshold;
    private AlgorithmNotifyPoliceEnum notifyPoliceType;

    public AlgorithmNotifyPolice(Map<String, Integer> timeSeriesTheshold,
            Map<String, Integer> eventSeriesTheshold, AlgorithmNotifyPoliceEnum notifyPolice) {
        this.timeSeriesTheshold = new HashMap<String, Integer>(timeSeriesTheshold);
        this.eventSeriesTheshold = new HashMap<String, Integer>( eventSeriesTheshold);
        this.notifyPoliceType = notifyPolice;
    }
    
    public Map<String, Integer> getEventSeriesTheshold() {
        return eventSeriesTheshold;
    }

    public AlgorithmNotifyPoliceEnum getNotifyPolice() {
        return notifyPoliceType;
    }

    public Map<String, Integer> getTimeSeriesTheshold() {
        return timeSeriesTheshold;
    }
}
