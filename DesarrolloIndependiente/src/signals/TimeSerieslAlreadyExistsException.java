package signals;

import algorithms.*;
import signals.*;

class TimeSerieslAlreadyExistsException extends RuntimeException {

    private String message;
    private TimeSeries timeSeries;

    public TimeSerieslAlreadyExistsException(String message,TimeSeries timeSeries) {
        super(message+" TimeSeries:"+timeSeries.getIdentifier());
        this.message = message;
        this.timeSeries=timeSeries;
    }

    public TimeSeries getTimeSeries() {
        return timeSeries;
    }


}
