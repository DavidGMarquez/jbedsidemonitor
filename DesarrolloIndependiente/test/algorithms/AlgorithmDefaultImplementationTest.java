package algorithms;

import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import signals.ReadResult;
import signals.TimeSeries;

public class AlgorithmDefaultImplementationTest {

    LinkedList<String> eventSignals;
    LinkedList<String> timeSignals;

    public AlgorithmDefaultImplementationTest() {
    }

    @Before
    public void setUp() {
        eventSignals = new LinkedList<String>();
        timeSignals = new LinkedList<String>();
        eventSignals.add("EventSeries1");
        eventSignals.add("EventSeries2");
        eventSignals.add("EventSeries3");
        timeSignals.add("TimeSeries1");
        timeSignals.add("TimeSeries2");
        timeSignals.add("TimeSeries3");

    }

    @Test
    public void testCreate() {
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "algorithm1", 0, 300, "NaN");
        Algorithm algorithm1 = new AlgorithmDefaultImplementation("algorithm1", timeSeriesOut1,
                timeSignals, eventSignals) {
            public boolean execute(ReadResult readResult) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        assertTrue(algorithm1.getIdentifier().equals("algorithm1"));
        assertTrue(algorithm1.getSignalToWrite().getIdentifier().equals("Out_Algorithm_1"));
        assertTrue(algorithm1.getNotifyPolice().getNotifyPolice().equals(AlgorithmNotifyPoliceEnum.ALL));
        assertEquals(algorithm1.getNotifyPolice().getEventSeriesTheshold().get("EventSeries1").intValue(), 10);
        assertEquals(algorithm1.getNotifyPolice().getEventSeriesTheshold().get("EventSeries2").intValue(), 10);
        assertEquals(algorithm1.getNotifyPolice().getEventSeriesTheshold().get("EventSeries3").intValue(), 10);
        assertEquals(algorithm1.getNotifyPolice().getTimeSeriesTheshold().get("TimeSeries1").intValue(), 100);
        assertEquals(algorithm1.getNotifyPolice().getTimeSeriesTheshold().get("TimeSeries2").intValue(), 100);
        assertEquals(algorithm1.getNotifyPolice().getTimeSeriesTheshold().get("TimeSeries3").intValue(), 100);
    }
}
