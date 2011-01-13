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
public class ReaderCallableEventSeries extends ReaderCallableOneSignal {

    private long firstInstantToInclude;
    private long lastInstantToInclude;

    public ReaderCallableEventSeries(String identifierSignal, String identifierOwner) {
        super(identifierSignal, identifierOwner);
    }

    @Override
    protected ReadResult read() {
        SignalManager signalManager = SignalManager.getInstance();
        this.readResult = new ReadResultEventSeries(identifierOwner, identifierSignal,
                new LinkedList<Event>(
                signalManager.readFromEventSeriesFromTo(identifierSignal,
                firstInstantToInclude, lastInstantToInclude)));
        return readResult;

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