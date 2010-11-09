/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.LinkedList;
import signals.WriterRunnable;
import signals.SignalManager;

/**
 *
 * @author USUARIO
 */
public class EventSeriesReaderCallable extends ReaderCallable {

    private long firstInstantToInclude;
    private long lastInstantToInclude;

    public EventSeriesReaderCallable(String identifierSignal, String identifierOwner) {
        super(identifierSignal, identifierOwner);
    }

    @Override
    void read() {
        SignalManager signalManager = SignalManager.getInstance();        
        this.readResult.addReadResultEventSeries(new ReadResultOneEventSeries(identifierSignal,
                new LinkedList<Event>(
                signalManager.readFromEventSeriesFromTo(identifierSignal,
                firstInstantToInclude, lastInstantToInclude))));

    }

    public void setFirstInstantToInclude(long firstInstantToInclude) {
        this.firstInstantToInclude = firstInstantToInclude;
    }

    public void setLastInstantToInclude(long lastInstantToInclude) {
        this.lastInstantToInclude = lastInstantToInclude;
    }

    public long getFirstInstantToInclude() {
        return firstInstantToInclude;
    }

    public long getLastInstantToInclude() {
        return lastInstantToInclude;
    }



}
