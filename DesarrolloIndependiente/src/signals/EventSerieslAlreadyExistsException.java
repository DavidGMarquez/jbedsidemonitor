package signals;

class EventSerieslAlreadyExistsException extends RuntimeException {

    private EventSeries eventSeries;

    public EventSerieslAlreadyExistsException(String message,EventSeries eventSeries) {
        super(message+" EventSeries:"+eventSeries.getIdentifier());
        this.eventSeries=eventSeries;
    }

    public EventSeries getEventSeries() {
        return eventSeries;
    }
}
