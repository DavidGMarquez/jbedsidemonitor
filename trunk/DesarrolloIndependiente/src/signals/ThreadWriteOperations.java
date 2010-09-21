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
 *  Algorimo
 *  Hacer metodos de interfaz para acceder y para poder saber lo que quiere, señales, señales que escribe, cada caunto
 *  LockManager
 * Cambiar la cola de WriteOperation al Thread y hacerla bloqueante.
 *  Hacer que el SignalManager cree estos hilos y que tenga acceso para pasar nuevas WriteOperation
 *  Cambiar la política de locks y crearlos separados de escritura y lectura el de lectura multiple el de escritura solo uno simple
 *  Hay que cambiar lo constructores para que no escape la referencia
 *  Hacer las clases inmutables
 * @author USUARIO
 */
public class ThreadWriteOperations implements Runnable {

    public SignalManager signalManager;
       private LinkedBlockingQueue<WriteOperation> writeOperations;


    public ThreadWriteOperations() {
                writeOperations = new LinkedBlockingQueue<WriteOperation>();
        this.signalManager = SignalManager.getInstance();
    }
//@duda lo ideal sería hacer algun tipo de cola bloqueante
    public void addWriteOperation(WriteOperation writeOperation) {
        System.out.println(Thread.currentThread().getName()+"Añadida operacion escritura");
        this.writeOperations.add(writeOperation);
    }

    public WriteOperation getAndRemoveWriteOperation() {
        try {
            return this.writeOperations.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadWriteOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void run() {
        WriteOperation WO;
        while (true) {
            System.out.println("Bucle Thread");
            WO = this.getAndRemoveWriteOperation();
            if (WO != null) {
                System.out.println("DEBUG ejecutada operacion de copia");
                signalManager.writeToTimeSeries(0, WO.getBufferToWrite());

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
