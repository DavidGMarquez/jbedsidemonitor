package signals;

import java.util.concurrent.Callable;

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
        return this.getReadResult();
    }

    abstract void read();

    /**
     *De algún modo habra que sacar el resultado de aquí
     * y borra este tipo de comentarios despu!s de leerlos
     */
    public ReadResult getReadResult() {
        return readResult;
    }
}
