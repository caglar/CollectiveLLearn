package edu.metu.lngamesml.gui;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 2/25/11
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class CatGameMainGUI {
    private JLabel lblBaseLearner;
    private JComboBox cmbBaseLearner;
    private JComboBox cmbLearningCombine;
    private JLabel lblNoOfAgents;
    private JLabel lblSamplingPercentage;
    private JFormattedTextField fTxtSamppling;
    private JFormattedTextField fTxtNoOfAgents;
    private JComboBox cmbExportStats;
    private JButton btnStart;
    private JLabel lblLearning;
    private JLabel lblExportResults;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        cmbBaseLearner.addItem("aa");
        cmbBaseLearner.addItem("bb");
        cmbBaseLearner.addItem("cc");
    }
}
