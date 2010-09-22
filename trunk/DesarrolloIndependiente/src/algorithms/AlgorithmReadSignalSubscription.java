/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

/**
 *
 * @author USUARIO
 * @pendiente hacer esta clase inmutable
 */
public class AlgorithmReadSignalSubscription {
    private int dataCounter;
    private int lastIndexRead;

    public AlgorithmReadSignalSubscription(int dataCounter, int lastIndexRead) {
        this.dataCounter = dataCounter;
        this.lastIndexRead = lastIndexRead;
    }

    public AlgorithmReadSignalSubscription(AlgorithmReadSignalSubscription algorithmReadSignalSubscription) {
        this.dataCounter=algorithmReadSignalSubscription.dataCounter;
        this.lastIndexRead=algorithmReadSignalSubscription.lastIndexRead;
    }

    public int getDataCounter() {
        return dataCounter;
    }

    public int getLastIndexRead() {
        return lastIndexRead;
    }


}
