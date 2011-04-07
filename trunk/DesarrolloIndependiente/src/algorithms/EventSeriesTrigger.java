package algorithms;

import java.util.LinkedList;
import signals.Event;
import signals.WriterRunnableEventSeries;

class EventSeriesTrigger {

    private String identifierSignal;
    private int theshold;
    LinkedList<Event> eventsAlreadyWritten;
    LinkedList<Event> eventsAlreadyDeleted;

    public EventSeriesTrigger(String identifierSignal,int theshold) {
        this.identifierSignal=identifierSignal;
        this.theshold = theshold;
        eventsAlreadyWritten=new LinkedList<Event>();
        eventsAlreadyDeleted=new LinkedList<Event>();
    }

    public void update(WriterRunnableEventSeries writerRunnableEventSeries) {
        if(writerRunnableEventSeries.getIdentifier().equals(identifierSignal)){
            eventsAlreadyDeleted.addAll(writerRunnableEventSeries.getEventsToDelete());
            eventsAlreadyWritten.addAll(writerRunnableEventSeries.getEventsToWrite());
        }
    }

    public boolean trigger() {
        if ((this.eventsAlreadyDeleted.size()+this.eventsAlreadyWritten.size())>this.theshold) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.eventsAlreadyDeleted.clear();
        this.eventsAlreadyWritten.clear();
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }

    public int getTheshold() {
        return theshold;
    }

    public LinkedList<Event> getEventsAlreadyDeletedCopy() {
        return new LinkedList<Event>(eventsAlreadyDeleted);
    }

    public LinkedList<Event> getEventsAlreadyWrittenCopy() {
        return new LinkedList<Event>(eventsAlreadyWritten);
    }

}
