/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zclasesParaBORRAR;

import algorithms.AlgorithmExecutionContext;
import algorithms.AlgorithmManager;
import zclasesParaBORRAR.ExecutionManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class threadExecution implements Runnable{

    public void run() {
         TaskForAlgorithmToExecute taskforAlgorithmToExecute;
         AlgorithmExecutionContext algorithmExecutionContext;
         ExecutionManager executionManager=ExecutionManager.getInstance();
         AlgorithmManager algorithmManager=AlgorithmManager.getInstance();
        while (true) {
            System.out.println("Bucle Thread");
            taskforAlgorithmToExecute = executionManager.getAndRemoveTaskToExecute();
            if (taskforAlgorithmToExecute != null) {
                algorithmExecutionContext=new AlgorithmExecutionContext(taskforAlgorithmToExecute.getSignalsData());
                algorithmManager.getAlgorithm(taskforAlgorithmToExecute.getAlgorithmIdentifier()).execute(algorithmExecutionContext);

            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadWriteOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


}
