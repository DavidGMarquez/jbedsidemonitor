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
public class TimeSeriesWriterRunnable extends WriterRunnable{

    public TimeSeriesWriterRunnable(String identifier) {
    super(identifier);
    }

   private float[] dataToWrite;
    @Override
    void write() {
    SignalManager signalManager=SignalManager.getInstance();
    signalManager.writeToTimeSeries(identifier, dataToWrite);
    }

    public void setDataToWrite(float[] dataToWrite) {
        this.dataToWrite = dataToWrite;
    }

}
