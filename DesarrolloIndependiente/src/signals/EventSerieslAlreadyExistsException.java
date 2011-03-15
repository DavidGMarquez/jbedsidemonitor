package signals;

import algorithms.*;
import signals.*;

class EventSerieslAlreadyExistsException extends RuntimeException {

    private String message;
    private EventSeries eventSeries;

    public EventSerieslAlreadyExistsException(String message,EventSeries eventSeries) {
        super(message+" EventSeries:"+eventSeries.getIdentifier());
        this.message = message;
        this.eventSeries=eventSeries;
    }

    public EventSeries getEventSeries() {
        return eventSeries;
    }
}
