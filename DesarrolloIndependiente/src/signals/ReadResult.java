/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

/**
 *
 * @author USUARIO
 */
class ReadResult {
   private String identifierSignal;
    private String identifierOwner;
    private float[] data;

    public ReadResult(String identifierSignal, String identifierOwner, float[] data) {
        this.identifierSignal = identifierSignal;
        this.identifierOwner = identifierOwner;
        this.data = data;
    }

    public float[] getData() {
        return data;
    }

    public String getIdentifierOwner() {
        return identifierOwner;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }




}
