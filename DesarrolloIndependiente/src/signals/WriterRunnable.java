package signals;

abstract class WriterRunnable implements Runnable {

    protected LockManager lockManager;

    public WriterRunnable() {
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.getLocks();
        this.write();
        this.releaseLocks();
    }

    abstract boolean getLocks();

    abstract void write();

    abstract void releaseLocks();
}
