package signals;

import algorithms.AlgorithmManager;

public abstract class WriterRunnable implements Runnable {

    protected LockManager lockManager;
    protected boolean notifyAlgorithmManager=true;

    public WriterRunnable() {
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.getLocks();
        this.write();
        this.releaseLocks();
        //pendiente @b he cambiado esto que sino no funciona
        //@pendiente revisar esto no se si va a dar problema con los writer de JSignal
        if(notifyAlgorithmManager)AlgorithmManager.getInstance().notifyNewData(this);
    }
    public void notNotifyAlgorithmManager(){
        this.notifyAlgorithmManager=false;
    }
    protected abstract boolean getLocks();

    protected abstract void write();

    protected abstract void releaseLocks();
}
