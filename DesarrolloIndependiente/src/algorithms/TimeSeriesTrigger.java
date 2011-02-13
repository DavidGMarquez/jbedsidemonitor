package algorithms;

class TimeSeriesTrigger {
    //Para acceder al tiempo tengo que poder acceder a la señal para saber
    //la frecuencia, por eso lo he hecho en muestras provisionalmente
    //@comentario yo no tengo claro que esto tenga que ser "provisional"; esta implementacion
    //es perfectamente válida; que le pasen a esta clase el número de muestras en vez de el numero de milisegundos

    //@comentario no se esta empleando sincronizacion y hace falta. Quizs podria emplearse como monitor
    //el propio objeto.

    //@comentario es necesario determinar qué politica de concurrencia sigue esta clase. Para simplificar las cosas
    //el mantener las instancias de esta clase confinadas dentro de Trigger nos puede ayudar

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
