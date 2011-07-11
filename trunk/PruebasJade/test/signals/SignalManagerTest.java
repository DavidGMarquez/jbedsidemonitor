/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import auxiliarTools.AuxTestUtilities;
import org.junit.Test;
import static org.junit.Assert.*;

public class SignalManagerTest {

    public SignalManagerTest() {
    }

    @Test
    public void testInstance() {
        SignalManager signalManager = SignalManager.getInstance();
        SignalManager aaa = SignalManager.getInstance();
        assertEquals(aaa, signalManager);
    }

    @Test
    public void testAddTimeSeries() {
        AuxTestUtilities.reset();
        SignalManager signalManager = SignalManager.getInstance();
        TimeSeries timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        TimeSeries timeSeries2 = new TimeSeries("TimeSeries2", "Simulated", 1, 100, "mv");
        TimeSeries timeSeries3 = new TimeSeries("TimeSeries3", "Simulated", 1, 100, "mv");
        TimeSeries timeSeries4 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        signalManager.addTimeSeries(timeSeries1);
        signalManager.addTimeSeries(timeSeries2);
        signalManager.addTimeSeries(timeSeries3);
        try {
            signalManager.addTimeSeries(timeSeries3);
            fail("Agregada la misma señal dos veces");
        } catch (TimeSerieslAlreadyExistsException e) {
            assertEquals(e.getTimeSeries(), timeSeries3);
        }
        try {
            signalManager.addTimeSeries(timeSeries4);
            fail("Agregada la misma señal dos veces");
        } catch (TimeSerieslAlreadyExistsException e) {
            assertEquals(e.getTimeSeries().getIdentifier(), timeSeries1.getIdentifier());
        }
    }

    @Test
    public void testAddEventSeries() {
        AuxTestUtilities.reset();
        SignalManager signalManager = SignalManager.getInstance();
        EventSeries eventSeries1 = new EventSeries("EventSeries1", "Simulated", 1, new ArrayList<String>(), "mv");
        EventSeries eventSeries2 = new EventSeries("EventSeries2", "Simulated", 1, new ArrayList<String>(), "mv");
        EventSeries eventSeries3 = new EventSeries("EventSeries3", "Simulated", 1, new ArrayList<String>(), "mv");
        EventSeries eventSeries4 = new EventSeries("EventSeries1", "Simulated", 1, new ArrayList<String>(), "mv");
        signalManager.addEventSeries(eventSeries1);
        signalManager.addEventSeries(eventSeries2);
        signalManager.addEventSeries(eventSeries3);
        try {
        signalManager.addEventSeries(eventSeries3);
            fail("Agregada la misma señal dos veces");
        } catch (EventSerieslAlreadyExistsException e) {
            assertEquals(e.getEventSeries(), eventSeries3);
        }
        try {
            signalManager.addEventSeries(eventSeries4);
            fail("Agregada la misma señal dos veces");
        } catch (EventSerieslAlreadyExistsException e) {
            assertEquals(e.getEventSeries().getIdentifier(), eventSeries1.getIdentifier());
        }



    }
}
