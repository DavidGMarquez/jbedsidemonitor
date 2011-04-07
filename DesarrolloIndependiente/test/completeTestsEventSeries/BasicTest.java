/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsEventSeries;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Event;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import completeTestsTimeSeries.AlgorithmStupid2XMultiSignalsImplementationOrder;
import completeTestsTimeSeries.CompleteTestOrder;
import completeTestsTimeSeries.SerialTimeSeriesSeriesGeneratorOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import signals.EventSeries;
import signals.SignalManager;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;
import static org.junit.Assert.*;

public class BasicTest {

    public BasicTest() {
    }
    EventSeries eventSeries1;
    TimeSeries timeSeries1_out;
    AlgorithmDefaultImplementation algorithm1;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;
    int iterations=10;
    int sizeOfIterations=10;
    @Before
    public void setUp() {
        AuxTestUtilities.reset();
        eventSeries1 = new EventSeries("EventSeries1", "Simulated", 0, new ArrayList<String>(), "NaN");
        timeSeries1_out = new TimeSeries("TimeSeries1_Algorithm1", "Simulated", 1, 100, "mv");
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();

        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupid2XMultiSignalsImplementationOrder("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);



    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSerialEventGenerator() {
        AuxTestUtilities.reset();
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries1_out);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        SerialEventSeriesSeriesGenerator serialEventSeriesSeriesGenerator = new SerialEventSeriesSeriesGenerator(10, 10, iterations, "EventSeries1", sizeOfIterations);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompleteTestOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        SortedSet<Event> events = SignalManager.getInstance().getEventsCopy("EventSeries1");
        assertEquals(events.size(),(iterations)*sizeOfIterations);
        System.out.println("Tamano"+events.size());
        Iterator<Event> iterator = events.iterator();
        int index=0;
        while(iterator.hasNext()){
            Event next = iterator.next();
            assertEquals(next, new Event(index, "Originated bySerialEventSeriesGenerator", null));
            assertEquals(next.getLocation(),index);
            assertEquals(next.getType(),"Originated bySerialEventSeriesGenerator");
            index++;
        }
    }

}
