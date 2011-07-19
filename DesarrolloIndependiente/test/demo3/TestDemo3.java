/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo3;

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
import demo1.DataSourceSinTimeSeries;
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
public class TestDemo3 {

    public TestDemo3() {
    }

    public static void main(String[] args) {
        TimeSeries timeSeries1;
        //Entrada un seno
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 0, 250, "mv");
        //Algoritmo sobre la primera señal
        AlgorithmDefaultImplementation algorithmDerivateTimeSeries;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("TimeSeries1_Algorithm1", "Algorithm1", 0, 250, "NaN");
        algorithmDerivateTimeSeries = new AlgorithmDerivateTimeSeries("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);


        //Algoritmo sobre la primera de las señales para detectar maximos y mínimos
        AlgorithmDefaultImplementation algorithmLatidoInTimeSeries;
        EventSeries eventSeries1MaxOut;
        LinkedList<String> eventSignals3;
        LinkedList<String> timeSignals3;
        eventSignals3 = new LinkedList<String>();
        timeSignals3 = new LinkedList<String>();
        timeSignals3.add("TimeSeries1_Algorithm1");

        ArrayList<String> entradas = new ArrayList<String>();
        entradas.add("TimeSeries1_Algorithm1");
        entradas.add("TimeSeries1");
        eventSeries1MaxOut = new EventSeries("Latidos", "AlgorithmLatidos", 0, entradas, "NaN");
        algorithmLatidoInTimeSeries = new AlgorithmLatidoInTimeSeries("AlgorithmLatidos", eventSeries1MaxOut, timeSignals3, eventSignals3);
        SignalManager.getInstance().getJSignalAdapter().setAnnotationIntervalSize(100);
  

        //Agregamos al SignalManager las señales
        SignalManager.getInstance().addTimeSeries(timeSeries1);

        //Agregamos al SignalManager los algoritmos
        AlgorithmManager.getInstance().addAlgorithm(algorithmDerivateTimeSeries);
        AlgorithmManager.getInstance().addAlgorithm(algorithmLatidoInTimeSeries);

        //Iniciamos los Datasources
        int iterations = 10000;
        DataSourceFileTimeSeries dataSourceFileTimeSeries = new DataSourceFileTimeSeries(10, 250, iterations, "TimeSeries1", 10, "ecg.txt");
       // DataSourceSinTimeSeries dataSourceSinTimeSeries = new DataSourceSinTimeSeries(10, 1000, iterations, "TimeSeries1", 10, 0.001);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            System.out.println("No sleep");
        }
        CreatorUserInterface.main(args);

    }
}
