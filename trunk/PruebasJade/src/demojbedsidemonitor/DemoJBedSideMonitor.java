package demojbedsidemonitor;

import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmManager;
import java.util.*;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSourceAdapter;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;
import signals.IllegalReadException;
import signals.SignalManager;
import signals.TimeSeries;
import signals.WriterRunnableTimeSeries;

public class DemoJBedSideMonitor extends JSignalMonitorDataSourceAdapter {

    public DemoJBedSideMonitor() {
        generateScenario();
    }

    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        ArrayList<String> availableCategoriesOfAnnotations = new ArrayList<String>();
        return availableCategoriesOfAnnotations;
    }

    public float[] getChannelData(String signalName, long firstValueInMiliseconds,
            long lastValueInMiliseconds) {
        if (lastValueInMiliseconds < 0) {
            return new float[1];
        }

        int frequency = 1000;
        int firstSample = (int) firstValueInMiliseconds / frequency;
        int lastSample = (int) lastValueInMiliseconds / frequency;
        ConsecutiveSamplesAvailableInfo consecutiveSamplesTimeSeries = SignalManager.getInstance().getConsecutiveSamplesTimeSeries(signalName);
        if(lastSample>consecutiveSamplesTimeSeries.getOlderSampleAvailable()+consecutiveSamplesTimeSeries.getSamplesReadyToReadInOrder())
            lastSample=consecutiveSamplesTimeSeries.getOlderSampleAvailable()+consecutiveSamplesTimeSeries.getSamplesReadyToReadInOrder();

        System.out.println("GetChannelData"+signalName+"Desde"+firstSample+"Hasta"+lastSample);
        try {
            if((lastSample-firstSample)>0){
            float[] readFromTimeSeries = SignalManager.getInstance().readSecureFromTimeSeries(signalName, (int) firstSample, (int)lastSample-firstSample);
            return readFromTimeSeries;
            }
 else
            return new float[1];
        } catch (IllegalReadException e) {
            System.out.println("Exception"+e.getMessage());
            return new float[1];
        }
        
    }

    public float getChannelValueAtTime(String signalName, long timeInMiliseconds) {


        int sample = (int) timeInMiliseconds / 1000;
        if (sample < 0) {
            return 0;
        }
        System.out.println("GetChannelValue"+signalName+"Sample"+sample);
         try {
            return SignalManager.getInstance().readSecureFromTimeSeries(signalName, (int) sample, (int) sample + 1)[0];
        } catch (IllegalReadException e) {
            return 0;
        }

        
    }

    public List<JSignalMonitorAnnotation> getAnnotations(long firstValue, long lastValue) {
        ArrayList<JSignalMonitorAnnotation> marksToReturn = new ArrayList<JSignalMonitorAnnotation>();
        return marksToReturn;
    }

    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue, long lastValue) {

        ArrayList<JSignalMonitorMark> marksToReturn = new ArrayList<JSignalMonitorMark>();
        return marksToReturn;
    }

    public short[] getSignalEmphasisLevels(String signalName, long firstValueInMiliseconds,
            long lastValueInMiliseconds) {
        if (lastValueInMiliseconds < 0) {
            return new short[1];
        }
        int frequency = 1;
        int firstSample = (int) firstValueInMiliseconds / frequency;
        int lastSample = (int) lastValueInMiliseconds / frequency;
        float[] readFromTimeSeries = SignalManager.getInstance().readSecureFromTimeSeries(signalName, (int) firstSample, (int) lastSample);
        short[] tmp = new short[readFromTimeSeries.length];
        for (int i = 0; i < readFromTimeSeries.length; i++) {
            tmp[i] = (short) readFromTimeSeries[i];
        }
        return tmp;
    }

    private void generateScenario() {
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
        SinTimeSeriesGeneratorOrder sinTimeSeriesGenerator = new SinTimeSeriesGeneratorOrder(10, 1, 100, "TimeSeries1");
           try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
           
        }

    }
}
