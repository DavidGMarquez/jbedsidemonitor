/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vehicleclass;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 * @todo En principio, yo simplificaria; un evento de escritura para cada escritura en cada señal
 */
public class WriteEvent {
     private Map<String,Integer> signalsDataWrited=null;
    public WriteEvent(Map<String,Integer> signalsDataWrited) {
        this.signalsDataWrited=new HashMap<String,Integer>(signalsDataWrited);
    }

    public Map<String, Integer> getSignalsDataWrited() {
        return signalsDataWrited;
    }

}
