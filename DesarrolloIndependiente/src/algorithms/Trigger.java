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

    public synchronized void notifyNewData(ResultEventSeriesWriter resultEventSeriesWriter) {
        this.eventSeriesTriggers.get(resultEventSeriesWriter.getIdentifier()).update(resultEventSeriesWriter);
    }

    public synchronized void notifyNewData(ResultTimeSeriesWriter resultTimeSeriesWriter) {
        this.timeSeriesTriggers.get(resultTimeSeriesWriter.getIdentifier()).update(resultTimeSeriesWriter);
    }
//@comentario a mi modo de verlo, para no emplear sintonización aqui tenemos que garantizar que cada uno de los
    //trigger individuales es thread safe. Cosa que ahora no se hace. Quizás podriamos emplear cada objeto tipo
    //Trigger como lock  para todos los objetos TimeSeriesTrigget y EventSeriesTrigger que contiene,
    //y así nos ahorramos el trabajo de hacerlos thread safe. En cualquier caso, tal y como esta este método
    //puede haber problemas de concurrencia con este metodo y cualquier otro metodo de esta clase
    //(los objetos TimeSeriesTrigget y EventSeriesTrigger están confinados en esta clase) que modifique
    //alguna instancia de TimeSeriesTrigget o de EventSeriesTrigger
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
    //@comentario: respecto a tu pregunta ¿estás reseteando algo más que los triggers? Yo no veo que
    //resetees nada más
    //@comentario he añadido la palabra synchronized. Tal y como ten%as el codigo, siempre que se llama a este método
    //se ha cogido un lock sobre this, y como se trata de un lock reentrante, este synchronized
    //no está cambiando nada. Pero prefiero que esté ahi porque si el dÝa de mañana se te da por llamar a
    //este método desde otro sitio y no a quienes el lock adecuado tienes un problema de concurrencia
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

    //Syncrhonized
    public synchronized ReaderCallable getReaderCallableAndReset() {
        ReaderCallable readerCallable = this.getReaderCallable();
        this.reset();
        return readerCallable;
    }
//@comentario mismo comentario que para el metodo reset
    private synchronized ReaderCallable getReaderCallable() {


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
