package signals;

class TooMuchDataToWriteException extends RuntimeException {

    private String message;
    private String identifier;
    private long bufferCapacity;
    private long sizeToWrite;

    public TooMuchDataToWriteException(String message, long bufferCapacity, long sizeToWrite) {
        super(message);
        this.message = new String(message);
        this.bufferCapacity = bufferCapacity;
        this.sizeToWrite = sizeToWrite;
    }

    TooMuchDataToWriteException(TooMuchDataToWriteException e, String identifier) {
        super(e.message + " Signal:" + identifier);
        this.message = new String(e.message);
        this.identifier = new String(identifier);


    }

    public long getBufferCapacity() {
        return bufferCapacity;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getSizeToWrite() {
        return sizeToWrite;
    }
}
