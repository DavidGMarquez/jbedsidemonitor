/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.LinkedList;

/**
 *
 * @author USUARIO
 */
class ReadResultOneEventSeries {

    private String identifierSignal;
    private LinkedList<Event> eventsRead;

    public ReadResultOneEventSeries(String identifierSignal, LinkedList<Event> eventsRead) {
        this.identifierSignal = identifierSignal;
        this.eventsRead = eventsRead;
    }

    public LinkedList<Event> getEventsRead() {
        return eventsRead;
    }

    public String getIdentifierSignal() {
        return identifierSignal;
    }
}
