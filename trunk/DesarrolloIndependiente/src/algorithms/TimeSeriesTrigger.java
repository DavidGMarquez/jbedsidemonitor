package algorithms;

class TimeSeriesTrigger {
    //Para acceder al tiempo tengo que poder acceder a la señal para saber
    //la frecuencia, por eso lo he hecho en muestras provisionalmente

    private String identifierSignal;
    private long lastSampleReported;
    private long newDataInMs;
    private long theshold;

    public TimeSeriesTrigger(long theshold) {
        this.theshold = theshold;
        this.lastSampleReported = 0;
        this.newDataInMs = 0;
    }

    public void update(ResultTimeSeriesWriter resultTimeSeriesWriter) {
        this.newDataInMs += resultTimeSeriesWriter.getDataToWrite().length;
    }

    public boolean trigger() {
        if (this.newDataInMs > theshold) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.lastSampleReported += this.newDataInMs;
        this.newDataInMs = 0;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }
}
