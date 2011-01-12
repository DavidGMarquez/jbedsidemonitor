package signals;

abstract class WriterRunnable implements Runnable {

    protected String identifier;
    protected LockManager lockManager;

    public WriterRunnable(String identifier) {
        this.identifier = identifier;
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.lockManager.getWriteLock(identifier);
        this.write();
        this.lockManager.releaseWriteLock(identifier);
    }

    abstract void write();

    public String getIdentifier() {
        return identifier;
    }
}
