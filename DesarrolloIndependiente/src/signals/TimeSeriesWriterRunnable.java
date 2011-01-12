package signals;

public class TimeSeriesWriterRunnable extends WriterRunnableOneSignal {

    public TimeSeriesWriterRunnable(String identifier) {
        super(identifier);
    }

    public TimeSeriesWriterRunnable(String identifier, float[] dataToWrite) {
         this(identifier);
        copyArray(dataToWrite);
    }

    private float[] dataToWrite;

    @Override
    void write() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.writeToTimeSeries(identifier, dataToWrite);
    }

    public void setDataToWrite(float[] dataToWrite) {
        copyArray(dataToWrite);
    }

    private void copyArray(float[] dataToWrite) {
        float[] copy = new float[dataToWrite.length];
        System.arraycopy(dataToWrite, 0, copy, 0, dataToWrite.length);
        this.dataToWrite = copy;
    }
}
