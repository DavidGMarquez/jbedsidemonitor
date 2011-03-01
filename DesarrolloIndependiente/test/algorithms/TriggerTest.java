/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import signals.ReaderCallable;
import signals.ReaderCallableMultiSignal;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class TriggerTest {
  AlgorithmNotifyPolice algorithmNotifyPoliceComplete;
        AlgorithmNotifyPolice algorithmNotifyPoliceOnlyOne;
    public TriggerTest() {
      
    }


    @Before
    public void setUp() {
      HashMap<String, Integer> timeSeriesHold = new HashMap<String, Integer>();
    HashMap<String, Integer> eventSeriesHold = new HashMap<String, Integer>();
        timeSeriesHold.put("TimeSerie1", 10);
        timeSeriesHold.put("TimeSerie2", 120);
        timeSeriesHold.put("TimeSerie3", 230);
        eventSeriesHold.put("EventSerie1",10);
        eventSeriesHold.put("EventSerie2",120);
        eventSeriesHold.put("EventSerie3",230);
       algorithmNotifyPoliceOnlyOne= new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ONE);
       algorithmNotifyPoliceComplete=new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ALL);
    }


    @Test
    public void testCreate() {
        Trigger triggerOne = new Trigger("AlgorithmOne",algorithmNotifyPoliceOnlyOne);
        assertEquals(triggerOne.getIdentifierAlgorithm(),"AlgorithmOne");
        assertFalse(triggerOne.trigger());
        ReaderCallableMultiSignal readerCallableAndReset = triggerOne.getReaderCallableAndReset();

        Trigger triggerComplete = new Trigger("AlgorithmComplete",algorithmNotifyPoliceComplete);
        assertEquals(triggerComplete.getIdentifierAlgorithm(),"AlgorithmComplete");
        assertFalse(triggerComplete.trigger());


    }

    /**
     * Test of notifyNewData method, of class Trigger.
     */
    @Test
    public void testNotifyNewData_WriterRunnableTimeSeries() {
        System.out.println("notifyNewData");
        WriterRunnableTimeSeries writerRunnableTimeSeries = null;
        Trigger instance = null;
        instance.notifyNewData(writerRunnableTimeSeries);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trigger method, of class Trigger.
     */
    @Test
    public void testTrigger() {
        System.out.println("trigger");
        Trigger instance = null;
        boolean expResult = false;
        boolean result = instance.trigger();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReaderCallableAndReset method, of class Trigger.
     */
    @Test
    public void testGetReaderCallableAndReset() {
        System.out.println("getReaderCallableAndReset");
        Trigger instance = null;
        ReaderCallable expResult = null;
        ReaderCallable result = instance.getReaderCallableAndReset();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdentifierAlgorithm method, of class Trigger.
     */
    @Test
    public void testGetIdentifierAlgorithm() {
        System.out.println("getIdentifierAlgorithm");
        Trigger instance = null;
        String expResult = "";
        String result = instance.getIdentifierAlgorithm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}