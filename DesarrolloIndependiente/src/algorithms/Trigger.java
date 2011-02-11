package algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import signals.ReaderCallable;
import signals.ReaderCallableMultiSignal;
import signals.ReaderCallableTimeSeries;

public class Trigger {

    private String identifierAlgorithm;
    private Map<String, TimeSeriesTrigger> timeSeriesTriggers;
    private Map<String, EventSeriesTrigger> eventSeriesTriggers;
    //1 All 2 Only one
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
    //Syncronized

    public synchronized void notifyNewData(ResultEventSeriesWriter resultEventSeriesWriter) {
        this.eventSeriesTriggers.get(resultEventSeriesWriter.getIdentifier()).update(resultEventSeriesWriter);
    }
    //Syncronized

    public synchronized void notifyNewData(ResultTimeSeriesWriter resultTimeSeriesWriter) {
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

    //Tiene sentido que resetee todo? o solo debería resetear los triggers?
    private void reset() {
        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            timeSeriesTrigger.reset();
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            eventSeriesTrigger.reset();
        }
    }

    //Syncrhonized
    public synchronized ReaderCallable getReaderCallableAndReset() {
        ReaderCallable readerCallable = this.getReaderCallable();
        this.reset();
        return readerCallable;
    }

    private ReaderCallable getReaderCallable() {


        ///PENDIENTE DE TERMINAR

        //HACIENDO ESTO ME HE DADO CUENTA DE QUE LA ESTRUCTURA
        //DE LOS READER CALLABLES ES DEMASIADO COMPLEJA Y ANTINATURAL

        ReaderCallableMultiSignal readerCallable = new ReaderCallableMultiSignal(this.getIdentifierAlgorithm());

        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            if (timeSeriesTrigger.trigger()) {
                ReaderCallableTimeSeries readerCallableTimeSeries = 
                 new ReaderCallableTimeSeries(timeSeriesTrigger.getIdentifierSignal(), 
                        this.getIdentifierAlgorithm());

                timeSeriesTrigger.reset();
            }
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            if (eventSeriesTrigger.trigger()) {

                eventSeriesTrigger.reset();
            }
        }
        return readerCallable;
    }

    public String getIdentifierAlgorithm() {
        return identifierAlgorithm;
    }
}
