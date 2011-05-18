package signals;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WriterRunnableEventSeries extends WriterRunnableOneSignal {

    public WriterRunnableEventSeries(String identifier) {
        super(identifier);
        eventsToWrite = Collections.synchronizedList(new LinkedList<Event>());
        eventsToDelete = Collections.synchronizedList(new LinkedList<Event>());
    }

    public WriterRunnableEventSeries(WriterRunnableEventSeries writerRunnableEventSeries) {
        super(writerRunnableEventSeries.identifier);
        this.eventsToDelete=Collections.unmodifiableList(new LinkedList<Event>());
        this.eventsToWrite=Collections.unmodifiableList(new LinkedList<Event>());

        LinkedList<Event> eventsRare=new LinkedList<Event>();
        for(Event event:writerRunnableEventSeries.getEventsToDelete()){
            long location = event.getLocation();
            String type = event.getType();
            Map<String, String> copyOfAttributes = event.getCopyOfAttributes();
            Event event2=new Event(location, type, copyOfAttributes);
            eventsRare.add(event2);
            
        }
        this.eventsToDelete=Collections.unmodifiableList(eventsRare);
          LinkedList<Event> eventsRare2=new LinkedList<Event>();
        for(Event event:writerRunnableEventSeries.getEventsToWrite()){
            long location = event.getLocation();
            String type = event.getType();
            Map<String, String> copyOfAttributes = event.getCopyOfAttributes();
            Event event2=new Event(location, type, copyOfAttributes);
            eventsRare2.add(event2);

        }
      //  this.eventsToWrite=Collections.unmodifiableList(eventsRare2);
        /*eventsToDelete = Collections.synchronizedList(new LinkedList<Event>());
        for(Event event:writerRunnableEventSeries.getEventsToDelete()){
            long location = event.getLocation();
            String type = event.getType();
            Map<String, String> copyOfAttributes = event.getCopyOfAttributes();
            Event event2=new Event(location, type, copyOfAttributes);
            //eventsToDelete.add(event2);
          //  eventsToDelete.add(new Event(event.getLocation(), event.getType(), event.getCopyOfAttributes()));
        }
        eventsToWrite = Collections.synchronizedList(new LinkedList<Event>());
        for(Event event:writerRunnableEventSeries.getEventsToWrite()){
                        long location = event.getLocation();
            String type = event.getType();
            Map<String, String> copyOfAttributes = event.getCopyOfAttributes();
            Event event2=new Event(location, type, copyOfAttributes);
          //  eventsToWrite.add(new Event(event.getLocation(), event.getType(), event.getCopyOfAttributes()));
         * }
         */

        

    }
    private List<Event> eventsToDelete;
    private List<Event> eventsToWrite;

    @Override
    protected void write() {
        synchronized (eventsToDelete) {
            synchronized (eventsToWrite) {

                SignalManager signalManager = SignalManager.getInstance();
                for (Event event : eventsToDelete) {
                    signalManager.deleteEventToEventSeries(this.identifier, event);
                }
                for (Event event : eventsToWrite) {
                    signalManager.addEventToEventSeries(this.identifier, event);
                }
            }
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
