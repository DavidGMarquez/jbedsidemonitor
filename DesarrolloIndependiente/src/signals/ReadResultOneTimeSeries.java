/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 *
 * @author USUARIO
 */
class ReadResultOneTimeSeries {

    private String identifierSignal;
    private float[] data;
    private int posInitToRead;

    public ReadResultOneTimeSeries(String identifierSignal, float[] data, int posInitToRead) {
        this.identifierSignal = identifierSignal;
        this.data = data;
        this.posInitToRead = posInitToRead;
    }

    public int getPosInitToRead() {
        return posInitToRead;
    }

    public float[] getData() {
        return data;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }
}
