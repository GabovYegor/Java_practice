package Visualiazation;

import DataClasses.AlgorithmStepData;
import DataClasses.Edge;
import DataClasses.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainWindow extends JFrame {
    private static final int MAINWINDOW_WIDTH = 900;
    private static final int MAINWINDOW_HEIGHT = 700;
    private static final int BIGRADIUS = 30;
    private static final int BOUND_WIDTH = 500;
    private static final int BOUND_HEIGHT = 400;

    private Box boxVInputPanel;
    private JLabel lblNode;
    private JLabel lblEdgeFrom;
    private JLabel lblEdgeTo;
    private JLabel lblWeight;
    private JTextField txtfNode;
    private JTextField txtfEdgeFrom;
    private JTextField txtfEdgeTo;
    private JTextField txtfEdgeWeight;
    private JButton btnFromFile;
    private JButton btnSaveFile;
    private JButton btnEdgeAdd;
    private JButton btnNodeAdd;
    private JButton btnRepaint;
    private JButton btnGoToAlgorithm;

    private Box boxVOutputPanel;
    private JLabel lblStartNode;
    private JLabel lblAimNode;
    private JLabel lblLegthToAimNode;
    private JTextField txtfStartNode;
    private JTextField txtfAimNode;
    private JTextField txtfLengthToAimNode;
    private JButton btnStartAlgorithm;
    private JButton btnCalculateLength;
    private JButton btnNextStep;
    private JButton btnPreviousStep;
    private JButton btnReset;

    private JTextArea txtaLog;

    private DrawingPanel drawingPanel;

    private Graph graph;
    private ArrayList<AlgorithmStepData> graphStates;
    private int algorithmStepNum;

    public MainWindow(String title, Graph graph){
        super(title);
        this.graph = graph;
        algorithmStepNum = 0;
        initVariables();
        windowSettings();
        layoutSettins();
        buttonsSettings();
    }

    private void initVariables(){
        boxVInputPanel = Box.createVerticalBox();
        lblNode      = new JLabel("Name");
        lblEdgeFrom  = new JLabel("From");
        lblEdgeTo    = new JLabel("To");
        lblWeight    = new JLabel("Edge Weight");

        txtfNode       = new JTextField(2);
        txtfEdgeFrom   = new JTextField(2);
        txtfEdgeTo     = new JTextField(2);
        txtfEdgeWeight = new JTextField(2);

        btnFromFile  = new JButton("From File");
        btnSaveFile = new JButton("Save this Graph");
        btnNodeAdd   = new JButton("add Node");
        btnEdgeAdd   = new JButton("add Edge");
        btnRepaint   = new JButton("Repaint Graph");
        btnGoToAlgorithm = new JButton("go to algorithm");

        txtaLog = new JTextArea(10, 0);
        txtaLog.setText("Algorithm steps\n");
        txtaLog.setLineWrap(true);
        txtaLog.setWrapStyleWord(true);

        lblStartNode = new JLabel("input start Node");
        lblAimNode = new JLabel("Aim Node");
        lblLegthToAimNode = new JLabel("Length to Node");
        txtfStartNode  = new JTextField(2);
        txtfAimNode = new JTextField(2);
        txtfLengthToAimNode = new JTextField(2);
        boxVOutputPanel = Box.createVerticalBox();
        btnStartAlgorithm = new JButton("start algorithm");
        btnCalculateLength = new JButton("Get length");
        btnNextStep = new JButton("next step");
        btnPreviousStep = new JButton("previous step");
        btnReset = new JButton("Reset");
    }

    private void windowSettings(){
        setSize(MAINWINDOW_WIDTH, MAINWINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(new Point(450, 200));
        setResizable(true);
    }

    private void layoutSettins(){
        layoutInputSettings();
        layoutOutputSettings();
        layoutDrawingPanelSettings();
        getContentPane().add(boxVInputPanel, BorderLayout.WEST);
        //getContentPane().add(new JScrollPane(drawingPanel));
        getContentPane().add(drawingPanel);
        getContentPane().add(new JScrollPane(txtaLog), BorderLayout.SOUTH);
    }

    private void buttonsSettings(){

        btnFromFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                File file = null;
                if (fileopen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                    parseGraph(file);
                }
            }
        });

        btnSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                File file = null;
                if (fileopen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                    saveIntoFile(file);
                }
            }
        });

        btnNodeAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtfNode.getText().isEmpty()) {
                    for(int i = 0; i < graph.nodeCount(); ++i){
                        if(graph.getNodeByIndex(i).getName() == txtfNode.getText().charAt(0)) {
                            JOptionPane.showMessageDialog(null, "This node is already in graph");
                        }
                    }
                    graph.addNode(txtfNode.getText().charAt(0));
                    txtfNode.setText("");
                }
                else
                    JOptionPane.showMessageDialog(null, "Node`s name empty");
            }
        });

        btnEdgeAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                if(!txtfEdgeTo.getText().isEmpty() && !txtfEdgeFrom.getText().isEmpty() && !txtfEdgeWeight.getText().isEmpty()) {
                    int currentWeight = parseInt();
                    if(currentWeight == 0) {
                        txtfEdgeFrom.setText("");
                        txtfEdgeTo.setText("");
                        txtfEdgeWeight.setText("");
                        return;
                    }
                    graph.addEdge(txtfEdgeFrom.getText().charAt(0), txtfEdgeTo.getText().charAt(0), currentWeight);
                    txtfEdgeFrom.setText("");
                    txtfEdgeTo.setText("");
                    txtfEdgeWeight.setText("");
                    repaint();
                    flag = true;
                }

                for(int i = 0; i < graph.nodeCount(); ++i){
                    if(graph.getNodeByIndex(i).getColor() == Color.BLUE) {
                        for (int j = 0; j < graph.nodeCount(); ++j) {
                            if (graph.getNodeByIndex(j).getColor() == Color.YELLOW) {
                                if(!txtfEdgeWeight.getText().isEmpty()) {
                                    int currentWeight = parseInt();
                                    if(currentWeight == 0)
                                        return;
                                    graph.addEdge(graph.getNodeByIndex(i).getName(), graph.getNodeByIndex(j).getName(), currentWeight);
                                    flag = true;
                                }
                            }
                        }
                    }
                }

                if(!flag)
                    JOptionPane.showMessageDialog(null, "Edge fields Empty");
            }
        });

        btnRepaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                for(int i = 0; i < graph.nodeCount(); ++i){
                    graph.getNodeByIndex(i).setLocation(new Point(random.nextInt(BOUND_WIDTH) + BIGRADIUS, random.nextInt(BOUND_HEIGHT) + BIGRADIUS));
                }
            }
        });

        btnGoToAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(boxVInputPanel);
                getContentPane().add(boxVOutputPanel, BorderLayout.WEST);
                validate();
                repaint();
            }
        });

        btnStartAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtfStartNode.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Input start node name");
                else {
                    graphStates = graph.Dijkstra(txtfStartNode.getText().charAt(0));
                    algorithmStepNum = 0;
                    graph = graphStates.get(++algorithmStepNum).getGraph();
                    drawingPanel.updateGraph(graph);
                    drawingPanel.setTrueIsAlgorithmValue();
                    txtaLog.setText("");
                    for(int i = 0; i < algorithmStepNum; ++i){
                        txtaLog.append(graphStates.get(i).getStr());
                    }
                }
            }
        });

        // + вывести/пометить_ребра путь до вершины
        btnCalculateLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtfAimNode.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Input aim node name!");
                    return;
                }

                for(int i = 0; i < graph.nodeCount(); ++i){
                    if(graph.getNodeByIndex(i).getName() == txtfAimNode.getText().charAt(0)){
                        if(graph.getNodeByIndex(i).pathToString().length() != 0)
                            JOptionPane.showMessageDialog(null, "Lenght = " + String.valueOf(graph.getNodeByIndex(i).getDistance()) +
                                    '\n' + " PATH FROM " + txtfStartNode.getText().charAt(0) +
                                    " TO " + txtfAimNode.getText().charAt(0) + " : " + graph.getNodeByIndex(i).pathToString());
                        else
                            JOptionPane.showMessageDialog(null, "there is no way!");

                        if(graph.getNodeByIndex(i).getDistance() != Integer.MAX_VALUE)
                            txtfLengthToAimNode.setText(String.valueOf(graph.getNodeByIndex(i).getDistance()));
                        else
                            txtfLengthToAimNode.setText(String.valueOf('\u221E'));
                        return;
                    }
                }

                txtfLengthToAimNode.setText("");
                JOptionPane.showMessageDialog(null, "There are no node with this name!");
            }
        });

        // Вывести на экран информацию о текущем шаге алгоритма?
        btnNextStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(algorithmStepNum != graphStates.size() - 1) {
                    graph = graphStates.get(++algorithmStepNum).getGraph();
                    drawingPanel.updateGraph(graph);
                    txtaLog.setText("");
                    for(int i = 0; i <= algorithmStepNum; ++i){
                        txtaLog.append(graphStates.get(i).getStr() + '\n');
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Algorithm`s work end!");
            }
        });

        // Вывести на экран информацию о текущем шаге алгоритма?
        btnPreviousStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(algorithmStepNum != 0){
                    graph = graphStates.get(--algorithmStepNum).getGraph();
                    drawingPanel.updateGraph(graph);
                    txtaLog.setText("");
                    for(int i = 0; i <= algorithmStepNum; ++i){
                        txtaLog.append(graphStates.get(i).getStr() + '\n');
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "It`s already first step!");
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

                graph = new Graph();
                drawingPanel.setFalseIsAlgorithmValue();
                drawingPanel.updateGraph(graph);
                txtaLog.setText("");

                validate();
                repaint();
            }
        });
    }

    private void layoutInputSettings(){
        boxVInputPanel.setPreferredSize(new Dimension(150, 0));

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpFromFileBtn = Box.createHorizontalBox();
        boxHSetUpFromFileBtn.add(btnFromFile);
        boxVInputPanel.add(boxHSetUpFromFileBtn);

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpSaveFileBtn = Box.createHorizontalBox();
        boxHSetUpSaveFileBtn.add(btnSaveFile);
        boxVInputPanel.add(boxHSetUpSaveFileBtn);

        boxVInputPanel.add(Box.createVerticalStrut(50));
        Box boxHSetUpNodeLbl = Box.createHorizontalBox();
        boxHSetUpNodeLbl.add(Box.createHorizontalStrut(25));
        boxHSetUpNodeLbl.add(lblNode);
        boxHSetUpNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpNodeLbl.add(txtfNode);
        boxHSetUpNodeLbl.add(Box.createHorizontalStrut(60));
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
        Box boxHSetUpRepaintBtn = Box.createHorizontalBox();
        boxHSetUpRepaintBtn.add(btnRepaint);
        boxVInputPanel.add(boxHSetUpRepaintBtn);

        boxVInputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpFinishBtn = Box.createHorizontalBox();
        boxHSetUpFinishBtn.add(btnGoToAlgorithm);
        boxVInputPanel.add(boxHSetUpFinishBtn);

        boxVInputPanel.add(Box.createVerticalStrut((int)Double.POSITIVE_INFINITY));
    }

    private void layoutDrawingPanelSettings(){

        drawingPanel = new DrawingPanel(graph, txtfNode);
        drawingPanel.setPreferredSize(new Dimension(1000, 1000));
        drawingPanel.setBackground(new Color(230, 230, 230));
    }

    private void layoutOutputSettings(){
        boxVOutputPanel.setPreferredSize(new Dimension(150, 0));

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetUpStartNodeLbl = Box.createHorizontalBox();
        boxHSetUpStartNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpStartNodeLbl.add(lblStartNode);
        boxHSetUpStartNodeLbl.add(Box.createHorizontalStrut(5));
        boxHSetUpStartNodeLbl.add(txtfStartNode);
        boxHSetUpStartNodeLbl.add(Box.createHorizontalStrut(5));
        boxVOutputPanel.add(boxHSetUpStartNodeLbl);

        boxVOutputPanel.add(Box.createVerticalStrut(10));
        Box boxHSetStartAlgorithmBtn = Box.createHorizontalBox();
        boxHSetStartAlgorithmBtn.add(btnStartAlgorithm);
        boxVOutputPanel.add(boxHSetStartAlgorithmBtn);

        boxVOutputPanel.add(Box.createVerticalStrut(40));
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

    private void parseGraph(File file) {

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file))
        {
            //Read JSON file
            JSONArray nodesList = (JSONArray) jsonParser.parse(reader);
            System.out.println(nodesList);

            //Iterate over employee array
            nodesList.forEach( emp -> parseNodeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseNodeObject(JSONObject node)
    {
        String name = (String) node.get("name");
        System.out.println(name);
        graph.addNode(name.charAt(0));

        JSONArray location = (JSONArray) node.get("location");
        System.out.println(location.get(0) + " " + location.get(1) + '\n');
        Long xl = (Long)(location.get(0));
        int x = xl.intValue();
        Long yl = (Long)(location.get(1));
        int y = yl.intValue();
        graph.getNodeByIndex(graph.nodeCount()-1).setLocation(new Point(x, y));

        JSONArray adjacencyList = (JSONArray) node.get("adjacencyList");
        ArrayList<Edge> adjacencyListForSetUp = new ArrayList<Edge>();
        Iterator adjListItr = adjacencyList.iterator();
        while ((adjListItr.hasNext())){
            JSONArray currentEdge = (JSONArray) adjListItr.next();
            Long  wl = (Long) currentEdge.get(1);
            int weight = wl.intValue();
            adjacencyListForSetUp.add(new Edge(String.valueOf(currentEdge.get(0)).charAt(0), weight));
            System.out.println(currentEdge.get(0) + " " + currentEdge.get(1) + '\n');
        }
        graph.getNodeByIndex(graph.nodeCount()-1).setAdjacencyList(adjacencyListForSetUp);
    }

    private void saveIntoFile(File file){
        JSONArray nodes = new JSONArray();
        for(int i = 0; i < graph.nodeCount(); ++i) {
            JSONObject oneNode = new JSONObject();
            oneNode.put("name", String.valueOf(graph.getNodeByIndex(i).getName()));

            JSONArray location = new JSONArray();
            location.add(0, graph.getNodeByIndex(i).getLocation().x);
            location.add(1, graph.getNodeByIndex(i).getLocation().y);
            oneNode.put("location", location);

            JSONArray adjacencyList = new JSONArray();

            for(int j = 0; j < graph.getNodeByIndex(i).edgeCount(); ++j){
                JSONArray edgeInfo = new JSONArray();
                edgeInfo.add(0, String.valueOf(graph.getNodeByIndex(i).getEdgeByIndex(j).getEndNodeName()));
                edgeInfo.add(1, graph.getNodeByIndex(i).getEdgeByIndex(j).getWeight());
                adjacencyList.add(edgeInfo);
            }
            oneNode.put("adjacencyList", adjacencyList);
            nodes.add(oneNode);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(nodes.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + nodes);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR FILE OPEN");
        }
    }

    private int parseInt(){
        String str = txtfEdgeWeight.getText();
        int totalNum = 0;
        if(str.charAt(0) == '0' || str.charAt(0) == '-'){
            JOptionPane.showMessageDialog(null, "number must be >= 0!");
            return 0;
        }

        for(int i = 0; i < str.length(); ++i){
            if((int)str.charAt(i) < 48 || (int)str.charAt(i) > 57) {
                JOptionPane.showMessageDialog(null, "Edge weight must be only integer!");
                return 0;
            }
            totalNum = totalNum * 10 + (int)(str.charAt(i)) - 48;
        }
        return totalNum;
    }
}