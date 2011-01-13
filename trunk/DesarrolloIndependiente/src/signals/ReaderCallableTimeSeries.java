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
public class ReaderCallableTimeSeries extends ReaderCallableOneSignal {

    private int posInitToRead;
    private int sizeToRead;

    public ReaderCallableTimeSeries(String identifierSignal, String identifierOwner) {
        super(identifierSignal, identifierOwner);
    }

    @Override
    protected ReadResult read() {
        SignalManager signalManager = SignalManager.getInstance();
        this.readResult = new ReadResultTimeSeries(identifierOwner, identifierSignal,
                signalManager.readFromTimeSeries(identifierSignal, posInitToRead, sizeToRead), posInitToRead);
        return readResult;
    }

    public void setPosInitToRead(int posInitToRead) {
        this.posInitToRead = posInitToRead;
    }

    public void setSizeToRead(int sizeToRead) {
        this.sizeToRead = sizeToRead;
    }
}
