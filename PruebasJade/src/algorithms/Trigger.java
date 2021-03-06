package algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import signals.ReadResultEventSeries;
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
            this.timeSeriesTriggers.put(keyTimeSeries, new TimeSeriesTrigger(keyTimeSeries,
                    algorithmNotifyPolice.getTimeSeriesTheshold().get(keyTimeSeries)));
        }
        Set<String> keySetEventSeries = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String keyEventSeries : keySetEventSeries) {
            this.eventSeriesTriggers.put(keyEventSeries, new EventSeriesTrigger(keyEventSeries,
                    algorithmNotifyPolice.getEventSeriesTheshold().get(keyEventSeries)));
        }
    }

    public synchronized void notifyNewData(WriterRunnableOneSignal writerRunnableOneSignal) {
        if (writerRunnableOneSignal instanceof WriterRunnableTimeSeries) {
            TimeSeriesTrigger trigger = this.timeSeriesTriggers.get(writerRunnableOneSignal.getIdentifier());
            trigger.update((WriterRunnableTimeSeries) writerRunnableOneSignal);
        } else {
            if (writerRunnableOneSignal instanceof WriterRunnableEventSeries) {
                EventSeriesTrigger trigger = this.eventSeriesTriggers.get(writerRunnableOneSignal.getIdentifier());
                trigger.update((WriterRunnableEventSeries) writerRunnableOneSignal);
            }
        }
    }

    public synchronized boolean trigger() {
        //@debug BORRAR
        //@debug   System.out.println("Trigger Para"+this.getIdentifierAlgorithm());
        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {

            //@debug   System.out.println("Trigger Para"+timeSeriesTrigger.getIdentifierSignal()+"NewData"+timeSeriesTrigger.getNewData()+"TheShold"+timeSeriesTrigger.getTheshold());
            if (timeSeriesTrigger.trigger() && notifyPolice.equals(notifyPolice.ONE)) {
                //@debug                System.out.println("OK");
                return true;
            } else if (!timeSeriesTrigger.trigger() && notifyPolice.equals(notifyPolice.ALL)) {
                //@debug System.out.println("FALSO");
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
            //@debug  System.out.println("FALSO_FALSO");
            return false;
        } else {
            //@debug  System.out.println("OK_OK");
            return true;
        }
    }

    public synchronized ReaderCallable getReaderCallableIfTriggerAndReset() {
        if (this.trigger()) {
            return this.getReaderCallableAnResetTriggers();
        }
        return null;
    }
    //@pendiente quizás sea necesario eliminarlo, no aporta nada
    public synchronized ReaderCallableMultiSignal getReaderCallableAndReset() {
        ReaderCallableMultiSignal readerCallable = this.getReaderCallableAnResetTriggers();
        return readerCallable;
    }

    private synchronized ReaderCallableMultiSignal getReaderCallableAnResetTriggers() {
        //@comprobar que da igual si envio los multisignal o los one signal
        //@pendiente tal y como esta la cosa nunca le llegan a los algoritmos ReadResult simples, siempre le llegan Multi aunque sean de una sola señal
        ReaderCallableMultiSignal readerCallable = new ReaderCallableMultiSignal(this.getIdentifierAlgorithm());

        Collection<TimeSeriesTrigger> valuesTimeSeriesTrigger = timeSeriesTriggers.values();
        for (TimeSeriesTrigger timeSeriesTrigger : valuesTimeSeriesTrigger) {
            if (timeSeriesTrigger.trigger()) {
                ReaderCallableTimeSeries readerCallableTimeSeries =
                        new ReaderCallableTimeSeries(timeSeriesTrigger.getIdentifierSignal(),
                        this.getIdentifierAlgorithm());
                readerCallableTimeSeries.setPosInitToRead(timeSeriesTrigger.getLastSampleReported() + 1);
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
                        this.getIdentifierAlgorithm(),eventSeriesTrigger.getEventsAlreadyWrittenCopy()
                        ,eventSeriesTrigger.getEventsAlreadyDeletedCopy());
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
