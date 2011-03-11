/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import signals.EventSeries;
import signals.LockManager;
import signals.SignalManager;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class BasicTest {

    public BasicTest() {
    }
    TimeSeries timeSeries1;
    TimeSeries timeSeries2;
    TimeSeries timeSeries3;
    EventSeries eventSeries1;
    EventSeries eventSeries2;
    EventSeries eventSeries3;
    AlgorithmDefaultImplementation algorithm1;
    AlgorithmDefaultImplementation algorithm2;
    AlgorithmDefaultImplementation algorithm3;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;
    LinkedList<String> eventSignals2;
    LinkedList<String> timeSignals2;
    LinkedList<String> eventSignals3;
    LinkedList<String> timeSignals3;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        AuxTestUtilities.reset();

        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        timeSeries2 = new TimeSeries("TimeSeries2", "Simulated", 1, 100, "mv");
        timeSeries3 = new TimeSeries("TimeSeries3", "Simulated", 1, 100, "mv");

        eventSeries1 = new EventSeries("EventSeries1", "Simulated", 1, new ArrayList<String>(), "mv");
        eventSeries2 = new EventSeries("EventSeries2", "Simulated", 1, new ArrayList<String>(), "mv");
        eventSeries3 = new EventSeries("EventSeries3", "Simulated", 1, new ArrayList<String>(), "mv");

        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        eventSignals1.add("EventSeries1");
        eventSignals1.add("EventSeries2");
        eventSignals1.add("EventSeries3");
        timeSignals1.add("TimeSeries1");
        timeSignals1.add("TimeSeries2");
        timeSignals1.add("TimeSeries3");

        eventSignals2 = new LinkedList<String>();
        timeSignals2 = new LinkedList<String>();
        eventSignals2.add("EventSeries1");
        eventSignals2.add("EventSeries2");
        timeSignals2.add("TimeSeries1");
        timeSignals2.add("TimeSeries2");

        eventSignals3 = new LinkedList<String>();
        timeSignals3 = new LinkedList<String>();
        eventSignals3.add("EventSeries1");
        timeSignals3.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        EventSeries eventSeriesOut2 = new EventSeries("Out_Algorithm_2", "Algorithm2", 0, new ArrayList<String>(), "NaN");
        TimeSeries timeSeriesOut3 = new TimeSeries("Out_Algorithm_3", "Algorithm3", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupidImplementation("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);
        algorithm2 = new AlgorithmStupidImplementation("Algorithm2", eventSeriesOut2, timeSignals2, eventSignals2);
        algorithm3 = new AlgorithmStupidImplementation("Algorithm3", timeSeriesOut3, timeSignals3, eventSignals3);


    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBasico() {
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        assertTrue(SignalManager.getInstance().getAllEventSeriesNames().size()==1);
        assertTrue(SignalManager.getInstance().getAllTimeSeriesNames().size()==1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        assertTrue(SignalManager.getInstance().getAllTimeSeriesNames().size()==2);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeries1", 1000);
        SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(BasicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testEjecucionAlgoritmoBasico() {
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm3);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeries1", 1000);
        SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSeries1", 100, 0, 100);
        SignalManager.getInstance().encueWriteOperation(writerRunnableEventSeries);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BasicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
