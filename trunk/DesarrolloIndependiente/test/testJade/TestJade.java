package testJade;

import completeTestsTimeSeries.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import com.sun.org.apache.xml.internal.security.algorithms.Algorithm;
import completeTestsTimeSeries.AlgorithmStupid2XMultiSignalsImplementationOrder;
import completeTestsTimeSeries.AlgorithmStupidFileImplementation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import signals.EventSeries;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;
import static org.junit.Assert.*;

public class TestJade {

    public TestJade() {
    }
    TimeSeries timeSeries1;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;
    String nameAlgorithmOUT = "AlgorithmJade";
    int sizeData = 30;
    int numberAgents = 30;

    @Before
    public void setUp() {
        AuxTestUtilities.reset();
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv", 1000000);
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBasico() {
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        for (int i = 0; i < numberAgents; i++) {
            String nameOut = nameAlgorithmOUT.concat(new Integer(i).toString());
            TimeSeries timeSeriesOutJade = new TimeSeries(nameOut.concat("OUT"), nameOut, 0, 100, "NaN");
            AlgorithmJADE algorithmJade = new AlgorithmJADE(nameOut, timeSeriesOutJade, timeSignals1, eventSignals1);
            AlgorithmManager.getInstance().addAlgorithm(algorithmJade);
        }

        JADEGenerator jADEGenerator = new JADEGenerator(30, "TimeSeries1");
        Thread threadJade = new Thread(jADEGenerator, "threadJADEGenerator");
        threadJade.start();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }


  ConsecutiveSamplesAvailableInfo consecutiveSamplesTimeSeries1 = SignalManager.getInstance().getConsecutiveSamplesTimeSeries("TimeSeries1");
            System.out.println("TimeSeries1"+" "+new Integer(consecutiveSamplesTimeSeries1.getOlderSampleAvailable()+consecutiveSamplesTimeSeries1.getSamplesReadyToReadInOrder()).toString());
        for (int i = 0; i < numberAgents; i++) {
            String nameSerieOut = nameAlgorithmOUT.concat(new Integer(i).toString().concat("OUT"));
            ConsecutiveSamplesAvailableInfo consecutiveSamplesTimeSeries = SignalManager.getInstance().getConsecutiveSamplesTimeSeries(nameSerieOut);
            System.out.println(nameSerieOut+" "+new Integer(consecutiveSamplesTimeSeries.getOlderSampleAvailable()+consecutiveSamplesTimeSeries.getSamplesReadyToReadInOrder()).toString());
        }
    }
}
