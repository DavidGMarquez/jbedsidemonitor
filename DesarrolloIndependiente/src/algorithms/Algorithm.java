/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

/**
 *
 * @author USUARIO
 */
public interface Algorithm {
    //Nuevos
    public AlgorithmNotifyPolice getNotifyPolice();



    //Antiguos
    public String getIdentifier();
    public AlgorithmReadSubscription getReadSubscription();
    public String getIdentifierSignalToWrite();
    public boolean execute(AlgorithmExecutionContext ExCon);

     //  Hacer metodos de interfaz para acceder y para poder saber lo que quiere, señales, señales que escribe, cada caunto



}
