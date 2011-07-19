/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo2;

import demo2.*;
import java.util.ArrayList;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import signals.EventSeries;
import signals.SignalManager;
import userInterface.CreatorUserInterface;

/**
 *
 * @author USUARIO
 */
public class TestDemo2 {

    public TestDemo2() {
    }

    public static void main(String[] args) {
        TimeSeries timeSeries1;
        //Entrada un seno
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 0, 100, "mv");
        TimeSeries timeSeries2;
        //Entrada un coseno
        timeSeries2 = new TimeSeries("TimeSeries2", "Simulated", 0, 100, "mv");

        //Algoritmo sobre la primera señal
        AlgorithmDefaultImplementation algorithmPowTimeSeries;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("TimeSeries1_Algorithm1", "Algorithm1", 0, 100, "NaN");
        algorithmPowTimeSeries = new AlgorithmPowTimeSeries("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1, new Float(2));

       //Algoritmo sobre la salida del anterior
        AlgorithmDefaultImplementation algorithmPowTimeSeriesOut;
        LinkedList<String> eventSignals2;
        LinkedList<String> timeSignals2;
        eventSignals2 = new LinkedList<String>();
        timeSignals2 = new LinkedList<String>();
        timeSignals2.add("TimeSeries1_Algorithm1");
        TimeSeries timeSeriesOut2 = new TimeSeries("TimeSeries1_Algorithm1_Algorithm2", "Algorithm2", 0, 100, "NaN");
        algorithmPowTimeSeriesOut = new AlgorithmPowTimeSeries("Algorithm2", timeSeriesOut2, timeSignals2, eventSignals2, new Float(0.1));

   //Algoritmo raiz
        AlgorithmDefaultImplementation algorithmSqrtTimeSeries;
        LinkedList<String> eventSignals3;
        LinkedList<String> timeSignals3;
        eventSignals3 = new LinkedList<String>();
        timeSignals3 = new LinkedList<String>();
        timeSignals3.add("TimeSeries2");
        TimeSeries timeSeriesOut3 = new TimeSeries("TimeSeries2_Algorithm3", "Algorithm3", 0, 100, "NaN");
        algorithmSqrtTimeSeries = new AlgorithmSqrtTimeSeries("Algorithm3", timeSeriesOut3, timeSignals3, eventSignals3);

        
  

        //Agregamos al SignalManager las señales
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries2);

        //Agregamos al SignalManager los algoritmos
        AlgorithmManager.getInstance().addAlgorithm(algorithmPowTimeSeries);
        AlgorithmManager.getInstance().addAlgorithm(algorithmPowTimeSeriesOut);
        AlgorithmManager.getInstance().addAlgorithm(algorithmSqrtTimeSeries);

        //Iniciamos los Datasources
        int iterations = 10000;
        DataSourceSinTimeSeries dataSourceSinTimeSeries = new DataSourceSinTimeSeries(10, 100, iterations, "TimeSeries1", 10, 0.01);
        DataSourceCosTimeSeries dataSourceCosTimeSeries = new DataSourceCosTimeSeries(10, 100, iterations, "TimeSeries2", 10, 0.01);
     
    
        CreatorUserInterface.main(args);

    }
}
