package algorithms;

/**
 * Must Be Immutable
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
