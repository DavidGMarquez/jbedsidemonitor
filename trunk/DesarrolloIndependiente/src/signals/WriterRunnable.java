package signals;

public abstract class WriterRunnable implements Runnable {

    protected LockManager lockManager;

    public WriterRunnable() {
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.getLocks();
        this.write();
        this.releaseLocks();
    }

    protected abstract boolean getLocks();

    protected abstract void write();

    protected abstract void releaseLocks();
}
