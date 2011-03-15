/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package completeTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import signals.EventSeries;
import signals.SignalManager;
import signals.WriterRunnableTimeSeries;
import java.util.LinkedList;
import algorithms.AlgorithmDefaultImplementation;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.TimeSeries;
import algorithms.AlgorithmManager;
import auxiliarTools.AuxTestUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import signals.EventSeries;
import signals.LockManager;
import signals.SignalManager;
import signals.WriterRunnableEventSeries;
import signals.WriterRunnableTimeSeries;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class Basic2AlgorithmsTest {

    public Basic2AlgorithmsTest() {
    }
    TimeSeries timeSeries1;
    TimeSeries timeSeries2;
    TimeSeries timeSeries3;
    EventSeries eventSeries1;
    EventSeries eventSeries2;
    EventSeries eventSeries3;
    AlgorithmDefaultImplementation algorithmIN;
    AlgorithmDefaultImplementation algorithmOUT;
    LinkedList<String> eventSignals1;
    LinkedList<String> timeSignals1;
    LinkedList<String> eventSignals2;
    LinkedList<String> timeSignals2;
    String nameAlgorithmOUT;
    @Before
    public void setUp() {
        AuxTestUtilities.reset();

        timeSeries1 = new TimeSeries("TimeSeries1", "Simulated", 1, 100, "mv");

        eventSignals1 = new LinkedList<String>();
        timeSignals1 = new LinkedList<String>();
        timeSignals1.add("TimeSeries1");

        eventSignals2 = new LinkedList<String>();
        timeSignals2 = new LinkedList<String>();
        timeSignals2.add("Out_Algorithm_IN");

        TimeSeries timeSeriesOut1 = new TimeSeries("Out_Algorithm_IN", "Algorithm1", 0, 300, "NaN");
        TimeSeries timeSeriesOut3 = new TimeSeries("Out_Algorithm_OUT", "Algorithm3", 0, 300, "NaN");
        nameAlgorithmOUT="FileAlgorithmOUT";
        algorithmIN = new AlgorithmStupid2XImplementation("AlgorithmIN", timeSeriesOut1, timeSignals1, eventSignals1);
        algorithmOUT = new AlgorithmStupidFileImplementation("AlgorithmOUT", timeSeriesOut3, timeSignals2, eventSignals2,nameAlgorithmOUT);



    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBasico() {
        SignalManager.getInstance().addTimeSeries(timeSeries1);
        AlgorithmManager.getInstance().addAlgorithm(algorithmIN);
        AlgorithmManager.getInstance().addAlgorithm(algorithmOUT);
        WriterRunnableTimeSeries writerRunnableTimeSeries = AuxTestUtilities.generarWriterRunnableTime("TimeSeries1", 1000);
        SignalManager.getInstance().encueWriteOperation(writerRunnableTimeSeries);
        float[] dataToWrite = writerRunnableTimeSeries.getDataToWrite();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        file = new File(nameAlgorithmOUT);
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        br = new BufferedReader(fr);
        String line;
        float[] dataRead=new float[dataToWrite.length];
        try {
            int i=0;
            while ((line = br.readLine()) != null) {
                dataRead[i]=new Float(line).floatValue();
                i++;
            }

        } catch (IOException ex) {
            Logger.getLogger(Basic2AlgorithmsTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0;i < dataRead.length;i++) {
            System.out.println("Comparar "+dataToWrite[i]+" y "+dataRead[i]/2);
           assertTrue(dataToWrite[i]==dataRead[i]/2);
        }
        //@pendiente Test escribir mas de dos señales al mismo tiempo
        //@pendiente hacerlo para señales de eventos
    }

}
