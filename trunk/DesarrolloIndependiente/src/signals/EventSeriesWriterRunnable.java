package signals;

import java.util.LinkedList;

/**
 *@todo Esta clase debe ser más general; además de permitir añadir eventos
 * debería permitir borrarlos. Por otro lado, como comentario menor, tienes
 * una tendencia bastante grande emplear ArrayList. Por detrás, esta estructura
 * tiene un array, lo que quiere decir que los cambios de tamaño pueden ser
 * bastante costosos. Si no necesitas acceso aleatorio, polo general y siempre
 * preferible un LinkedList
 * //@duda con borrarlos te refieres a que la operación de escritura en el eventSeries pueda borrar eventos
 * o que se puedan borrar de el almacen este temporal de eventsToWrite
 */
public class EventSeriesWriterRunnable extends WriterRunnable {

    public EventSeriesWriterRunnable(String identifier) {
        super(identifier);
        eventsToWrite = new LinkedList<Event>();
        eventsToDelete = new LinkedList<Event>();
    }
    //Para modificar eventos se pone el antigo en la lista de borrar y el nuevo en la de añadir
    private LinkedList<Event> eventsToDelete;
    private LinkedList<Event> eventsToWrite;

    @Override
    void write() {
        SignalManager signalManager = SignalManager.getInstance();
        for (int i = 0; i < eventsToDelete.size(); i++) {
            signalManager.deleteEventToEventSeries(this.identifier, eventsToDelete.get(i));
        }
        for (int i = 0; i < eventsToWrite.size(); i++) {
            signalManager.addEventToEventSeries(this.identifier, eventsToWrite.get(i));
        }
    }

    public void addEventToWrite(Event e) {
        this.eventsToWrite.add(e);
    }
    public void addEventToDelete(Event e){
        this.eventsToDelete.add(e);
    }
}
