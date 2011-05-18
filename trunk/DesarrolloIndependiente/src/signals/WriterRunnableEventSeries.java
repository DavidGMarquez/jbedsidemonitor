package signals;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WriterRunnableEventSeries extends WriterRunnableOneSignal {

    public WriterRunnableEventSeries(String identifier) {
        super(identifier);
        eventsToWrite = new LinkedList<Event>();
        eventsToDelete = new LinkedList<Event>();
    }

    public WriterRunnableEventSeries(WriterRunnableEventSeries writerRunnableEventSeries) {
        super(writerRunnableEventSeries.identifier);
        this.eventsToDelete = new LinkedList<Event>();
        this.eventsToWrite = new LinkedList<Event>();

        for (Event event : writerRunnableEventSeries.getEventsToDelete()) {
            long location = event.getLocation();
            String type = event.getType();
            Map<String, String> copyOfAttributes = event.getCopyOfAttributes();
            Event eventCopy = new Event(location, type, copyOfAttributes);
            this.eventsToDelete.add(eventCopy);
        }
        for (Event event : writerRunnableEventSeries.getEventsToDelete()) {
            long location = event.getLocation();
            String type = event.getType();
            Map<String, String> copyOfAttributes = event.getCopyOfAttributes();
            Event eventCopy = new Event(location, type, copyOfAttributes);
            this.eventsToDelete.add(eventCopy);
        }


    }
    private List<Event> eventsToDelete;
    private List<Event> eventsToWrite;

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
        return new LinkedList<Event>(eventsToDelete);
    }

    public LinkedList<Event> getEventsToWrite() {
        return new LinkedList<Event>(eventsToWrite);
    }
}
