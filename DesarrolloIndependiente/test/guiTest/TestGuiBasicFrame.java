/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTest;

import completeTestsTimeSeries.AlgorithmStupid2XMultiSignalsImplementationOrder;
import completeTestsTimeSeries.SinTimeSeriesGeneratorOrder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
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
import signals.WriterRunnableTimeSeries;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class TestGuiBasicFrame {

    public TestGuiBasicFrame() {
    }

    public static void main(String[] args) {
        AuxTestUtilities.reset();
        TimeSeries timeSeries1;
        TimeSeries timeSeries1_out;
        AlgorithmDefaultImplementation algorithmIN;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        timeSeries1_out = new TimeSeries("TimeSeries1_AlgorithmIN", "Simulated", 1, 100, "mv");
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();

        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_IN", "Algorithm1", 0, 300, "NaN");
        algorithmIN = new AlgorithmStupid2XMultiSignalsImplementationOrder("AlgorithmIN", timeSeriesOut1, timeSignals1, eventSignals1);
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries1_out);
        AlgorithmManager.getInstance().addAlgorithm(algorithmIN);
        int iterations = 100;
        SinTimeSeriesGeneratorOrder sinTimeSeriesGenerator = new SinTimeSeriesGeneratorOrder(10, 1, iterations, "TimeSeries1");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
        }
        float[] readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1", 0);
        System.out.println("TimeSeries1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length == (iterations + 1) * 10);
        //System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            //if(readNewFromTimeSeriesTimeSeries1[i]!=((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))))
            //  System.out.print("_");
            //System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i] + "   " + ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
            assertEquals(readNewFromTimeSeriesTimeSeries[i], ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))), 0.001);

        }
        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_AlgorithmIN", 0);
        System.out.println("TimeSeries1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > ((iterations + 1) * 10) / 2);
        //System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            //if(readNewFromTimeSeriesTimeSeries1[i]!=((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))))
            //  System.out.print("_");
            //System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i] + "   " + ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
            assertEquals(readNewFromTimeSeriesTimeSeries[i], ((float) 2 * (Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100)))), 0.001);

        }
        /*  FrameTestAutomatic frame1 = new FrameTestAutomatic();
        frame1.setSize(800, 500);
        frame1.setVisible(true);*/
        FrameTestAutomatic frame1 = new FrameTestAutomatic(SignalManager.getInstance().getJSignalAdapter());
        frame1.setSize(800, 500);
        frame1.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
    }
}
