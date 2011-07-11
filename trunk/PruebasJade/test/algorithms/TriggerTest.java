/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import auxiliarTools.AuxTestUtilities;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import signals.Event;
import static org.junit.Assert.*;
import signals.ReaderCallable;
import signals.ReaderCallableEventSeries;
import signals.ReaderCallableMultiSignal;
import signals.ReaderCallableOneSignal;
import signals.ReaderCallableTimeSeries;
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
        eventSeriesHold.put("EventSerie1", 10);
        eventSeriesHold.put("EventSerie2", 120);
        eventSeriesHold.put("EventSerie3", 230);
        algorithmNotifyPoliceOnlyOne = new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ONE);
        algorithmNotifyPoliceComplete = new AlgorithmNotifyPolice(timeSeriesHold, eventSeriesHold, AlgorithmNotifyPoliceEnum.ALL);
    }

    @Test
    public void testCreate() {
        Trigger triggerOne = new Trigger("AlgorithmOne", algorithmNotifyPoliceOnlyOne);
        assertEquals(triggerOne.getIdentifierAlgorithm(), "AlgorithmOne");
        assertFalse(triggerOne.trigger());
        ReaderCallableMultiSignal readerCallableAndResetOne = triggerOne.getReaderCallableAndReset();
        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie1").getTheshold(), 10);
        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie2").getTheshold(), 120);
        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie4"), null);
        assertEquals(triggerOne.getEventSeriesTriggers().get(""), null);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie1").getTheshold(), 10);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie2").getTheshold(), 120);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie4"), null);
        assertEquals(triggerOne.getTimeSeriesTriggers().get(""), null);

        Trigger triggerComplete = new Trigger("AlgorithmComplete", algorithmNotifyPoliceComplete);
        assertEquals(triggerComplete.getIdentifierAlgorithm(), "AlgorithmComplete");
        assertFalse(triggerComplete.trigger());
        ReaderCallableMultiSignal readerCallableAndResetComplete = triggerComplete.getReaderCallableAndReset();

        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie1").getTheshold(), 10);
        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie2").getTheshold(), 120);
        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie4"), null);
        assertEquals(triggerOne.getEventSeriesTriggers().get(""), null);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie1").getTheshold(), 10);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie2").getTheshold(), 120);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie4"), null);
        assertEquals(triggerOne.getTimeSeriesTriggers().get(""), null);
    }

    @Test
    public void testNotifyNewData() {
        Trigger triggerOne = new Trigger("AlgorithmOne", algorithmNotifyPoliceOnlyOne);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 33, 100, 100);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 33);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        triggerOne.notifyNewData(writerRunnableEventSeries);
        assertTrue(triggerOne.trigger());
        triggerOne.notifyNewData(writerRunnableTimeSeries);
        assertTrue(triggerOne.trigger());
        assertEquals(triggerOne.getEventSeriesTriggers().get("EventSerie1").getEventsAlreadyWrittenCopy().size(), 33);
        assertEquals(triggerOne.getTimeSeriesTriggers().get("TimeSerie1").getNewData(), 33);
        Trigger triggerComplete = new Trigger("AlgorithmComplete", algorithmNotifyPoliceComplete);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        triggerComplete.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerComplete.trigger());
        triggerComplete.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerComplete.trigger());
        assertEquals(triggerComplete.getEventSeriesTriggers().get("EventSerie1").getEventsAlreadyWrittenCopy().size(), 33);
        assertEquals(triggerComplete.getTimeSeriesTriggers().get("TimeSerie1").getNewData(), 33);
    }

    @Test
    public void testTrigger() {
        Trigger triggerOneA = new Trigger("AlgorithmOneA", algorithmNotifyPoliceOnlyOne);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 33, 100, 100);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 33);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        assertFalse(triggerOneA.trigger());
        triggerOneA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerOneA.trigger());
        triggerOneA.notifyNewData(writerRunnableTimeSeries);

        assertTrue(triggerOneA.trigger());
        triggerOneA.getReaderCallableAndReset();
        assertFalse(triggerOneA.trigger());
        triggerOneA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerOneA.trigger());
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(66);
        triggerOneA.notifyNewData(writerRunnableTimeSeries);

        assertTrue(triggerOneA.trigger());



        Trigger triggerOneB = new Trigger("AlgorithmOneB", algorithmNotifyPoliceOnlyOne);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 33, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 33);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        assertFalse(triggerOneB.trigger());
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerOneB.trigger());
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerOneB.trigger());
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 115, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 115);
        assertFalse(triggerOneB.trigger());
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerOneB.trigger());
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(115);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerOneB.trigger());
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerOneB.trigger());
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(230);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerOneB.trigger());
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        assertTrue(triggerOneB.trigger());
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(375);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        assertTrue(triggerOneB.trigger());


        Trigger triggerCompleteA = new Trigger("AlgorithmCompleteA", algorithmNotifyPoliceComplete);
        assertFalse(triggerCompleteA.trigger());
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 33, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 33);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerCompleteA.trigger());
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 330, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 330);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(330);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerCompleteA.trigger());
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 330, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 330);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(363);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerCompleteA.trigger());
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 230, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 230);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(230);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerCompleteA.trigger());
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 1, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 1);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(231);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertTrue(triggerCompleteA.trigger());

        triggerCompleteA.getReaderCallableAndReset();

        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 55, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 35);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(398);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 323, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 313);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(643);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 200, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 343);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(574);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertFalse(triggerCompleteA.trigger());
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        assertTrue(triggerCompleteA.trigger());


    }

    @Test
    public void testGetReaderCallableAndReset() {
        Trigger triggerOneA = new Trigger("AlgorithmOneA", algorithmNotifyPoliceOnlyOne);
        WriterRunnableEventSeries writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 100, 100, 100);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 33);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        triggerOneA.notifyNewData(writerRunnableEventSeries);
        triggerOneA.notifyNewData(writerRunnableTimeSeries);
        assertTrue(triggerOneA.trigger());
        ReaderCallableMultiSignal readerCallableA = triggerOneA.getReaderCallableAndReset();
        HashMap<String, ReaderCallableOneSignal> expectedResults = new HashMap<String, ReaderCallableOneSignal>();
        expectedResults.put("TimeSerie1", new ReaderCallableTimeSeries("TimeSerie1", "AlgorithmOneA", 0, 34));
        LinkedList<ReaderCallableOneSignal> readerCallables = readerCallableA.getReaderCallables();
        assertFalse(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));
        expectedResults.put("TimeSerie1", new ReaderCallableTimeSeries("TimeSerie1", "AlgorithmOneA", 0, 33));
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));
        triggerOneA.notifyNewData(writerRunnableEventSeries);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(66);
        triggerOneA.notifyNewData(writerRunnableTimeSeries);
        readerCallableA = triggerOneA.getReaderCallableAndReset();
        readerCallables = readerCallableA.getReaderCallables();
        expectedResults.put("TimeSerie1", new ReaderCallableTimeSeries("TimeSerie1", "AlgorithmOneA", 33, 33));
        assertFalse(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));
        expectedResults.put("EventSerie2", new ReaderCallableEventSeries("EventSerie2", "AlgorithmOneA", writerRunnableEventSeries.getEventsToWrite(), new LinkedList<Event>()));
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));

        expectedResults = new HashMap<String, ReaderCallableOneSignal>();

        Trigger triggerOneB = new Trigger("AlgorithmOneB", algorithmNotifyPoliceOnlyOne);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 33, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 33);
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);

        ReaderCallableMultiSignal readerCallableB = triggerOneB.getReaderCallableAndReset();
        readerCallables = readerCallableB.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));

        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 115, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 115);
        readerCallableB = triggerOneB.getReaderCallableAndReset();
        readerCallables = readerCallableB.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));



        triggerOneB.notifyNewData(writerRunnableEventSeries);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(115);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(230);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        expectedResults.put("EventSerie3", new ReaderCallableEventSeries("EventSerie3", "AlgorithmOneB",
                writerRunnableEventSeries.getEventsToWrite(), writerRunnableEventSeries.getEventsToDelete()));
        readerCallableB = triggerOneB.getReaderCallableAndReset();
        readerCallables = readerCallableB.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));
        expectedResults.remove("EventSerie3");
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(345);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        expectedResults.put("TimeSerie3", new ReaderCallableTimeSeries("TimeSerie3", "AlgorithmOneB", 0, 345));
        readerCallableB = triggerOneB.getReaderCallableAndReset();
        readerCallables = readerCallableB.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));

        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 1000, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 333);
        expectedResults.put("EventSerie3", new ReaderCallableEventSeries("EventSerie3", "AlgorithmOneB", writerRunnableEventSeries.getEventsToWrite(), writerRunnableEventSeries.getEventsToDelete()));
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(678);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 10, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 3);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(36);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 110, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 120);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(120);
        triggerOneB.notifyNewData(writerRunnableTimeSeries);
        triggerOneB.notifyNewData(writerRunnableEventSeries);
        expectedResults.put("EventSerie1", new ReaderCallableEventSeries("EventSerie1", "AlgorithmOneB", writerRunnableEventSeries.getEventsToWrite(), writerRunnableEventSeries.getEventsToDelete()));
        expectedResults.put("TimeSerie1", new ReaderCallableTimeSeries("TimeSerie1", "AlgorithmOneB", 0, 120));
        expectedResults.put("TimeSerie3", new ReaderCallableTimeSeries("TimeSerie3", "AlgorithmOneB", 345, 333));
        readerCallableB = triggerOneB.getReaderCallableAndReset();
        readerCallables = readerCallableB.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));

        expectedResults = new HashMap<String, ReaderCallableOneSignal>();

        Trigger triggerCompleteA = new Trigger("AlgorithmCompleteA", algorithmNotifyPoliceComplete);
        ReaderCallableMultiSignal readerCallableCompleteA = triggerCompleteA.getReaderCallableAndReset();
        readerCallables = readerCallableCompleteA.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 33, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 33);
        LinkedList<Event> auxiliarEvents1Deleted = new LinkedList<Event>(writerRunnableEventSeries.getEventsToDelete());
        LinkedList<Event> auxiliarEvents1Written = new LinkedList<Event>(writerRunnableEventSeries.getEventsToWrite());
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(33);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 330, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 330);
        expectedResults.put("EventSerie2", new ReaderCallableEventSeries("EventSerie2", "AlgorithmCompleteA", writerRunnableEventSeries.getEventsToWrite(), writerRunnableEventSeries.getEventsToDelete()));
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(330);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 330, 100, 100);
        auxiliarEvents1Deleted.addAll(writerRunnableEventSeries.getEventsToDelete());
        auxiliarEvents1Written.addAll(writerRunnableEventSeries.getEventsToWrite());
        expectedResults.put("EventSerie1", new ReaderCallableEventSeries("EventSerie1", "AlgorithmCompleteA", auxiliarEvents1Written, auxiliarEvents1Deleted));

        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 330);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(363);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 230, 100, 100);
        LinkedList<Event> auxiliarEvents3Deleted = new LinkedList<Event>(writerRunnableEventSeries.getEventsToDelete());
        LinkedList<Event> auxiliarEvents3Written = new LinkedList<Event>(writerRunnableEventSeries.getEventsToWrite());
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 230);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(230);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 1, 100, 100);
        auxiliarEvents3Deleted.addAll(writerRunnableEventSeries.getEventsToDelete());
        auxiliarEvents3Written.addAll(writerRunnableEventSeries.getEventsToWrite());
        expectedResults.put("EventSerie3", new ReaderCallableEventSeries("EventSerie3", "AlgorithmCompleteA", auxiliarEvents3Written, auxiliarEvents3Deleted));

        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 1);
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(231);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);

        expectedResults.put("TimeSerie1", new ReaderCallableTimeSeries("TimeSerie1", "AlgorithmCompleteA", 0, 363));
        expectedResults.put("TimeSerie2", new ReaderCallableTimeSeries("TimeSerie2", "AlgorithmCompleteA", 0, 330));
        expectedResults.put("TimeSerie3", new ReaderCallableTimeSeries("TimeSerie3", "AlgorithmCompleteA", 0, 231));
        readerCallableCompleteA = triggerCompleteA.getReaderCallableAndReset();
        readerCallables = readerCallableCompleteA.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));

        LinkedList<Event> auxiliarEvents2Deleted = new LinkedList<Event>();
        LinkedList<Event> auxiliarEvents2Written = new LinkedList<Event>();

        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie1", 55, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie1", 35);
        auxiliarEvents1Deleted.clear();
        auxiliarEvents1Written.clear();
        auxiliarEvents1Deleted.addAll(writerRunnableEventSeries.getEventsToDelete());
        auxiliarEvents1Written.addAll(writerRunnableEventSeries.getEventsToWrite());

        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(398);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie2", 323, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie2", 313);
        auxiliarEvents2Deleted.clear();
        auxiliarEvents2Written.clear();
        auxiliarEvents2Deleted.addAll(writerRunnableEventSeries.getEventsToDelete());
        auxiliarEvents2Written.addAll(writerRunnableEventSeries.getEventsToWrite());

        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(643);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        writerRunnableEventSeries = AuxTestUtilities.generarWriterRunnableEvents("EventSerie3", 555, 100, 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSerie3", 343);
        auxiliarEvents3Deleted.clear();
        auxiliarEvents3Written.clear();
        auxiliarEvents3Deleted.addAll(writerRunnableEventSeries.getEventsToDelete());
        auxiliarEvents3Written.addAll(writerRunnableEventSeries.getEventsToWrite());
        writerRunnableTimeSeries.setOlderSampleAvailable(0);
        writerRunnableTimeSeries.setSamplesToReadInOrder(574);
        triggerCompleteA.notifyNewData(writerRunnableTimeSeries);
        triggerCompleteA.notifyNewData(writerRunnableEventSeries);
        expectedResults.put("EventSerie1", new ReaderCallableEventSeries("EventSerie1", "AlgorithmCompleteA", auxiliarEvents1Written, auxiliarEvents1Deleted));
        expectedResults.put("TimeSerie1", new ReaderCallableTimeSeries("TimeSerie1", "AlgorithmCompleteA", 363, 35));
        expectedResults.put("EventSerie2", new ReaderCallableEventSeries("EventSerie2", "AlgorithmCompleteA", auxiliarEvents2Written, auxiliarEvents2Deleted));
        expectedResults.put("TimeSerie2", new ReaderCallableTimeSeries("TimeSerie2", "AlgorithmCompleteA", 330, 313));
        expectedResults.put("EventSerie3", new ReaderCallableEventSeries("EventSerie3", "AlgorithmCompleteA", auxiliarEvents3Written, auxiliarEvents3Deleted));
        expectedResults.put("TimeSerie3", new ReaderCallableTimeSeries("TimeSerie3", "AlgorithmCompleteA", 231, 343));
        readerCallableCompleteA = triggerCompleteA.getReaderCallableAndReset();
        readerCallables = readerCallableCompleteA.getReaderCallables();
        assertTrue(AuxTestUtilities.compareReaderCallables(readerCallables, expectedResults));
    }
}
