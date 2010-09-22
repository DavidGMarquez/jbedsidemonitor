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
 *  @pendiente Esta clase debería ser inmutable
 */
public class AlgorithmReadSubscription {
    private Map<String,Integer> signalsSubscription=null;
    private boolean updateWithoutAllSignals=false;

    public AlgorithmReadSubscription(Map<String,Integer> signalsSubscription,boolean onlyIfAllSignals) {
            this.signalsSubscription=new HashMap<String,Integer>(signalsSubscription);
            this.updateWithoutAllSignals=onlyIfAllSignals;
    }
    public Integer getSignalSubscription(String identifier)
    {
        return this.signalsSubscription.get(identifier);
    }
    public ArrayList<String> getAllSignalsSubscription()
    {
       return new ArrayList<String>( this.signalsSubscription.keySet());
    }
    public boolean isUpdateWithoutAllSignals(){
        return this.updateWithoutAllSignals;
    }






//  Hacer metodos de interfaz para acceder y para poder saber lo que quiere, señales, señales que escribe, cada caunto
}
