/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiFinalTest;

import guiTest.*;
import java.util.ArrayList;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Event;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import signals.EventSeries;
import signals.SignalManager;
import userInterface.Creator;
import userInterface.JBedSideMonitorMainWindow;

/**
 *
 * @author USUARIO
 */
public class TestGuiFinal {

    public TestGuiFinal() {
    }

    public static void main(String[] args) {
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
        algorithmIN = new Algorithm2XMultiSignalsImplementationGui("AlgorithmIN", timeSeriesOut1, timeSignals1, eventSignals1);
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addEventSeries(eventSeries1);
        SignalManager.getInstance().addEventSeries(eventSeries2);
        AlgorithmManager.getInstance().addAlgorithm(algorithmIN);
        int iterations = 10000;
        DataSourceSinTimeSeries dataSourceSinTimeSeries = new DataSourceSinTimeSeries(10, 100, iterations, "TimeSeries1",10,0.01);
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
        Creator.main(args);

    }
}
