package algorithms;

class EventSeriesTrigger {
    //Practicamente las mismas dudas que para la de Tiempo

        private String identifierSignal;
    private long newEventCount;
    private long lastEventReported;
    private long theshold;

    public EventSeriesTrigger(long theshold) {
        this.theshold = theshold;
        this.newEventCount = 0;
        this.lastEventReported = 0;
    }

    public void update(ResultEventSeriesWriter resultEventSeriesWriter) {
        //Cuando contamos eventos nuevos... como afectan los que se eliminan?
        this.newEventCount += resultEventSeriesWriter.getEventsToWrite().size();
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
}
