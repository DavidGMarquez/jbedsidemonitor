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
import userInterface.CreatorUserInterface;
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
        TimeSeries timeSeries2;
        //Dos entradas sin y cosh
        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 0, 100, "mv");
        timeSeries2 = new TimeSeries("TimeSeries2", "Simulated", 0, 100, "mv");
        //Primer algoritmo sobre la primera señal
        AlgorithmDefaultImplementation algorithmMultiplierOffsetSin;
        LinkedList<String> eventSignals1;
        LinkedList<String> timeSignals1;
        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
        TimeSeries timeSeriesOut1 = new TimeSeries("TimeSeries1_Algorithm1", "Algorithm1", 0, 100, "NaN");
        algorithmMultiplierOffsetSin = new AlgorithmMultiplierOffsetTimeSeries("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1, new Float(1), new Float(0));


        //El mismo algoritmo pero sobre otra señal
        AlgorithmDefaultImplementation algorithmMultiplierOffsetCosh;
        LinkedList<String> eventSignals2;
        LinkedList<String> timeSignals2;
        eventSignals2 = new LinkedList<String>();
        timeSignals2 = new LinkedList<String>();
        timeSignals2.add("TimeSeries2");
        TimeSeries timeSeriesOut2 = new TimeSeries("TimeSeries2_Algorithm2", "Algorithm2", 0, 100, "NaN");
        algorithmMultiplierOffsetCosh = new AlgorithmMultiplierOffsetTimeSeries("Algorithm2", timeSeriesOut2, timeSignals2, eventSignals2, new Float(1.5), new Float(0));

        //Algoritmo sobre la primera de las señales para detectar maximos y mínimos
        AlgorithmDefaultImplementation algorithmMaxMin;
        EventSeries eventSeries1MaxOut;
        LinkedList<String> eventSignals3;
        LinkedList<String> timeSignals3;
        eventSignals3 = new LinkedList<String>();
        timeSignals3 = new LinkedList<String>();
        timeSignals3.add("TimeSeries1_Algorithm1");

        ArrayList<String> entradas = new ArrayList<String>();
        entradas.add("TimeSeries1_Algorithm1");
        eventSeries1MaxOut = new EventSeries("EventSeriesMaxOut", "Simulated", 0, entradas, "NaN");
        algorithmMaxMin = new AlgorithmMaxMinInTimeSeries("AlgorithmMax", eventSeries1MaxOut, timeSignals3, eventSignals3);

        //Algoritmo acumulator
        AlgorithmDefaultImplementation algorithmAcumulator;
        EventSeries eventSeries2Acumulator;
        LinkedList<String> eventSignals4;
        LinkedList<String> timeSignals4;
        eventSignals4 = new LinkedList<String>();
        timeSignals4 = new LinkedList<String>();
        timeSignals4.add("TimeSeries1");
        timeSignals4.add("TimeSeries2");

        ArrayList<String> entradasAcumultator = new ArrayList<String>();
        entradasAcumultator.add("TimeSeries1");
        entradasAcumultator.add("TimeSeries2");
        eventSeries2Acumulator = new EventSeries("EventSeriesAcumultatorOut", "Simulated", 0, entradasAcumultator, "NaN");
        algorithmAcumulator = new AlgorithmAcumulatorTimeSeries("Algorithm3", eventSeries2Acumulator, timeSignals4, eventSignals4, 1000);


        //Agregamos al SignalManager las señales
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries2);

        //Agregamos al SignalManager los algoritmos
        AlgorithmManager.getInstance().addAlgorithm(algorithmMultiplierOffsetSin);
        AlgorithmManager.getInstance().addAlgorithm(algorithmMultiplierOffsetCosh);
        AlgorithmManager.getInstance().addAlgorithm(algorithmMaxMin);
        AlgorithmManager.getInstance().addAlgorithm(algorithmAcumulator);

        //Iniciamos los Datasources
        int iterations = 10000;
        DataSourceSinTimeSeries dataSourceSinTimeSeries = new DataSourceSinTimeSeries(10, 100, iterations, "TimeSeries1", 10, 0.01);
        DataSourceCosTimeSeries dataSourceCosTimeSeries = new DataSourceCosTimeSeries(10, 100, iterations, "TimeSeries2", 10, 0.01);
        try {
            Thread.sleep(1000);
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
