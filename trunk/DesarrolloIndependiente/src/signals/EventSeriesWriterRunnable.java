/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class EventSeriesWriterRunnable extends WriterRunnable {

    public EventSeriesWriterRunnable(String identifier) {
        super(identifier);
        eventsToWrite=new ArrayList<Event>();
    }
    private ArrayList<Event> eventsToWrite;

    @Override
    void write() {
        SignalManager signalManager = SignalManager.getInstance();
        for (int i = 0; i < eventsToWrite.size(); i++) {
            signalManager.writeEvent(this.identifier,eventsToWrite.get(i));
        }
    }

    public void addEventToWrite(Event e)
    {
        this.eventsToWrite.add(e);
    }
}
