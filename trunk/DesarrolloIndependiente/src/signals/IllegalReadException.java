package signals;

class IllegalReadException extends RuntimeException {

    private String message;
    private String identifier;
    private int bufferCapacity;
    private int lastSampleWrite;
    private int posStartReading;
    private int numDataToRead;

    public IllegalReadException(String message, int bufferCapacity, int posStartReading,
            int numDataToRead, int lastSampleWrite) {
        super(message);
        this.message = message;
        this.bufferCapacity = bufferCapacity;
        this.lastSampleWrite = lastSampleWrite;
        this.posStartReading = posStartReading;
        this.numDataToRead = numDataToRead;
    }

    IllegalReadException(IllegalReadException e, String identifier) {
        super(e.message + " Signal:" + identifier);
        //@comentario animal de bellotas ¡los String! son inmutables!
        this.message = e.message;
        this.identifier = identifier;

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

    public int getNumDataToRead() {
        return numDataToRead;
    }

    public int getPosStartReading() {
        return posStartReading;
    }
}
