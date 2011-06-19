/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowInfoTimeSeries.java
 *
 * Created on 13-jun-2011, 9:35:27
 */
package userInterface;

import algorithms.Algorithm;
import algorithms.AlgorithmDefaultImplementation;
import algorithms.AlgorithmExecutionInfo;
import algorithms.AlgorithmNotifyPolice;
import algorithms.AlgorithmNotifyPoliceEnum;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import signals.ReadResult;
import signals.TimeSeries;

/**
 *
 * @author USUARIO
 */
public class ShowExecutionInfoAlgorithm extends javax.swing.JDialog {

    /** Creates new form ShowInfoTimeSeries */
    public ShowExecutionInfoAlgorithm(java.awt.Frame parent, boolean modal, AlgorithmExecutionInfo algorithmExecutionInfo) {
        super(parent, modal);
        initComponents();
        showExecutionInfoAlgorithm(algorithmExecutionInfo);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaSeriesIsGeneratedFrom1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelIdentifier = new javax.swing.JLabel();
        jLabelnumberOfUpdateTriggers = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabelnumberOfExecutions = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Algorithm");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText("Identifier:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Number of Update Triggers:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Number of Executions:");

        jLabelIdentifier.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabelIdentifier.setText("Identifier:");

        jLabelnumberOfUpdateTriggers.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelnumberOfUpdateTriggers.setText("Agent:");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new java.awt.GridBagConstraints());

        jLabelnumberOfExecutions.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelnumberOfExecutions.setText("Notify Police");

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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelnumberOfUpdateTriggers))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelnumberOfExecutions))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
                .addContainerGap())
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
                    .addComponent(jLabel2)
                    .addComponent(jLabelnumberOfUpdateTriggers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelnumberOfExecutions))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
}//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AlgorithmExecutionInfo algorithmExecutionInfo = new AlgorithmExecutionInfo("algorithmDefault");
                algorithmExecutionInfo.incrementExecutionUpdates();
                algorithmExecutionInfo.incrementExecutionUpdates();
                algorithmExecutionInfo.incrementExecutionUpdates();
                algorithmExecutionInfo.incrementTriggerUpdates();
                algorithmExecutionInfo.incrementTriggerUpdates();
                ShowExecutionInfoAlgorithm dialog = new ShowExecutionInfoAlgorithm(new javax.swing.JFrame(), true, algorithmExecutionInfo);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelIdentifier;
    private javax.swing.JLabel jLabelnumberOfExecutions;
    private javax.swing.JLabel jLabelnumberOfUpdateTriggers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaSeriesIsGeneratedFrom1;
    // End of variables declaration//GEN-END:variables

    private void showExecutionInfoAlgorithm(AlgorithmExecutionInfo algorithmExecutionInfo) {
        jLabelIdentifier.setText(algorithmExecutionInfo.getIdentifierAlgorithm());
        jLabelnumberOfUpdateTriggers.setText(algorithmExecutionInfo.getNumberOfTriggerUpdates().toString());
        jLabelnumberOfExecutions.setText(algorithmExecutionInfo.getNumberOfExecutions().toString());
    }
}
