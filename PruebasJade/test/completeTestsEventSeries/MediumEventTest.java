/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTestsEventSeries;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmDefaultImplementationOneSignal;
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

public class MediumEventTest {
    /*Partimos de un generador en serio. El primer algoritmo saca los eventos en
     * doble tiempo, el segundo algoritmo coje esos eventos y solo saca los que no son
     * multiplos de 3 y ademas de la señal que le llega borra todos los múltiplos de 2
     */

    public MediumEventTest() {
    }
    EventSeries eventSeries1;
    EventSeries eventSeries2;
    EventSeries eventSeries3;
    EventSeries eventSeries1_out;
    EventSeries eventSeries1_outB;
    EventSeries eventSeries1_out_out;
    EventSeries eventSeries1_out_out_out;
    EventSeries eventSeries2_out;
    EventSeries eventSeries3_out;
    AlgorithmDefaultImplementationOneSignal algorithm1;
    AlgorithmDefaultImplementationOneSignal algorithm2;
    AlgorithmDefaultImplementationOneSignal algorithm3;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;
    LinkedList<String> eventSignals2;
    LinkedList<String> timeSignals2;
    LinkedList<String> eventSignals3;
    LinkedList<String> timeSignals3;
    int iterations = 100;
    int sizeOfIterations = 10;

    @Before
    public void setUp() {
        AuxTestUtilities.reset();
        eventSeries1 = new EventSeries("EventSeries1", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries2 = new EventSeries("EventSeries2", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries3 = new EventSeries("EventSeries3", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries1_out = new EventSeries("EventSeries1_Algorithm1", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries1_outB = new EventSeries("EventSeries1_Algorithm3", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries2_out = new EventSeries("EventSeries2_Algorithm2", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries3_out = new EventSeries("EventSeries3_Algorithm3", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries1_out_out = new EventSeries("EventSeries1_Algorithm1_Algorithm2", "Simulated", 0, new ArrayList<String>(), "NaN");
        eventSeries1_out_out_out = new EventSeries("EventSeries1_Algorithm1_Algorithm2_Algorithm3", "Simulated", 0, new ArrayList<String>(), "NaN");

        eventSignals1 = new LinkedList<String>();
        eventSignals1.add("EventSeries1");
        timeSignals1 = new LinkedList<String>();
        eventSignals2 = new LinkedList<String>();
        eventSignals2.add("EventSeries1_Algorithm1");
        timeSignals2 = new LinkedList<String>();
        eventSignals3 = new LinkedList<String>();
        eventSignals3.add("EventSeries1");
        eventSignals3.add("EventSeries1_Algorithm1_Algorithm2");
        timeSignals3 = new LinkedList<String>();
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupid2XMultiSignalsEventImplementation("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);
        TimeSeries timeSeriesOut2 = new TimeSeries("Out_Algorithm_2", "Algorithm2", 0, 300, "NaN");
        algorithm2 = new AlgorithmStupidMul3MinusDel2MulPreMultiSignalsEventImplementation("Algorithm2", timeSeriesOut2, timeSignals2, eventSignals2);
        TimeSeries timeSeriesOut3 = new TimeSeries("Out_Algorithm_3", "Algorithm3", 0, 300, "NaN");
        algorithm3 = new AlgorithmStupid2XMultiSignalsEventImplementation("Algorithm3", timeSeriesOut3, timeSignals3, eventSignals3);


    }

    @After
    public void tearDown() {
    }

    @Test
    public void test2Algorithm() {

        AuxTestUtilities.reset();
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addEventSeries(eventSeries1_out);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm2);
        SignalManager.getInstance().addEventSeries(eventSeries1_out_out);
        SerialEventSeriesGenerator serialEventSeriesSeriesGenerator = new SerialEventSeriesGenerator(10, 10, iterations, "EventSeries1", sizeOfIterations);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompleteTestOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        SortedSet<Event> events = SignalManager.getInstance().getEventsCopy("EventSeries1");
        assertEquals(events.size(), (iterations) * sizeOfIterations);
        System.out.println("Tamano" + events.size());
        int index = 0;
        for (Event currentEvent : events) {
            assertEquals(currentEvent, new Event(index, "Originated bySerialEventSeriesGenerator", null));
            assertEquals(currentEvent.getLocation(), index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            index++;
        }

        events = SignalManager.getInstance().getEventsCopy("EventSeries1_Algorithm1");
        assertEquals(events.size(), (iterations) * sizeOfIterations / 2);
        System.out.println("Tamano" + events.size());
        index = 0;
        for (Event currentEvent : events) {
            index++;
            assertEquals(currentEvent, new Event(2 * index, "Originated bySerialEventSeriesGenerator", null));
            assertEquals(currentEvent.getLocation(), 2 * index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            index++;
        }

        events = SignalManager.getInstance().getEventsCopy("EventSeries1_Algorithm1_Algorithm2");
        assertEquals(events.size(), Math.ceil((iterations) * sizeOfIterations * 2 / 3), 0.0001);
        System.out.println("Tamano" + events.size());
        index = 0;
        for (Event currentEvent : events) {
            while ((index % 3 == 0)) {
                index++;
            }
            assertEquals(currentEvent.getLocation(), 2 * index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            assertEquals(currentEvent, new Event(2 * index, "Originated bySerialEventSeriesGenerator", null));
            index++;

        }

    }

    @Test
    public void test2Signals() {

        AuxTestUtilities.reset();
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addEventSeries(eventSeries1_out);
        SignalManager.getInstance().addEventSeries(eventSeries1_outB);
        SignalManager.getInstance().addEventSeries(eventSeries1_out_out);
        SignalManager.getInstance().addEventSeries(eventSeries1_out_out_out);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm2);
        AlgorithmManager.getInstance().addAlgorithm(algorithm3);
        SerialEventSeriesGenerator serialEventSeriesSeriesGenerator = new SerialEventSeriesGenerator(10, 10, iterations, "EventSeries1", sizeOfIterations);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompleteTestOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        SortedSet<Event> events = SignalManager.getInstance().getEventsCopy("EventSeries1");
        assertEquals(events.size(), (iterations) * sizeOfIterations);
        System.out.println("Tamano" + events.size());
        int index = 0;
        for (Event currentEvent : events) {
            assertEquals(currentEvent, new Event(index, "Originated bySerialEventSeriesGenerator", null));
            assertEquals(currentEvent.getLocation(), index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            index++;
        }

        events = SignalManager.getInstance().getEventsCopy("EventSeries1_Algorithm1");
        assertEquals(events.size(), (iterations) * sizeOfIterations / 2);
        System.out.println("Tamano" + events.size());
        index = 0;
        for (Event currentEvent : events) {
            index++;
            assertEquals(currentEvent, new Event(2 * index, "Originated bySerialEventSeriesGenerator", null));
            assertEquals(currentEvent.getLocation(), 2 * index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            index++;
        }

        events = SignalManager.getInstance().getEventsCopy("EventSeries1_Algorithm1_Algorithm2");
        assertEquals(events.size(), Math.ceil((iterations) * sizeOfIterations * 2 / 3), 0.0001);
        System.out.println("Tamano" + events.size());
        index = 0;
        for (Event currentEvent : events) {
            while ((index % 3 == 0)) {
                index++;
            }
            assertEquals(currentEvent.getLocation(), 2 * index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            assertEquals(currentEvent, new Event(2 * index, "Originated bySerialEventSeriesGenerator", null));
            index++;

        }
        events = SignalManager.getInstance().getEventsCopy("EventSeries1_Algorithm3");
        assertEquals(events.size(), (iterations) * sizeOfIterations);
        System.out.println("Tamano" + events.size());
        index = 0;
        for (Event currentEvent : events) {
            assertEquals(currentEvent, new Event(2 * index, "Originated bySerialEventSeriesGenerator", null));
            assertEquals(currentEvent.getLocation(), 2 * index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            index++;
        }
        events = SignalManager.getInstance().getEventsCopy("EventSeries1_Algorithm1_Algorithm2_Algorithm3");
        //@pendiente aqui suele fallar
        assertEquals(events.size(), Math.ceil((iterations) * sizeOfIterations * 2 / 3), 0.0001);
        System.out.println("Tamano" + events.size());
        index = 0;
        for (Event currentEvent : events) {
            while ((index % 3 == 0)) {
                index++;
            }
            assertEquals(currentEvent.getLocation(), 2*2 * index);
            assertEquals(currentEvent.getType(), "Originated bySerialEventSeriesGenerator");
            assertEquals(currentEvent, new Event(2*2 * index, "Originated bySerialEventSeriesGenerator", null));
            index++;

        }
    }
}
