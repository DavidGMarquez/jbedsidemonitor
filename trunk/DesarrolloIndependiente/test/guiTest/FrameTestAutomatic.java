package guiTest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import net.javahispano.jsignalwb.jsignalmonitor.ChannelProperties;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;
import signals.JSignalAdapter;
import signals.SignalManager;

public class FrameTestAutomatic extends JFrame {

    private BorderLayout borderLayout1 = new BorderLayout();
    private JToolBar jToolBar1 = new JToolBar();
    private JButton jButtonShowXY = new JButton();
    private JButton jButtonAdjustRanges = new JButton();
    private JSignalAdapter jSignalAdapter;
    private JSignalMonitor jSignalMonitor;
    private JButton jButtonDoNotShowSignal3 = new JButton();
    private JButton jButtonShowSignal3 = new JButton();
    private JButton jButtonRealTime = new JButton();

    public FrameTestAutomatic(JSignalAdapter jSignalAdapter) {
        this.jSignalAdapter = jSignalAdapter;
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        //@JSignalMonitor configuracion Inicial
        jSignalMonitor = new JSignalMonitor(jSignalAdapter);
        LinkedList<String> allTimeSeriesNames = jSignalAdapter.getAllTimeSeriesNames();
        for (String signalName : allTimeSeriesNames) {
            /*ChannelProperties properties = new ChannelProperties(signalName, jSignalAdapter.getOrigin(signalName)
                    , jSignalAdapter.getFrecuencySignal(signalName), jSignalAdapter.getDataSize(signalName));*/
            ChannelProperties properties = new ChannelProperties(signalName, 0, 1, jSignalAdapter.getDataSize(signalName));
            System.out.println(signalName+" TamanoSignal:"+jSignalAdapter.getDataSize(signalName));
            properties.setHasEmphasis(false);
            //@pendiente tocar esto de nuevo y calcular el rango
            properties.setVisibleRange(-1, 1);
            properties.setHasEmphasis(false);
            jSignalMonitor.addChannel(signalName, properties);
        }
        this.getContentPane().add(jSignalMonitor.getJSignalMonitorPanel());
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        jButtonShowXY.setText("Mostar XY");
        jButtonShowXY.addActionListener(new Frame1_jButton1_actionAdapter(this));
        jButtonAdjustRanges.setText("Ajustar rangos");
        jButtonAdjustRanges.addActionListener(new Frame1_jButton2_actionAdapter(this));
        jButtonDoNotShowSignal3.setText("Quitar senhal 3");
        jButtonDoNotShowSignal3.addActionListener(new Frame1_jButton3_actionAdapter(this));
        jButtonShowSignal3.setText("Mostrar senhal 3");
        jButtonShowSignal3.addActionListener(new Frame1_jButton4_actionAdapter(this));
        jButtonRealTime.setText("Actualizar en tiempo real");
        jButtonRealTime.addActionListener(new FrameJSignalMonitor_jButtonRealTime_actionAdapter(this));
        this.getContentPane().add(jToolBar1, BorderLayout.NORTH);

        jToolBar1.add(jButtonShowXY);
        jToolBar1.add(jButtonAdjustRanges);
        jToolBar1.add(jButtonDoNotShowSignal3);
        jToolBar1.add(jButtonRealTime);
        jToolBar1.add(jButtonShowSignal3);
        jButtonShowSignal3.setVisible(false);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        //@JSignalMonitor mostrar los valores XY
        jSignalMonitor.setRepresentingXYValues(!jSignalMonitor.isRepresentingXYValues());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        calculaMaxAndMinOfSignalsAndSetVisibleRange();
    }

    private void calculaMaxAndMinOfSignalsAndSetVisibleRange() {
        /*  for (int i = 0; i < 3; i++) {
        float[] values = demoJSM.data[i];
        float min = values[0];
        float max = values[0];
        for (int index = 0; index < values.length; index++) {
        if (values[index] < min) {
        min = values[index];
        }
        if (values[index] > max) {
        max = values[index];
        }
        }
        //@JSignalMonitor establecer para cada senhal su maximo y minimo
        ChannelProperties channelProperties = jSignalMonitor.getChannelProperties("Signal " + (i + 1));
        channelProperties.setVisibleRange(min, max);
        jSignalMonitor.repaintAll();
        }*/
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        //@JSignalMonitor quitar dinamicamente la tercera senhal
        jSignalMonitor.removeChannel("Signal 3");
        jButtonDoNotShowSignal3.setVisible(false);
        jButtonShowSignal3.setVisible(true);
    }

    public void jButton4_actionPerformed(ActionEvent e) {
        //@JSignalMonitor anhadir dinamicamente la tercera senhal
        ChannelProperties properties3 = new ChannelProperties("Signal 3", 100000, 1, 800);
        properties3.setVisibleRange(0, 500);
        properties3.setHasEmphasis(true);
        jSignalMonitor.addChannel("Signal 3", properties3);

        jButtonDoNotShowSignal3.setVisible(true);
        jButtonShowSignal3.setVisible(false);
    }

    public static void main(String[] args) {
        /*  FrameTestAutomatic frame1 = new FrameTestAutomatic();
        frame1.setSize(800, 500);
        frame1.setVisible(true);*/
    }

    public void jButtonRealTime_actionPerformed(ActionEvent e) {
        Timer timer = new Timer(100, new ActionListener() {

            private int samples = 500; //tiempo inicial

            public void actionPerformed(ActionEvent e) {
                //@JSignalMonitor Actualizar las senhales segn van llegando
                samples += 1;
                jSignalMonitor.getChannelProperties("Signal 1").setDataSize(samples);
                jSignalMonitor.getChannelProperties("Signal 2").setDataSize(samples);
                jSignalMonitor.getChannelProperties("Signal 3").setDataSize(samples - 100);
                //esto para que siempre nos muestre las ultimas muestras
                jSignalMonitor.setScrollValue(jSignalMonitor.getEndTime());
                //esto para qu!erepinte la pantalla
                jSignalMonitor.repaintAll();
            }
        });
        timer.start();

    }
}

class FrameJSignalMonitor_jButtonRealTime_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    FrameJSignalMonitor_jButtonRealTime_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonRealTime_actionPerformed(e);
    }
}

class Frame1_jButton4_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    Frame1_jButton4_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton4_actionPerformed(actionEvent);
    }
}

class Frame1_jButton3_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    Frame1_jButton3_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton3_actionPerformed(actionEvent);
    }
}

class Frame1_jButton2_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    Frame1_jButton2_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton2_actionPerformed(actionEvent);
    }
}

class Frame1_jButton1_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    Frame1_jButton1_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
