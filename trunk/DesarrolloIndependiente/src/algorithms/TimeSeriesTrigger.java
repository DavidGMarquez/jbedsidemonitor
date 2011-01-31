/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
class TimeSeriesTrigger {
    //@duda para que era lastSampleReported? Como lo hago?
    //Para acceder al tiempo tengo que poder acceder a la señal para saber la frecuencia, por eso lo he hecho en muestras
    //Que ocurre cuando se hacew el reset? Como sabemos cuantas muestras se han eliminado y si no han quedado pendientes.
    //Es decir si entre el trigger y el reset han ocurrido updates.
    private long lastSampleReported;
    private long newDataInMs;
    private long theshold;

    public TimeSeriesTrigger(long theshold) {
        this.theshold = theshold;
        this.reset();
    }

    public void update(ResultTimeSeriesWriter resultTimeSeriesWriter) {
        this.newDataInMs = +resultTimeSeriesWriter.getDataToWrite().length;
    }

    public boolean trigger() {
        if (this.newDataInMs > theshold) {
            return true;
        } else {
            return false;
        }


    }

    public void reset() {

        this.newDataInMs = 0;
        this.lastSampleReported = 0;
    }
}
