package signals;

import completeTestsTimeSeries.AlgorithmStupidImplementation;
import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesterReaderService {

    public TesterReaderService() {
    }

    @Test
    public void testReaderTimeSeries() {
        SignalManager.getInstance().start();
        AuxTestUtilities.reset();
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        AlgorithmDefaultImplementation algorithm1;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("Signal 1");
        timeSignals1.add("Signal 2");
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupidImplementation("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        assertFalse(signalManager == null);
        signalManager.addTimeSeries(new TimeSeries("Signal 1", "Simulated", 1, 100, "mv"));
        signalManager.addTimeSeries(new TimeSeries("Signal 2", "Simulated", 1, 100, "mv"));
        WriterRunnableTimeSeries writer1 = new WriterRunnableTimeSeries("Signal 1");
        WriterRunnableTimeSeries writer2 = new WriterRunnableTimeSeries("Signal 2");
        float[] dataToWrite1 = new float[10];
        AuxTestUtilities.secuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        AuxTestUtilities.secuentialArray(dataToWrite2);
        writer1.setDataToWrite(dataToWrite1);
        writer1.setIndexInitToWrite(0);
        writer2.setDataToWrite(dataToWrite2);
        writer2.setIndexInitToWrite(0);
        signalManager.encueWriteOperation(writer1);
        signalManager.encueWriteOperation(writer2);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(AuxTestUtilities.compareArray(dataToWrite1,
                signalManager.readFromTimeSeries("Signal 1", 0, 10), dataToWrite1.length));
        assertTrue(AuxTestUtilities.compareArray(dataToWrite2,
                signalManager.readFromTimeSeries("Signal 2", 0, 100), dataToWrite2.length));
        ReaderCallableTimeSeries reader1 = new ReaderCallableTimeSeries("Signal 1", "Algorithm1");
        reader1.setPosInitToRead(0);
        reader1.setSizeToRead(10);
        ReaderCallableTimeSeries reader2 = new ReaderCallableTimeSeries("Signal 2", "Algorithm1");
        reader2.setPosInitToRead(0);
        reader2.setSizeToRead(100);
        signalManager.encueReadOperation(reader1);
        signalManager.encueReadOperation(reader2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        AuxTestUtilities.printArray(dataToWrite1);
        AuxTestUtilities.printArray(dataToWrite2);
    }

    @Test
    public void testReaderEventSeries() {
        AuxTestUtilities.reset();
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        AlgorithmDefaultImplementation algorithm1;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        eventSignals1.add("EventSeries1");
        eventSignals1.add("Signal 1");
        eventSignals1.add("Signal 2");
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupidImplementation("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);


        assertFalse(signalManager == null);
        signalManager.addEventSeries(new EventSeries("Events1", "Algorithm1", 100, new ArrayList<String>(), "mv"));
        signalManager.addEventSeries(new EventSeries("Events2", "Algorithm1", 100, new ArrayList<String>(), "mv"));
        WriterRunnableEventSeries writer1 = new WriterRunnableEventSeries("Events1");
        WriterRunnableEventSeries writer2 = new WriterRunnableEventSeries("Events2");
        LinkedList<Event> events1 = new LinkedList<Event>();
        AuxTestUtilities.eventosAleatorios(events1, 20, 1000, 300);
        LinkedList<Event> events2 = new LinkedList<Event>();
        AuxTestUtilities.eventosAleatorios(events2, 10, 9900, 130);
        for (int i = 0; i < events1.size(); i++) {
            writer1.addEventToWrite(events1.get(i));
        }
        AuxTestUtilities.imprimirEventos(events1);
        for (int i = 0; i < events2.size(); i++) {
            writer2.addEventToWrite(events2.get(i));
        }
        signalManager.encueWriteOperation(writer1);
        signalManager.encueWriteOperation(writer2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
        assertTrue(AuxTestUtilities.eventosCompararListas(events1, new LinkedList<Event>(signalManager.getEventsCopy("Events1"))));
        assertTrue(AuxTestUtilities.eventosCompararListas(events1, new LinkedList<Event>(signalManager.readFromEventSeriesFromTo("Events1", 1000, 1300))));
        assertTrue(AuxTestUtilities.eventosCompararListas(events2, new LinkedList<Event>(signalManager.getEventsCopy("Events2"))));
        ReaderCallableEventSeries reader1 = new ReaderCallableEventSeries("Events1", "Algorithm1",new LinkedList<Event>(),new LinkedList<Event>());
        ReaderCallableEventSeries reader2 = new ReaderCallableEventSeries("Events2", "Algorithm1", new LinkedList<Event>(),new LinkedList<Event>());
        signalManager.encueReadOperation(reader1);
        signalManager.encueReadOperation(reader2);
        WriterRunnableEventSeries writer3 = new WriterRunnableEventSeries("Events1");
        for (int i = 0; i < events1.size(); i++) {
            writer3.addEventToDelete(events1.get(i));
        }
    //@debug    System.out.println(signalManager.getEventsCopy("Events1").size());
        signalManager.encueWriteOperation(writer3);
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
  //@debug      System.out.println(signalManager.getEventsCopy("Events1").size());
        assertEquals(0, signalManager.getEventsCopy("Events1").size());

        ReaderCallableEventSeries reader3 = new ReaderCallableEventSeries("Events1", "Algorithm1",new LinkedList<Event>(),new LinkedList<Event>());
        signalManager.encueReadOperation(reader3);

    }
}
