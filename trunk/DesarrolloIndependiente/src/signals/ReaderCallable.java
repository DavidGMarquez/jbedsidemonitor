/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.concurrent.Callable;
import signals.ReadResult;

/**
 *
 * @author USUARIO
 */
abstract class ReaderCallable implements Callable<ReadResult> {

    protected String identifierSignal;
    protected String identifierOwner;
    protected LockManager lockManager;
    protected ReadResult readResult;

    public ReaderCallable(String identifierSignal,String identifierOwner) {
        this.identifierSignal=identifierSignal;
        this.identifierOwner=identifierOwner;
        this.lockManager = LockManager.getInstance();
    }

    public ReadResult call() {
        this.lockManager.getReadLock(identifierSignal);
        this.read();
        this.lockManager.releaseReadLock(identifierSignal);
        return this.readResult;
    }

    abstract void read();
}
