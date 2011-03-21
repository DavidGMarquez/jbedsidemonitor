package signals;

public class WriterRunnableTimeSeries extends WriterRunnableOneSignal {

    private float[] dataToWrite;
    private int indexInitToWrite;

    public WriterRunnableTimeSeries(String identifier) {
        super(identifier);
        this.dataToWrite = null;
        this.indexInitToWrite = -1;
    }

    public WriterRunnableTimeSeries(String identifier, float[] dataToWrite) {
        this(identifier);
        copyArray(dataToWrite);
        this.indexInitToWrite = -1;
    }

    public WriterRunnableTimeSeries(String identifier, float[] dataToWrite, int indexInitToWrite) {
        this(identifier);
        copyArray(dataToWrite);
        this.indexInitToWrite = indexInitToWrite;
    }

    @Override
    protected void write() {
        SignalManager signalManager = SignalManager.getInstance();
        if (indexInitToWrite == -1) {
            signalManager.writeToTimeSeries(identifier, dataToWrite);
        } else {
            signalManager.writeToTimeSeries(identifier, dataToWrite, indexInitToWrite);
        }
    }

    public void setDataToWrite(float[] dataToWrite) {
        this.copyArray(dataToWrite);
    }

    private void copyArray(float[] dataToWrite) {
        float[] copy = new float[dataToWrite.length];
        System.arraycopy(dataToWrite, 0, copy, 0, dataToWrite.length);
        this.dataToWrite = copy;
    }

    public float[] getDataToWrite() {
        return dataToWrite;
    }

    public int getIndexInitToWrite() {
        return indexInitToWrite;
    }
}
