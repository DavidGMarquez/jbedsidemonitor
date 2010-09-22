/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import vehicleclass.WriteOperation;
import java.util.ArrayList;
import signals.EventSeries;
import signals.TimeSeries;

/** Singleton Facade
 *
 * @author USUARIO
 */
public class SignalManager {

    private ArrayList<TimeSeries> timeSeries;
    private ArrayList<EventSeries> eventSeries;
    private ArrayList<WriteOperation> writeOperations;
    private LockManager lockManager;

    private SignalManager() {
        lockManager=LockManager.getInstance();
        timeSeries = new ArrayList<TimeSeries>();
        eventSeries = new ArrayList<EventSeries>();
        writeOperations = new ArrayList<WriteOperation>();
    }
    private static final SignalManager INSTANCE = new SignalManager();

    public static SignalManager getInstance() {
        return INSTANCE;
    }
    //@duda quizas haya que hacer una copia de TS

    public boolean addTimeSeries(TimeSeries TS) {
        return this.timeSeries.add(TS);
    }
    //@duda quizas haya que hacer una copia de TS

    public boolean addAllTimeSeries(ArrayList<TimeSeries> TS) {
        return this.timeSeries.addAll(TS);
    }

    public float[] readFromTimeSeries(int index, int posSrc, int sizeToRead) {
        this.lockManager.getReadLock(index);
        float result[]=this.timeSeries.get(index).read(posSrc, sizeToRead);
        this.lockManager.releaseReadLock(index);
        return result;
    }

    public float[] readNewFromTimeSeries(int index, int indexLastRead) {
        this.lockManager.getReadLock(index);
        if (this.timeSeries.get(index).getIndexNewsample() != -1) {
            float result[]=this.timeSeries.get(index).read(indexLastRead, (this.timeSeries.get(index).getIndexNewsample() - indexLastRead) + 1 % this.timeSeries.get(index).getCapacity());
            this.lockManager.releaseReadLock(index);
            return result;
        } else {
            this.lockManager.releaseReadLock(index);
            return new float[0];
        }
    }

    public boolean writeToTimeSeries(int index, float[] dataToWrite) {
                this.lockManager.getWriteLock(index);
        boolean result=this.timeSeries.get(index).write(dataToWrite);
        this.lockManager.releaseWriteLock(index);
        return result;
    }

    public void addWriteOperation(WriteOperation writeOperation) {
        this.writeOperations.add(writeOperation);
    }

    public WriteOperation getAndRemoveWriteOperation() {
        if (this.writeOperations.isEmpty()) {
            return null;
        } else {
            return this.writeOperations.remove(0);
        }

    }

    public int getSignalIndex(String identifier) {
    for(int i=0;i<this.timeSeries.size();i++)
    {
        if( this.timeSeries.get(i).getIdentifier().equals(identifier))
        {
            return i;
        }
    }
    return -1;
    }
}
