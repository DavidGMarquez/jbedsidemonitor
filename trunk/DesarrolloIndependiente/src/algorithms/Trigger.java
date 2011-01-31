/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author USUARIO
 */
public class Trigger {

    private Map<String, TimeSeriesTrigger> timeSeriesTriggers;
    private Map<String, EventSeriesTrigger> eventSeriesTriggers;
    //1 All 2 Solo una
    private int notifyPolice;

    Trigger(AlgorithmNotifyPolice algorithmNotifyPolice) {
        this.timeSeriesTriggers = new HashMap<String, TimeSeriesTrigger>();
        this.eventSeriesTriggers = new HashMap<String, EventSeriesTrigger>();

        this.notifyPolice = algorithmNotifyPolice.getNotifyPolice();

        Set<String> keySetTimeSeries = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
        for (String keyTimeSeries : keySetTimeSeries) {
            this.timeSeriesTriggers.put(keyTimeSeries,
               new TimeSeriesTrigger(algorithmNotifyPolice.getTimeSeriesTheshold().get(keyTimeSeries)));
        }
        Set<String> keySetEventSeries = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String keyEventSeries : keySetEventSeries) {
            this.eventSeriesTriggers.put(keyEventSeries,
               new EventSeriesTrigger(algorithmNotifyPolice.getEventSeriesTheshold().get(keyEventSeries)));
        }

    }

    public void notifyNewData(ResultEventSeriesWriter resultEventSeriesWriter) {
        this.eventSeriesTriggers.get(resultEventSeriesWriter.getIdentifier()).update(resultEventSeriesWriter);
    }

    public void notifyNewData(ResultTimeSeriesWriter resultTimeSeriesWriter) {
        this.timeSeriesTriggers.get(resultTimeSeriesWriter.getIdentifier()).update(resultTimeSeriesWriter);
    }

    public boolean trigger() {
        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            if (timeSeriesTrigger.trigger() && notifyPolice == 2) {
                return true;
            } else if (!timeSeriesTrigger.trigger() && notifyPolice == 1) {
                return false;
            }
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            if (eventSeriesTrigger.trigger() && notifyPolice == 2) {
                return true;
            } else if (!eventSeriesTrigger.trigger() && notifyPolice == 1) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            timeSeriesTrigger.reset();
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            eventSeriesTrigger.reset();
        }
    }
}
