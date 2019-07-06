/*
    1) Добавить в inputPane поле для начальной вершины
    2) Добавить в outPane возможность узнать расстояние до вершины
    3) Каждый Layout можно вынести в отдальный файл
 */

package Visualiazation;

import DataClasses.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private Box boxVInputPanel;
    private JLabel lblStartNode;
    private JLabel lblNode;
    private JLabel lblEdgeFrom;
    private JLabel lblEdgeTo;
    private JLabel lblWeight;
    private JTextField txtfStartNode;
    private JTextField txtfNode;
    private JTextField txtfEdgeFrom;
    private JTextField txtfEdgeTo;
    private JTextField txtfEdgeWeight;
    private JButton btnFromFile;
    private JButton btnEdgeAdd;
    private JButton btnNodeAdd;
    private JButton btnFinish;
    private JTextArea txtaLog;

    private DrawingPanel drawingPanel;

    private Box boxVOutputPanel;
    private JLabel lblAimNode;
    private JLabel lblLegthToAimNode;
    private JTextField txtfAimNode;
    private JTextField txtfLengthToAimNode;
    private JButton btnCalculateLength;
    private JButton btnNextStep;
    private JButton btnPreviousStep;
    private JButton btnReset;

    private Graph graph;

    public MainWindow(String title, Graph graph){
        super(title);
        this.graph = graph;
        initVariables();
        windowSettings();
        layoutSettins();
        buttonsSettings();
    }

    private void initVariables(){
        boxVInputPanel = Box.createVerticalBox();
        lblStartNode = new JLabel("input start Node");
        lblNode = new JLabel("input Node");
        lblEdgeFrom = new JLabel("From");
        lblEdgeTo = new JLabel("To");
        lblWeight = new JLabel("Edge Weight");

        txtfStartNode = new JTextField(2);
        txtfNode = new JTextField(2);
        txtfEdgeFrom = new JTextField(2);
        txtfEdgeTo = new JTextField(2);
        txtfEdgeWeight = new JTextField(2);

        btnFromFile = new JButton("From File");
        btnNodeAdd = new JButton("add Node");
        btnEdgeAdd = new JButton("add Edge");
        btnFinish = new JButton("Finish");

        txtaLog = new JTextArea(10, 0);
        txtaLog.setText("Algorithm steps\n");
        txtaLog.setLineWrap(true);
        txtaLog.setWrapStyleWord(true);

        drawingPanel = new DrawingPanel(graph);

        lblAimNode = new JLabel("Aim Node");
        lblLegthToAimNode = new JLabel("Length to Node");
        txtfAimNode = new JTextField(2);
        txtfLengthToAimNode = new JTextField(2);
        boxVOutputPanel = Box.createVerticalBox();
        btnCalculateLength = new JButton("Get length");
        btnNextStep = new JButton("next step");
        btnPreviousStep = new JButton("previous step");
        btnReset = new JButton("Reset");
    }

    private void windowSettings(){
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(new Point(400, 400));
        setResizable(true);
    }

    private void layoutSettins(){
        layoutInputSettings();
        layoutOutputSettings();
        layoutDrawingPanelSettings();
        getContentPane().add(boxVInputPanel, BorderLayout.WEST);
        getContentPane().add(new JScrollPane(drawingPanel));
        getContentPane().add(new JScrollPane(txtaLog), BorderLayout.SOUTH);
    }

    private void buttonsSettings(){
        btnNodeAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtfNode.getText().isEmpty()) {
                    graph.addNode(txtfNode.getText().charAt(0));
                    txtfNode.setText("");
                }
                else
                    JOptionPane.showMessageDialog(null, "message empty");
            }
        });

        btnEdgeAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtfEdgeTo.getText().isEmpty() && !txtfEdgeFrom.getText().isEmpty() && !txtfEdgeWeight.getText().isEmpty()) {
                    graph.addEdge(txtfEdgeFrom.getText().charAt(0), txtfEdgeTo.getText().charAt(0), 3);
                    txtfEdgeFrom.setText("");
                    txtfEdgeTo.setText("");
                    txtfEdgeWeight.setText("");
                }
                else
                    JOptionPane.showMessageDialog(null, "message empty");
            }
        });

        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(boxVInputPanel);
                getContentPane().add(boxVOutputPanel, BorderLayout.WEST);
                validate();
                repaint();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(boxVOutputPanel);
                JPanel panel = new JPanel();
                panel.setBackground(Color.BLUE);
                panel.setPreferredSize(new Dimension(150, 0));
                getContentPane().add(boxVInputPanel, BorderLayout.WEST);
                validate();
                repaint();
            }
        });
    }

    private void layoutInputSettings(){
        boxVInputPanel.setPreferredSize(new Dimension(150, 0));

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpStartNodeLbl = Box.createHorizontalBox();
        boxHSetUpStartNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpStartNodeLbl.add(lblStartNode);
        boxHSetUpStartNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpStartNodeLbl.add(txtfStartNode);
        boxHSetUpStartNodeLbl.add(Box.createHorizontalStrut(5));
        boxVInputPanel.add(boxHSetUpStartNodeLbl);

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpFromFileBtn = Box.createHorizontalBox();
        boxHSetUpFromFileBtn.add(btnFromFile);
        boxVInputPanel.add(boxHSetUpFromFileBtn);

        boxVInputPanel.add(Box.createVerticalStrut(50));
        Box boxHSetUpNodeLbl = Box.createHorizontalBox();
        boxHSetUpNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpNodeLbl.add(lblNode);
        boxHSetUpNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpNodeLbl.add(txtfNode);
        boxHSetUpNodeLbl.add(Box.createHorizontalStrut(25));
        boxVInputPanel.add(boxHSetUpNodeLbl);

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpNodeBtn = Box.createHorizontalBox();
        boxHSetUpNodeBtn.add(btnNodeAdd);
        boxVInputPanel.add(boxHSetUpNodeBtn);

        boxVInputPanel.add(Box.createVerticalStrut(50));
        Box boxHSetUpEdgeLbl = Box.createHorizontalBox();
        boxHSetUpEdgeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpEdgeLbl.add(lblEdgeFrom);
        boxHSetUpEdgeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpEdgeLbl.add(txtfEdgeFrom);
        boxHSetUpEdgeLbl.add(Box.createHorizontalStrut(10));
        boxHSetUpEdgeLbl.add(lblEdgeTo);
        boxHSetUpEdgeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpEdgeLbl.add(txtfEdgeTo);
        boxHSetUpEdgeLbl.add(Box.createHorizontalStrut(15));
        boxVInputPanel.add(boxHSetUpEdgeLbl);

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpEdgeWeight = Box.createHorizontalBox();
        boxHSetUpEdgeWeight.add(Box.createHorizontalStrut(5));
        boxHSetUpEdgeWeight.add(lblWeight);
        boxHSetUpEdgeWeight.add(Box.createHorizontalStrut(5));
        boxHSetUpEdgeWeight.add(txtfEdgeWeight);
        boxHSetUpEdgeWeight.add(Box.createHorizontalStrut(25));
        boxVInputPanel.add(boxHSetUpEdgeWeight);

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpEdgeBtn = Box.createHorizontalBox();
        boxHSetUpEdgeBtn.add(btnEdgeAdd);
        boxVInputPanel.add(boxHSetUpEdgeBtn);

        boxVInputPanel.add(Box.createVerticalStrut(50));
        Box boxHSetUpFinishBtn = Box.createHorizontalBox();
        boxHSetUpFinishBtn.add(btnFinish);
        boxVInputPanel.add(boxHSetUpFinishBtn);

        boxVInputPanel.add(Box.createVerticalStrut((int)Double.POSITIVE_INFINITY));
    }

    private void layoutDrawingPanelSettings(){
        drawingPanel = new DrawingPanel(graph);
        drawingPanel.setPreferredSize(new Dimension(1000, 1000));
        drawingPanel.setBackground(new Color(230, 230, 230));
    }

    private void layoutOutputSettings(){
        boxVOutputPanel.setPreferredSize(new Dimension(150, 0));

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpAimNodeLbl = Box.createHorizontalBox();
        boxHSetUpAimNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpAimNodeLbl.add(lblAimNode);
        boxHSetUpAimNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpAimNodeLbl.add(txtfAimNode);
        boxHSetUpAimNodeLbl.add(Box.createHorizontalStrut(45));
        boxVOutputPanel.add(boxHSetUpAimNodeLbl);

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpAimNodeLength = Box.createHorizontalBox();
        boxHSetUpAimNodeLength.add(Box.createHorizontalStrut(5));
        boxHSetUpAimNodeLength.add(lblLegthToAimNode);
        boxHSetUpAimNodeLength.add(Box.createHorizontalStrut(5));
        boxHSetUpAimNodeLength.add(txtfLengthToAimNode);
        boxHSetUpAimNodeLength.add(Box.createHorizontalStrut(5));
        boxVOutputPanel.add(boxHSetUpAimNodeLength);

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpLengthBtn = Box.createHorizontalBox();
        boxHSetUpLengthBtn.add(btnCalculateLength);
        boxVOutputPanel.add(boxHSetUpLengthBtn);

        boxVOutputPanel.add(Box.createVerticalStrut(50));
        Box boxHSetUpNextBtn = Box.createHorizontalBox();
        boxHSetUpNextBtn.add(btnNextStep);
        boxVOutputPanel.add(boxHSetUpNextBtn);

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpPreviousBtn = Box.createHorizontalBox();
        boxHSetUpPreviousBtn.add(btnPreviousStep);
        boxVOutputPanel.add(boxHSetUpPreviousBtn);

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpResetBtn = Box.createHorizontalBox();
        boxHSetUpResetBtn.add(btnReset);
        boxVOutputPanel.add(boxHSetUpResetBtn);

        boxVOutputPanel.add(Box.createVerticalStrut((int)Double.POSITIVE_INFINITY));
    }
}