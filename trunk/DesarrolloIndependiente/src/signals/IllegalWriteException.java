package signals;

class IllegalWriteException extends RuntimeException {

    private String message;
    private String identifier;
    private int bufferCapacity;
    private int lastSampleWrite;
    private int numberOfSamplesWrite;
    private float[] dataToWrite;
    private int sampleInitToWrite;

    public IllegalWriteException(String message, int bufferCapacity, float[] dataToWrite, int sampleInitToWrite, int lastSampleWrite, int numberOfSamplesWrite) {
        super(message);
        this.message = new String(message);
        this.bufferCapacity = bufferCapacity;
        this.lastSampleWrite = lastSampleWrite;
        this.numberOfSamplesWrite = numberOfSamplesWrite;
        this.sampleInitToWrite = sampleInitToWrite;
        this.dataToWrite = dataToWrite;
    }

    IllegalWriteException(IllegalWriteException e, String identifier) {
        super(e.message + " Signal:" + identifier);
        this.message = new String(e.message);
        this.identifier = new String(identifier);

    }

    public int getBufferCapacity() {
        return bufferCapacity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getLastSampleWrite() {
        return lastSampleWrite;
    }

    public int getNumberOfSamplesWrite() {
        return numberOfSamplesWrite;
    }

    public float[] getDataToWrite() {
        return dataToWrite;
    }

    public int getSampleInitToWrite() {
        return sampleInitToWrite;
    }
}
