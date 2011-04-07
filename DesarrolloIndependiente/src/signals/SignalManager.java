package signals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;

/** Singleton Facade
 *
 */
public class SignalManager {

    private ConcurrentMap<String, TimeSeries> timeSeries;
    private ConcurrentMap<String, EventSeries> eventSeries;
    private LockManager lockManager;
    private ExecutorServiceWriter executorServiceWriter;
    private CompletionExecutorServiceReader completionExecutorServiceReader;
    private static final SignalManager INSTANCE = new SignalManager();

    private SignalManager() {
        lockManager = LockManager.getInstance();
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        executorServiceWriter = new ExecutorServiceWriter();
        completionExecutorServiceReader = new CompletionExecutorServiceReader();
    }

    public static SignalManager getInstance() {
        return INSTANCE;
    }
    //@pendiente si es necesario hacer una copia del objeto ya que es mutable
    //Comentario hasta que se haga para que no se olvide copia defensiva

    public Series addSeries(Series series) {
        if (series instanceof TimeSeries) {
            return this.addTimeSeries((TimeSeries) series);
        }
        if (series instanceof EventSeries) {
            return this.addEventSeries((EventSeries) series);
        }
        return null;
    }

    public TimeSeries addTimeSeries(TimeSeries ts) {
        if (this.timeSeries.get(ts.getIdentifier()) == null) {
            this.lockManager.addLock(ts.getIdentifier());
            return this.timeSeries.put(ts.getIdentifier(), ts);
        }
        throw new TimeSerieslAlreadyExistsException("TimeSeries already exists in Signal Manager", ts);
    }

    public EventSeries addEventSeries(EventSeries eventSeries) {
        if (this.eventSeries.get(eventSeries.getIdentifier()) == null) {
            this.lockManager.addLock(eventSeries.getIdentifier());
            return this.eventSeries.put(eventSeries.getIdentifier(), eventSeries);
        }
        throw new EventSerieslAlreadyExistsException("EventSeries already exists in Signal Manager", eventSeries);
    }

    public void encueWriteOperation(WriterRunnable writerRunnable) {
        this.executorServiceWriter.executeWriterRunnable(writerRunnable);
    }

    public void encueReadOperation(ReaderCallable readerCallable) {
        this.completionExecutorServiceReader.executeReaderCallable(readerCallable);

    }

    //@metodo debug no USAR segun api
    public float[] readFromTimeSeries(String identifier, int posSrc, int sizeToRead) {
        return this.timeSeries.get(identifier).read(posSrc, sizeToRead);
    }
//@metodo debug no USAR segun api

    public float[] readNewFromTimeSeries(String identifier, int indexLastRead) {
        if (this.timeSeries.get(identifier).getLastSampleWrite() != -1) {
            float result[] = this.timeSeries.get(identifier).read(indexLastRead,
                    (this.timeSeries.get(identifier).getLastSampleWrite()
                    - indexLastRead) + 1);
            return result;
        } else {
            return new float[0];
        }
    }
    //@metodo debug no USAR segun api

    public  SortedSet<Event> getEventsCopy(String identifier) {
        return this.eventSeries.get(identifier).getEventsCopy();
    }
//@metodo debug no USAR segun api

    public SortedSet<Event> readFromEventSeriesFromTo(String identifierSignal, long firstInstantToInclude, long lastInstantToInclude) {
        return this.eventSeries.get(identifierSignal).getEvents(firstInstantToInclude, lastInstantToInclude);
    }

    ConsecutiveSamplesAvailableInfo writeToTimeSeries(String identifier, float[] dataToWrite, int indexInitToWrite) {
        return this.timeSeries.get(identifier).write(dataToWrite, indexInitToWrite);
    }

    void addEventToEventSeries(String identifier, Event event) {
        this.eventSeries.get(identifier).addEvent(event);
    }

    boolean deleteEventToEventSeries(String identifier, Event event) {
        return this.eventSeries.get(identifier).deleteEvent(event);
    }

    public void reset() {
        lockManager = LockManager.getInstance();
        timeSeries = new ConcurrentHashMap<String, TimeSeries>();
        eventSeries = new ConcurrentHashMap<String, EventSeries>();
        executorServiceWriter = new ExecutorServiceWriter();
        completionExecutorServiceReader = new CompletionExecutorServiceReader();
        this.initiateThread();

    }

/////////A partir de aqui los métodos son discutibles
    // Estan puesto public para los test
    void initiateThread() {
        Thread threadCompletionService = new Thread(completionExecutorServiceReader, "threadComletion");
        threadCompletionService.start();
    }

    public LinkedList<String> getAllTimeSeriesNames() {
        return new LinkedList<String>(this.timeSeries.keySet());
    }

    public LinkedList<String> getAllEventSeriesNames() {
        return new LinkedList<String>(this.eventSeries.keySet());
    }
}
