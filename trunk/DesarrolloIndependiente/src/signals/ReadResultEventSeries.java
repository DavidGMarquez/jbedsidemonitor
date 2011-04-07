package signals;

import java.util.LinkedList;
import java.util.SortedSet;

public class ReadResultEventSeries extends ReadResultOneSignal {

    private LinkedList<Event> eventsReadWritten;
    private LinkedList<Event> eventsReadDeleted;
    private SortedSet<Event> eventsUnmodifiableCopy;

    public ReadResultEventSeries(String identifierOwner, String identifierSignal, LinkedList<Event> eventsReadWritten, LinkedList<Event> eventsReadDeleted,SortedSet<Event> eventsUnmodifiableCopy) {
        super(identifierOwner, identifierSignal);
        this.eventsReadDeleted = eventsReadDeleted;
        this.eventsReadWritten = eventsReadWritten;
    }

    public LinkedList<Event> getEventsReadDeleted() {
        return eventsReadDeleted;
    }

    public LinkedList<Event> getEventsReadWritten() {
        return eventsReadWritten;
    }
}
