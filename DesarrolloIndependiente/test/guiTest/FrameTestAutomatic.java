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
    private JButton jButtonRealTime = new JButton();
    private JButton jButtonRefresh = new JButton();
    private JComboBox jComboBoxSignals = new JComboBox();
    private JComboBox jComboBoxAnnotations = new JComboBox();
    private JMenuBar jMenuBarTop=new JMenuBar();
    private JMenu jMenuInicial=new JMenu("Archivo");
    private JMenu jMenuSecundario=new JMenu("Señales");
    private JMenuItem jMenuSalir=new JMenuItem("Salir");
    private Timer timer = null;

    public FrameTestAutomatic(JSignalAdapter jSignalAdapter) {

        //Para que se cierre la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jSignalAdapter = jSignalAdapter;

        //@JSignalMonitor configuracion Inicial
        jSignalMonitor = new JSignalMonitor(jSignalAdapter);
        jSignalAdapter.setjSignalMonitor(jSignalMonitor);
        LinkedList<String> allTimeSeriesNames = jSignalAdapter.getAllTimeSeriesNames();
        for (String signalName : allTimeSeriesNames) {
            ChannelProperties properties = new ChannelProperties(signalName, 0, jSignalAdapter.getFrecuencySignalTimeSeries(signalName), jSignalAdapter.getDataSizeTimeSeries(signalName));
            System.out.println(signalName + " TamanoSignal:" + jSignalAdapter.getDataSizeTimeSeries(signalName));
            properties.setHasEmphasis(false);
            //@pendiente tocar esto de nuevo y calcular el rango
            properties.setVisibleRange(-1, 1);
            properties.setHasEmphasis(false);
            jSignalMonitor.addChannel(signalName, properties);
        }
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.getContentPane().add(jSignalMonitor.getJSignalMonitorPanel());
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        jButtonShowXY.setText("Mostar XY");
        jButtonShowXY.addActionListener(new Frame1_jButton1_actionAdapter(this));
        jButtonAdjustRanges.setText("Ajustar rangos");
        jButtonAdjustRanges.addActionListener(new Frame1_jButton2_actionAdapter(this));
        jButtonRealTime.setText("Actualizar en tiempo real");
        jButtonRealTime.addActionListener(new FrameJSignalMonitor_jButtonRealTime_actionAdapter(this));
        jButtonRefresh.setText("Actualizar");
        jButtonRefresh.addActionListener(new FrameJSignalMonitor_jButtonRefresh_actionAdapter(this));
        jComboBoxSignals = new JComboBox(jSignalAdapter.getAllTimeSeriesNames().toArray());
        jComboBoxSignals.addActionListener(new FrameJSignalMonitor_jComboBoxSignals_actionAdapter(this));
        jComboBoxAnnotations = new JComboBox(jSignalAdapter.getAllEventSeriesNames().toArray());
        jComboBoxAnnotations.addActionListener(new FrameJSignalMonitor_jComboBoxAnnotations_actionAdapter(this));
        this.getContentPane().add(jToolBar1, BorderLayout.NORTH);

        jToolBar1.add(jButtonShowXY);
        jToolBar1.add(jButtonAdjustRanges);
        jToolBar1.add(jButtonRealTime);
        jToolBar1.add(jButtonRefresh);
        jToolBar1.add(jComboBoxSignals);
        jToolBar1.add(jComboBoxAnnotations);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        //@JSignalMonitor mostrar los valores XY
        jSignalMonitor.setRepresentingXYValues(!jSignalMonitor.isRepresentingXYValues());
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        calculaMaxAndMinOfSignalsAndSetVisibleRange();
    }

    private void calculaMaxAndMinOfSignalsAndSetVisibleRange() {
        //@JSignalMonitor establecer para cada senhal su maximo y minimo
        for (int i = 0; i < jSignalMonitor.channelsSize(); i++) {
            String signalName = jSignalMonitor.getChannelName(i);
            ChannelProperties channelProperties = jSignalMonitor.getChannelProperties(signalName);
            channelProperties.setVisibleRange(jSignalAdapter.getMinSignalTimeSeries(signalName),
                    jSignalAdapter.getMaxSignalTimeSeries(signalName));
        }
        jSignalMonitor.repaintAll();
    }

    public void jButtonRealTime_actionPerformed(ActionEvent e) {
        if (timer == null) {
            timer = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (Math.abs(jSignalMonitor.getScrollValue() + jSignalMonitor.getVisibleTime() - jSignalMonitor.getEndTime()) < 200*jSignalMonitor.getFrecuency()) {
                        jSignalMonitor.repaintAll();
                        jSignalMonitor.setScrollValue(jSignalMonitor.getEndTime());
                    } else {
                        jSignalMonitor.repaintAll();

                    }
                }
            });
            timer.start();
        } else {
            if (timer.isRunning()) {
                timer.stop();

            } else {
                timer.start();
            }
        }
    }

    public void jButtonRefresh_actionPerformed(ActionEvent e) {
        System.out.println("Time:" + jSignalMonitor.getVisibleTime() + "EndTime:" + jSignalMonitor.getEndTime() + "Scrool:" + jSignalMonitor.getScrollValue() + "BasTiem:" + jSignalMonitor.getScrollBaseTime()+"frec"+jSignalMonitor.getFrecuency());        
        System.out.println(Math.abs(jSignalMonitor.getScrollValue() + jSignalMonitor.getVisibleTime() - jSignalMonitor.getEndTime())/jSignalMonitor.getFrecuency());
        if (Math.abs(jSignalMonitor.getScrollValue() + jSignalMonitor.getVisibleTime() - jSignalMonitor.getEndTime()) < 200*jSignalMonitor.getFrecuency()) {
            jSignalMonitor.repaintAll();
            jSignalMonitor.setScrollValue(jSignalMonitor.getEndTime());
        } else {
            jSignalMonitor.repaintAll();
        }
    }

    public void jComboBoxSignals_actionPerformed(ActionEvent e) {
        String signalSelection = (String) jComboBoxSignals.getSelectedItem();
        System.out.println("Elegido" + signalSelection);
        if (jSignalMonitor.hasChannel(signalSelection)) {
            jSignalMonitor.removeChannel(signalSelection);
        } else {
            ChannelProperties properties = new ChannelProperties(signalSelection, 0,
                    jSignalAdapter.getFrecuencySignalTimeSeries(signalSelection), jSignalAdapter.getDataSizeTimeSeries(signalSelection));
            properties.setVisibleRange(-1, 1);
            properties.setHasEmphasis(false);
            jSignalMonitor.addChannel(signalSelection, properties);
        }
    }

    public void jComboBoxAnnotations_actionPerformed(ActionEvent e) {
        String signalSelection = (String) jComboBoxAnnotations.getSelectedItem();
        System.out.println("Elegido"+signalSelection);
        jSignalAdapter.switchEventSeriesToAnnotations(signalSelection);
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

class FrameJSignalMonitor_jButtonRefresh_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    FrameJSignalMonitor_jButtonRefresh_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonRefresh_actionPerformed(e);
    }
}

class FrameJSignalMonitor_jComboBoxSignals_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    FrameJSignalMonitor_jComboBoxSignals_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBoxSignals_actionPerformed(e);
    }
}

class FrameJSignalMonitor_jComboBoxAnnotations_actionAdapter implements ActionListener {

    private FrameTestAutomatic adaptee;

    FrameJSignalMonitor_jComboBoxAnnotations_actionAdapter(FrameTestAutomatic adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jComboBoxAnnotations_actionPerformed(e);
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
