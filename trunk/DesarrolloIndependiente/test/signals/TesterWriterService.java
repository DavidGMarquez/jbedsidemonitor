package signals;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesterWriterService {

    public TesterWriterService() {
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
        TestUtilities.SecuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        TestUtilities.SecuentialArray(dataToWrite2);
        Writer1.setDataToWrite(dataToWrite1);
        Writer2.setDataToWrite(dataToWrite2);
        signalManager.encueWriteOperation(Writer1);
        signalManager.encueWriteOperation(Writer2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(TestUtilities.compareArray(dataToWrite1, signalManager.readFromTimeSeries("Signal 1", 0, 10), dataToWrite1.length));
        assertTrue(TestUtilities.compareArray(dataToWrite2, signalManager.readFromTimeSeries("Signal 2", 0, 100), dataToWrite2.length));
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
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(1, signalManager.getEvents("Signal 1").size());
        assertEquals(3, signalManager.getEvents("Signal 2").size());

    }
}
