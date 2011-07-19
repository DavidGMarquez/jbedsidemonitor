/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowInfoTimeSeries.java
 *
 * Created on 13-jun-2011, 9:35:27
 */
package demo2;

import userInterface.*;
import algorithms.Algorithm;
import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmExecutionInfo;
import algorithms.AlgorithmNotifyPolice;
import algorithms.AlgorithmNotifyPoliceEnum;
import guiTest.SinTimeSeriesGeneratorGui;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import signals.ReadResult;
import signals.SignalManager;
import signals.TimeSeries;

/**
 *
 * @author USUARIO
 */
public class DataSourceCosTimeSeriesGui extends javax.swing.JDialog {

    DataSourceCosTimeSeries dataSourceCoshTimeSeries;

    /** Creates new form ShowInfoTimeSeries */
    public DataSourceCosTimeSeriesGui(java.awt.Frame parent, boolean modal, DataSourceCosTimeSeries dataSourceCoshTimeSeries) {
        super(parent, modal);
        initComponents();
        this.dataSourceCoshTimeSeries = dataSourceCoshTimeSeries;
        showInfo(dataSourceCoshTimeSeries);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaSeriesIsGeneratedFrom1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelIdentifier = new javax.swing.JLabel();
        jLabelCurrentIteration = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonAceptarCambios = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jTextFieldLimitOfIterations = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldSizeOfIteration = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldMultiplier = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldFrecuency = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabelSignalNameOut = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTextAreaSeriesIsGeneratedFrom1.setColumns(20);
        jTextAreaSeriesIsGeneratedFrom1.setRows(5);
        jScrollPane3.setViewportView(jTextAreaSeriesIsGeneratedFrom1);

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DataSourceCoshTimeSeries");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText("Identifier:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setText("CurrentIteration:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel3.setText("LimitOfIterations:");

        jLabelIdentifier.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabelIdentifier.setText("Identifier:");

        jLabelCurrentIteration.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCurrentIteration.setText("Agent:");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButtonAceptarCambios.setText("Save Changes");
        jButtonAceptarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarCambiosActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAceptarCambios, new java.awt.GridBagConstraints());

        jButtonSalir.setText("Exit");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 57;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 55, 0, 24);
        jPanel1.add(jButtonSalir, gridBagConstraints);

        jTextFieldLimitOfIterations.setText("jTextField1");
        jTextFieldLimitOfIterations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLimitOfIterationsActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel4.setText("SizeOfIteration:");

        jTextFieldSizeOfIteration.setText("jTextField1");
        jTextFieldSizeOfIteration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSizeOfIterationActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel5.setText("Multiplier:");

        jTextFieldMultiplier.setText("jTextField1");
        jTextFieldMultiplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMultiplierActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel6.setText("Frecuency:");

        jTextFieldFrecuency.setText("jTextField1");
        jTextFieldFrecuency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFrecuencyActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel8.setText("S:ignalNameOut");

        jLabelSignalNameOut.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelSignalNameOut.setText("Agent:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelIdentifier))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLimitOfIterations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSizeOfIteration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMultiplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFrecuency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCurrentIteration))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSignalNameOut)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelIdentifier))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabelSignalNameOut))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelCurrentIteration))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldLimitOfIterations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldSizeOfIteration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldMultiplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldFrecuency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAceptarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarCambiosActionPerformed
        saveInfo();
        dispose();
}//GEN-LAST:event_jButtonAceptarCambiosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jTextFieldLimitOfIterationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLimitOfIterationsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLimitOfIterationsActionPerformed

    private void jTextFieldSizeOfIterationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSizeOfIterationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSizeOfIterationActionPerformed

    private void jTextFieldMultiplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMultiplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMultiplierActionPerformed

    private void jTextFieldFrecuencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFrecuencyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFrecuencyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DataSourceCosTimeSeries dataSourceCoshTimeSeries = new DataSourceCosTimeSeries(10, 100, 1000, "TimeSeries1", 10, 0.01);

                DataSourceCosTimeSeriesGui dialog = new DataSourceCosTimeSeriesGui(new javax.swing.JFrame(), true, dataSourceCoshTimeSeries);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAceptarCambios;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCurrentIteration;
    private javax.swing.JLabel jLabelIdentifier;
    private javax.swing.JLabel jLabelSignalNameOut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaSeriesIsGeneratedFrom1;
    private javax.swing.JTextField jTextFieldFrecuency;
    private javax.swing.JTextField jTextFieldLimitOfIterations;
    private javax.swing.JTextField jTextFieldMultiplier;
    private javax.swing.JTextField jTextFieldSizeOfIteration;
    // End of variables declaration//GEN-END:variables

    private void showInfo(DataSourceCosTimeSeries dataSourceCoshTimeSeries) {
        jLabelIdentifier.setText(dataSourceCoshTimeSeries.getIdentifier());
        jLabelSignalNameOut.setText(dataSourceCoshTimeSeries.getNameSignal());
        jLabelCurrentIteration.setText(String.valueOf(dataSourceCoshTimeSeries.getCurrentIteration()));
        jTextFieldFrecuency.setText(String.valueOf(dataSourceCoshTimeSeries.getFrecuency()));
        jTextFieldLimitOfIterations.setText(String.valueOf(dataSourceCoshTimeSeries.getLimitOfItIterations()));
        jTextFieldMultiplier.setText(String.valueOf(dataSourceCoshTimeSeries.getMultiplier()));
        jTextFieldSizeOfIteration.setText(String.valueOf(dataSourceCoshTimeSeries.getSizeOfIteration()));
    }

    private void saveInfo() {
        SignalManager.getInstance().getLockToModifyDataSources();
        try {
            this.dataSourceCoshTimeSeries.setFrecuency(Float.valueOf(jTextFieldFrecuency.getText()));
            this.dataSourceCoshTimeSeries.setLimitOfItIterations(Integer.valueOf(jTextFieldLimitOfIterations.getText()));
            this.dataSourceCoshTimeSeries.setMultiplier(Double.valueOf(jTextFieldMultiplier.getText()));
            this.dataSourceCoshTimeSeries.setSizeOfIteration(Integer.valueOf(jTextFieldSizeOfIteration.getText()));
        } finally {
            SignalManager.getInstance().releaseLockToModifyDataSources();
        }
    }
}
