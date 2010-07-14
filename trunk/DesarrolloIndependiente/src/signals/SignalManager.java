/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

/** Singleton Facade
 *
 * @author USUARIO
 */
public class SignalManager {
    private SignalManager(){}
     private static final SignalManager INSTANCE= new SignalManager();

    public static SignalManager getInstance(){
        return INSTANCE;
    }

}
