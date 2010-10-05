package signals;

import java.util.ArrayList;

/**
 *@todo Esta clase debe ser más general; además de permitir añadir eventos
 * debería permitir borrarlos. Por otro lado, como comentario menor, tienes
 * una tendencia bastante grande emplear ArrayList. Por detrás, esta estructura
 * tiene un array, lo que quiere decir que los cambios de tamaño pueden ser
 * bastante costosos. Si no necesitas acceso aleatorio, polo general y siempre
 * preferible un LinkedList
 */
public class EventSeriesWriterRunnable extends WriterRunnable {

    public EventSeriesWriterRunnable(String identifier) {
        super(identifier);
        eventsToWrite=new ArrayList<Event>();
    }
    private ArrayList<Event> eventsToWrite;

    @Override
    void write() {
        SignalManager signalManager = SignalManager.getInstance();
        for (int i = 0; i < eventsToWrite.size(); i++) {
            signalManager.addEventToEventSeries(this.identifier,eventsToWrite.get(i));
        }
    }

    public void addEventToWrite(Event e){
        this.eventsToWrite.add(e);
    }
}