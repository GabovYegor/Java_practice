package Visualiazation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Пользовательский класс для расширения функционала класса JFrame
public class MainWindow extends JFrame {
    private static final int MAINWINDOW_WIDTH = 900;
    private static final int MAINWINDOW_HEIGHT = 700;

    Box boxVInputPanel;
    private JLabel lblNode;
    private JLabel lblEdgeFrom;
    private JLabel lblEdgeTo;
    private JLabel lblWeight;
    JTextField txtfNode;
    JTextField txtfEdgeFrom;
    JTextField txtfEdgeTo;
    JTextField txtfEdgeWeight;
    JButton btnManual;
    JButton btnFromFile;
    JButton btnSaveFile;
    JButton btnEdgeAdd;
    JButton btnNodeAdd;
    JButton btnNodeRemove;
    JButton btnRepaint;
    JButton btnGoToAlgorithm;
    JButton btnResetInput;

    Box boxVOutputPanel;
    private JLabel lblStartNode;
    private JLabel lblAimNode;
    JTextField txtfStartNode;
    JTextField txtfAimNode;
    JButton btnStartAlgorithm;
    JButton btnFinishAlgorithm;
    JButton btnNextStep;
    JButton btnPreviousStep;
    JButton btnCalculateLength;
    JButton btnResetOutput;
    JTextArea txtaLog;
    MainWindow thisWindow = this;

    public MainWindow(String title) {
        super(title);
        initVariables();
        windowSettings();
        layoutSettins();
    }

    // Вынесение инициализации за пределы конструктора,
    // в отдельныю функцию чтобы не засорять конструктор
    private void initVariables() {
        boxVInputPanel = Box.createVerticalBox();
        lblNode = new JLabel("Name");
        lblEdgeFrom = new JLabel("From");
        lblEdgeTo = new JLabel("To");
        lblWeight = new JLabel("Weight");

        txtfNode = new JTextField(2);
        txtfNode.addKeyListener(new EdgeKeyListener(txtfNode, 1));

        txtfEdgeFrom = new JTextField(2);
        txtfEdgeFrom.addKeyListener(new EdgeKeyListener(txtfEdgeFrom, 1));

        txtfEdgeTo = new JTextField(2);
        txtfEdgeTo.addKeyListener(new EdgeKeyListener(txtfEdgeTo, 1));

        txtfEdgeWeight = new JTextField(5);
        txtfEdgeWeight.addKeyListener(new EdgeKeyListener(txtfEdgeWeight, 3));

        btnManual = new JButton("Manual");
        btnFromFile = new JButton("From File");
        btnSaveFile = new JButton("Save this Graph");
        btnNodeAdd = new JButton("Add Node");
        btnNodeRemove = new JButton("Remove Node");
        btnEdgeAdd = new JButton("Add Edge");
        btnRepaint = new JButton("Repaint Graph");
        btnGoToAlgorithm = new JButton("Go to algorithm");
        btnResetInput = new JButton("Reset");

        txtaLog = new JTextArea(10, 0);
        txtaLog.setText("Algorithm steps\n");
        txtaLog.setLineWrap(true);
        txtaLog.setWrapStyleWord(true);

        lblStartNode = new JLabel("Input start Node");
        lblAimNode = new JLabel("Aim Node");

        txtfStartNode = new JTextField(2);
        txtfStartNode.addKeyListener(new EdgeKeyListener(txtfStartNode, 1));

        txtfAimNode = new JTextField(2);
        txtfAimNode.addKeyListener(new EdgeKeyListener(txtfAimNode, 1));

        boxVOutputPanel = Box.createVerticalBox();
        btnStartAlgorithm = new JButton("Start algorithm");
        btnFinishAlgorithm = new JButton("Finish algorithm");
        btnNextStep = new JButton("Next step");
        btnPreviousStep = new JButton("Previous step");
        btnCalculateLength = new JButton("Get length");
        btnResetOutput = new JButton("Reset");
    }

    // Базовые настройки главного всплывающего окна -
    // размер, положение, изменяемость размера
    private void windowSettings() {
        setSize(MAINWINDOW_WIDTH, MAINWINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(new Point(450, 200));
        setResizable(false);
    }

    // Настройка расположения пользовательских компонент
    // кнопок, лэйблов, текстовых полей
    private void layoutSettins() {
        layoutInputSettings();
        layoutOutputSettings();
        getContentPane().add(boxVInputPanel, BorderLayout.WEST);
        getContentPane().add(new JScrollPane(txtaLog), BorderLayout.SOUTH);
    }

    // Настроить внешний вид для пользовательской кнопки
    // избавления от дублирования кода
    private void setUpSimpleButton(Box mainBox, JButton btn) {
        mainBox.add(Box.createVerticalStrut(10));
        Box boxH = Box.createHorizontalBox();
        boxH.add(btn);
        boxH.add(Box.createHorizontalGlue());
        mainBox.add(boxH);
    }

    // Настроить внешний вид для пользовательского лэйбла + тестового поля
    // избавления от дублирования кода
    private void setUpLblTxtf(Box mainBox, JLabel lbl, JTextField txtf, int offset) {
        mainBox.add(Box.createVerticalStrut(10));
        Box boxH = Box.createHorizontalBox();
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(lbl);
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(txtf);
        boxH.add(Box.createHorizontalStrut(offset));
        mainBox.add(boxH);
    }

    // Настроить внешний вид для пары пользовательских лэйблов + тестовых полей
    // избавления от дублирования кода
    private void setUpTwoLblTxtf(Box mainBox, JLabel lbl_1, JTextField txtf_1, JLabel lbl_2, JTextField txtf_2, int offset) {
        mainBox.add(Box.createVerticalStrut(10));
        Box boxH = Box.createHorizontalBox();
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(lbl_1);
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(txtf_1);
        boxH.add(Box.createHorizontalStrut(10));
        boxH.add(lbl_2);
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(txtf_2);
        boxH.add(Box.createHorizontalStrut(15));
        boxH.add(Box.createHorizontalStrut(offset));
        mainBox.add(boxH);
    }

    // Настройка расположения пользовательских компонент
    // для настройки панели ввода графа
    private void layoutInputSettings() {
        boxVInputPanel.setPreferredSize(new Dimension(150, 0));

        setUpSimpleButton(boxVInputPanel, btnManual);
        setUpSimpleButton(boxVInputPanel, btnFromFile);
        setUpSimpleButton(boxVInputPanel, btnSaveFile);
        boxVInputPanel.add(Box.createVerticalStrut(20));
        setUpLblTxtf(boxVInputPanel, lblNode, txtfNode, 80);
        setUpSimpleButton(boxVInputPanel, btnNodeAdd);
        setUpSimpleButton(boxVInputPanel, btnNodeRemove);
        boxVInputPanel.add(Box.createVerticalStrut(20));
        setUpTwoLblTxtf(boxVInputPanel, lblEdgeFrom, txtfEdgeFrom, lblEdgeTo, txtfEdgeTo, 20);
        setUpLblTxtf(boxVInputPanel, lblWeight, txtfEdgeWeight, 60);
        setUpSimpleButton(boxVInputPanel, btnEdgeAdd);
        boxVInputPanel.add(Box.createVerticalStrut(20));
        setUpSimpleButton(boxVInputPanel, btnRepaint);
        setUpSimpleButton(boxVInputPanel, btnGoToAlgorithm);
        setUpSimpleButton(boxVInputPanel, btnResetInput);

        boxVInputPanel.add(Box.createVerticalStrut((int) Double.POSITIVE_INFINITY));
    }

    // Настройка расположения пользовательских компонент
    // для настройки панели работы алгоритма
    private void layoutOutputSettings() {
        boxVOutputPanel.setPreferredSize(new Dimension(150, 0));

        setUpLblTxtf(boxVOutputPanel, lblStartNode, txtfStartNode, 10);
        setUpSimpleButton(boxVOutputPanel, btnStartAlgorithm);
        setUpSimpleButton(boxVOutputPanel, btnFinishAlgorithm);
        boxVOutputPanel.add(Box.createVerticalStrut(30));
        setUpSimpleButton(boxVOutputPanel, btnNextStep);
        setUpSimpleButton(boxVOutputPanel, btnPreviousStep);
        boxVOutputPanel.add(Box.createVerticalStrut(30));
        setUpLblTxtf(boxVOutputPanel, lblAimNode, txtfAimNode, 60);
        setUpSimpleButton(boxVOutputPanel, btnCalculateLength);
        boxVOutputPanel.add(Box.createVerticalStrut(180));
        setUpSimpleButton(boxVOutputPanel, btnResetOutput);
        boxVOutputPanel.add(Box.createVerticalStrut((int) Double.POSITIVE_INFINITY));
    }

    // Добавление слушателя к клавиатуре
    // избавления от дублирования кода
    class EdgeKeyListener extends KeyAdapter {
        private JTextField txtfToSetUp;
        private int maxSimbolsNum;

        EdgeKeyListener(JTextField txtfToSetUp, int maxSimbolsNum) {
            this.txtfToSetUp = txtfToSetUp;
            this.maxSimbolsNum = maxSimbolsNum;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            if (txtfToSetUp.getText().length() >= maxSimbolsNum) {
                e.consume();
                JOptionPane.showMessageDialog(null, "Max count of symbols");
            }
        }
    }
}

