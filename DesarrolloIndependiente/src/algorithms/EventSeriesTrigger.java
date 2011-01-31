/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author USUARIO
 */
class EventSeriesTrigger {
//Practicamente las mismas dudas que para la de Tiempo
    //Cuando contamos eventos nuevos... como afectan los que se eliminan?
    private long newEventCount;
    private long lastEventReported;
    private long theshold;

    public EventSeriesTrigger(long theshold) {
        this.theshold = theshold;
        this.reset();
    }

    public void update(ResultEventSeriesWriter resultEventSeriesWriter) {
    }

    public boolean trigger() {
        if (this.newEventCount > this.theshold) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.newEventCount = 0;
        this.lastEventReported = 0;
    }
}
