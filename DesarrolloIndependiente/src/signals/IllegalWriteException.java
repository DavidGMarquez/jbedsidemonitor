package signals;

class IllegalWriteException extends RuntimeException {

    private String message;
    private String identifier;
    private int bufferCapacity;
    private int lastSampleWritten;
    private float[] dataToWrite;
    private int sampleInitToWrite;

    public IllegalWriteException(String message, int bufferCapacity, float[] dataToWrite,
            int sampleInitToWrite, int lastSampleWritten) {
        super(message);
        this.message =  message;
        this.bufferCapacity = bufferCapacity;
        this.lastSampleWritten = lastSampleWritten;
        this.sampleInitToWrite = sampleInitToWrite;
        this.dataToWrite = dataToWrite;
    }

    IllegalWriteException(IllegalWriteException e, String identifier) {
        super(e.message + " Signal:" + identifier);
        this.message = e.message;
        this.identifier = identifier;

    }

    public int getBufferCapacity() {
        return bufferCapacity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getLastSampleWritten() {
        return lastSampleWritten;
    }

    public float[] getDataToWrite() {
        return dataToWrite;
    }

    public int getSampleInitToWrite() {
        return sampleInitToWrite;
    }
}
