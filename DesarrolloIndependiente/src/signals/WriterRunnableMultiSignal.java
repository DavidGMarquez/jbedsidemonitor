package signals;

import java.util.LinkedList;

class WriterRunnableMultiSignal extends WriterRunnable {
    //@pendiente habría que convertir WriterRunnable en una única interfaz para todo

    //@pendiente posibilidad de convertir las dos listas en una sola
    private LinkedList<WriterRunnableOneSignal> writerRunnables;
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

    public boolean getLocks() {
        LinkedList<String> locksTemporal = new LinkedList<String>();
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
            if (this.lockManager.tryWriteLock(writerRunnableOneSignal.getIdentifier()) == true) {
                locksTemporal.add(writerRunnableOneSignal.getIdentifier());
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

    public void releaseLocks() {
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
            this.lockManager.releaseReadLock(writerRunnableOneSignal.getIdentifier());
        }
    }

    public void write() {
        for (WriterRunnableOneSignal writerRunnableOneSignal : writerRunnables) {
            writerRunnableOneSignal.write();
        }
    }
}
