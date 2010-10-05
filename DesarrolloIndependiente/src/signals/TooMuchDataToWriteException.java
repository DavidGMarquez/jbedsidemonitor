/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

/**
 *
 * @author USUARIO
 */
class TooMuchDataToWriteException extends RuntimeException{
    private String message;
    private String identifier;
    private long bufferCapacity;
    private long sizeToWrite;

    public TooMuchDataToWriteException(String message) {
        super(message);
        this.message=new String(message);
    }

    TooMuchDataToWriteException(TooMuchDataToWriteException e, String identifier,long bufferCapacity,long sizeToWrite) {
        super(e.message+" Signal:"+identifier);
        this.message=new String(e.message);
        this.identifier=new String(identifier);
        this.bufferCapacity=bufferCapacity;
        this.sizeToWrite=sizeToWrite;

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
