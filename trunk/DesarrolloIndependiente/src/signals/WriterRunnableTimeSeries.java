package signals;

public class WriterRunnableTimeSeries extends WriterRunnableOneSignal {

    private float[] dataToWrite;
    private int indexInitToWrite;
    //@pendiente pasarlo a un objeto propio y no aqui como estan metidos
    private int sampleInitToReadInOrder;
    private int samplesToReadInOrder;


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
            //@pendiente pasarlo a un objeto propio y no aqui como estan metidos
            int[] resultWrite=signalManager.writeToTimeSeries(identifier, dataToWrite, indexInitToWrite);
            this.sampleInitToReadInOrder=resultWrite[0];
            this.samplesToReadInOrder=resultWrite[1];
        }

    }

    public void setDataToWrite(float[] dataToWrite) {
        this.copyArray(dataToWrite);
    }

    public void setIndexInitToWrite(int indexInitToWrite) {
        this.indexInitToWrite = indexInitToWrite;
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

    public int getSampleInitToReadInOrder() {
        return sampleInitToReadInOrder;
    }

    public int getSamplesToReadInOrder() {
        return samplesToReadInOrder;
    }
    //@debug
    public void setSampleInitToReadInOrder(int sampleInitToReadInOrder) {
        this.sampleInitToReadInOrder = sampleInitToReadInOrder;
    }
//@debug
    public void setSamplesToReadInOrder(int samplesToReadInOrder) {
        this.samplesToReadInOrder = samplesToReadInOrder;
    }
    

}
