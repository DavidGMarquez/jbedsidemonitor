/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import signals.EventSeries;
import signals.SignalManager;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;
import static org.junit.Assert.*;

public class BasicTestOrder {

    public BasicTestOrder() {
    }
    TimeSeries timeSeries1;
    TimeSeries timeSeries1_out;
    AlgorithmDefaultImplementation algorithm1;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;

    @Before
    public void setUp() {
        AuxTestUtilities.reset();
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        timeSeries1_out = new TimeSeries("TimeSeries1_Algorithm1", "Simulated", 1, 100, "mv");
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");

        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupid2XMultiSignalsImplementationOrder("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);



    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBasicoOrden() {
        AuxTestUtilities.reset();
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries1_out);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        SerialTimeSeriesSeriesGeneratorOrder serialTimeSeriesSeriesGenerator = new SerialTimeSeriesSeriesGeneratorOrder(10, 1, 100, "TimeSeries1");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompleteTestOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        float[] readNewFromTimeSeriesTimeSeries1 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1", 0);
        System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries1.length; i++) {
            if (readNewFromTimeSeriesTimeSeries1[i] != i) {
                System.out.print("_");
            }
            System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i]);
            //assertEquals(readNewFromTimeSeriesTimeSeries1[i], i, 0.001);

        }
        readNewFromTimeSeriesTimeSeries1 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1", 0);
        System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        int errors=0;
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries1.length; i++) {
            if (readNewFromTimeSeriesTimeSeries1[i] != 2*i) {
                System.out.print("_");
            }
           System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i]);
          //  assertEquals(readNewFromTimeSeriesTimeSeries1[i], 2*i, 0.001);
            if(readNewFromTimeSeriesTimeSeries1[i]!=2*i)
            {
                errors++;
            }
            
        }
        System.out.println("Errors: "+errors);

    }
}
