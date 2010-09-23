/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import signals.LockManager;
import vehicleclass.WriteOperation;
import java.util.ArrayList;
import signals.EventSeries;
import signals.TimeSeries;

/** Singleton Facade
 *
 * @todo  En la API esta clase debería permitir identificar las señales por
 *  Su nombre y no por el índice. Es mas facil de usar Y no se expone
 *  un detalle de implementación. Por otro lado, la clase se construye más fácil
 * usando hasmaps en vez de ArrayList. Así no hay que iterar para encontrar la señal.
 *
 * @author USUARIO
 */
public class SignalManager {

    private ArrayList<TimeSeries> timeSeries;//@todo hashmap
    private ArrayList<EventSeries> eventSeries;//@todo hashmap
    private ArrayList<WriteOperation> writeOperations;//@todo hashmap?
    private LockManager lockManager;
    private static final SignalManager INSTANCE = new SignalManager();

    private SignalManager() {
        lockManager = LockManager.getInstance();
        timeSeries = new ArrayList<TimeSeries>();
        eventSeries = new ArrayList<EventSeries>();
        writeOperations = new ArrayList<WriteOperation>();
    }
    

    public static SignalManager getInstance() {
        return INSTANCE;
    }
    //@duda quizas haya que hacer una copia de TS

    //@todo no anhadir, crear
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
//¿cuál es la diferencia entre este método y el siguiente? Me parece que
//solo ste debería ser publico y deberia hacer su trabajo a través del siguiente.
    public boolean writeToTimeSeries(int index, float[] dataToWrite) {
        this.lockManager.getWriteLock(index);
        boolean result=this.timeSeries.get(index).write(dataToWrite);
        this.lockManager.releaseWriteLock(index);
        return result;
    }

    public void addWriteOperation(WriteOperation writeOperation) {
        this.writeOperations.add(writeOperation);
    }

    //¿quién va a usar esto y para que?
    public WriteOperation getAndRemoveWriteOperation() {
        if (this.writeOperations.isEmpty()) {
            return null;
        } else {
            return this.writeOperations.remove(0);
        }

    }

    public int getSignalIndex(String identifier) {
    for(int i=0;i<this.timeSeries.size();i++){
        if( this.timeSeries.get(i).getIdentifier().equals(identifier)){
            return i;
        }
    }
    return -1;
    }
}
