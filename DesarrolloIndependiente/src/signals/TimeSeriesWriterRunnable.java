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

    public void setDataToWrite(float[] dataToWrite) {
        float[] copy = new float[dataToWrite.length];
        System.arraycopy(dataToWrite, 0, copy, 0, dataToWrite.length);
        this.dataToWrite = copy;
    }
}
