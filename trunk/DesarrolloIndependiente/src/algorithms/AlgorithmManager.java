package algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import signals.ReadResult;
import signals.ReaderCallable;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;

/**
 * Singlenton
 */
public class AlgorithmManager {

    private Map<String, Algorithm> algorithmsByName;
    private Map<String, LinkedList<String>> algorithmsNameBySignalName;
    private Map<String, Trigger> triggersByAlgorithmName;
    private ExecutorServiceAlgorithm executorServiceAlgorithm;
    private static final AlgorithmManager INSTANCE = new AlgorithmManager();

    public static AlgorithmManager getInstance() {
        return INSTANCE;
    }

    private AlgorithmManager() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsNameBySignalName = new HashMap<String, LinkedList<String>>();
        this.triggersByAlgorithmName = new HashMap<String, Trigger>();
        this.executorServiceAlgorithm = new ExecutorServiceAlgorithm();
    }

    //@pendiente comprobar que los identificadores no se repitan y lanzar
    //excepción si es necesario.

    public Algorithm addAlgorithm(Algorithm algorithm) {
        this.addTrigger(algorithm);
        this.addSignalNamesToMap(algorithm);
        return this.algorithmsByName.put(algorithm.getIdentifier(), algorithm);
    }

    private Trigger addTrigger(Algorithm algorithm) {
        return this.triggersByAlgorithmName.put(algorithm.getIdentifier(), new Trigger(algorithm.getIdentifier(),algorithm.getNotifyPolice()));
    }

    private void addSignalNamesToMap(Algorithm algorithm) {
        AlgorithmNotifyPolice algorithmNotifyPolice = algorithm.getNotifyPolice();
        Set<String> timeSeriesNames = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
        for (String timeSeriesName : timeSeriesNames) {
            LinkedList<String> algorithmNames = this.algorithmsNameBySignalName.get(timeSeriesName);
            if (algorithmNames == null) {
                algorithmNames = new LinkedList<String>();
            }
            algorithmNames.add(algorithm.getIdentifier());
            this.algorithmsNameBySignalName.put(timeSeriesName, algorithmNames);
        }
        Set<String> eventSeriesNames = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String eventSeriesName : eventSeriesNames) {
            LinkedList<String> algorithmNames = this.algorithmsNameBySignalName.get(eventSeriesName);
            if (algorithmNames == null) {
                algorithmNames = new LinkedList<String>();
            }
            algorithmNames.add(algorithm.getIdentifier());
            this.algorithmsNameBySignalName.put(eventSeriesName, algorithmNames);
        }
    }
//@comentario cada vez que se termina una lectura SignalManager debería notificar (llamar a este metodo)
    public void notifyNewData(WriterRunnableEventSeries writerRunnableEventSeries) {
        String signalName = writerRunnableEventSeries.getIdentifier();
        LinkedList<String> algorithmNames = this.algorithmsNameBySignalName.get(signalName);
        for (String algorithmName : algorithmNames) {
            Trigger algorithmTrigger = this.triggersByAlgorithmName.get(algorithmName);
            algorithmTrigger.notifyNewData(writerRunnableEventSeries);
            this.checkTriggers();
        }
    }
//@comentario ¿para que dos métodos? ¿no nos podemos arreglar con uno que reciba un ReadResult?
    public void notifyNewData(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        String signalName = writerRunnableTimeSeries.getIdentifier();
        LinkedList<String> algorithmNames = this.algorithmsNameBySignalName.get(signalName);
        for (String algorithmName : algorithmNames) {
            Trigger algorithmTrigger = this.triggersByAlgorithmName.get(algorithmName);
            algorithmTrigger.notifyNewData(writerRunnableTimeSeries);
            this.checkTriggers();
        }
    }

    private void checkTriggers() {
        Set<String> algorithmNames = this.triggersByAlgorithmName.keySet();
        for (String algorithmName : algorithmNames) {
            Trigger triggerAlgorithm = this.triggersByAlgorithmName.get(algorithmName);
            if (triggerAlgorithm.trigger()) {
                ReaderCallable readerCallable = triggerAlgorithm.getReaderCallableAndReset();
                 signals.SignalManager.getInstance().encueReadOperation(readerCallable);
            }
        }
    }

    public void processData(ReadResult readResult) {
        Algorithm algorithm = this.algorithmsByName.get(readResult.getIdentifierOwner());
//@comentario al ejecutar los test del paquete signals se generan NullPointerExceptions
//porque algorithm siempre vale null aqui
//@pendiente revisar esto
        this.encueAlgorithmReadResultOperation(algorithm, readResult);
    }

    private void encueAlgorithmReadResultOperation(Algorithm algorithm, ReadResult readResult) {
        this.executorServiceAlgorithm.executeAlgorithmReadResult(algorithm, readResult);
    }

    public Algorithm getAlgorithm(String name) {
        return this.algorithmsByName.get(name);
    }
}