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

/**
 * Singlenton
 * @author USUARIO
 */
public class AlgorithmManager {

    private Map<String, Algorithm> algorithmsByName;
    private Map<String, String> algorithmsBySignal;
    private Map<String, Trigger> triggersByAlgorithmName;

    private static final AlgorithmManager INSTANCE = new AlgorithmManager();

    public static AlgorithmManager getInstance() {
        return INSTANCE;
    }

    //Mejor asi o inicialar ya todo?
    private AlgorithmManager() {
        this.algorithmsByName = new HashMap<String, Algorithm>();
        this.algorithmsBySignal= new HashMap<String, String>();
        this.triggersByAlgorithmName=new HashMap<String, Trigger>();
    }
    //habría que comprobar los identificadores y hacer copia probablemente

    public Algorithm addAlgorithm(Algorithm algorithm) {
        this.addTrigger(algorithm);
        this.addSignalNamesToMap(algorithm);
        return this.algorithmsByName.put(algorithm.getIdentifier(), algorithm);
    }
    public Trigger addTrigger(Algorithm algorithm){
        return this.triggersByAlgorithmName.put(algorithm.getIdentifier(), new Trigger(algorithm.getNotifyPolice()));
    }
    public void addSignalNamesToMap(Algorithm algorithm)
    {
        AlgorithmNotifyPolice algorithmNotifyPolice=algorithm.getNotifyPolice();
        
        Set<String> timeSeriesNames = algorithmNotifyPolice.getTimeSeriesTheshold().keySet();
           for (String timeSeriesName : timeSeriesNames) {
           this.algorithmsBySignal.put(algorithm.getIdentifier(), timeSeriesName);
        }
           Set<String> eventSeriesNames = algorithmNotifyPolice.getEventSeriesTheshold().keySet();
        for (String eventSeriesName : eventSeriesNames) {
           this.algorithmsBySignal.put(algorithm.getIdentifier(), eventSeriesName);
        }


        
    }

    public Algorithm getAlgorithm(String name) {
        return this.algorithmsByName.get(name);
    }
}
