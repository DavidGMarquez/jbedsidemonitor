/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import auxiliarTools.AuxTestUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class TimeSeriesTriggerTest {

    public TimeSeriesTriggerTest() {
    }

    @Test
    public void testCreate() {
        TimeSeriesTrigger timeSeriesTrigger = new TimeSeriesTrigger("TimeSeriesOne", 300);
        assertEquals(timeSeriesTrigger.getIdentifierSignal(), "TimeSeriesOne");
        assertEquals(timeSeriesTrigger.getTheshold(), 300);
        assertEquals(timeSeriesTrigger.getNewData(), 0);
        assertEquals(timeSeriesTrigger.getLastSampleReported(), 0);
        assertEquals(timeSeriesTrigger.trigger(), false);


    }

    @Test
    public void testUpdatePlain() {
        TimeSeriesTrigger timeSeriesTrigger = new TimeSeriesTrigger("TimeSeriesOne", 300);
        WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries("TimeSeriesOne");
        float[] prueba={1,2,3};
        writerRunnableTimeSeries.setDataToWrite(prueba);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertEquals(timeSeriesTrigger.getNewData(), 3);
    }

    @Test
    public void testUpdateComplete() {
        TimeSeriesTrigger timeSeriesTrigger = new TimeSeriesTrigger("TimeSeriesOne", 300);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesOne", 33);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 99);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesOne", 300);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 399);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("", 99);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 399);

        timeSeriesTrigger = new TimeSeriesTrigger("TimeSeriesTwo", 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesTwo", 99);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 99);

        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesOne", 166);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 99);

        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesTwo", 1);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesTwo", 1);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 101);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesTwo", 166);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 267);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesTwo", 166);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getNewData(), 433);


    }

    @Test
    public void testTrigger() {
        TimeSeriesTrigger timeSeriesTrigger = new TimeSeriesTrigger("TimeSeriesOne", 1000);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesOne", 333);
        assertFalse(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
    }

    @Test
    public void testReset() {
        TimeSeriesTrigger timeSeriesTrigger = new TimeSeriesTrigger("TimeSeriesOne", 100);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesOne", 100);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertFalse(timeSeriesTrigger.trigger());
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertTrue(timeSeriesTrigger.trigger());
        assertEquals(timeSeriesTrigger.getLastSampleReported(), 0);
        assertEquals(timeSeriesTrigger.getNewData(), 200);
        assertEquals(timeSeriesTrigger.getTheshold(), 100);
        timeSeriesTrigger.reset();
        assertEquals(timeSeriesTrigger.getLastSampleReported(), 200);
        assertEquals(timeSeriesTrigger.getNewData(), 0);
        assertEquals(timeSeriesTrigger.getTheshold(), 100);
        writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeriesOne", 33);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertEquals(timeSeriesTrigger.getLastSampleReported(), 200);
        assertEquals(timeSeriesTrigger.getNewData(), 33);
        assertEquals(timeSeriesTrigger.getTheshold(), 100);
        timeSeriesTrigger.reset();
        assertEquals(timeSeriesTrigger.getLastSampleReported(), 233);
        assertEquals(timeSeriesTrigger.getNewData(), 0);
        assertEquals(timeSeriesTrigger.getTheshold(), 100);
        timeSeriesTrigger.update(writerRunnableTimeSeries);
        assertEquals(timeSeriesTrigger.getLastSampleReported(), 233);
        assertEquals(timeSeriesTrigger.getNewData(), 33);
        assertEquals(timeSeriesTrigger.getTheshold(), 100);
    }
}