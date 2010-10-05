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
import signals.TimeSeriesReaderCallable;
import signals.TimeSeriesWriterRunnable;
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
    public void TestReaderTimeSeries() {
        SignalManager signalManager = SignalManager.getInstance();
        signalManager.initiateThread();
        assertFalse(signalManager == null);
        signalManager.addTimeSeries(new TimeSeries("Signal 1", "Simulated", 1, 100, "mv"));
        signalManager.addTimeSeries(new TimeSeries("Signal 2", "Simulated", 1, 100, "mv"));

        TimeSeriesWriterRunnable writer1 = new TimeSeriesWriterRunnable("Signal 1");
        TimeSeriesWriterRunnable writer2 = new TimeSeriesWriterRunnable("Signal 2");

        float[] dataToWrite1 = new float[10];
        this.SecuentialArray(dataToWrite1);
        float[] dataToWrite2 = new float[100];
        this.SecuentialArray(dataToWrite2);
        writer1.setDataToWrite(dataToWrite1);
        writer2.setDataToWrite(dataToWrite2);

        signalManager.addWriterRunnable(writer1);
        signalManager.addWriterRunnable(writer2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertArrayEquals(dataToWrite1, signalManager.readFromTimeSeries("Signal 1", 0, 10));
        assertArrayEquals(dataToWrite2, signalManager.readFromTimeSeries("Signal 2", 0, 100));


        TimeSeriesReaderCallable reader1= new TimeSeriesReaderCallable("Signal 1", "Algorithm 1");
        reader1.setPosInitToRead(0);
        reader1.setSizeToRead(10);
        TimeSeriesReaderCallable reader2= new TimeSeriesReaderCallable("Signal 2", "Algorithm 1");
        reader2.setPosInitToRead(0);;
        reader2.setSizeToRead(100);
        signalManager.addReaderCallable(reader1);
        signalManager.addReaderCallable(reader2);
        System.out.println("Comparar 1");
         printArray(dataToWrite1);
         System.out.println("Comparar 2");
         printArray(dataToWrite2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TesterWriterService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
