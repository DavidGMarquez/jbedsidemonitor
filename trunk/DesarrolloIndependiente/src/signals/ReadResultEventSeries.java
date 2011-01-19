package signals;

import java.util.LinkedList;

class ReadResultEventSeries extends ReadResultOneSignal{

    private LinkedList<Event> eventsRead;

    public ReadResultEventSeries(String identifierOwner,String identifierSignal, LinkedList<Event> eventsRead) {
        super(identifierOwner,identifierSignal);
        this.eventsRead = eventsRead;
    }

    public LinkedList<Event> getEventsRead() {
        return eventsRead;
    }
}
