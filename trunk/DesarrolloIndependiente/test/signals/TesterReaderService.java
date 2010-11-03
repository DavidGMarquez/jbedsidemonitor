package signals;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class TesterReaderService {

    public TesterReaderService() {
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
    public void testReaderTimeSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        assertFalse(signalManager == null);
        signalManager.addTimeSeries(new TimeSeries("Signal 1", "Simulated", 1, 100, "mv"));
        signalManager.addTimeSeries(new TimeSeries("Signal 2", "Simulated", 1, 100, "mv"));

        TimeSeriesWriterRunnable writer1 = new TimeSeriesWriterRunnable("Signal 1");
        TimeSeriesWriterRunnable writer2 = new TimeSeriesWriterRunnable("Signal 2");

        float[] dataToWrite1 = new float[10];
        this.secuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        this.secuentialArray(dataToWrite2);
        writer1.setDataToWrite(dataToWrite1);
        writer2.setDataToWrite(dataToWrite2);

        signalManager.encueWriteOperation(writer1);
        signalManager.encueWriteOperation(writer2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertArrayEquals(dataToWrite1, signalManager.readFromTimeSeries("Signal 1", 0, 10));
        assertArrayEquals(dataToWrite2, signalManager.readFromTimeSeries("Signal 2", 0, 100));


        TimeSeriesReaderCallable reader1 = new TimeSeriesReaderCallable("Signal 1", "Algorithm 1");
        reader1.setPosInitToRead(0);
        reader1.setSizeToRead(10);
        TimeSeriesReaderCallable reader2 = new TimeSeriesReaderCallable("Signal 2", "Algorithm 1");
        reader2.setPosInitToRead(0);        
        reader2.setSizeToRead(100);
        signalManager.encueReadOperation(reader1);
        signalManager.encueReadOperation(reader2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Comparar 1");
        printArray(dataToWrite1);
        System.out.println("Comparar 2");
        printArray(dataToWrite2);

    }

    @Test
    public void testReaderEventSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        assertFalse(signalManager == null);
        signalManager.addEventSeries(new EventSeries("Events1", "Agente1", 100, new ArrayList<String>(), "mv"));
        signalManager.addEventSeries(new EventSeries("Events2", "Agente2", 100, new ArrayList<String>(), "mv"));

        EventSeriesWriterRunnable writer1 = new EventSeriesWriterRunnable("Events1");
        EventSeriesWriterRunnable writer2 = new EventSeriesWriterRunnable("Events2");
        LinkedList<Event> events1 = new LinkedList<Event>();
        this.eventosAleatorios(events1, 20, 1000, 300);
        LinkedList<Event> events2 = new LinkedList<Event>();
        this.eventosAleatorios(events2, 10, 9900, 130);
        for (int i = 0; i < events1.size(); i++) {
            writer1.addEventToWrite(events1.get(i));

        }
        imprimirEventos(events1);
        for (int i = 0; i < events2.size(); i++) {
            writer2.addEventToWrite(events2.get(i));

        }
        signalManager.encueWriteOperation(writer1);
        signalManager.encueWriteOperation(writer2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        assertTrue(eventosCompararListas(events1, new LinkedList<Event>(signalManager.getEvents("Events1"))));
        assertTrue(eventosCompararListas(events1, new LinkedList<Event>(signalManager.readFromEventSeriesFromTo("Events1", 1000, 1300))));
        assertTrue(eventosCompararListas(events2, new LinkedList<Event>(signalManager.getEvents("Events2"))));



        EventSeriesReaderCallable reader1 = new EventSeriesReaderCallable("Events1", "Agente1");
        reader1.setFirstInstantToInclude(1000);
        reader1.setLastInstantToInclude(1300);
        EventSeriesReaderCallable reader2 = new EventSeriesReaderCallable("Events2", "Agente2");
        reader2.setFirstInstantToInclude(9900);
        reader2.setLastInstantToInclude(9900+130);
        signalManager.encueReadOperation(reader1);
        signalManager.encueReadOperation(reader2);

        EventSeriesWriterRunnable writer3 = new EventSeriesWriterRunnable("Events1");
        for (int i = 0; i < events1.size(); i++) {
            writer3.addEventToDelete(events1.get(i));

        }
        System.out.println(signalManager.getEvents("Events1").size());
        signalManager.encueWriteOperation(writer3);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
          EventSeriesReaderCallable reader3 = new EventSeriesReaderCallable("Events1", "Agente1");
        reader3.setFirstInstantToInclude(1000);
        reader3.setLastInstantToInclude(1300);
        signalManager.encueReadOperation(reader3);
        System.out.println(signalManager.getEvents("Events1").size());
        assertTrue(0 == signalManager.getEvents("Events1").size());


    }

    void secuentialArray(float[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
    }

    private void assertArrayEquals(float[] dataToWrite1, float[] readFromTimeSeries) {
        for (int i = 0; i < dataToWrite1.length; i++) {
            assertEquals(dataToWrite1[i], readFromTimeSeries[i], 0.001);
        }
    }

    private void printArray(float[] readFromTimeSeries) {
        for (int i = 0; i < readFromTimeSeries.length; i++) {
            System.out.println(readFromTimeSeries[i]);
        }
    }

    private void eventosAleatorios(LinkedList<Event> eventos, int numberofevents, int timeinit, int duration) {
        for (int i = 0; i < numberofevents; i++) {
            eventos.add(new Event(timeinit + ((new Double(Math.random()*99999).longValue()) % duration), "GeneradoAleatorio", null));
        }
    }

    private void imprimirEventos(LinkedList<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            System.out.println("Event " + i+ " time" + events.get(i).getLocation() + " tipo " + events.get(i).getType());
        }
    }

    private boolean eventosCompararListas(LinkedList<Event> eventos1, LinkedList<Event> eventos2) {

        HashSet hashset1 = new HashSet<Event>(eventos1);
        HashSet hashset2 = new HashSet<Event>(eventos2);
        HashSet hashset3 = new HashSet<Event>(eventos1);
        HashSet hashset4 = new HashSet<Event>(eventos2);
        hashset1.removeAll(hashset2);
        hashset4.removeAll(hashset3);
        if (hashset4.size() == 0 && hashset1.size() == 0) {
            return true;
        } else {
            return false;
        }




    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
