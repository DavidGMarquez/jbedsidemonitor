/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import vehicleclass.ReadOperation;
import signals.SignalManager;
import vehicleclass.ReadOperationOneSignal;
import vehicleclass.TaskForAlgorithmToExecute;

/**

 * @author USUARIO
 */
public class ThreadReadOperations implements Runnable {

    public SignalManager signalManager;
       private LinkedBlockingQueue<ReadOperation> readOperations;


    public ThreadReadOperations() {
                readOperations = new LinkedBlockingQueue<ReadOperation>();
        this.signalManager = SignalManager.getInstance();
    }
//@duda lo ideal sería hacer algun tipo de cola bloqueante
    public void addReadOperation(ReadOperation readOperation) {
        System.out.println(Thread.currentThread().getName()+"Añadida operacion escritura");
        this.readOperations.add(readOperation);
    }

    public ReadOperation getAndRemoveWriteOperation() {
        try {
            return this.readOperations.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadReadOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void run() {
        ReadOperation readOperation;
        ReadOperationOneSignal operationOneSignal = null;

        while (true) {
            readOperation = this.getAndRemoveWriteOperation();
            if (readOperation != null) {//@todo yo creo que nunca devuelve null
                TaskForAlgorithmToExecute taskforAlgorithmToExecute= new TaskForAlgorithmToExecute(operationOneSignal.getIdentifier());
                while(readOperation.getNumberOfReadOperationSignals()!=0)
                {
                    operationOneSignal=readOperation.getAndRemoveReadOperationOneSignal();
                    taskforAlgorithmToExecute.addSignalData(operationOneSignal.getIdentifier(), signalManager.readFromTimeSeries((operationOneSignal.getIdentifier()), operationOneSignal.getIndexToRead(),operationOneSignal.getQuantityToRead()));
                }
               // signalManager.writeToTimeSeries(0, readOperation.getBufferToWrite());

            } else {
                try {
                    Thread.sleep(10);//idem
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadReadOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
