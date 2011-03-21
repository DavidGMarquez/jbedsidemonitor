package signals;

import auxiliarTools.AuxTestUtilities;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class TimeSeriesTest {

    @Test
    public void testConstructor() {
        TimeSeries ts = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        assertEquals(ts.getAgent(), "Watson");
        assertEquals(ts.getIdentifier(), "Series1");
        assertEquals(new Date().getTime() >= ts.getOrigin(), true);
        assertEquals(100000, ts.getCapacity(), 0.0001);
        assertEquals(10, ts.getFrequency(), 0.0001);
        assertEquals(ts.getSamplescounter(), 0);
        assertEquals("mV", ts.getUnits());
    }

    @Test
    public void basicWriting() {
        TimeSeries ts = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 1, "mV");
        float[] dataToWrite = null;
        dataToWrite = AuxTestUtilities.generateArray(3000);
        ts.write(dataToWrite);
        assertEquals(AuxTestUtilities.compareArray(ts.read(0, 3000), dataToWrite, 3000), true);
        assertEquals(1 * 3600 * 6, ts.getCapacity(), 0.0001);
    }

    @Test
    public void writingRandomNumbers() {
        TimeSeries ts = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        float[] dataToWrite = null;
        dataToWrite = AuxTestUtilities.generateArray(75000);
        assertEquals(ts.getIndexNewsample(), -1);
        assertEquals(ts.getIndexOldestsample(), -1);
        assertEquals(ts.getSamplescounter(), 0);
        ts.write(dataToWrite);
        assertEquals(ts.getIndexNewsample(), 74999);
        assertEquals(ts.getIndexOldestsample(), 0);
        assertEquals(ts.getSamplescounter(), 75000);
        assertEquals(AuxTestUtilities.compareArray(ts.read(0, 75000), dataToWrite, 75000), true);
        dataToWrite = AuxTestUtilities.generateArray(75000);
        ts.write(dataToWrite);
        assertEquals(ts.getIndexNewsample(), 49999);
        assertEquals(ts.getIndexOldestsample(), 50000);
        assertEquals(ts.getSamplescounter(), 100000);
        assertEquals(AuxTestUtilities.compareArray(ts.read(75000, 75000), dataToWrite, 75000), true);
    }

    @Test
    public void writing() {
        TimeSeries ts = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        float[] dataToWrite = null;
        dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(0, 100);
        assertEquals(ts.getIndexNewsample(), -1);
        assertEquals(ts.getIndexOldestsample(), -1);
        assertEquals(ts.getSamplescounter(), 0);
        ts.write(dataToWrite);
        assertEquals(ts.getIndexNewsample(), 99);
        assertEquals(ts.getIndexOldestsample(), 0);
        assertEquals(ts.getSamplescounter(), 100);
        assertEquals(AuxTestUtilities.compareArray(ts.read(0, 100), dataToWrite, 100), true);
        assertEquals(ts.read(0, 1)[0], 0, 0.001);
        assertEquals(ts.read(2, 1)[0], 2, 0.001);
        assertEquals(ts.read(66, 1)[0], 66, 0.001);
        assertEquals(ts.read(99, 1)[0], 99, 0.001);
    }

    @Test
    public void testTooMuchDataToWriteException() {
        TimeSeries ts = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        float[] dataToWrite = null;
        try {
            dataToWrite = AuxTestUtilities.generateArray(100001);
            ts.write(dataToWrite);
            fail("Deberia haber fallado");
        } catch (TooMuchDataToWriteException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void writingRandomNumbersOrder() {
        TimeSeries ts = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        float[] dataToWrite = null;
        dataToWrite = AuxTestUtilities.generateArray(75000);
        assertEquals(ts.getIndexNewsample(), -1);
        assertEquals(ts.getIndexOldestsample(), -1);
        assertEquals(ts.getSamplescounter(), 0);
        ts.write(dataToWrite, 75000);
        assertEquals(ts.getIndexNewsample(), 49999);
        assertEquals(ts.getIndexOldestsample(), 50000);
        assertEquals(ts.getSamplescounter(), 100000);
        assertEquals(AuxTestUtilities.compareArray(ts.read(75000, 75000), dataToWrite, 75000), true);
        dataToWrite = AuxTestUtilities.generateArray(75000);
        ts.write(dataToWrite, 0);
        assertEquals(ts.getIndexNewsample(), 74999);
        assertEquals(ts.getIndexOldestsample(), 75000);
        assertEquals(ts.getSamplescounter(), 100000);
        assertEquals(AuxTestUtilities.compareArray(ts.read(0, 75000), dataToWrite, 75000), true);


    }
}
