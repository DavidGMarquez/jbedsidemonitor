package signals;

import algorithms.AlgorithmManager;

public abstract class WriterRunnable implements Runnable {

    protected LockManager lockManager;

    public WriterRunnable() {
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.getLocks();
        this.write();
        this.releaseLocks();
                //pendiente @b he cambiado esto que sino no funciona
        AlgorithmManager.getInstance().notifyNewData(this);
    }

    protected abstract boolean getLocks();

    protected abstract void write();

    protected abstract void releaseLocks();
}
