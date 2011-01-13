package signals;

abstract class ReaderCallableOneSignal extends ReaderCallable {

    protected String identifierSignal;

    public ReaderCallableOneSignal(String identifierSignal, String identifierOwner) {
        super(identifierOwner);
        this.identifierSignal = identifierSignal;
    }

    protected boolean getLocks() {
        this.lockManager.getReadLock(identifierSignal);
        return true;

    }

    protected void releaseLocks() {
        this.lockManager.releaseReadLock(identifierSignal);

    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }
}
