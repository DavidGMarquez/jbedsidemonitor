package signals;

import java.util.concurrent.Callable;

public abstract  class ReaderCallable implements Callable<ReadResult> {

    protected String identifierOwner;
    protected LockManager lockManager;
    protected ReadResult readResult;

    public ReaderCallable(String identifierOwner) {
        this.identifierOwner = identifierOwner;
        this.lockManager = LockManager.getInstance();
        readResult=null;
    }

    @Override
    public ReadResult call() {
        this.getLocks(); 
        this.read();
        this.releaseLocks();
        return this.getReadResult();
    }

    protected abstract ReadResult read();
    protected abstract boolean getLocks();
    protected abstract void releaseLocks();


    public ReadResult getReadResult() {
        return readResult;
    }

    public String getIdentifierOwner() {
        return identifierOwner;
    }

}
