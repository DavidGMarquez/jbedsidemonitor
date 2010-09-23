/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import vehicleclass.ReadOperationOneSignal;
import vehicleclass.WriteEvent;
import algorithms.Algorithm;
import integration.AlgorithmManager;
import algorithms.AlgorithmReadSignalSubscription;
import integration.ThreadManager;
import vehicleclass.ReadOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.SignalManager;

/**
 *
 * @author USUARIO
 */
public class ThreadScheduler implements Runnable {

    public SignalManager signalManager;
    public AlgorithmManager algorithmManager;
    public ThreadManager generalManager;
    private LinkedBlockingQueue<WriteEvent> writeEvents;
    private Map<String, Map<String, AlgorithmReadSignalSubscription>> signalsDataCountersForAlgorithms = null;

    public ThreadScheduler() {
        writeEvents = new LinkedBlockingQueue<WriteEvent>();
        this.signalManager = SignalManager.getInstance();
        this.algorithmManager = AlgorithmManager.getInstance();
        this.generalManager=ThreadManager.getInstance();
        this.signalsDataCountersForAlgorithms = new HashMap<String, Map<String, AlgorithmReadSignalSubscription>>();
    }

    public void addWriteEvent(WriteEvent writeEvent) {
        System.out.println(Thread.currentThread().getName() + "Añadid evento escritura");
        this.writeEvents.add(writeEvent);
    }

    private WriteEvent getAndRemoveWriteOperation() {
        try {
            return this.writeEvents.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void run() {
        this.initCounters();
        WriteEvent writeEvent;
        while (true) {
            System.out.println("Bucle Thread");
            writeEvent = this.getAndRemoveWriteOperation();
            if (writeEvent != null) {
                System.out.println("DEBUG recibio evento escritura");
                this.processWriteEvent(writeEvent);
            } else {
            }
        }
    }

    private void processWriteEvent(WriteEvent writeEvent) {
        ArrayList<String> signalsWritedIdentifiers = new ArrayList<String>(writeEvent.getSignalsDataWrited().keySet());
        ArrayList<String> algorithmIdentifiers = new ArrayList<String>(this.signalsDataCountersForAlgorithms.keySet());
        for (int i = 0; i < algorithmIdentifiers.size(); i++) {
            ReadOperation readOperation = new ReadOperation();
            Map<String, AlgorithmReadSignalSubscription> algorithmCountersCopy = new HashMap<String, AlgorithmReadSignalSubscription>(signalsDataCountersForAlgorithms.get(algorithmIdentifiers.get(i)));
            Map<String, AlgorithmReadSignalSubscription> algorithmCountersCopyIfReadOperation = new HashMap<String, AlgorithmReadSignalSubscription>(algorithmCountersCopy);
            for (int j = 0; j < signalsWritedIdentifiers.size(); j++) {

                String auxiliarIdentifier = signalsWritedIdentifiers.get(j);
                if (algorithmCountersCopy.containsKey(auxiliarIdentifier) == true) {

                    AlgorithmReadSignalSubscription ifNotReadOperationSuscriptionData = algorithmCountersCopy.get(auxiliarIdentifier);
                    ifNotReadOperationSuscriptionData = new AlgorithmReadSignalSubscription(ifNotReadOperationSuscriptionData.getDataCounter() + writeEvent.getSignalsDataWrited().get(auxiliarIdentifier).intValue(), ifNotReadOperationSuscriptionData.getLastIndexRead());
                    int auxiliarSuscriptionQuantity = algorithmManager.getAlgorithm(algorithmIdentifiers.get(i)).getReadSubscription().getSignalSubscription(auxiliarIdentifier).intValue();
                    AlgorithmReadSignalSubscription ifReadOperationSuscriptionData = new AlgorithmReadSignalSubscription(ifNotReadOperationSuscriptionData);
                    if (ifNotReadOperationSuscriptionData.getDataCounter() >= auxiliarSuscriptionQuantity) {
                        readOperation.addReadOperationOneSignal(new ReadOperationOneSignal(auxiliarIdentifier, ifNotReadOperationSuscriptionData.getLastIndexRead(), auxiliarSuscriptionQuantity));
                        ifReadOperationSuscriptionData = new AlgorithmReadSignalSubscription(ifNotReadOperationSuscriptionData.getDataCounter() - auxiliarSuscriptionQuantity, ifNotReadOperationSuscriptionData.getLastIndexRead() + auxiliarSuscriptionQuantity);
                    }
                    algorithmCountersCopy.put(auxiliarIdentifier, ifNotReadOperationSuscriptionData);
                    algorithmCountersCopyIfReadOperation.put(auxiliarIdentifier, ifReadOperationSuscriptionData);

                }
            }
            //Si tenemos operacion la pasamos

            if (!algorithmManager.getAlgorithm(algorithmIdentifiers.get(i)).getReadSubscription().isUpdateWithoutAllSignals()) {
                if (readOperation.getNumberOfReadOperationSignals() == algorithmManager.getAlgorithm(algorithmIdentifiers.get(i)).getReadSubscription().getAllSignalsSubscription().size()) {
                    addReadOperation(readOperation);
                    signalsDataCountersForAlgorithms.put(algorithmIdentifiers.get(i), algorithmCountersCopyIfReadOperation);
                } else {
                    signalsDataCountersForAlgorithms.put(algorithmIdentifiers.get(i), algorithmCountersCopy);
                }
            } else {
                if (readOperation.getNumberOfReadOperationSignals() > 0) {
                    addReadOperation(readOperation);
                    signalsDataCountersForAlgorithms.put(algorithmIdentifiers.get(i), algorithmCountersCopyIfReadOperation);
                }
            }



        }


    }

    private void addReadOperation(ReadOperation readOperation) {

    generalManager.addReadOperation(readOperation);
    }

    private void initCounters() {
        ArrayList<Algorithm> algorithms = this.algorithmManager.getAllAlgorithms();
        for (int i = 0; i < algorithms.size(); i++) {
            ArrayList<String> signalsIdentifiers = algorithms.get(i).getReadSubscription().getAllSignalsSubscription();
            HashMap<String, AlgorithmReadSignalSubscription> signalsCounters = new HashMap<String, AlgorithmReadSignalSubscription>();
            for (int j = 0; j < signalsIdentifiers.size(); i++) {
                signalsCounters.put(signalsIdentifiers.get(i), new AlgorithmReadSignalSubscription(0, 0));
            }
            this.signalsDataCountersForAlgorithms.put(algorithms.get(i).getIdentifier(), signalsCounters);
        }
    }
}
