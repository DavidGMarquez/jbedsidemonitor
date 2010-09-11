/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

/**
 *
 * @author USUARIO
 */
//@pendiente convertir clase en inmutable
public class WriteOperation {
    private float[] bufferToWrite;
    private String identifierSignal;

    public WriteOperation(float[] bufferToWrite, String identifier) {
        this.bufferToWrite = bufferToWrite;
        this.identifierSignal = identifier;
    }

    public float[] getBufferToWrite() {
        return bufferToWrite;
    }


    public String getIdentifier() {
        return identifierSignal;
    }





}
