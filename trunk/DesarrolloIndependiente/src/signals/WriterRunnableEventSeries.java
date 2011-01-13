package signals;

import java.util.LinkedList;

public class WriterRunnableEventSeries extends WriterRunnableOneSignal {

    public WriterRunnableEventSeries(String identifier) {
        super(identifier);
        eventsToWrite = new LinkedList<Event>();
        eventsToDelete = new LinkedList<Event>();
    }
    //Para modificar eventos se pone el antigo en la lista de borrar y el nuevo en la de añadir
    private LinkedList<Event> eventsToDelete;
    private LinkedList<Event> eventsToWrite;

    @Override
    protected void write() {
        SignalManager signalManager = SignalManager.getInstance();
        for (Event event : eventsToDelete) {
            signalManager.deleteEventToEventSeries(this.identifier, event);
        }
        for (Event event : eventsToWrite) {
            signalManager.addEventToEventSeries(this.identifier, event);
        }
    }

    public void addEventToWrite(Event e) {
        this.eventsToWrite.add(e);
    }

    public void addEventToDelete(Event e) {
        this.eventsToDelete.add(e);
    }
}
