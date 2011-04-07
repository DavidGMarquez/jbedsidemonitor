package completeTestsTimeSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmDefaultImplementationOneSignal;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import signals.EventSeries;
import signals.SignalManager;
import signals.TimeSeries;
import static org.junit.Assert.*;

public class TimeSeries_CompleteTest {

    TimeSeries timeSeries1;
    TimeSeries timeSeries2;
    TimeSeries timeSeries1_out;
    TimeSeries timeSeries2_out;
    TimeSeries timeSeries1_out_out;
    TimeSeries timeSeries2_out_out;
    TimeSeries timeSeries1_out_out_out;
    TimeSeries timeSeries2_out_out_out;
    TimeSeries timeSeries3;
    TimeSeries timeSeries3_out;
    EventSeries eventSeries1;
    EventSeries eventSeries2;
    EventSeries eventSeries3;
    AlgorithmDefaultImplementation algorithm1;
    AlgorithmDefaultImplementation algorithm2;
    AlgorithmDefaultImplementation algorithm3;
    AlgorithmDefaultImplementationOneSignal algorithm4;
    AlgorithmDefaultImplementation algorithm5;
    AlgorithmDefaultImplementation algorithm6;
    AlgorithmDefaultImplementation algorithm7;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;
    LinkedList<String> eventSignals2;
    LinkedList<String> timeSignals2;
    LinkedList<String> eventSignals3;
    LinkedList<String> timeSignals3;
    LinkedList<String> eventSignals4;
    LinkedList<String> timeSignals4;
    LinkedList<String> eventSignals5;
    LinkedList<String> timeSignals5;
    LinkedList<String> eventSignals6;
    LinkedList<String> timeSignals6;
    LinkedList<String> eventSignals7;
    LinkedList<String> timeSignals7;
//@pendiente de vez en cuando aparece un 0 al final no se porque
    //@pendiente quizas hiciera falta un notify all cuando liberamos locks?
    //@pendiente detener los threads y los completions servers

    public TimeSeries_CompleteTest() {
    }

    @Before
    public void setUp() {
        AuxTestUtilities.reset();

        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");
        timeSeries2 = new TimeSeries("TimeSeries2", "Simulated", 1, 100, "mv");
        timeSeries1_out = new TimeSeries("TimeSeries1_Algorithm1", "Simulated", 1, 100, "mv");
        timeSeries2_out = new TimeSeries("TimeSeries2_Algorithm1", "Simulated", 1, 100, "mv");
        timeSeries1_out_out = new TimeSeries("TimeSeries1_Algorithm1_Algorithm2", "Simulated", 1, 100, "mv");
        timeSeries2_out_out = new TimeSeries("TimeSeries2_Algorithm1_Algorithm3", "Simulated", 1, 100, "mv");
        timeSeries1_out_out_out = new TimeSeries("TimeSeries1_Algorithm1_Algorithm2_Algorithm4", "Simulated", 1, 100, "mv");
        timeSeries2_out_out_out = new TimeSeries("TimeSeries2_Algorithm1_Algorithm3_Algorithm4", "Simulated", 1, 100, "mv");
        timeSeries3 = new TimeSeries("TimeSeries3", "Simulated", 1, 100, "mv");
        timeSeries3_out = new TimeSeries("TimeSeries3_Algorithm4", "Simulated", 1, 100, "mv");

        eventSeries1 = new EventSeries("EventSeries1", "Simulated", 1, new ArrayList<String>(), "mv");
        eventSeries2 = new EventSeries("EventSeries2", "Simulated", 1, new ArrayList<String>(), "mv");
        eventSeries3 = new EventSeries("EventSeries3", "Simulated", 1, new ArrayList<String>(), "mv");

        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");
        timeSignals1.add("TimeSeries2");

        eventSignals2 = new LinkedList<String>();
        timeSignals2 = new LinkedList<String>();
        timeSignals2.add("TimeSeries1_Algorithm1");

        eventSignals3 = new LinkedList<String>();
        timeSignals3 = new LinkedList<String>();
        timeSignals3.add("TimeSeries2_Algorithm1");

        eventSignals4 = new LinkedList<String>();
        timeSignals4 = new LinkedList<String>();
        timeSignals4.add("TimeSeries1_Algorithm1_Algorithm2");
        timeSignals4.add("TimeSeries2_Algorithm1_Algorithm3");
        timeSignals4.add("TimeSeries3");

        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_1", "Algorithm1", 0, 300, "NaN");
        // EventSeries eventSeriesOut2 = new EventSeries("Out_Algorithm_2", "Algorithm2", 0, new ArrayList<String>(), "NaN");
        TimeSeries timeSeriesOut2 = new TimeSeries("Out_Algorithm_2", "Algorithm1", 0, 300, "NaN");
        TimeSeries timeSeriesOut3 = new TimeSeries("Out_Algorithm_3", "Algorithm3", 0, 300, "NaN");
        TimeSeries timeSeriesOut4 = new TimeSeries("Out_Algorithm_4", "Algorithm3", 0, 300, "NaN");
        algorithm1 = new AlgorithmStupid2XMultiSignalsImplementationOrder("Algorithm1", timeSeriesOut1, timeSignals1, eventSignals1);
        algorithm2 = new AlgorithmStupidRootMultiSignalsImplementation("Algorithm2", timeSeriesOut2, timeSignals2, eventSignals2);
        algorithm3 = new AlgorithmStupidRootMultiSignalsImplementation("Algorithm3", timeSeriesOut3, timeSignals3, eventSignals3);
        algorithm4 = new AlgorithmStupidFileMultiSignalsImplementationOrder("Algorithm4", timeSeriesOut4, timeSignals4, eventSignals4);


    }

    @Test
    public void testBasicMassiveAlgorithm() {
        AuxTestUtilities.reset();
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        SignalManager.getInstance().addTimeSeries(timeSeries2);
        SignalManager.getInstance().addTimeSeries(timeSeries1_out);
        SignalManager.getInstance().addTimeSeries(timeSeries2_out);
        SignalManager.getInstance().addTimeSeries(timeSeries1_out_out);
        SignalManager.getInstance().addTimeSeries(timeSeries2_out_out);
        SignalManager.getInstance().addTimeSeries(timeSeries3);
        AlgorithmManager.getInstance().addAlgorithm(algorithm1);
        AlgorithmManager.getInstance().addAlgorithm(algorithm2);
        AlgorithmManager.getInstance().addAlgorithm(algorithm3);
        AlgorithmManager.getInstance().addAlgorithm(algorithm4);
        int iterations = 1000;

        SinTimeSeriesGeneratorOrder sinTimeSeriesGenerator = new SinTimeSeriesGeneratorOrder(10, 100, iterations, "TimeSeries1");
        SerialTimeSeriesSeriesGeneratorOrder serialTimeSeriesSeriesGenerator = new SerialTimeSeriesSeriesGeneratorOrder(10, 100, iterations, "TimeSeries2");
        SumSerial_SinTimeSeriesGeneratorOrder sumSerial_SinTimeSeriesGeneratorOrder = new SumSerial_SinTimeSeriesGeneratorOrder(10, 100, iterations, "TimeSeries3");
        try {
            Thread.sleep(180000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CompleteTestOrder.class.getName()).log(Level.SEVERE, null, ex);
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

        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries2", 0);
        System.out.println("TimeSeries2 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length == (iterations + 1) * 10);
        //System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries2.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            //System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries2[i] + "   " +2*i);
            assertEquals(readNewFromTimeSeriesTimeSeries[i], i, 0.001);
        }

        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1", 0);
        System.out.println("TimeSeries1_Algorithm1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        //System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            //  if(readNewFromTimeSeriesTimeSeries1[i]!=2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))))
            //    System.out.print("_");
            //System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i] + "   " + 2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
            assertEquals(readNewFromTimeSeriesTimeSeries[i], 2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))), 0.001);
        }


        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries2_Algorithm1", 0);
        System.out.println("TimeSeries2_Algorithm1 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            assertEquals(readNewFromTimeSeriesTimeSeries[i], 2 * i, 0.001);
        }
        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1_Algorithm2", 0);
        System.out.println("TimeSeries1_Algorithm1_Algorithm2 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            float value_compare = (float) Math.sqrt(2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
            if (Float.compare(value_compare, Float.NaN) == 0) {
                value_compare = 0;
            }
            assertEquals(readNewFromTimeSeriesTimeSeries[i], value_compare, 0.001);
        }
        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries2_Algorithm1_Algorithm3", 0);
        System.out.println("TimeSeries2_Algorithm1_Algorithm3 TAM:" + readNewFromTimeSeriesTimeSeries.length);
        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            float value_compare = (float) Math.sqrt(2 * i);
            if (Float.compare(value_compare, Float.NaN) == 0) {
                value_compare = 0;
            }
            assertEquals(readNewFromTimeSeriesTimeSeries[i], value_compare, 0.001);
        }

        readNewFromTimeSeriesTimeSeries = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries3", 0);
        System.out.println("TimeSeries3 TAM:" + readNewFromTimeSeriesTimeSeries.length);

        assertTrue(readNewFromTimeSeriesTimeSeries.length > 0);
        for (int i = 0; i < readNewFromTimeSeriesTimeSeries.length - 1; i++) {
            float value_compare = (float) i + ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100)));

            if (Float.compare(value_compare, Float.NaN) == 0) {
                value_compare = 0;
            }
            assertEquals(readNewFromTimeSeriesTimeSeries[i], value_compare, 0.001);

        }
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        String line;
        String line2;

        float[] dataRead = new float[999999];
        file = new File("TimeSeries1_Algorithm1_Algorithm2");
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        br = new BufferedReader(fr);

        int contador = 0;
        try {

            while ((line = br.readLine()) != null) {
                if ((line2 = br.readLine()) != null) {
                    contador++;
                    dataRead[new Integer(line).intValue()] = new Float(line2).floatValue();
                }
            }
            assertTrue(contador < (999999 - 10000));
            assertTrue(contador > 0);
        } catch (IOException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        file = new File("TimeSeries2_Algorithm1_Algorithm3");
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        br = new BufferedReader(fr);

        int contador2 = 0;
        try {

            while ((line = br.readLine()) != null) {
                if ((line2 = br.readLine()) != null) {
                    contador2++;
                    dataRead[new Integer(line).intValue()] += new Float(line2).floatValue();
                }
            }
            assertTrue(contador2 < (999999 - 10000));
            assertTrue(contador2 > 0);
        } catch (IOException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        file = new File("TimeSeries3");
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        br = new BufferedReader(fr);

        int contador3 = 0;
        try {

            while ((line = br.readLine()) != null) {
                if ((line2 = br.readLine()) != null) {
                    contador3++;
                    dataRead[new Integer(line).intValue()] += new Float(line2).floatValue();
                }
            }
            assertTrue(contador3 < (999999 - 10000));
            assertTrue(contador3 > 0);
        } catch (IOException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Contador 1" + contador);
        System.out.println("Contador 2" + contador2);
        System.out.println("Contador 3" + contador3);
        int contadorFin;
        if (contador > contador2) {
            contadorFin = contador2;
        } else {
            contadorFin = contador;
        }
        if (contadorFin > contador3) {
            contadorFin = contador3;
        }


        System.out.println(contadorFin);
        for (int i = 0; i < contadorFin; i++) {
            float value_compare1 = (float) Math.sqrt(2 * i);
            float value_compare2 = (float) Math.sqrt(2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
            float value_compare3 = (float) i + ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100)));

            if (Float.compare(value_compare1, Float.NaN) == 0) {
                value_compare1 = 0;
            }
            if (Float.compare(value_compare2, Float.NaN) == 0) {
                value_compare2 = 0;
            }
            if (Float.compare(value_compare3, Float.NaN) == 0) {
                value_compare3 = 0;
            }
            assertEquals(value_compare1+value_compare2+value_compare3, dataRead[i], 000.1);
        }

    }

    /*   @Test
    public void testBasic1Algorithm() {
    AuxTestUtilities.reset();
    SignalManager.getInstance().addTimeSeries(timeSeries1);
    SignalManager.getInstance().addTimeSeries(timeSeries2);
    SignalManager.getInstance().addTimeSeries(timeSeries1_out);
    SignalManager.getInstance().addTimeSeries(timeSeries2_out);
    SignalManager.getInstance().addTimeSeries(timeSeries1_out_out);
    SignalManager.getInstance().addTimeSeries(timeSeries2_out_out);
    SignalManager.getInstance().addTimeSeries(timeSeries1_out_out_out);
    SignalManager.getInstance().addTimeSeries(timeSeries2_out_out_out);
    SignalManager.getInstance().addTimeSeries(timeSeries3_out);
    AlgorithmManager.getInstance().addAlgorithm(algorithm1);
    AlgorithmManager.getInstance().addAlgorithm(algorithm2);
    AlgorithmManager.getInstance().addAlgorithm(algorithm3);
    //    AlgorithmManager.getInstance().addAlgorithm(algorithm4);
    SinTimeSeriesGenerator sinTimeSeriesGenerator = new SinTimeSeriesGenerator(10, 10, 500, "TimeSeries1");
    SerialTimeSeriesSeriesGenerator serialTimeSeriesSeriesGenerator = new SerialTimeSeriesSeriesGenerator(10, 100, 40, "TimeSeries2");



    try {
    Thread.sleep(10000);
    } catch (InterruptedException ex) {
    Logger.getLogger(TimeSeries_CompleteTest.class.getName()).log(Level.SEVERE, null, ex);
    }

    float[] readNewFromTimeSeriesTimeSeries1 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1", 0);
    //@pendiente de vez en cuando aparece un 0 al final no se porque
    System.out.println("Tamano" + readNewFromTimeSeriesTimeSeries1.length);
    for (int i = 0; i < readNewFromTimeSeriesTimeSeries1.length - 1; i++) {
    System.out.println(i + "  " + readNewFromTimeSeriesTimeSeries1[i] + "   " + 2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))));
    assertEquals(readNewFromTimeSeriesTimeSeries1[i], 2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100))), 0.001);

    }
    float[] readNewFromTimeSeriesTimeSeries2 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries2_Algorithm1", 0);
    for (int i = 0; i < readNewFromTimeSeriesTimeSeries2.length - 1; i++) {
    assertEquals(readNewFromTimeSeriesTimeSeries2[i], 2 * i, 0.001);
    }
    readNewFromTimeSeriesTimeSeries1 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1_Algorithm2", 0);
    for (int i = 0; i < readNewFromTimeSeriesTimeSeries1.length - 1; i++) {
    assertEquals(readNewFromTimeSeriesTimeSeries1[i], Math.sqrt(2 * ((float) Math.sin(((float) ((int) (i / 10))) / 10 + ((float) (i % 10) / 100)))), 0.001);

    }
    readNewFromTimeSeriesTimeSeries2 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries2_Algorithm1_Algorithm3", 0);
    for (int i = 0; i < readNewFromTimeSeriesTimeSeries2.length - 1; i++) {
    assertEquals(readNewFromTimeSeriesTimeSeries2[i], Math.sqrt(2 * i), 0.001);
    }
    readNewFromTimeSeriesTimeSeries1 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries1_Algorithm1_Algorithm2_Algorithm4", 0);
    for (int i = 0; i < readNewFromTimeSeriesTimeSeries1.length - 1; i++) {
    assertEquals(readNewFromTimeSeriesTimeSeries1[i], 2 * (Math.sqrt(2 * ((float) Math.sin(((float) ((int) (i / 10)))) / 10 + ((float) (i % 10) / 100)))), 0.001);

    }
    readNewFromTimeSeriesTimeSeries2 = SignalManager.getInstance().readNewFromTimeSeries("TimeSeries2_Algorithm1_Algorithm3_Algorithm4", 0);
    for (int i = 0; i < readNewFromTimeSeriesTimeSeries2.length - 1; i++) {
    assertEquals(readNewFromTimeSeriesTimeSeries2[i], 2 * (Math.sqrt(2 * i)), 0.001);
    }

    }*/
}
