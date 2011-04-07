package signals;

import java.util.LinkedList;
import java.util.SortedSet;

public class ReaderCallableEventSeries extends ReaderCallableOneSignal {

      private LinkedList<Event> eventsReadWritten;
    private LinkedList<Event> eventsReadDeleted;


   

    public ReaderCallableEventSeries(String identifierSignal, String identifierOwner,
            LinkedList<Event> eventsReadWritten,LinkedList<Event> eventsReadDeleted) {
        super(identifierSignal, identifierOwner);
        this.eventsReadDeleted=eventsReadDeleted;
        this.eventsReadWritten=eventsReadWritten;
    }

    @Override
    protected ReadResult read() {
        this.readResult = new ReadResultEventSeries(identifierOwner, identifierSignal,
                this.eventsReadWritten,this.eventsReadDeleted,
                SignalManager.getInstance().getEventsUnmodifiableCopy(identifierSignal));
                

        return readResult;

    }

    public LinkedList<Event> getEventsReadDeleted() {
        return eventsReadDeleted;
    }

    public void setEventsReadDeleted(LinkedList<Event> eventsReadDeleted) {
        this.eventsReadDeleted = eventsReadDeleted;
    }

    public LinkedList<Event> getEventsReadWritten() {
        return eventsReadWritten;
    }

    public void setEventsReadWritten(LinkedList<Event> eventsReadWritten) {
        this.eventsReadWritten = eventsReadWritten;
    }

 
}
