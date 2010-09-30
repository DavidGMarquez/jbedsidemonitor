package signals;

public class TimeSeriesWriterRunnable extends WriterRunnable {

    public TimeSeriesWriterRunnable(String identifier) {
        super(identifier);
    }
    private float[] dataToWrite;

    @Override
    void write() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.writeToTimeSeries(identifier, dataToWrite);
    }
//@todo Realiza una copia defensiva
    public void setDataToWrite(float[] dataToWrite) {
        this.dataToWrite = dataToWrite;
    }
}
