package algorithms;

import signals.WriterRunnableTimeSeries;

class TimeSeriesTrigger {
    //@comentario no se esta empleando sincronizacion y hace falta. Quizs podria emplearse como monitor
    //el propio objeto.
    //@comentario es necesario determinar qué politica de concurrencia sigue esta clase. Para simplificar las cosas
    //el mantener las instancias de esta clase confinadas dentro de Trigger nos puede ayudar
    //@pendiente especificar políticas de sincronización para los Triggers individuales

    private String identifierSignal;
    private int lastSampleReported;
    private int newDataInMs;
    private int theshold;

    public TimeSeriesTrigger(String identifierSignal,int theshold) {
        this.identifierSignal=identifierSignal;
        this.theshold = theshold;
        this.lastSampleReported = 0;
        this.newDataInMs = 0;
    }

    public void update(WriterRunnableTimeSeries writerRunnableTimeSeries) {
        if(writerRunnableTimeSeries.getIdentifier().equals(identifierSignal))
        this.newDataInMs += writerRunnableTimeSeries.getDataToWrite().length;
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

    public int getLastSampleReported() {
        return lastSampleReported;
    }

    public int getNewDataInMs() {
        return newDataInMs;
    }

    public int getTheshold() {
        return theshold;
    }
}
