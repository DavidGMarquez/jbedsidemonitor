/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package integration;

import algorithms.AlgorithmManager;
import datasource.DriverReaderJSignal;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.ReadOperation;
import signals.SignalManager;
import signals.ThreadReadOperations;
import signals.ThreadWriteOperations;
import signals.WriteOperation;

/**
 *
 * @author USUARIO
 */
public class GeneralManager {
    DriverReaderJSignal driverReaderJSignal=null;
    ThreadWriteOperations ThreadWriteOperations=null;
        ThreadReadOperations ThreadReadOperations=null;


  private static final GeneralManager INSTANCE = new GeneralManager();
    public GeneralManager() {
        SignalManager signalManager=SignalManager.getInstance();
        AlgorithmManager algorithmManager=AlgorithmManager.getInstance();
     
    }

    public static GeneralManager getInstance() {
        return INSTANCE;
    }
    public void start(){


                        driverReaderJSignal=new DriverReaderJSignal();
         Thread threadDriverReaderJSignal=new Thread(driverReaderJSignal,"threadDriverReaderJSignal");
        threadDriverReaderJSignal.start();

                 ThreadWriteOperations=new ThreadWriteOperations();
         Thread threadThreadWriteOperations=new Thread(ThreadWriteOperations,"threadThreadWriteOperations");
        threadThreadWriteOperations.start();

                 ThreadReadOperations=new ThreadReadOperations();
         Thread threadThreadReadOperations=new Thread(ThreadReadOperations,"threadThreadReadOperations");
        threadThreadReadOperations.start();
    }
    public void stop(){

    }

    public void addWriteOperation(WriteOperation writeOperation) {
        System.out.println(Thread.currentThread().getName()+"Añadiento operacion Generla manager");
        this.ThreadWriteOperations.addWriteOperation(writeOperation);
    }
        public void addReadOperation(ReadOperation readOperation) {
        System.out.println(Thread.currentThread().getName()+"Añadiento operacion Generla manager");
        this.ThreadReadOperations.addReadOperation(readOperation);
    }


}
