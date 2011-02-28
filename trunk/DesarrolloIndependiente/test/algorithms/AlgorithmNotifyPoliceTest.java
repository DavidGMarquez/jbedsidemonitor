/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import auxiliarTools.AuxTestUtilities;
import java.util.HashMap;
import java.util.Map;
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
public class AlgorithmNotifyPoliceTest {

    HashMap<String, Integer> timeSeriesHold = new HashMap<String, Integer>();
    HashMap<String, Integer> eventSeriesHold = new HashMap<String, Integer>();

    public AlgorithmNotifyPoliceTest() {
    }

    @Before
    public void setUp() {
        timeSeriesHold = new HashMap<String, Integer>();
        eventSeriesHold = new HashMap<String, Integer>();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreationSimple() {
        AlgorithmNotifyPolice algorithmNotifyPolice = new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ALL);
    }

    @Test
    public void testCreationComplete() {
        timeSeriesHold.put("Serie3", new Integer(3));
        timeSeriesHold.put("Serie4", new Integer(4));
        timeSeriesHold.put("Serie1", new Integer(1));
        timeSeriesHold.put("Serie2", new Integer(2));
        eventSeriesHold.put("Serie3", new Integer(3));
        eventSeriesHold.put("Serie4", new Integer(4));
        eventSeriesHold.put("Serie1", new Integer(1));
        eventSeriesHold.put("Serie2", new Integer(2));



        AlgorithmNotifyPolice algorithmNotifyPolice = new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ALL);




        //@duda debería de exigir esto? es decir que haga copia de todo lo que se le paasa por defecto?
        assertEquals(algorithmNotifyPolice.getTimeSeriesTheshold().get("Serie4").intValue(), 4);
        assertEquals(algorithmNotifyPolice.getTimeSeriesTheshold().get("Serie3").intValue(), 3);
        assertEquals(algorithmNotifyPolice.getTimeSeriesTheshold().get("Serie2").intValue(), 2);
        assertEquals(algorithmNotifyPolice.getTimeSeriesTheshold().get("Serie1").intValue(), 1);
        assertTrue(algorithmNotifyPolice.getTimeSeriesTheshold().get("Serie5") == null);
        assertEquals(algorithmNotifyPolice.getEventSeriesTheshold().get("Serie4").intValue(), 4);
        assertEquals(algorithmNotifyPolice.getEventSeriesTheshold().get("Serie3").intValue(), 3);
        assertEquals(algorithmNotifyPolice.getEventSeriesTheshold().get("Serie2").intValue(), 2);
        assertEquals(algorithmNotifyPolice.getEventSeriesTheshold().get("Serie1").intValue(), 1);
        assertTrue(algorithmNotifyPolice.getEventSeriesTheshold().get("Serie5") == null);
        assertTrue(algorithmNotifyPolice.getNotifyPolice() == AlgorithmNotifyPoliceEnum.ALL);
        eventSeriesHold.clear();
        timeSeriesHold.clear();
        int iterations = 100;
        int[] generateArrayInteger = AuxTestUtilities.generateArrayInteger(iterations);
        for (int i = 0; i < iterations; i++) {
            eventSeriesHold.put(("AleatorioEvento" + new Integer(i).toString()), generateArrayInteger[i]);
            timeSeriesHold.put(("AleatorioTiempo" + new Integer(i).toString()), generateArrayInteger[i]);
        }

        AlgorithmNotifyPolice algorithmNotifyPoliceComplete = new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ONE);
        eventSeriesHold.clear();
        timeSeriesHold.clear();
        for (int i = 0; i < iterations; i++) {
            assertEquals(algorithmNotifyPoliceComplete.getEventSeriesTheshold().get("AleatorioEvento" + new Integer(i).toString()).intValue(), generateArrayInteger[i]);
            assertEquals(algorithmNotifyPoliceComplete.getTimeSeriesTheshold().get("AleatorioTiempo" + new Integer(i).toString()).intValue(), generateArrayInteger[i]);
        }
        assertTrue(algorithmNotifyPoliceComplete.getNotifyPolice() == AlgorithmNotifyPoliceEnum.ONE);
        assertTrue(algorithmNotifyPoliceComplete.getEventSeriesTheshold().get("Serie1") == null);
        assertTrue(algorithmNotifyPoliceComplete.getTimeSeriesTheshold().get("Serie1") == null);




    }
}
