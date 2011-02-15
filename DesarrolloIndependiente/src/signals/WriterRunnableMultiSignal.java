package signals;

import java.util.LinkedList;

public class WriterRunnableMultiSignal extends WriterRunnable {

    protected LinkedList<WriterRunnableOneSignal> writerRunnables;
    protected LockManager lockManager;

    public WriterRunnableMultiSignal() {

        writerRunnables = new LinkedList<WriterRunnableOneSignal>();
        this.lockManager = LockManager.getInstance();
    }

    @Override
    public void run() {
        this.getLocks();
        //@pendiente aqui habría que ver la estrategia para si no se pueden obtener los locks
        this.write();
        this.releaseLocks();
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
            this.lockManager.releaseReadLock(writerRunnableOneSignal.getIdentifier());
        }
    }

    public void addWriterRunnableOneSignal(WriterRunnableOneSignal writerRunnableOneSignal) {
        this.writerRunnables.add(writerRunnableOneSignal);
    }
}
