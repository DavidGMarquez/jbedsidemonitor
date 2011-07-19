/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

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
import userInterface.CreatorUserInterface;
import userInterface.JBedSideMonitorMainWindow;

/**
 *
 * @author USUARIO
 */
public class TestDemo1 {

    public TestDemo1() {
    }

    public static void main(String[] args) {
        TimeSeries timeSeries1;
        //Entrada un seno
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 0, 1000, "mv");
        //Algoritmo sobre la primera señal
        AlgorithmDefaultImplementation algorithmMultiplierOffsetSin;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("TimeSeries1_Algorithm1", "Algorithm1", 0, 1000, "NaN");
        algorithmMultiplierOffsetSin = new AlgorithmMultiplierOffsetTimeSeries("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1, new Float(2), new Float(0.5));


        //Algoritmo sobre la primera de las señales para detectar maximos y mínimos
        AlgorithmDefaultImplementation algorithmMaxMin;
        EventSeries eventSeries1MaxOut;
        LinkedList<String> eventSignals3;
        LinkedList<String> timeSignals3;
        eventSignals3 = new LinkedList<String>();
        timeSignals3 = new LinkedList<String>();
        timeSignals3.add("TimeSeries1");

        ArrayList<String> entradas = new ArrayList<String>();
        entradas.add("TimeSeries1");
        eventSeries1MaxOut = new EventSeries("EventSeriesMaxOut", "Simulated", 0, entradas, "NaN");
        algorithmMaxMin = new AlgorithmMaxMinInTimeSeries("AlgorithmMax", eventSeries1MaxOut, timeSignals3, eventSignals3);

  

        //Agregamos al SignalManager las señales
        SignalManager.getInstance().addTimeSeries(timeSeries1);

        //Agregamos al SignalManager los algoritmos
        AlgorithmManager.getInstance().addAlgorithm(algorithmMultiplierOffsetSin);
        AlgorithmManager.getInstance().addAlgorithm(algorithmMaxMin);

        //Iniciamos los Datasources
        int iterations = 10000;
        DataSourceSinTimeSeries dataSourceSinTimeSeries = new DataSourceSinTimeSeries(10, 1000, iterations, "TimeSeries1", 10, 0.001);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            System.out.println("No sleep");
        }
        float[] readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1", 0);
        System.out.println("TimeSeries1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1", 0);
        System.out.println("TimeSeries1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
//        SortedSet<Event> eventsCopy = SignalManager.getInstance().getEventsCopy("EventSeries1");
//        System.out.println("EventsSeries1 TAM:" + eventsCopy.size());
        
        CreatorUserInterface.main(args);

    }
}
