/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo0;

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
public class TestDemo0 {

    public TestDemo0() {
    }

    public static void main(String[] args) {
        TimeSeries timeSeries1;
        //Entrada un seno
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 0, 100, "mv");
        //Algoritmo sobre la primera señal
        AlgorithmDefaultImplementation algorithmPowTimeSeries;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("TimeSeries1_Algorithm1", "Algorithm1", 0, 100, "NaN");
        algorithmPowTimeSeries = new AlgorithmPowTimeSeries("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1, new Float(2));



  

        //Agregamos al SignalManager las señales
        SignalManager.getInstance().addTimeSeries(timeSeries1);

        //Agregamos al SignalManager los algoritmos
        AlgorithmManager.getInstance().addAlgorithm(algorithmPowTimeSeries);

        //Iniciamos los Datasources
        int iterations = 10000;
        DataSourceSinTimeSeries dataSourceSinTimeSeries = new DataSourceSinTimeSeries(10, 100, iterations, "TimeSeries1", 10, 0.01);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            System.out.println("No sleep");
        }
 
        CreatorUserInterface.main(args);

    }
}
