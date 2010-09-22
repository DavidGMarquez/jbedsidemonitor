/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import algorithms.Algorithm;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class AlgorithmManager {

    private ArrayList<Algorithm> algorithms;

    //Mejor asi o inicialar ya todo?
    private AlgorithmManager() {
        this.algorithms = new ArrayList<Algorithm>();
    }
    //habría que comprobar los identificadores y hacer copia probablemente

    public boolean addAlgorithm(Algorithm algorithm) {
        return this.algorithms.add(algorithm);
    }

    public Algorithm getAlgorithm(int index) {
        return this.algorithms.get(index);
    }

    public Algorithm getAlgorithm(String identifier) {
        for (int i = 0; i < this.algorithms.size(); i++) {
            if (this.algorithms.get(i).getIdentifier().equals(identifier)) {
                return this.algorithms.get(i);
            }
        }
        return null;
    }
    public ArrayList<Algorithm> getAllAlgorithms()
    {
        return this.algorithms;
    }
    private static final AlgorithmManager INSTANCE = new AlgorithmManager();

    public static AlgorithmManager getInstance() {
        return INSTANCE;
    }
}
