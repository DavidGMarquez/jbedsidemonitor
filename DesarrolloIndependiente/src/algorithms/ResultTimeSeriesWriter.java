/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

/**
 *
 * @author USUARIO
 */
public class ResultTimeSeriesWriter {
    protected String identifier;
    private float[] dataToWrite;

    public ResultTimeSeriesWriter(String identifier, float[] dataToWrite) {
        this.identifier = identifier;
        this.dataToWrite = dataToWrite;
    }




    public float[] getDataToWrite() {
        return dataToWrite;
    }

    public String getIdentifier() {
        return identifier;
    }


}
