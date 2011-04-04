package signals;

import algorithms.AlgorithmManager;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriterRunnableMultiSignal extends WriterRunnable {

    protected LinkedList<WriterRunnableOneSignal> writerRunnables;
    protected LockManager lockManager;

    public WriterRunnableMultiSignal() {

        writerRunnables = new LinkedList<WriterRunnableOneSignal>();
        this.lockManager = LockManager.getInstance();
    }

    @Override
    public void run() {
       while(!this.getLocks()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(WriterRunnableMultiSignal.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        this.write();
        this.releaseLocks();
                        //pendiente @b he cambiado esto que sino no funciona
        AlgorithmManager.getInstance().notifyNewData(this);
    }

    protected boolean getLocks() {
        LinkedList<String> locksTemporal = new LinkedList<String>();
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
            if (this.lockManager.tryWriteLock(writerRunnableOneSignal.getIdentifier()) == true) {
                locksTemporal.add(writerRunnableOneSignal.getIdentifier());
            } else {
                break;
            }
        }
        if (locksTemporal.size() == writerRunnables.size()) {
            return true;
        } else {
            for (String identifierSignal : locksTemporal) {
                this.lockManager.releaseWriteLock(identifierSignal);
            }
            return false;
        }
    }

    protected void write() {
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
            writerRunnableOneSignal.write();
        }
    }

    protected void releaseLocks() {
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
            this.lockManager.releaseWriteLock(writerRunnableOneSignal.getIdentifier());
        }
    }

    public void addWriterRunnableOneSignal(WriterRunnableOneSignal writerRunnableOneSignal) {
        this.writerRunnables.add(writerRunnableOneSignal);
    }

    public LinkedList<WriterRunnableOneSignal> getWriterRunnables() {
        return new LinkedList<WriterRunnableOneSignal>(writerRunnables);
    }

}
