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
import signals.WriterRunnableTimeSeries;

public class Trigger {

    private String identifierAlgorithm;
    private Map<String, TimeSeriesTrigger> timeSeriesTriggers;
    private Map<String, EventSeriesTrigger> eventSeriesTriggers;
    private AlgorithmNotifyPoliceEnum notifyPolice;

    Trigger(AlgorithmNotifyPolice algorithmNotifyPolice) {
        this.timeSeriesTriggers = new HashMap<String, TimeSeriesTrigger>();
        this.eventSeriesTriggers = new HashMap<String, EventSeriesTrigger>();
        this.notifyPolice = algorithmNotifyPolice.getNotifyPolice();

        Set<String> keySetTimeSeries = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
        for (String keyTimeSeries : keySetTimeSeries) {
            this.timeSeriesTriggers.put(keyTimeSeries,
                    new TimeSeriesTrigger(keyTimeSeries,algorithmNotifyPolice.getTimeSeriesTheshold().get(keyTimeSeries)));
        }
        Set<String> keySetEventSeries = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String keyEventSeries : keySetEventSeries) {
            this.eventSeriesTriggers.put(keyEventSeries,
                    new EventSeriesTrigger(keyEventSeries,algorithmNotifyPolice.getEventSeriesTheshold().get(keyEventSeries)));
        }

    }

    public synchronized void notifyNewData(WriterRunnableEventSeries writerRunnableEventSeries) {
        this.eventSeriesTriggers.get(writerRunnableEventSeries.getIdentifier()).update(writerRunnableEventSeries);
    }

    public synchronized void notifyNewData(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        this.timeSeriesTriggers.get(writerRunnableTimeSeries.getIdentifier()).update(writerRunnableTimeSeries);
    }
//@comentario a mi modo de verlo, para no emplear sintonización aqui tenemos que garantizar que cada uno de los
    //trigger individuales es thread safe. Cosa que ahora no se hace. Quizás podriamos emplear cada objeto tipo
    //Trigger como lock  para todos los objetos TimeSeriesTrigget y EventSeriesTrigger que contiene,
    //y así nos ahorramos el trabajo de hacerlos thread safe. En cualquier caso, tal y como esta este método
    //puede haber problemas de concurrencia con este metodo y cualquier otro metodo de esta clase
    //(los objetos TimeSeriesTrigget y EventSeriesTrigger están confinados en esta clase) que modifique
    //alguna instancia de TimeSeriesTrigget o de EventSeriesTrigger
    //@pendiente mirar el modelo de sincronización para esto
    //@revisar que todos los metodos tienen que tener sincronizacion
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
        return true;
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

    public synchronized ReaderCallable getReaderCallableAndReset() {
        ReaderCallable readerCallable = this.getReaderCallable();
        //Creo que el reset ya lo cubro al ir creando this.reset();
        return readerCallable;
    }

    private synchronized ReaderCallable getReaderCallable() {

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
                readerCallableTimeSeries.setSizeToRead(timeSeriesTrigger.getNewDataInMs());
                timeSeriesTrigger.reset();
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
            }
        }
        return readerCallable;
    }

    public String getIdentifierAlgorithm() {
        return identifierAlgorithm;
    }
}
