/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import auxiliarTools.AuxTestUtilities;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import signals.EventSeries;
import signals.TimeSeries;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class AlgorithmManagerTest {

    LinkedList<String> eventSignalsA;
    LinkedList<String> timeSignalsA;
    LinkedList<String> eventSignalsB;
    LinkedList<String> timeSignalsB;

    public AlgorithmManagerTest() {
    }

    @Before
    public void setUp() {
        //@duda quizas algun método para reiniciar los Singlenton?
        eventSignalsA = new LinkedList<String>();
        timeSignalsA = new LinkedList<String>();
        eventSignalsA.add("EventSeries1");
        eventSignalsA.add("EventSeries2");
        eventSignalsA.add("EventSeries3");
        timeSignalsA.add("TimeSeries1");
        timeSignalsA.add("TimeSeries2");
        timeSignalsA.add("TimeSeries3");
        eventSignalsB = new LinkedList<String>();
        timeSignalsB = new LinkedList<String>();
        eventSignalsB.add("EventSeries2");
        eventSignalsB.add("EventSeries3");
        eventSignalsB.add("EventSeries4");
        timeSignalsB.add("TimeSeries2");
        timeSignalsB.add("TimeSeries3");
        timeSignalsB.add("TimeSeries4");

    }

    @After
    public void tearDown() {
        AuxTestUtilities.reset();
    }

    @Test
    public void testInstance() {
        AlgorithmManager instance1 = AlgorithmManager.getInstance();
        AlgorithmManager instance2 = AlgorithmManager.getInstance();
        AlgorithmManager instance3 = AlgorithmManager.getInstance();
        assertEquals(instance1, instance2);
        assertEquals(instance3, AlgorithmManager.getInstance());
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm_1", 0, 300, "NaN");
        instance1.addAlgorithm(new AlgorithmDefaultImplementation("Algorithm_1", timeSeriesOut1, timeSignalsA, eventSignalsA));
        assertEquals(instance1, AlgorithmManager.getInstance());

    }

    @Test
    public void testAddAlgorithm() {

        AuxTestUtilities.reset();
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm_1", 0, 300, "NaN");
        Algorithm algorithm1 = new AlgorithmDefaultImplementation("Algorithm_1", timeSeriesOut1, timeSignalsA, eventSignalsA);
        AlgorithmManager instance = AlgorithmManager.getInstance();
        assertEquals(instance.getAllAlgorithmNames().size(), 0);
        instance.addAlgorithm(algorithm1);
        assertEquals(instance.getAllAlgorithmNames().size(), 1);
        assertEquals(algorithm1, instance.getAlgorithm("Algorithm_1"));
        Trigger triggerAlgorithm1 = instance.getTrigger("Algorithm_1");
        assertEquals(triggerAlgorithm1.getIdentifierAlgorithm(), "Algorithm_1");
        assertTrue(triggerAlgorithm1.getEventSeriesTriggers().get("EventSeries1").getTheshold() == 10);
        assertTrue(triggerAlgorithm1.getEventSeriesTriggers().get("EventSeries2").getTheshold() == 10);
        assertTrue(triggerAlgorithm1.getEventSeriesTriggers().get("EventSeries3").getTheshold() == 10);
        assertTrue(triggerAlgorithm1.getTimeSeriesTriggers().get("TimeSeries1").getTheshold() == 100);
        assertTrue(triggerAlgorithm1.getTimeSeriesTriggers().get("TimeSeries2").getTheshold() == 100);
        assertTrue(triggerAlgorithm1.getTimeSeriesTriggers().get("TimeSeries3").getTheshold() == 100);
        Set<String> eventSeriesNames = triggerAlgorithm1.getEventSeriesTriggers().keySet();
        assertTrue(eventSeriesNames.size() == 3);
        eventSeriesNames.removeAll(eventSignalsA);
        assertTrue(eventSeriesNames.isEmpty());
        Set<String> timeSeriesNames = triggerAlgorithm1.getTimeSeriesTriggers().keySet();
        assertTrue(timeSeriesNames.size() == 3);
        timeSeriesNames.removeAll(timeSignalsA);
        assertTrue(timeSeriesNames.isEmpty());
        LinkedList<String> algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("EventSeries1");
        assertTrue(algorithmNameBySignalName.size() == 1);
        algorithmNameBySignalName.remove("Algorithm_1");
        assertTrue(algorithmNameBySignalName.isEmpty());
        algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("EventSeries2");
        algorithmNameBySignalName.remove("Algorithm_1");
        assertTrue(algorithmNameBySignalName.isEmpty());
        algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("EventSeries3");
        algorithmNameBySignalName.remove("Algorithm_1");
        assertTrue(algorithmNameBySignalName.isEmpty());
        algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("TimeSeries1");
        algorithmNameBySignalName.remove("Algorithm_1");
        assertTrue(algorithmNameBySignalName.isEmpty());
        algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("TimeSeries2");
        algorithmNameBySignalName.remove("Algorithm_1");
        assertTrue(algorithmNameBySignalName.isEmpty());
        algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("TimeSeries3");
        algorithmNameBySignalName.remove("Algorithm_1");
        assertTrue(algorithmNameBySignalName.isEmpty());


    }

    @Test
    public void testAddMultiAlgorithm() {
        AuxTestUtilities.reset();
        AlgorithmManager instance = AlgorithmManager.getInstance();
        assertEquals(instance.getAllAlgorithmNames().size(), 0);
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm_1", 0, 300, "NaN");
        EventSeries eventSeriesOut2 = new EventSeries("Out_Algorithm_2", "Algorithm_2", 0, new ArrayList<String>(), "NaN");
        TimeSeries timeSeriesOut3 = new TimeSeries("Out_Algorithm_3", "Algorithm_3", 0, 300, "NaN");
        Algorithm algorithm1 = new AlgorithmDefaultImplementation("Algorithm_1", timeSeriesOut1, timeSignalsA, eventSignalsA);
        Algorithm algorithm2 = new AlgorithmDefaultImplementation("Algorithm_2", eventSeriesOut2, timeSignalsA, eventSignalsA);
        Algorithm algorithm3 = new AlgorithmDefaultImplementation("Algorithm_3", timeSeriesOut3, timeSignalsB, eventSignalsB);

        instance.addAlgorithm(algorithm1);
        instance.addAlgorithm(algorithm2);
        instance.addAlgorithm(algorithm3);
        instance.addAlgorithm(algorithm3);
        assertEquals(instance.getAllAlgorithmNames().size(), 3);
        assertEquals(instance.getAlgorithm("Algorithm_1"), algorithm1);
        assertTrue(instance.getAlgorithm("Algorithm_2").getSignalToWrite().getIdentifier().equals("Out_Algorithm_2"));
        assertTrue(instance.getAlgorithmNamesToSignal("EventSeries1").size() == 2);
        LinkedList<String> algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("EventSeries1");
        algorithmNameBySignalName.remove("Algorithm_1");
        algorithmNameBySignalName.remove("Algorithm_2");
        assertTrue(algorithmNameBySignalName.isEmpty());
        assertTrue(instance.getAlgorithmNamesToSignal("TimeSeries2").size() == 3);
        algorithmNameBySignalName = instance.getAlgorithmNamesToSignal("TimeSeries2");
        algorithmNameBySignalName.remove("Algorithm_1");
        algorithmNameBySignalName.remove("Algorithm_2");
        algorithmNameBySignalName.remove("Algorithm_3");
        assertTrue(algorithmNameBySignalName.isEmpty());


    }
}
