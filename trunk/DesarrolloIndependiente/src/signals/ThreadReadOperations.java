/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        while (true) {
            System.out.println("Bucle Thread");
            readOperation = this.getAndRemoveWriteOperation();
            if (readOperation != null) {
                System.out.println("DEBUG ejecutada operacion de copia");
               // signalManager.writeToTimeSeries(0, readOperation.getBufferToWrite());

            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadReadOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
