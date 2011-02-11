/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import algorithms.Algorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import signals.ReadResult;
import signals.ReaderCallable;

/**
 * Singlenton
 * @author USUARIO
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

    //Mejor asi o inicialar ya todo?
    private AlgorithmManager() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsNameBySignalName = new HashMap<String, LinkedList<String>>();
        this.triggersByAlgorithmName = new HashMap<String, Trigger>();
        this.executorServiceAlgorithm= new ExecutorServiceAlgorithm();
    }
    //habría que comprobar los identificadores y hacer copia probablemente

    public Algorithm addAlgorithm(Algorithm algorithm) {
        this.addTrigger(algorithm);
        this.addSignalNamesToMap(algorithm);
        return this.algorithmsByName.put(algorithm.getIdentifier(), algorithm);
    }

    private Trigger addTrigger(Algorithm algorithm) {
        return this.triggersByAlgorithmName.put(algorithm.getIdentifier(), new Trigger(algorithm.getNotifyPolice()));
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

    public void notifyNewData(ResultEventSeriesWriter resultEventSeriesWriter) {
        String signalName = resultEventSeriesWriter.getIdentifier();
        LinkedList<String> algorithmNames = this.algorithmsNameBySignalName.get(signalName);
        for (String algorithmName : algorithmNames) {
            Trigger algorithmTrigger = this.triggersByAlgorithmName.get(algorithmName);
            algorithmTrigger.notifyNewData(resultEventSeriesWriter);
            this.checkTriggers();
        }
    }

    public void notifyNewData(ResultTimeSeriesWriter resultTimeSeriesWriter) {
        String signalName = resultTimeSeriesWriter.getIdentifier();
        LinkedList<String> algorithmNames = this.algorithmsNameBySignalName.get(signalName);
        for (String algorithmName : algorithmNames) {
            Trigger algorithmTrigger = this.triggersByAlgorithmName.get(algorithmName);
            algorithmTrigger.notifyNewData(resultTimeSeriesWriter);
            this.checkTriggers();
        }
    }

    private void checkTriggers() {
        Set<String> algorithmNames = this.triggersByAlgorithmName.keySet();
        for (String algorithmName : algorithmNames) {
            Trigger triggerAlgorithm = this.triggersByAlgorithmName.get(algorithmName);
            if (triggerAlgorithm.trigger()) {
                //Crear la operación de lectura y reiniciar el trigger

                //Este siguiente método debería ser bloqueante y reiniciar el Trigger
                ReaderCallable readerCallable=triggerAlgorithm.getReaderCallableAndReset();
                //No se si mover el CompletionReader aqui o dejarlo en el signalManager
                signals.SignalManager.getInstance().encueReadOperation(readerCallable);
            }
        }
    }
    public void processData(ReadResult readResult) {
        Algorithm algorithm=this.algorithmsByName.get(readResult.getIdentifierOwner());


        //@pendiente aqui habría que llamar al executorService para ejecutarlo.
        this.encueAlgorithmReadResultOperation(algorithm, readResult);

        //Necesitamos otro nuevo ya que esta vez solo es de de ejecución por lo tanto podría tenerlo esta misma clase

        
    }
    private void encueAlgorithmReadResultOperation(Algorithm algorithm,ReadResult readResult) {
        this.executorServiceAlgorithm.executeAlgorithmReadResult(algorithm, readResult);
    }

    public Algorithm getAlgorithm(String name) {
        return this.algorithmsByName.get(name);
    }
}
