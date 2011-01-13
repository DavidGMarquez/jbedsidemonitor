package signals;

public class WriterRunnableTimeSeries extends WriterRunnableOneSignal {

    public WriterRunnableTimeSeries(String identifier) {
        super(identifier);
    }

    public WriterRunnableTimeSeries(String identifier, float[] dataToWrite) {
         this(identifier);
        copyArray(dataToWrite);
    }

    private float[] dataToWrite;

    @Override
    protected void write() {
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
