package signals;

abstract class WriterRunnableOneSignal extends WriterRunnable {

    protected String identifier;

    public WriterRunnableOneSignal(String identifier) {
        super();
        this.identifier = identifier;
    }
    protected boolean getLocks() {
        this.lockManager.getWriteLock(identifier);
        return true;
    }

   protected void releaseLocks() {
        this.lockManager.releaseWriteLock(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }
}
