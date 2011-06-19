package algorithms;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import signals.ReadResult;
import signals.ReaderCallable;
import signals.SignalManager;
import signals.WriterRunnable;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableMultiSignal;
import signals.WriterRunnableOneSignal;
import signals.WriterRunnableTimeSeries;
import sun.security.x509.AlgorithmId;

/**
 * Singlenton
 */
public class AlgorithmManager {

    private Map<String, Algorithm> algorithmsByName;
    private Map<String, LinkedList<String>> algorithmsToNotifyBySignalName;
    private Map<String, Trigger> triggersByAlgorithmName;
    private ExecutorServiceAlgorithm executorServiceAlgorithm;
    private ConcurrentHashMap<String, Boolean> activeAlgorithms;
    private static final AlgorithmManager INSTANCE = new AlgorithmManager();
    private ConcurrentMap<String, AlgorithmExecutionInfo> executionInfoByAlgorithmName;

    public static AlgorithmManager getInstance() {
        return INSTANCE;
    }

    private AlgorithmManager() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsToNotifyBySignalName = new HashMap<String, LinkedList<String>>();
        this.triggersByAlgorithmName = new HashMap<String, Trigger>();
        this.executorServiceAlgorithm = new ExecutorServiceAlgorithm();
        this.activeAlgorithms = new ConcurrentHashMap<String, Boolean>();
        this.executionInfoByAlgorithmName=new ConcurrentHashMap<String, AlgorithmExecutionInfo>();

    }

    public Algorithm addAlgorithm(Algorithm algorithm) {
        if (this.algorithmsByName.get(algorithm.getIdentifier()) == null) {
            SignalManager.getInstance().addSeries(algorithm.getSignalToWrite());
            this.addTrigger(algorithm);
            this.addSignalNamesToMap(algorithm);
            this.addExecutionInfo(algorithm);
            this.activeAlgorithms.put(algorithm.getIdentifier(), Boolean.TRUE);
            return this.algorithmsByName.put(algorithm.getIdentifier(), algorithm);
        } else {
            throw new AlgorithmAlreadyExistsException("Algorithm already exists in Algorithm Manager", algorithm);
        }
    }

    private Trigger addTrigger(Algorithm algorithm) {
        return this.triggersByAlgorithmName.put(algorithm.getIdentifier(),
                new Trigger(algorithm.getIdentifier(), algorithm.getNotifyPolice()));
    }

    private void addSignalNamesToMap(Algorithm algorithm) {
        AlgorithmNotifyPolice algorithmNotifyPolice = algorithm.getNotifyPolice();
        Set<String> timeSeriesNames = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
        for (String timeSeriesName : timeSeriesNames) {
            LinkedList<String> algorithmNames = this.algorithmsToNotifyBySignalName.get(timeSeriesName);
            if (algorithmNames == null) {
                algorithmNames = new LinkedList<String>();
            }
            algorithmNames.add(algorithm.getIdentifier());
            this.algorithmsToNotifyBySignalName.put(timeSeriesName, algorithmNames);
        }
        Set<String> eventSeriesNames = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String eventSeriesName : eventSeriesNames) {
            LinkedList<String> algorithmNames = this.algorithmsToNotifyBySignalName.get(eventSeriesName);
            if (algorithmNames == null) {
                algorithmNames = new LinkedList<String>();
            }
            algorithmNames.add(algorithm.getIdentifier());
            this.algorithmsToNotifyBySignalName.put(eventSeriesName, algorithmNames);
        }
    }
    public void addExecutionInfo(Algorithm algorithm){
        AlgorithmExecutionInfo algorithmExecutionInfo=new AlgorithmExecutionInfo(algorithm.getIdentifier());
        executionInfoByAlgorithmName.put(algorithm.getIdentifier(), algorithmExecutionInfo);
    }

    public void notifyNewData(WriterRunnable writerRunnable) {
        if (writerRunnable instanceof WriterRunnableOneSignal) {
            WriterRunnableOneSignal writerRunnableOneSignal = (WriterRunnableOneSignal) writerRunnable;
            String signalName = writerRunnableOneSignal.getIdentifier();

            LinkedList<String> algorithmNames = this.algorithmsToNotifyBySignalName.get(signalName);
            if (algorithmNames != null) {
                for (String algorithmName : algorithmNames) {
                    if (activeAlgorithms.get(algorithmName).booleanValue()) {
                        this.executionInfoByAlgorithmName.get(algorithmName).incrementTriggerUpdates();
                        Trigger algorithmTrigger = this.triggersByAlgorithmName.get(algorithmName);
                        algorithmTrigger.notifyNewData(writerRunnableOneSignal);
                    }
                }
                this.checkTriggers();
            }
            //@pediente no se porque es necesario la copia pero bueno
            //@pendiente esto pasarlo a sus clases correspondiente haciendo un constructor de copia
            WriterRunnable writerRunnableCopy = null;
            if (writerRunnable instanceof WriterRunnableEventSeries) {
                WriterRunnableEventSeries writerRunnableEventSeries = (WriterRunnableEventSeries) writerRunnable;
                writerRunnableCopy = new WriterRunnableEventSeries(writerRunnableEventSeries);
            }
            if (writerRunnable instanceof WriterRunnableTimeSeries) {
                WriterRunnableTimeSeries writerRunnableTimeS = (WriterRunnableTimeSeries) writerRunnable;
                writerRunnableCopy = new WriterRunnableTimeSeries(writerRunnableTimeS);
            }
            //Esto es para que no vuelva  a notificar a este método
            writerRunnableCopy.notNotifyAlgorithmManager();
            SignalManager.getInstance().getJSignalAdapter().executeWriterRunnable(writerRunnableCopy);
        } else {
            WriterRunnableMultiSignal writerRunnableMultiSignal = (WriterRunnableMultiSignal) writerRunnable;
            LinkedList<WriterRunnableOneSignal> writerRunnables = writerRunnableMultiSignal.getWriterRunnables();
            for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
                this.notifyNewData(writerRunnableOneSignal);
            }
        }
    }

    private void checkTriggers() {
        Set<String> algorithmNames = this.triggersByAlgorithmName.keySet();
        for (String algorithmName : algorithmNames) {
            Trigger triggerAlgorithm = this.triggersByAlgorithmName.get(algorithmName);
            ReaderCallable readerCallable = triggerAlgorithm.getReaderCallableIfTriggerAndReset();
            if (readerCallable != null) {
                signals.SignalManager.getInstance().encueReadOperation(readerCallable);
            }
        }
    }

    public void processData(ReadResult readResult) {
        Algorithm algorithm = this.algorithmsByName.get(readResult.getIdentifierOwner());
        this.executionInfoByAlgorithmName.get(algorithm.getIdentifier()).incrementExecutionUpdates();
        this.encueAlgorithmReadResultOperation(algorithm, readResult);
    }

    private void encueAlgorithmReadResultOperation(Algorithm algorithm, ReadResult readResult) {
        this.executorServiceAlgorithm.executeAlgorithmReadResult(algorithm, readResult);
    }

    public boolean desactiveAlgorithm(String algorithmName) {
        return this.activeAlgorithms.put(algorithmName, Boolean.FALSE).booleanValue();
    }

    public boolean activeAlgorithm(String algorithmName) {
        return this.activeAlgorithms.put(algorithmName, Boolean.TRUE).booleanValue();
    }
    public boolean isActiveAlgorithm(String algorithmName){
        return this.activeAlgorithms.get(algorithmName);
    }
    //@comentario metodo de depuracion

    public Algorithm getAlgorithm(String name) {
        return this.algorithmsByName.get(name);
    }
    public AlgorithmExecutionInfo getAlgorithmExecutionInfo(String name){
        return executionInfoByAlgorithmName.get(name);
    }
    //@comentario metodo de depuracion

    public LinkedList<String> getAllAlgorithmNames() {
        return (new LinkedList<String>(this.algorithmsByName.keySet()));
    }
    //@comentario metodo de depuracion

    public Trigger getTrigger(String algorithmName) {
        return this.triggersByAlgorithmName.get(algorithmName);
    }
    //@comentario metodo de depuracion

    public LinkedList<String> getAlgorithmNamesToSignal(String signalName) {
        return this.algorithmsToNotifyBySignalName.get(signalName);
    }
    //@comentario metodo de depuracion

    public void reset() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsToNotifyBySignalName = new HashMap<String, LinkedList<String>>();
        this.triggersByAlgorithmName = new HashMap<String, Trigger>();
        this.executorServiceAlgorithm = new ExecutorServiceAlgorithm();
        this.executionInfoByAlgorithmName=new ConcurrentHashMap<String, AlgorithmExecutionInfo>();
    }
}
