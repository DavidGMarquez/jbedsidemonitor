package signals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class EvenSeriesTest {

    public void testConstructor() {
        EventSeries evenSeries = generateStandardTestEventSeries();
        assertEquals(evenSeries.getIdentifier(), "O2");
        assertEquals(evenSeries.getAgent(), "Simulador");
        assertEquals(new Date().getTime() >= evenSeries.getOrigin(), true);
        assertEquals(evenSeries.getUnits(), "mV");
    }

    @Test
    public void TestModf1() {
        ArrayList<String> imputs = new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos ");
        EventSeries evenSeries = generateStandardTestEventSeries();
        ArrayList<String> imputs2 = evenSeries.getSeriesIsGeneratedFrom();
        imputs2.remove(0);
        assertEquals(evenSeries.getSeriesIsGeneratedFrom().get(0), imputs.get(0));
        imputs.remove(0);
        try {
            assertEquals(evenSeries.getEvents(0, 0).size(), 0);
            assertEquals(evenSeries.getNumberOfEvents(), 0);
        } catch (Exception e) {
            e.getCause();
            fail("Deberia de funcionar y dar un set vacio");
        }
    }

    @Test
    public void TesAddEvent1() {
        EventSeries evenSeries = generateStandardTestEventSeries();
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e1 = new Event(new Date().getTime(), "A", new HashMap<String, String>());
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e2 = new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e3 = new Event(new Date().getTime(), "C", null);
        evenSeries.addEvent(e1);
        evenSeries.addEvent(e2);
        evenSeries.addEvent(e3);
        System.out.println(e1.getLocation() + " " + e2.getLocation() + " " + e3.getLocation() + " " + (e1.getLocation() - e3.getLocation()));
        System.out.println(evenSeries.getEvents(e1.getLocation(), e3.getLocation()).size());
        assertEquals(e1, evenSeries.getEvents(e1.getLocation(), e3.getLocation()).first());
        assertEquals(e3, evenSeries.getEvents(e1.getLocation(), e3.getLocation()).last());
        assertEquals(e1.getLocation(), evenSeries.getFirstevent());
        assertEquals(e3.getLocation(), evenSeries.getLastevent());
        evenSeries.deleteEvent(e3);
        assertEquals(e2.getLocation(), evenSeries.getLastevent());
        evenSeries.deleteEvent(e1);
        assertEquals(evenSeries.getFirstevent(), evenSeries.getLastevent());
        assertEquals(1, evenSeries.getNumberOfEvents());
    }

    @Test
    public void TesDeleteAddEvent() {
        EventSeries evenSeries = generateStandardTestEventSeries();
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e1 = new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e2 = new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e3 = new Event(new Date().getTime(), "C", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e4 = new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e5 = new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e6 = new Event(new Date().getTime(), "C", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e7 = new Event(new Date().getTime(), "D", null);
        evenSeries.addEvent(e1);
        evenSeries.addEvent(e2);
        evenSeries.addEvent(e3);
        evenSeries.addEvent(e4);
        evenSeries.addEvent(e5);
        evenSeries.addEvent(e6);
        evenSeries.addEvent(e7);
        assertEquals(evenSeries.getNumberOfEvents(), 7);

        assertEquals(evenSeries.getEvents(e2.getLocation(), e6.getLocation()).size(), 5);
        evenSeries.getEvents(e2.getLocation(), e6.getLocation()).remove(e3);
        assertEquals(evenSeries.getEvents(e2.getLocation(), e6.getLocation()).size(), 5);
    }

    @Test
    public void TestEventsSameTime() {
        EventSeries evenSeries = generateStandardTestEventSeries();
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long time = new Date().getTime();
        Event e1 = new Event(time, "A", null);
        Event e2 = new Event(time, "A", null);
        evenSeries.addEvent(e1);
        evenSeries.addEvent(e2);
        assertEquals(evenSeries.getNumberOfEvents(), 1);
        evenSeries.deleteEvent(e1);
        assertEquals(evenSeries.getNumberOfEvents(), 0);


        EventSeries evenSeries2 = generateStandardTestEventSeries();
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long time2 = new Date().getTime();
        Event e12 = new Event(time2, "C", null);
        Event e22 = new Event(time2, "D", null);
        evenSeries2.addEvent(e12);
        evenSeries2.addEvent(e22);
        assertEquals(evenSeries2.getNumberOfEvents(), 2);
        evenSeries2.deleteEvent(e2);
        assertEquals(evenSeries.getNumberOfEvents(), 0);
        evenSeries2.deleteEvent(e12);
        assertEquals(evenSeries2.getNumberOfEvents(), 1);
    }

    @Test
    public void deleteEventsSameTime() {
        EventSeries evenSeries = generateStandardTestEventSeries();
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long time = new Date().getTime();
        Event e1 = new Event(time, "A", null);
        Event e2 = new Event(time, "A", null);
        Event e3 = new Event(time, "B", null);
        Event e4 = new Event(time, "D", null);
        evenSeries.addEvent(e1);
        evenSeries.addEvent(e2);
        evenSeries.addEvent(e3);
        evenSeries.addEvent(e4);
        assertEquals(evenSeries.getNumberOfEvents(), 3);
        evenSeries.deleteEventsAtLocation(time);
        assertEquals(evenSeries.getNumberOfEvents(), 0);
        evenSeries.addEvent(e1);
        evenSeries.addEvent(e3);
        evenSeries.addEvent(e4);
        evenSeries.addEvent(e1);
        evenSeries.addEvent(e2);
        evenSeries.addEvent(e3);
        evenSeries.addEvent(e4);
        assertEquals(evenSeries.getNumberOfEvents(), 3);
        assertEquals(evenSeries.getEventTypes().size(), 3);
        Event e11 = new Event(time + 2, "A", null);
        Event e22 = new Event(time + 1, "A", null);
        Event e33 = new Event(time + 2, "B", null);
        Event e44 = new Event(time + 1, "D", null);
        evenSeries.addEvent(e11);
        evenSeries.addEvent(e22);
        evenSeries.addEvent(e33);
        evenSeries.addEvent(e44);
        assertEquals(evenSeries.getNumberOfEvents(), 7);
        assertEquals(evenSeries.getEventTypes().size(), 3);
        evenSeries.deleteEventsAtLocation(time + 2);
        assertEquals(evenSeries.getNumberOfEvents(), 5);
        assertEquals(evenSeries.getEventTypes().size(), 3);
        evenSeries.deleteEvent(e3);
        assertEquals(evenSeries.getNumberOfEvents(), 4);
        assertEquals(evenSeries.getEventTypes().size(), 2);
        evenSeries.deleteEventsAtLocation(time);
        assertEquals(evenSeries.getNumberOfEvents(), 2);
        assertEquals(evenSeries.getEventTypes().size(), 2);
        evenSeries.deleteEvent(e44);
        assertEquals(evenSeries.getNumberOfEvents(), 1);
        assertEquals(evenSeries.getEventTypes().size(), 1);
        evenSeries.addEvent(e11);
        assertEquals(evenSeries.getNumberOfEvents(), 2);
        assertEquals(evenSeries.getEventTypes().size(), 1);
        evenSeries.deleteEventsAtLocation(time);
        evenSeries.deleteEventsAtLocation(time + 1);
        evenSeries.deleteEventsAtLocation(time + 2);
        assertEquals(evenSeries.getNumberOfEvents(), 0);
        assertEquals(evenSeries.getEventTypes().size(), 0);
    }

    private EventSeries generateStandardTestEventSeries() {
        ArrayList<String> imputs = new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos ");
        EventSeries evenSeries = new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        return evenSeries;
    }
}
