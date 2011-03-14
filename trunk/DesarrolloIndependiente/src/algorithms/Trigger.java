package algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import signals.ReaderCallable;
import signals.ReaderCallableEventSeries;
import signals.ReaderCallableMultiSignal;
import signals.ReaderCallableTimeSeries;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableOneSignal;
import signals.WriterRunnableTimeSeries;

public class Trigger {

    private String identifierAlgorithm;
    private Map<String, TimeSeriesTrigger> timeSeriesTriggers;
    private Map<String, EventSeriesTrigger> eventSeriesTriggers;
    private AlgorithmNotifyPoliceEnum notifyPolice;

    Trigger(String identifierAlgorithm, AlgorithmNotifyPolice algorithmNotifyPolice) {
        this.identifierAlgorithm = identifierAlgorithm;
        this.timeSeriesTriggers = new HashMap<String, TimeSeriesTrigger>();
        this.eventSeriesTriggers = new HashMap<String, EventSeriesTrigger>();
        this.notifyPolice = algorithmNotifyPolice.getNotifyPolice();

        Set<String> keySetTimeSeries = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
        for (String keyTimeSeries : keySetTimeSeries) {
            this.timeSeriesTriggers.put(keyTimeSeries,
                    new TimeSeriesTrigger(keyTimeSeries, algorithmNotifyPolice.getTimeSeriesTheshold().get(keyTimeSeries)));
        }
        Set<String> keySetEventSeries = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String keyEventSeries : keySetEventSeries) {
            this.eventSeriesTriggers.put(keyEventSeries,
                    new EventSeriesTrigger(keyEventSeries, algorithmNotifyPolice.getEventSeriesTheshold().get(keyEventSeries)));
        }

    }

    public synchronized void notifyNewData(WriterRunnableOneSignal writerRunnableOneSignal) {
        if (writerRunnableOneSignal instanceof WriterRunnableTimeSeries) {
            this.timeSeriesTriggers.get(writerRunnableOneSignal.getIdentifier()).update((WriterRunnableTimeSeries) writerRunnableOneSignal);
        } else {
            if (writerRunnableOneSignal instanceof WriterRunnableEventSeries) {
                this.eventSeriesTriggers.get(writerRunnableOneSignal.getIdentifier()).update((WriterRunnableEventSeries) writerRunnableOneSignal);
            } else {
                //@pendiente lanzar excepcion
            }
        }
    }

    public synchronized boolean trigger() {
        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            if (timeSeriesTrigger.trigger() && notifyPolice.equals(notifyPolice.ONE)) {
                return true;
            } else if (!timeSeriesTrigger.trigger() && notifyPolice.equals(notifyPolice.ALL)) {
                return false;
            }
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            if (eventSeriesTrigger.trigger() && notifyPolice.equals(notifyPolice.ONE)) {
                return true;
            } else if (!eventSeriesTrigger.trigger() && notifyPolice.equals(notifyPolice.ALL)) {
                return false;
            }
        }
        if (notifyPolice.equals(notifyPolice.ONE)) {
            return false;
        } else if (notifyPolice.equals(notifyPolice.ALL)) {
            return true;
        }
        return false;
    }

    //Tiene sentido que resetee todo? o solo debería resetear los triggers?
    //@comentario: respecto a tu pregunta ¿estás reseteando algo más que los triggers? Yo no veo que
    //resetees nada más
    //@respuesta me exprese mal. Quiero decir reseteo solo los triggers que estan con trigger() o todos?
    private synchronized void reset() {
        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            timeSeriesTrigger.reset();
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            eventSeriesTrigger.reset();
        }
    }

    public synchronized ReaderCallableMultiSignal getReaderCallableAndReset() {
        //@duda debería comprobar si el trigger esta activado antes de devolver el callable?
        //Es decir ahora mismo aunque la politica sea ALL si le pedimos el readerCallable nos los da solo de
        //las señales que estan listas
        ReaderCallableMultiSignal readerCallable = this.getReaderCallable();
        //Creo que el reset ya lo cubro al ir creando this.reset();
        return readerCallable;
    }

    private synchronized ReaderCallableMultiSignal getReaderCallable() {
        //@pendiente que devuelva no solo MultiSignal sino también las OneSignal si es el caso
        ReaderCallableMultiSignal readerCallable = new ReaderCallableMultiSignal(this.getIdentifierAlgorithm());
        //Si tenemos la modalidad de que actualice solo cuando este una sola señal lista,
        //el número de señales será variable

        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            if (timeSeriesTrigger.trigger()) {
                ReaderCallableTimeSeries readerCallableTimeSeries =
                        new ReaderCallableTimeSeries(timeSeriesTrigger.getIdentifierSignal(),
                        this.getIdentifierAlgorithm());
                readerCallableTimeSeries.setPosInitToRead(timeSeriesTrigger.getLastSampleReported());
                readerCallableTimeSeries.setSizeToRead(timeSeriesTrigger.getNewData());
                timeSeriesTrigger.reset();
                readerCallable.addReaderCallableOneSignal(readerCallableTimeSeries);
            }
        }
        Collection<EventSeriesTrigger> valuesEventSeriesTrigger = eventSeriesTriggers.values();
        for (EventSeriesTrigger eventSeriesTrigger : valuesEventSeriesTrigger) {
            if (eventSeriesTrigger.trigger()) {
                ReaderCallableEventSeries readerCallableEventSeries =
                        new ReaderCallableEventSeries(eventSeriesTrigger.getIdentifierSignal(),
                        this.getIdentifierAlgorithm());
                readerCallableEventSeries.setFirstInstantToInclude(eventSeriesTrigger.getLastEventReported());
                readerCallableEventSeries.setLastInstantToInclude(eventSeriesTrigger.getNewEventCount());
                eventSeriesTrigger.reset();
                readerCallable.addReaderCallableOneSignal(readerCallableEventSeries);
            }
        }
        return readerCallable;
    }

    public String getIdentifierAlgorithm() {
        return identifierAlgorithm;
    }

    //@debug metodo de depuracion solamente
    public synchronized Map<String, EventSeriesTrigger> getEventSeriesTriggers() {
        return new HashMap<String, EventSeriesTrigger>(eventSeriesTriggers);
    }
    //@debug metodo de depuracion solamente

    public synchronized Map<String, TimeSeriesTrigger> getTimeSeriesTriggers() {
        return new HashMap<String, TimeSeriesTrigger>(timeSeriesTriggers);
    }
}
