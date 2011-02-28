package signals;

import auxiliarTools.AuxTestUtilities;
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
        WriterRunnableTimeSeries Writer1 = new WriterRunnableTimeSeries("Signal 1");
        WriterRunnableTimeSeries Writer2 = new WriterRunnableTimeSeries("Signal 2");
        float[] dataToWrite1 = new float[10];
        AuxTestUtilities.secuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        AuxTestUtilities.secuentialArray(dataToWrite2);
        Writer1.setDataToWrite(dataToWrite1);
        Writer2.setDataToWrite(dataToWrite2);
        signalManager.encueWriteOperation(Writer1);
        signalManager.encueWriteOperation(Writer2);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(AuxTestUtilities.compareArray(dataToWrite1, signalManager.readFromTimeSeries("Signal 1", 0, 10), dataToWrite1.length));
        assertTrue(AuxTestUtilities.compareArray(dataToWrite2, signalManager.readFromTimeSeries("Signal 2", 0, 100), dataToWrite2.length));
    }

    @Test
    public void TestWriterEventSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        assertFalse(signalManager == null);
        signalManager.addEventSeries(new EventSeries("Signal 1", "Simulated", 1, new ArrayList<String>(), "mv"));
        signalManager.addEventSeries(new EventSeries("Signal 2", "Simulated", 1, new ArrayList<String>(), "mv"));
        WriterRunnableEventSeries Writer1 = new WriterRunnableEventSeries("Signal 1");
        WriterRunnableEventSeries Writer2 = new WriterRunnableEventSeries("Signal 2");
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

    @Test
    public void TestMultiWriter() {
        SignalManager signalManager = SignalManager.getInstance();
        assertFalse(signalManager == null);
        signalManager.addTimeSeries(new TimeSeries("Signal 1Time", "Simulated", 1, 100, "mv"));
        signalManager.addTimeSeries(new TimeSeries("Signal 2Time", "Simulated", 1, 100, "mv"));
        signalManager.addEventSeries(new EventSeries("Signal 1Event", "Simulated", 1, new ArrayList<String>(), "mv"));
        signalManager.addEventSeries(new EventSeries("Signal 2Event", "Simulated", 1, new ArrayList<String>(), "mv"));
        WriterRunnableMultiSignal writerRunnableMultiSignal = new WriterRunnableMultiSignal();
        WriterRunnableTimeSeries writer1T = new WriterRunnableTimeSeries("Signal 1Time");
        WriterRunnableTimeSeries writer2T = new WriterRunnableTimeSeries("Signal 2Time");
        WriterRunnableEventSeries writer1E = new WriterRunnableEventSeries("Signal 1Event");
        WriterRunnableEventSeries writer2E = new WriterRunnableEventSeries("Signal 2Event");
        float[] dataToWrite1 = new float[10];
        AuxTestUtilities.secuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        AuxTestUtilities.secuentialArray(dataToWrite2);
        writer1T.setDataToWrite(dataToWrite1);
        writer2T.setDataToWrite(dataToWrite2);
        Event e1 = new Event(1, "a", null);
        Event e2 = new Event(2, "b", null);
        Event e3 = new Event(3, "c", null);
        writer1E.addEventToWrite(e1);
        writer2E.addEventToWrite(e1);
        writer2E.addEventToWrite(e2);
        writer2E.addEventToWrite(e3);
        writerRunnableMultiSignal.addWriterRunnableOneSignal(writer1T);
        writerRunnableMultiSignal.addWriterRunnableOneSignal(writer2T);
        writerRunnableMultiSignal.addWriterRunnableOneSignal(writer1E);
        writerRunnableMultiSignal.addWriterRunnableOneSignal(writer2E);
        assertEquals(0, signalManager.getEvents("Signal 1Event").size());
        assertEquals(0, signalManager.getEvents("Signal 2Event").size());
        signalManager.encueWriteOperation(writerRunnableMultiSignal);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(AuxTestUtilities.compareArray(dataToWrite1, signalManager.readFromTimeSeries("Signal 1Time", 0, 10), dataToWrite1.length));
        assertTrue(AuxTestUtilities.compareArray(dataToWrite2, signalManager.readFromTimeSeries("Signal 2Time", 0, 100), dataToWrite2.length));
        assertEquals(1, signalManager.getEvents("Signal 1Event").size());
        assertEquals(3, signalManager.getEvents("Signal 2Event").size());
    }
}
