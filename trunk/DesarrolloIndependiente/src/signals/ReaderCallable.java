package signals;

import java.util.concurrent.Callable;

abstract class ReaderCallable implements Callable<ReadResult> {

    protected String identifierSignal;//lista de identificadores
    protected String identifierOwner;
    protected LockManager lockManager;
    protected ReadResult readResult;

    public ReaderCallable(String identifierSignal, String identifierOwner) {
        this.identifierSignal = identifierSignal;
        this.identifierOwner = identifierOwner;
        this.lockManager = LockManager.getInstance();
        this.readResult = new ReadResult(identifierOwner);
    }

    @Override
    public ReadResult call() {//dar soporte a lectura de mltiples senhales
        this.lockManager.getReadLock(identifierSignal);
        this.read();
        this.lockManager.releaseReadLock(identifierSignal);
        return this.getReadResult();
    }

    abstract void read();

    public ReadResult getReadResult() {
        return readResult;
    }
}
