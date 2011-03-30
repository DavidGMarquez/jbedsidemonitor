package signals;

import java.util.LinkedList;

public class WriterRunnableEventSeries extends WriterRunnableOneSignal {

    public WriterRunnableEventSeries(String identifier) {
        super(identifier);
        eventsToWrite = new LinkedList<Event>();
        eventsToDelete = new LinkedList<Event>();
    }

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

    public LinkedList<Event> getEventsToDelete() {
        return eventsToDelete;
    }

    public LinkedList<Event> getEventsToWrite() {
        return eventsToWrite;
    }
    
}
