/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;

/** Singleton Facade
 *
 * @author USUARIO
 */
public class SignalManager {

    private ArrayList<TimeSeries> timeSeries;
    private ArrayList<EventSeries> eventSeries;
    private ArrayList<WriteOperation> writeOperations;

    private SignalManager() {
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
        this.timeSeries.get(index).getReadLock();
        float result[]=this.timeSeries.get(index).read(posSrc, sizeToRead);
        this.timeSeries.get(index).releaseReadLock();
        return result;
    }

    public float[] readNewFromTimeSeries(int index, int indexLastRead) {
        this.timeSeries.get(index).getReadLock();
        if (this.timeSeries.get(index).getIndexNewsample() != -1) {
            float result[]=this.timeSeries.get(index).read(indexLastRead, (this.timeSeries.get(index).getIndexNewsample() - indexLastRead) + 1 % this.timeSeries.get(index).getCapacity());
            this.timeSeries.get(index).releaseReadLock();
            return result;
        } else {
            this.timeSeries.get(index).releaseReadLock();
            return new float[0];
        }
    }

    public boolean writeToTimeSeries(int index, float[] dataToWrite) {
                this.timeSeries.get(index).getWriteLock();
        boolean result=this.timeSeries.get(index).write(dataToWrite);
        this.timeSeries.get(index).releaseWriteLock();
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
}
