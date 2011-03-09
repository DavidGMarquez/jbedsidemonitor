/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import signals.ReadResult;

/**
 *
 * @author USUARIO
 */
public class AlgorithmDefaultImplementationTest {
LinkedList<String> eventSignals;
LinkedList<String> timeSignals;
    public AlgorithmDefaultImplementationTest() {
    }

    @Before
    public void setUp() {
        eventSignals=new LinkedList<String>();
        timeSignals=new LinkedList<String>();
        eventSignals.add("EventSeries1");
        eventSignals.add("EventSeries2");
        eventSignals.add("EventSeries3");
        timeSignals.add("TimeSeries1");
        timeSignals.add("TimeSeries2");
        timeSignals.add("TimeSeries3");

    }

    @Test
    public void testCreate() {
        Algorithm algorithm1=new AlgorithmDefaultImplementation("algorithm1", "out_algorithm1", timeSignals, eventSignals);
        assertTrue(algorithm1.getIdentifier().equals("algorithm1"));
        assertTrue(algorithm1.getIdentifierSignalToWrite().endsWith("out_algorithm1"));
        assertTrue(algorithm1.getNotifyPolice().getNotifyPolice().equals(AlgorithmNotifyPoliceEnum.ALL));
        assertEquals(algorithm1.getNotifyPolice().getEventSeriesTheshold().get("EventSeries1").intValue(),10);
        assertEquals(algorithm1.getNotifyPolice().getEventSeriesTheshold().get("EventSeries2").intValue(),10);
        assertEquals(algorithm1.getNotifyPolice().getEventSeriesTheshold().get("EventSeries3").intValue(),10);
        assertEquals(algorithm1.getNotifyPolice().getTimeSeriesTheshold().get("TimeSeries1").intValue(),100);
        assertEquals(algorithm1.getNotifyPolice().getTimeSeriesTheshold().get("TimeSeries2").intValue(),100);
        assertEquals(algorithm1.getNotifyPolice().getTimeSeriesTheshold().get("TimeSeries3").intValue(),100);
    }

    @Test
    public void testExecute() {
        //@pendiente no se como probar exactamente esto....

    }

}