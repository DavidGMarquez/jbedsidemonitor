/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

import guiTest.*;
import completeTestsTimeSeries.*;
import algorithms.AlgorithmDefaultImplementation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JFrame;
import signals.ReadResult;
import signals.ReadResult;
import signals.ReadResultEventSeries;
import signals.ReadResultEventSeries;
import signals.ReadResultMultiSignal;
import signals.ReadResultMultiSignal;
import signals.ReadResultTimeSeries;
import signals.ReadResultTimeSeries;
import signals.Series;
import signals.Series;
import signals.SignalManager;
import signals.SignalManager;
import signals.TimeSeries;
import signals.WriterRunnableTimeSeries;
import signals.WriterRunnableTimeSeries;

/**
 *
 * @author USUARIO
 */
public class AlgorithmMultiplierOffsetTimeSeries extends AlgorithmDefaultImplementation {

    private Map<String, Integer> indexOfWrite;
    private Float multiplier;
    private Float offset;

    public AlgorithmMultiplierOffsetTimeSeries(String identifier, Series signalToWrite, LinkedList<String> timeSeries, LinkedList<String> eventSeries, Float multiplier,Float offset) {
        super(identifier, signalToWrite, timeSeries, eventSeries);
        indexOfWrite = new HashMap<String, Integer>();
        this.multiplier = multiplier;
        this.offset=offset;
    }

    public boolean execute(ReadResult readResult) {
        if (readResult instanceof ReadResultMultiSignal) {
            ReadResultMultiSignal readResultMultiSignal = (ReadResultMultiSignal) readResult;
            readResultMultiSignal.getReadResults().size();
            for (ReadResult readResultOne : readResultMultiSignal.getReadResults()) {
                if (readResultOne instanceof ReadResultTimeSeries) {
                    process((ReadResultTimeSeries) readResultOne);
                }
            }
        }
        if (readResult instanceof ReadResultTimeSeries) {
            process((ReadResultTimeSeries) readResult);
        }
        if (readResult instanceof ReadResultEventSeries){
            ReadResultEventSeries readResultEventSeries= (ReadResultEventSeries) readResult;

        }
        return true;
    }

    public void process(ReadResultTimeSeries readResultTimeSeries) {
        float[] data = readResultTimeSeries.getData();
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] * multiplier.floatValue() +offset.floatValue();
        }
        if (indexOfWrite.get(readResultTimeSeries.getIdentifierSignal()) == null) {
            indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), new Integer(0));
        }
        int auxIndexOfWrite = indexOfWrite.get(readResultTimeSeries.getIdentifierSignal()).intValue();
        WriterRunnableTimeSeries writerRunnableTimeSeries = new WriterRunnableTimeSeries(readResultTimeSeries.getIdentifierSignal() + "_" + this.getIdentifier(), data, readResultTimeSeries.getPosInitToRead());
        indexOfWrite.put(readResultTimeSeries.getIdentifierSignal(), auxIndexOfWrite + data.length);
        this.waitAndSendWriterRunable(writerRunnableTimeSeries);
    }

    public boolean hasConfigurationGui() {
        return true;
    }

    public void showConfigurationGui(JFrame jframe) {
        AlgorithmMultiplierOffsetTimeSeriesGui algorithmMultiplierOffsetTimeSeriesGui = new AlgorithmMultiplierOffsetTimeSeriesGui(jframe, true, this);
        algorithmMultiplierOffsetTimeSeriesGui.setLocationRelativeTo(jframe);
        algorithmMultiplierOffsetTimeSeriesGui.setVisible(true);
    }

    public Float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Float multiplier) {
        this.multiplier = multiplier;
    }

    public Float getOffset() {
        return offset;
    }

    public void setOffset(Float offset) {
        this.offset = offset;
    }

}
