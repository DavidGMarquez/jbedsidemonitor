/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package integration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import vehicleclass.TaskForAlgorithmToExecute;

/**
 *
 * @author USUARIO
 */
public class ExecutionManager {
        private LinkedBlockingQueue<TaskForAlgorithmToExecute> tasksToExecute;


      private static final ExecutionManager INSTANCE = new ExecutionManager();
        public static ExecutionManager getInstance() {
        return INSTANCE;
    }

            public ExecutionManager() {
                tasksToExecute = new LinkedBlockingQueue<TaskForAlgorithmToExecute>();
    }
//@duda lo ideal sería hacer algun tipo de cola bloqueante
    public void addWriteOperation(TaskForAlgorithmToExecute tasksToExecute) {
        System.out.println(Thread.currentThread().getName()+"Añadida operacion escritura");
        this.tasksToExecute.add(tasksToExecute);
    }

    public TaskForAlgorithmToExecute getAndRemoveTaskToExecute() {
        try {
            return this.tasksToExecute.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecutionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
