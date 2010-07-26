/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class EvenSeriesTest {

    public EvenSeriesTest() {
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
        try{
        assertEquals( NTS.getEvents(0,0).size(), 0);
        assertEquals( NTS.getNumberOfEvents(), 0);
        }
        catch(Exception e)
        {
          e.getCause();
          fail("Deberia de funcionar y dar un set vacio");
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
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e1=new Event(new Date().getTime(), "A", new HashMap<String,String>());
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e2=new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e3=new Event(new Date().getTime(), "C", null);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        NTS.addEvent(e3);            System.out.println(e1.getMoment()+" "+e2.getMoment()+" "+e3.getMoment()+" "+(e1.getMoment()-e3.getMoment()));
            System.out.println(NTS.getEvents(e1.getMoment(),e3.getMoment()).size());
        assertEquals(e1,NTS.getEvents(e1.getMoment(),e3.getMoment()).first());
        assertEquals(e3,NTS.getEvents(e1.getMoment(),e3.getMoment()).last());
        assertEquals(e1.getMoment(), NTS.getFirstevent());
        assertEquals(e3.getMoment(), NTS.getLastevent());
        NTS.deleteEvent(e3);
        assertEquals(e2.getMoment(), NTS.getLastevent());
        NTS.deleteEvent(e1);
        assertEquals(NTS.getFirstevent(), NTS.getLastevent());
        assertEquals(1,NTS.getNumberOfEvents());
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
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e1=new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e2=new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e3=new Event(new Date().getTime(), "C", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e4=new Event(new Date().getTime(), "A", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e5=new Event(new Date().getTime(), "B", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Event e6=new Event(new Date().getTime(), "C", null);
        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Event e7=new Event(new Date().getTime(), "D", null);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        NTS.addEvent(e3);
        NTS.addEvent(e4);
        NTS.addEvent(e5);
        NTS.addEvent(e6);
        NTS.addEvent(e7);
        assertEquals(NTS.getNumberOfEvents(), 7);

        assertEquals(NTS.getEvents(e2.getMoment(), e6.getMoment()).size(),5);
        NTS.getEvents(e2.getMoment(), e6.getMoment()).remove(e3);
        assertEquals(NTS.getEvents(e2.getMoment(), e6.getMoment()).size(),5);
    }
@Test
              public void TestEventsSameTime(){
        ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long time=new Date().getTime();
        Event e1=new Event(time, "A", null);
        Event e2=new Event(time, "A", null);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        assertEquals(NTS.getNumberOfEvents(), 1);
        NTS.deleteEvent(e1);
        assertEquals(NTS.getNumberOfEvents(), 0);


        EventSeries NTS2=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long time2=new Date().getTime();
        Event e12=new Event(time2, "C", null);
        Event e22=new Event(time2, "D", null);
        NTS2.addEvent(e12);
        NTS2.addEvent(e22);
        assertEquals(NTS2.getNumberOfEvents(), 2);
        NTS2.deleteEvent(e2);
        assertEquals(NTS.getNumberOfEvents(), 0);
        NTS2.deleteEvent(e12);
        assertEquals(NTS2.getNumberOfEvents(), 1);
}
@Test
              public void TestDeleteEventsSameTime(){

    ArrayList<String> imputs=new ArrayList<String>();
        imputs.add("Electro");
        imputs.add("Oxigeno");
        imputs.add(" Latidos " );
        EventSeries NTS=new EventSeries("O2", "Simulador", new Date().getTime(), imputs, "mV");
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(EvenSeriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        long time=new Date().getTime();
        Event e1=new Event(time, "A", null);
        Event e2=new Event(time, "A", null);
        Event e3=new Event(time, "B", null);
        Event e4=new Event(time, "D", null);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        NTS.addEvent(e3);
        NTS.addEvent(e4);
        assertEquals(NTS.getNumberOfEvents(), 3);
        NTS.deleteEventsAtLocation(time);
        assertEquals(NTS.getNumberOfEvents(), 0);
        NTS.addEvent(e1);
        NTS.addEvent(e3);
        NTS.addEvent(e4);
        NTS.addEvent(e1);
        NTS.addEvent(e2);
        NTS.addEvent(e3);
        NTS.addEvent(e4);
        assertEquals(NTS.getNumberOfEvents(), 3);
        assertEquals(NTS.getEventTypes().size(),3);
        Event e11=new Event(time+2, "A", null);
        Event e22=new Event(time+1, "A", null);
        Event e33=new Event(time+2, "B", null);
        Event e44=new Event(time+1, "D", null);
                NTS.addEvent(e11);
        NTS.addEvent(e22);
        NTS.addEvent(e33);
        NTS.addEvent(e44);
                assertEquals(NTS.getNumberOfEvents(), 7);
        assertEquals(NTS.getEventTypes().size(),3);
        NTS.deleteEventsAtLocation(time+2);
                        assertEquals(NTS.getNumberOfEvents(), 5);
        assertEquals(NTS.getEventTypes().size(),3);
        NTS.deleteEvent(e3);
                        assertEquals(NTS.getNumberOfEvents(), 4);
        assertEquals(NTS.getEventTypes().size(),2);
        NTS.deleteEventsAtLocation(time);
                        assertEquals(NTS.getNumberOfEvents(), 2);
        assertEquals(NTS.getEventTypes().size(),2);
        NTS.deleteEvent(e44);
                        assertEquals(NTS.getNumberOfEvents(), 1);
        assertEquals(NTS.getEventTypes().size(),1);
                NTS.addEvent(e11);
                                        assertEquals(NTS.getNumberOfEvents(), 2);
        assertEquals(NTS.getEventTypes().size(),1);
        NTS.deleteEventsAtLocation(time);
        NTS.deleteEventsAtLocation(time+1);
        NTS.deleteEventsAtLocation(time+2);
                                        assertEquals(NTS.getNumberOfEvents(), 0);
        assertEquals(NTS.getEventTypes().size(),0);
        


}

}