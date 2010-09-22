/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicleclass;

import algorithms.AlgorithmExecutionContext;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author USUARIO
 */
//@pendiente convertir clase en inmutable
public class TaskForAlgorithmToExecute {

    HashMap<String, float[]> signalsData;
    String algorithmIdentifier;

    public TaskForAlgorithmToExecute(String algorithmIdentifier) {
        signalsData = new HashMap<String, float[]>();
        this.algorithmIdentifier=algorithmIdentifier;
    }

    public boolean addSignalData(String identifier, float[] data) {
        if (this.signalsData.put(identifier, data) != null) {
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, float[]> getSignalsData() {
        return signalsData;
    }



    public String getAlgorithmIdentifier() {
        return algorithmIdentifier;
    }




}
