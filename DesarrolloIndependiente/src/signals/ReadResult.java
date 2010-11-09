package signals;

import java.util.LinkedList;
/**
 *
 * @comentario: mismo comentario que para ReadResultOneTimeSeries
 */
class ReadResult {

    private String identifierOwner;
    private LinkedList<ReadResultOneEventSeries> readResultsEventSeries;
    private LinkedList<ReadResultOneTimeSeries> readResultsTimeSeries;

    public ReadResult(String identifierOwner) {
        this.identifierOwner = identifierOwner;
        readResultsEventSeries = new LinkedList<ReadResultOneEventSeries>();
        readResultsTimeSeries = new LinkedList<ReadResultOneTimeSeries>();
    }

    public String getIdentifierOwner() {
        return identifierOwner;
    }

    public LinkedList<ReadResultOneEventSeries> getReadResultsEventSeries() {
        return readResultsEventSeries;
    }

    public LinkedList<ReadResultOneTimeSeries> getReadResultsTimeSeries() {
        return readResultsTimeSeries;
    }

    public void addReadResultTimeSeries(ReadResultOneTimeSeries readResultOneTimeSeries) {
        this.readResultsTimeSeries.add(readResultOneTimeSeries);
    }

    public void addReadResultEventSeries(ReadResultOneEventSeries readResultOneEventSeries) {
        this.readResultsEventSeries.add(readResultOneEventSeries);
    }
}