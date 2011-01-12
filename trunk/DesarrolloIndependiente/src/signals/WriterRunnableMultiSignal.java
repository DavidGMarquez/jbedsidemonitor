package signals;

import java.util.LinkedList;

class WriterRunnableMultiSignal implements Runnable {
    //@pendiente habría que convertir WriterRunnable en una única interfaz para todo

    //@pendiente posibilidad de convertir las dos listas en una sola
    private LinkedList<EventSeriesWriterRunnable> writeEventSeriesrunnables;
    private LinkedList<TimeSeriesWriterRunnable> writeTimeSeriesrunnables;
    protected LockManager lockManager;

    public WriterRunnableMultiSignal() {

        writeEventSeriesrunnables = new LinkedList<EventSeriesWriterRunnable>();
        writeTimeSeriesrunnables = new LinkedList<TimeSeriesWriterRunnable>();
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.getLocks();
        //@pendiente aqui habría que ver la estrategia para si no se pueden obtener los locks
        this.write();
        this.releaseLocks();
    }

    public boolean getLocks() {
        LinkedList<String> locksTemporal = new LinkedList<String>();
        for (EventSeriesWriterRunnable eventSeriesWriterRunnable : writeEventSeriesrunnables) {
            if (this.lockManager.tryWriteLock(eventSeriesWriterRunnable.getIdentifier()) == true) {
                locksTemporal.add(eventSeriesWriterRunnable.getIdentifier());
            }
        }
        for (TimeSeriesWriterRunnable timeSeriesWriterRunnable : writeTimeSeriesrunnables) {
            if (this.lockManager.tryWriteLock(timeSeriesWriterRunnable.getIdentifier()) == true) {
                locksTemporal.add(timeSeriesWriterRunnable.getIdentifier());
            }
        }
        if (locksTemporal.size() == (writeEventSeriesrunnables.size() + writeTimeSeriesrunnables.size())) {
            return true;
        } else {
            for (String identifierSignal : locksTemporal) {
                this.lockManager.releaseWriteLock(identifierSignal);
            }
            return false;
        }


    }

    public void releaseLocks() {
        for (EventSeriesWriterRunnable eventSeriesWriterRunnable : writeEventSeriesrunnables) {
            this.lockManager.releaseReadLock(eventSeriesWriterRunnable.getIdentifier());
        }
        for (TimeSeriesWriterRunnable timeSeriesWriterRunnable : writeTimeSeriesrunnables) {
            this.lockManager.releaseReadLock(timeSeriesWriterRunnable.getIdentifier());
        }
    }

    public void write() {
        for (EventSeriesWriterRunnable eventSeriesWriterRunnable : writeEventSeriesrunnables) {
            eventSeriesWriterRunnable.write();

        }
        for (TimeSeriesWriterRunnable timeSeriesWriterRunnable : writeTimeSeriesrunnables) {
            timeSeriesWriterRunnable.write();
        }
    }
}
