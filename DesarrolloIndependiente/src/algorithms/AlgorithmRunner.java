/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import signals.ReadResult;

/**
 *
 * @author USUARIO
 */
class AlgorithmRunner implements Runnable{
    Algorithm algorithm;
    ReadResult readResult;

    public AlgorithmRunner(Algorithm algorithm, ReadResult readResult) {
        this.algorithm=algorithm;
        this.readResult=readResult;
    }

    public void run() {
        //@pendiente aun no esta especificado el Algorithm
        algorithm.execute(readResult);
    }


}
