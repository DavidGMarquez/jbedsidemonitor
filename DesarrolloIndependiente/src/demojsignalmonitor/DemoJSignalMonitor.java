package demojsignalmonitor;

import java.awt.Color;
import java.util.*;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitorDataSourceAdapter;
import net.javahispano.jsignalwb.jsignalmonitor.defaultmarks.*;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorAnnotation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.JSignalMonitorMark;

public class DemoJSignalMonitor extends JSignalMonitorDataSourceAdapter {

    private final int registerLength = 1000;
    float[][] data;
    private short[][] emphasis;

    private HashMap<String, Integer> mapingFromSinalNamesToChannels = new HashMap<String, Integer>();
    private HashMap<String, LinkedList<JSignalMonitorMark>> mapingFromSinalNamesToMarks =
            new HashMap<String, LinkedList<JSignalMonitorMark>>();

    private LinkedList<JSignalMonitorMark> marksOfSignal1 = new LinkedList<JSignalMonitorMark>();
    private LinkedList<JSignalMonitorMark> marksOfSignal2 = new LinkedList<JSignalMonitorMark>();
    private LinkedList<JSignalMonitorMark> marksOfSignal3 = new LinkedList<JSignalMonitorMark>();


    private ArrayList<String> availableCategoriesOfAnnotations = new ArrayList<String>();
    private LinkedList<JSignalMonitorAnnotation> anotaionList = new LinkedList<JSignalMonitorAnnotation>();

    public DemoJSignalMonitor() {
        generateData();
        generateMappings();
        generateSomeMarksForSignal1();
        generateSomeMarksForSignal2();
        generateSomeMarksForSignal3();
        generateSomeAnnotaions();

    }

    public ArrayList<String> getAvailableCategoriesOfAnnotations() {
        return availableCategoriesOfAnnotations;
    }

    public float[] getChannelData(String signalName, long firstValueInMiliseconds,
                                  long lastValueInMiliseconds) {
        int signal = mapingFromSinalNamesToChannels.get(signalName);

        if (signal == 2) {
            firstValueInMiliseconds = correctBeginingSignal2(firstValueInMiliseconds);
            lastValueInMiliseconds = correctEndSignal2(lastValueInMiliseconds);
        }

        if (lastValueInMiliseconds < 0) {
            return new float[1];
        }

        int firstSample = (int) firstValueInMiliseconds / 1000;
        int lastSample = (int) lastValueInMiliseconds / 1000;
        float[] tmp = new float[lastSample - firstSample + 1];
        for (int i = 0; i < tmp.length && (firstSample + i) < data[signal].length; i++) {
            tmp[i] = data[signal][firstSample + i];
        }
        return tmp;
    }

    public float getChannelValueAtTime(String signalName, long timeInMiliseconds) {
        int signal = mapingFromSinalNamesToChannels.get(signalName);
        int sample = (int) timeInMiliseconds / 1000;
        if (sample < 0) {
            return 0;
        }
        return data[signal][sample];
    }

    public List<JSignalMonitorAnnotation> getAnnotations(long firstValue, long lastValue) {
        ArrayList<JSignalMonitorAnnotation> marksToReturn = new ArrayList<JSignalMonitorAnnotation>();
        for (JSignalMonitorAnnotation mp : anotaionList) {
            if (mp.isInterval()) {
                if (mp.getEndTime() >= firstValue && mp.getAnnotationTime() < lastValue) {
                    marksToReturn.add(mp);
                }
            } else {
                if (mp.getAnnotationTime() >= firstValue && mp.getAnnotationTime() < lastValue) {
                    marksToReturn.add(mp);
                }
            }
        }
        return marksToReturn;
    }

    public List<JSignalMonitorMark> getChannelMark(String signalName, long firstValue, long lastValue) {

        LinkedList<JSignalMonitorMark> marks = mapingFromSinalNamesToMarks.get(signalName);

        ArrayList<JSignalMonitorMark> marksToReturn = new ArrayList<JSignalMonitorMark>();
        for (JSignalMonitorMark mp : marks) {
            if (mp.isInterval()) {
                if (mp.getEndTime() >= firstValue && mp.getMarkTime() < lastValue) {
                    marksToReturn.add(mp);
                }
            } else {
                if (mp.getMarkTime() >= firstValue && mp.getMarkTime() < lastValue) {
                    marksToReturn.add(mp);
                }
            }
        }
        return marksToReturn;
    }

    public short[] getSignalEmphasisLevels(String signalName, long firstValueInMiliseconds,
                                           long lastValueInMiliseconds) {
        int signal = mapingFromSinalNamesToChannels.get(signalName);

        if (signal == 2) {
            firstValueInMiliseconds = correctBeginingSignal2(firstValueInMiliseconds);
            lastValueInMiliseconds = correctEndSignal2(lastValueInMiliseconds);
        }

        if (lastValueInMiliseconds < 0) {
            return new short[1];
        }

        int firstSample = (int) firstValueInMiliseconds / 1000;
        int lastSample = (int) lastValueInMiliseconds / 1000;
        short[] tmp = new short[lastSample - firstSample + 1];
        for (int i = 0; i < tmp.length && (firstSample + i) < data[signal].length; i++) {
            tmp[i] = emphasis[signal][firstSample + i];
        }
        return tmp;
    }

    private long correctEndSignal2(long lastValueInMiliseconds) {
        lastValueInMiliseconds -= 100000;
        return lastValueInMiliseconds;
    }

    private long correctBeginingSignal2(long firstValueInMiliseconds) {
        firstValueInMiliseconds -= 100000; //esta seal limpieza 100 segundos despues
        if (firstValueInMiliseconds < 0) {
            firstValueInMiliseconds = 0;
        }
        return firstValueInMiliseconds;
    }

    private void generateData() {
        data = new float[3][1000];
        emphasis = new short[3][1000];

        for (int i = 0; i < registerLength; i++) {
            data[0][i] = (int) (50 + 50 * Math.cos(i / 20.0));
            if (data[0][i] > 50) {
                emphasis[0][i] = (short) (data[0][i]);
            }
            data[1][i] = (int) (50 * Math.sin(i / 20.0));

            if (data[1][i] < 0) {
                emphasis[1][i] = (short) ( -2 * data[1][i]);
            }
        }

        for (int i = 0; i < registerLength-200; i++) {
            data[2][i] = i;
        }
    }

    private void generateMappings() {
        mapingFromSinalNamesToChannels.put("Signal 1", 0);
        mapingFromSinalNamesToChannels.put("Signal 2", 1);
        mapingFromSinalNamesToChannels.put("Signal 3", 2);

        mapingFromSinalNamesToMarks.put("Signal 1", marksOfSignal1);
        mapingFromSinalNamesToMarks.put("Signal 2", marksOfSignal2);
        mapingFromSinalNamesToMarks.put("Signal 3", marksOfSignal3);
    }

    private void generateSomeMarksForSignal1() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 1; j < data[i].length; j++) {
                if (j % 100 == 0) {

                    DefaultIntervalMark m = new DefaultIntervalMark();
                    m.setMarkTime(j * 1000 - 5000);
                    m.setEndTime(j * 1000 + 5000);
                    marksOfSignal2.add(m);
                }
            }
        }
    }

    private void generateSomeMarksForSignal2() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 1; j < data[i].length; j++) {
                if (j % 63 == 0) {
                    DefaultIntervalMark m = new DefaultIntervalMark();
                    m.setMarkTime(j * 1000 - 10000);
                    m.setEndTime(j * 1000 + 10000);
                    if (data[i][j] > 95) {
                        m.setTitle("Máximo");
                        m.setColor(Color.red);
                    } else {
                        m.setTitle("Mínimo");
                        m.setColor(Color.blue);
                    }
                    marksOfSignal1.add(m);
                }

            }
        }
    }

    private void generateSomeMarksForSignal3() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 101; j < data[i].length - 100; j++) {
                double randomNumber = Math.random() * 1000;
                if (randomNumber >= 998) {
                    DefaultIntervalMark m = new DefaultIntervalMark();
                    m.setMarkTime(j * 1000 - 10000);
                    m.setEndTime(j * 1000 + 10000);
                    m.setColor(Color.darkGray);
                    marksOfSignal3.add(m);
                } else if (randomNumber >= 990) {
                    DefaultInstantMark ms = new DefaultInstantMark();
                    ms.setMarkTime(j * 1000);
                    ms.setIsImage(true);
                    marksOfSignal3.add(ms);
                }
            }
        }
    }

    private void generateSomeAnnotaions() {
        availableCategoriesOfAnnotations.add("Annotation1");
        availableCategoriesOfAnnotations.add("Annotation2");
        availableCategoriesOfAnnotations.add("AnnotationInst");

        for (int j = 1; j < registerLength; j++) {
            double randomNumber = Math.random() * 1000;
            if (randomNumber >= 990) {
                DefaultIntervalAnnotation anotaion = new DefaultIntervalAnnotation();
                anotaion.setAnnotationTime(j * 1000 - 10000);
                anotaion.setEndTime(j * 1000 + 10000);
                anotaion.setColor(Color.BLUE);
                anotaion.setTitle("Type 1");
                anotaion.setCategory("Annotation1");
                anotaionList.add(anotaion);
            } else if (randomNumber >= 980) {
                DefaultIntervalAnnotation anotaion = new DefaultIntervalAnnotation();
                anotaion.setAnnotationTime(j * 1000 - 5000);
                anotaion.setEndTime(j * 1000 + 5000);
                anotaion.setColor(Color.orange);
                anotaion.setTitle("Type 2");
                anotaion.setCategory("Annotation2");
                anotaionList.add(anotaion);
            } else if (randomNumber >= 960) {
                DefaultInstantAnnotation anotaion = new DefaultInstantAnnotation();
                anotaion.setAnnotationTime(j * 1000 - 5000);
                anotaion.setIsImage(true);
                anotaion.setTitle("Type 3");
                anotaion.setCategory("AnnotationInst");
                anotaionList.add(anotaion);
            }
        }
    }

}
