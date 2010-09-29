/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, TimeSeries> timeSeries;//@todo hashmap
    private Map<String, EventSeries> eventSeries;//@todo hashmap
    private LockManager lockManager;
    private ExecutorServiceWriter executorServiceWriter;
    private static final SignalManager INSTANCE = new SignalManager();

    private SignalManager() {
        lockManager = LockManager.getInstance();
        timeSeries = new HashMap<String, TimeSeries>();
        eventSeries = new HashMap<String, EventSeries>();
        executorServiceWriter = new ExecutorServiceWriter();
    }

    public static SignalManager getInstance() {
        return INSTANCE;
    }
    //@duda quizas haya que hacer una copia de TS

    //@todo no anhadir, crear
    //@pendiente crear constructor de copia y hacerlo
    public TimeSeries addTimeSeries(TimeSeries TS) {
        this.lockManager.addLock(TS.getIdentifier());
        return this.timeSeries.put(TS.getIdentifier(), TS);
    }
        public EventSeries addEventSeries(EventSeries eventSeries) {
        this.lockManager.addLock(eventSeries.getIdentifier());
        return this.eventSeries.put(eventSeries.getIdentifier(), eventSeries);
    }

            public void addWriterRunnable(WriterRunnable writerRunnable) {
        this.executorServiceWriter.executeWriterRunnable(writerRunnable);
    }

/////////A partir de aqui los métodos son discutibles

    public float[] readFromTimeSeries(String identifier, int posSrc, int sizeToRead) {
        return this.timeSeries.get(identifier).read(posSrc, sizeToRead);
    }

    public float[] readNewFromTimeSeries(String identifier, int indexLastRead) {
        if (this.timeSeries.get(identifier).getIndexNewsample() != -1) {
            float result[] = this.timeSeries.get(identifier).read(indexLastRead, (this.timeSeries.get(identifier).getIndexNewsample() - indexLastRead) + 1 % this.timeSeries.get(identifier).getCapacity());
            return result;
        } else {
            return new float[0];
        }
    }

    public boolean writeToTimeSeries(String identifier, float[] dataToWrite) {
        boolean result = this.timeSeries.get(identifier).write(dataToWrite);
        return result;
    }
    public void writeEvent(String identifier, Event event) {
        this.eventSeries.get(identifier).addEvent(event);
    }
    public ArrayList<Event> getEvents(String identifier){
        return this.eventSeries.get(identifier).getEventsCopy();
    }
}
