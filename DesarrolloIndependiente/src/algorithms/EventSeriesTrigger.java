package algorithms;

import signals.WriterRunnableEventSeries;

class EventSeriesTrigger {

    private String identifierSignal;
    private int newEventCount;
    private int lastEventReported;
    private int theshold;

    public EventSeriesTrigger(String identifierSignal,int theshold) {
        this.identifierSignal=identifierSignal;
        this.theshold = theshold;
        this.newEventCount = 0;
        this.lastEventReported = 0;
    }

    public void update(WriterRunnableEventSeries writerRunnableEventSeries) {
        if(writerRunnableEventSeries.getIdentifier().equals(identifierSignal)){
            //@duda dijimos que contaban tambien los borrados no?
            this.newEventCount += writerRunnableEventSeries.getEventsToWrite().size();
            this.newEventCount += writerRunnableEventSeries.getEventsToDelete().size();
        }
    }

    public boolean trigger() {
        if (this.newEventCount > this.theshold) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.lastEventReported += newEventCount;
        this.newEventCount = 0;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }

    public int getLastEventReported() {
        return lastEventReported;
    }

    public int getNewEventCount() {
        return newEventCount;
    }

    public int getTheshold() {
        return theshold;
    }
}
