/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 * @pendiente esta clase también debería ser inmutable
 */
public class AlgorithmExecutionContext {
    private Map<String,float[]> signals=null;

    public AlgorithmExecutionContext(Map<String,float[]> signalsSubscription,boolean onlyIfAllSignals) {
            this.signals=new HashMap<String,float[]>(signals);
    }
    public float[] getDataOfSignal(String identifier){
        return this.signals.get(identifier);
    }
    public ArrayList<String> getAllSignals(){
      return   new ArrayList<String>(this.signals.keySet());
    }

}
