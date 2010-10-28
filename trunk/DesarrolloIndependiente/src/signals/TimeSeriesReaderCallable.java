/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import signals.WriterRunnable;
import signals.SignalManager;

/**
 *
 * @author USUARIO
 */
public class TimeSeriesReaderCallable extends ReaderCallable {

    private int posInitToRead;
    private int sizeToRead;

    public TimeSeriesReaderCallable(String identifierSignal, String identifierOwner) {
        super(identifierSignal, identifierOwner);
    }

    @Override
    void read() {
        SignalManager signalManager = SignalManager.getInstance();
        this.readResult = new ReadResult(identifierOwner);
        this.readResult.addReadResultTimeSeries(new ReadResultOneTimeSeries(identifierSignal, signalManager.readFromTimeSeries(identifierSignal, posInitToRead, sizeToRead), posInitToRead));

    }

    public void setPosInitToRead(int posInitToRead) {
        this.posInitToRead = posInitToRead;
    }

    public void setSizeToRead(int sizeToRead) {
        this.sizeToRead = sizeToRead;
    }
}
