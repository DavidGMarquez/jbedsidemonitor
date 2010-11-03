package signals;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import signals.EventSeriesWriterRunnable;
import signals.EventSeries;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import signals.Event;
import signals.SignalManager;
import signals.TimeSeries;
import signals.TimeSeriesWriterRunnable;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class TesterWriterService {

    public TesterWriterService() {
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
    public void TestWriterTimeSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        assertFalse(signalManager == null);
        signalManager.addTimeSeries(new TimeSeries("Signal 1", "Simulated", 1, 100, "mv"));
        signalManager.addTimeSeries(new TimeSeries("Signal 2", "Simulated", 1, 100, "mv"));
        TimeSeriesWriterRunnable Writer1 = new TimeSeriesWriterRunnable("Signal 1");
        TimeSeriesWriterRunnable Writer2 = new TimeSeriesWriterRunnable("Signal 2");
        float[] dataToWrite1 = new float[10];
        this.SecuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        this.SecuentialArray(dataToWrite2);
        Writer1.setDataToWrite(dataToWrite1);
        Writer2.setDataToWrite(dataToWrite2);
        signalManager.encueWriteOperation(Writer1);
        signalManager.encueWriteOperation(Writer2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertArrayEquals(dataToWrite1, signalManager.readFromTimeSeries("Signal 1", 0, 10));
        assertArrayEquals(dataToWrite2, signalManager.readFromTimeSeries("Signal 2", 0, 100));
    }

    @Test
    public void TestWriterEventSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        assertFalse(signalManager == null);
        signalManager.addEventSeries(new EventSeries("Signal 1", "Simulated", 1, new ArrayList<String>(), "mv"));
        signalManager.addEventSeries(new EventSeries("Signal 2", "Simulated", 1, new ArrayList<String>(), "mv"));
        EventSeriesWriterRunnable Writer1 = new EventSeriesWriterRunnable("Signal 1");
        EventSeriesWriterRunnable Writer2 = new EventSeriesWriterRunnable("Signal 2");

        Event e1 = new Event(1, "a", null);
        Event e2 = new Event(2, "b", null);
        Event e3 = new Event(3, "c", null);
        Writer1.addEventToWrite(e1);
        Writer2.addEventToWrite(e1);
        Writer2.addEventToWrite(e2);
        Writer2.addEventToWrite(e3);
        assertEquals(0, signalManager.getEvents("Signal 1").size());
        assertEquals(0, signalManager.getEvents("Signal 2").size());

        signalManager.encueWriteOperation(Writer1);
        signalManager.encueWriteOperation(Writer2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(1, signalManager.getEvents("Signal 1").size());
        assertEquals(3, signalManager.getEvents("Signal 2").size());

    }

    private void SecuentialArray(float[] data) {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
