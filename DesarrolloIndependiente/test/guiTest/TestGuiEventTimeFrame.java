/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTest;

import completeTestsTimeSeries.SinTimeSeriesGeneratorOrder;
import java.util.ArrayList;
import completeTestsTimeSeries.AlgorithmStupid2XMultiSignalsImplementationOrder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Event;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import completeTestsEventSeries.SerialEventSeriesGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import signals.EventSeries;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;
import userInterface.JBedSideMonitorMainWindow;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class TestGuiEventTimeFrame {

    public TestGuiEventTimeFrame() {
    }

    public static void main(String[] args) {
        AuxTestUtilities.reset();
        TimeSeries timeSeries1;
        EventSeries eventSeries1;
        EventSeries eventSeries2;
        AlgorithmDefaultImplementation algorithmIN;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        eventSeries1 = new EventSeries("EventSeries1", "Simulated", 0, new ArrayList<String>(), "NaN");
        ArrayList<String> entradas= new ArrayList<String>();
        entradas.add("TimeSeries1");
        eventSeries2 = new EventSeries("EventSeries2", "TimeSeries1", 0, entradas, "NaN");
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();

        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("TimeSeries1_AlgorithmIN", "Algorithm1", 0, 100, "NaN");
        algorithmIN = new AlgorithmStupid2XMultiSignalsImplementationOrder("AlgorithmIN", timeSeriesOut1, timeSignals1, eventSignals1);
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addEventSeries(eventSeries2);
        AlgorithmManager.getInstance().addAlgorithm(algorithmIN);
        int iterations = 10000;
        SinTimeSeriesGeneratorGui sinTimeSeriesGeneratorGui = new SinTimeSeriesGeneratorGui(10, 100, iterations, "TimeSeries1",10,0.01);
        SerialEventSeriesGui serialEventSeriesGui=new SerialEventSeriesGui(10, 10, 1000, "EventSeries1", 10);
        SerialEventSeriesMark serialEventSeriesMark=new SerialEventSeriesMark(10, 10, 10000, "EventSeries2", 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("No sleep");
        }
        float[] readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1", 0);
        System.out.println("TimeSeries1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
       // assertTrue(readNewFromTimeSeriesTimeSeries.length == (iterations + 1) * 10);
        //System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        //assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            //if(readNewFromTimeSeriesTimeSeries1[i]!=((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))))
            //  System.out.print("_");
            //System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i] + "   " + ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
          //  assertEquals(readNewFromTimeSeriesTimeSeries[i], ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))), 0.001);

        }
        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_AlgorithmIN", 0);
        System.out.println("TimeSeries1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        //assertTrue(readNewFromTimeSeriesTimeSeries.length > ((iterations + 1) * 10) / 2);
        //System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        //assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            //if(readNewFromTimeSeriesTimeSeries1[i]!=((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))))
            //  System.out.print("_");
            //System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i] + "   " + ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
          //  assertEquals(readNewFromTimeSeriesTimeSeries[i], ((float) 2 * (Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100)))), 0.001);

        }
        SortedSet<Event> eventsCopy = SignalManager.getInstance().getEventsCopy("EventSeries1");
        System.out.println("EventsSeries1 TAM:"+eventsCopy.size());
        /*  FrameTestAutomatic frame1 = new FrameTestAutomatic();
        frame1.setSize(800, 500);
        frame1.setVisible(true);*/
/*        FrameTestAutomatic frame1 = new FrameTestAutomatic(SignalManager.getInstance().getJSignalAdapter());

        frame1.setSize(800, 500);
        frame1.setVisible(true);*/
        JBedSideMonitorMainWindow frame1 =new JBedSideMonitorMainWindow(SignalManager.getInstance().getJSignalAdapter());
        frame1.setSize(800, 500);
        frame1.setVisible(true);
    }
}
