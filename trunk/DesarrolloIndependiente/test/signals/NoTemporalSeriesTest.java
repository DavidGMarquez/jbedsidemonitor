/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class NoTemporalSeriesTest {

    public NoTemporalSeriesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getImputs method, of class EventSeries.
     */
    @Test
    public void testGetImputs() {
        System.out.println("getImputs");
        EventSeries instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getSeriesIsGeneratedFrom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTypeevents method, of class EventSeries.
     */
    @Test
    public void testGetTypeevents() {
        System.out.println("getTypeevents");
        EventSeries instance = null;
        Set<String> expResult = null;
        Set<String> result = instance.getEventTypes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstevent method, of class EventSeries.
     */
    @Test
    public void testGetFirstevent() {
        System.out.println("getFirstevent");
        EventSeries instance = null;
        long expResult = 0L;
        long result = instance.getFirstevent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastevent method, of class EventSeries.
     */
    @Test
    public void testGetLastevent() {
        System.out.println("getLastevent");
        EventSeries instance = null;
        long expResult = 0L;
        long result = instance.getLastevent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnits method, of class EventSeries.
     */
    @Test
    public void testGetUnits() {
        System.out.println("getUnits");
        EventSeries instance = null;
        String expResult = "";
        String result = instance.getUnits();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEvent method, of class EventSeries.
     */
    @Test
    public void testGetEvent() {
        System.out.println("getEvent");
        int index = 0;
        EventSeries instance = null;
        Event expResult = null;
        SortedSet<Event> result = instance.getEvent(index,index);
        assert(result.isEmpty());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addEvent method, of class EventSeries.
     */
    @Test
    public void testAddEvent() {
        System.out.println("addEvent");
        Event even = null;
        EventSeries instance = null;
        boolean expResult = false;
        boolean result = instance.addEvent(even);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteEvent method, of class EventSeries.
     */
    @Test
    public void testDeleteEvent() {
        System.out.println("deleteEvent");
        int index = 0;
        EventSeries instance = null;
        boolean expResult = false;
        boolean result = instance.deleteEvent(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void TestCrear1(){
        ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");

    }
        @Test
    public void TestCrear2(){
        ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        assertEquals(NTS.getIdentifier(), "O2");
        assertEquals(NTS.getAgent(), "Simulador");
        assertEquals(new Date().getTime()>=NTS.getOrigin(), true);
        assertEquals(NTS.getUnits(), "mV");

    }
        @Test
            public void TestModf1(){
        ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        ArrayList<String> imputs2=NTS.getSeriesIsGeneratedFrom();
        imputs2.remove(0);
        assertEquals(NTS.getSeriesIsGeneratedFrom().get(0),imputs.get(0));
        System.out.println(NTS.getSeriesIsGeneratedFrom().get(0)+" "+imputs.get(0));
        imputs.remove(0);
        System.out.println(NTS.getSeriesIsGeneratedFrom().get(0)+" "+imputs.get(0));
        try{        NTS.getEvent(0,0);
        fail("Deberia Fallar porque no hay eventos");}
        catch(Exception e)
        {
          e.getCause();   
        }


    }
        @Test
              public void TesAddEvent1(){
        ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e1=new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e2=new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e3=new Event(new Date().getTime(), "C", null);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        NTS.addEvent(e3);
        assertEquals(e1,NTS.getEvent(0));
        assertEquals(e2,NTS.getEvent(1));
        assertEquals(e3,NTS.getEvent(2));
        assertEquals(3,NTS.getTypeevents().size());
        assertEquals(e1.getMoment(), NTS.getFirstevent());
            System.out.println(e1.getMoment()+" "+e2.getMoment()+" "+e3.getMoment()+" "+(e1.getMoment()-e3.getMoment()));
        assertEquals(e3.getMoment(), NTS.getLastevent());
        NTS.deleteEvent(NTS.getSizeEvents()-1);
        assertEquals(e2.getMoment(), NTS.getLastevent());
        NTS.deleteEvent(0);
        assertEquals(NTS.getFirstevent(), NTS.getLastevent());
        assertEquals(1,NTS.getTypeevents().size());
    }
@Test
              public void TesDeleteAddEvent(){
        ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e1=new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e2=new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e3=new Event(new Date().getTime(), "C", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e4=new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e5=new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e6=new Event(new Date().getTime(), "C", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(NoTemporalSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e7=new Event(new Date().getTime(), "D", null);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        NTS.addEvent(e3);
        NTS.addEvent(e4);
        NTS.addEvent(e5);
        NTS.addEvent(e6);
        NTS.addEvent(e7);
        assertEquals(4,NTS.getTypeevents().size());
        assertEquals(7,NTS.getSizeEvents());
        NTS.deleteEvent(NTS.getSizeEvents()-1);
        assertEquals(3,NTS.getTypeevents().size());
        assertEquals(6,NTS.getSizeEvents());
        NTS.deleteEvent(0);
        NTS.deleteEvent(0);
        NTS.deleteEvent(0);
        assertEquals(3,NTS.getTypeevents().size());
        assertEquals(3,NTS.getSizeEvents());
        assertEquals(e4.getMoment(), NTS.getFirstevent());
        assertEquals(e6.getMoment(), NTS.getLastevent());
    }

}