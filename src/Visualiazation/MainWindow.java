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
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class ScheduledTask extends TimerTask {
    private Timer time;
    private int stepsNum;
    private int stepsCount;
    private MainWindow windowEx;
    ScheduledTask(Timer time, int stepsNum, int stepsCount, MainWindow windowEx){
        this.time = time;
        this.stepsNum = stepsNum;
        this.stepsCount = stepsCount;
        this.windowEx = windowEx;
    }
    public void setStepCount(int stepsCount){
        this.stepsCount = stepsCount;
    }

    @Override
    public void run() {
        if(stepsCount >= stepsNum ) {
            time.cancel();
            time.purge();
            windowEx.setUpCloseMainThreadAlgorithm();
            JOptionPane.showMessageDialog(null, "algorithm end work!");
            windowEx.updateTimer();
            windowEx.unblockAlgorithm();
            return;
        }
        stepsCount++;
        windowEx.nextStep();
    }
}


class EdgeKeyListener extends KeyAdapter {
    private JTextField txtfToSetUp;
    private int maxSimbolsNum;

    EdgeKeyListener(JTextField txtfToSetUp, int maxSimbolsNum){
        this.txtfToSetUp = txtfToSetUp;
        this.maxSimbolsNum = maxSimbolsNum;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if(txtfToSetUp.getText().length() >= maxSimbolsNum) {
            e.consume();
            JOptionPane.showMessageDialog(null, "Max count of symbols");
        }
    }
}

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
    private JButton btnManual;
    private JButton btnFromFile;
    private JButton btnSaveFile;
    private JButton btnEdgeAdd;
    private JButton btnNodeAdd;
    private JButton btnNodeRemove;
    private JButton btnRepaint;
    private JButton btnGoToAlgorithm;
    private JButton btnResetInput;

    private Box boxVOutputPanel;
    private JLabel lblStartNode;
    private JLabel lblAimNode;
    private JTextField txtfStartNode;
    private JTextField txtfAimNode;
    private JButton btnStartAlgorithm;
    private JButton btnFinishAlgorithm;
    private JButton btnNextStep;
    private JButton btnPreviousStep;
    private JButton btnCalculateLength;
    private JButton btnResetOutput;
    private ScheduledTask task;
    private JTextArea txtaLog;

    private DrawingPanel drawingPanel;

    private Graph graph;
    private ArrayList<AlgorithmStepData> graphStates;
    private Integer algorithmStepNum;
    private java.util.Timer timer = new Timer();
    private boolean isAlgorithhBlock;
    private boolean closeMainThreadAlgorithm;

    private MainWindow thisWindow = this;

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
        lblNode      = new JLabel("Name");
        lblEdgeFrom  = new JLabel("From");
        lblEdgeTo    = new JLabel("To");
        lblWeight    = new JLabel("Weight");

        txtfNode       = new JTextField(2);
        txtfNode.addKeyListener(new EdgeKeyListener(txtfNode, 1));

        txtfEdgeFrom   = new JTextField(2);
        txtfEdgeFrom.addKeyListener(new EdgeKeyListener(txtfEdgeFrom, 1));

        txtfEdgeTo     = new JTextField(2);
        txtfEdgeTo.addKeyListener(new EdgeKeyListener(txtfEdgeTo, 1));

        txtfEdgeWeight = new JTextField(5);
        txtfEdgeWeight.addKeyListener(new EdgeKeyListener(txtfEdgeWeight, 3));

        btnManual = new JButton("Manual");
        btnFromFile  = new JButton("From File");
        btnSaveFile = new JButton("Save this Graph");
        btnNodeAdd   = new JButton("Add Node");
        btnNodeRemove = new JButton("Remove Node");
        btnEdgeAdd   = new JButton("Add Edge");
        btnRepaint   = new JButton("Repaint Graph");
        btnGoToAlgorithm = new JButton("Go to algorithm");
        btnResetInput = new JButton("Reset");

        txtaLog = new JTextArea(10, 0);
        txtaLog.setText("Algorithm steps\n");
        txtaLog.setLineWrap(true);
        txtaLog.setWrapStyleWord(true);

        lblStartNode = new JLabel("Input start Node");
        lblAimNode = new JLabel("Aim Node");

        txtfStartNode  = new JTextField(2);
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

        algorithmStepNum = 0;
        timer = new Timer();
        isAlgorithhBlock = false;
        closeMainThreadAlgorithm = false;
    }

    private void windowSettings(){
        setSize(MAINWINDOW_WIDTH, MAINWINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(new Point(450, 200));
        setResizable(false);
    }

    private void layoutSettins(){
        layoutInputSettings();
        layoutOutputSettings();
        layoutDrawingPanelSettings();
        getContentPane().add(boxVInputPanel, BorderLayout.WEST);
        getContentPane().add(drawingPanel);
        getContentPane().add(new JScrollPane(txtaLog), BorderLayout.SOUTH);
    }

    private void buttonsSettings(){

        btnManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("Docs/manual.txt");
                StringBuilder allStrings = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        allStrings.append(line + '\n');
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, allStrings.toString());
            }
        });

        btnFromFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                File file = null;
                if (fileopen.showDialog(null, "Открыть файл") == JFileChooser.APPROVE_OPTION) {
                    graph = new Graph();
                    drawingPanel.updateGraph(graph);
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

        btnNodeRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtfNode.getText().isEmpty()) {
                    boolean isDelelete = false;
                    for (int i = 0; i < graph.nodeCount(); ++i) {
                        if (graph.getNodeByIndex(i).getName() == txtfNode.getText().charAt(0)) {
                            graph.removeNode(txtfNode.getText().charAt(0));
                            isDelelete = true;
                        }
                    }
                    if (!isDelelete)
                        JOptionPane.showMessageDialog(null, "Graph don`t contain this node");
                }
                else
                    JOptionPane.showMessageDialog(null, "Node`s name empty");
                txtfNode.setText("");
            }
        });

        btnEdgeAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtfEdgeTo.getText().isEmpty() && !txtfEdgeFrom.getText().isEmpty() &&
                        txtfEdgeTo.getText().charAt(0) == txtfEdgeFrom.getText().charAt(0)) {
                    JOptionPane.showMessageDialog(null, "Graph don`t have loop!");
                    txtfEdgeFrom.setText("");
                    txtfEdgeTo.setText("");
                    txtfEdgeWeight.setText("");
                    return;
                }

                boolean flag = false;
                if(!txtfEdgeTo.getText().isEmpty() && !txtfEdgeFrom.getText().isEmpty() && !txtfEdgeWeight.getText().isEmpty()) {
                    int currentWeight = parseInt();
                    if(currentWeight == 0) {
                        txtfEdgeFrom.setText("");
                        txtfEdgeTo.setText("");
                        txtfEdgeWeight.setText("");
                        return;
                    }

                    boolean isContain = false;
                    for(int i = 0; i < graph.nodeCount(); ++i)
                        if(graph.getNodeByIndex(i).getName() == txtfEdgeFrom.getText().charAt(0))
                            isContain = true;

                    if(isContain) {
                        for (int i = 0; i < graph.getNodeByName(txtfEdgeFrom.getText().charAt(0)).edgeCount(); ++i) {
                            if (graph.getNodeByName(txtfEdgeFrom.getText().charAt(0)).getEdgeByIndex(i).getEndNodeName() == txtfEdgeTo.getText().charAt(0)) {
                                JOptionPane.showMessageDialog(null, "Graph is already contain edge \n" +
                                                                                            "weight will be update");
                            }
                        }
                    }

                    graph.addEdge(txtfEdgeFrom.getText().charAt(0), txtfEdgeTo.getText().charAt(0), currentWeight);
                    repaint();
                    flag = true;
                }

                boolean isSetBlack = false;
                for(int i = 0; i < graph.nodeCount(); ++i){
                    if(graph.getNodeByIndex(i).getColor() == Color.BLUE) {
                        for (int j = 0; j < graph.nodeCount(); ++j) {
                            if (graph.getNodeByIndex(j).getColor() == Color.YELLOW) {
                                isSetBlack = true;
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

                if(isSetBlack)
                    for(int i = 0; i < graph.nodeCount(); ++i)
                        graph.getNodeByIndex(i).setColor(Color.BLACK);

                txtfEdgeFrom.setText("");
                txtfEdgeTo.setText("");
                txtfEdgeWeight.setText("");

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

        btnResetInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph = new Graph();
                drawingPanel.updateGraph(graph);
            }
        });

        btnStartAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtfStartNode.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Input start node name");
                    return;
                }
                boolean isRightNode = false;
                for(int i = 0; i < graph.nodeCount(); ++i){
                    if(graph.getNodeByIndex(i).getName() == txtfStartNode.getText().charAt(0))
                        isRightNode = true;
                }

                if(!isRightNode){
                    JOptionPane.showMessageDialog(null, "There are no node with this name");
                    return;
                }

                if(!isAlgorithhBlock) {
                    isAlgorithhBlock = true;
                    graphStates = graph.Dijkstra(txtfStartNode.getText().charAt(0));
                    graph = graphStates.get(algorithmStepNum).getGraph();
                    drawingPanel.updateGraph(graph);
                    drawingPanel.setTrueIsAlgorithmValue();
                    txtaLog.setText("");
                    txtaLog.append(graphStates.get(0).getStr());
                    algorithmStepNum = 0;
                    task = new ScheduledTask(timer, graphStates.size() - 1, 0, thisWindow);
                    setUpCloseMainThreadAlgorithm();
                    timer.schedule(task, 0, 1000);
                }
                else
                    JOptionPane.showMessageDialog(null, "Algorithm in process!!!");
            }
        });

        btnFinishAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmStepNum = graphStates.size() - 1;
                task.setStepCount(graphStates.size() - 1);
                drawingPanel.updateGraph(graphStates.get(graphStates.size()-1).getGraph());
                isAlgorithhBlock = false;
                updateTimer();
            }
        });

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
                        return;
                    }
                }

                JOptionPane.showMessageDialog(null, "There are no node with this name!");
            }
        });

        btnNextStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(closeMainThreadAlgorithm){
                    JOptionPane.showMessageDialog(null, "Main thread closed");
                    return;
                }
                if (drawingPanel.isAlgorithm){
                    if(algorithmStepNum != graphStates.size() - 1) {
                        graph = graphStates.get(++algorithmStepNum).getGraph();
                        drawingPanel.updateGraph(graph);
                        txtaLog.setText("");
                        for(int i = 0; i <= algorithmStepNum; ++i){
                            txtaLog.append(graphStates.get(i).getStr() + '\n' + '\n');
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Algorithm`s work end!");
                }
                else
                    JOptionPane.showMessageDialog(null, "Algorithm don`t start!");
            }
        });

        btnPreviousStep.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(closeMainThreadAlgorithm){
                    JOptionPane.showMessageDialog(null, "Main thread closed");
                    return;
                }

                if(algorithmStepNum != 0){
                    graph = graphStates.get(--algorithmStepNum).getGraph();
                    drawingPanel.updateGraph(graph);
                    txtaLog.setText("");
                    for(int i = 0; i <= algorithmStepNum; ++i){
                        txtaLog.append(graphStates.get(i).getStr() + '\n' + '\n');
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "It`s already first step!");
            }
        });

        btnResetOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(boxVOutputPanel);
                JPanel panel = new JPanel();
                panel.setBackground(Color.BLUE);
                panel.setPreferredSize(new Dimension(130, 0));
                getContentPane().add(boxVInputPanel, BorderLayout.WEST);

                graph = new Graph();
                drawingPanel.setFalseIsAlgorithmValue();
                drawingPanel.updateGraph(graph);
                txtaLog.setText("Algorithm steps");

                updateTimer();
                isAlgorithhBlock = false;
                algorithmStepNum = 0;

                validate();
                repaint();
            }
        });
    }

    private void setUpSimpleButton(Box mainBox, JButton btn){
        mainBox.add(Box.createVerticalStrut(10));
        Box boxH = Box.createHorizontalBox();
        boxH.add(btn);
        boxH.add(Box.createHorizontalGlue());
        mainBox.add(boxH);
    }

    private void setUpLblTxtf(Box mainBox, JLabel lbl, JTextField txtf, int offset){
        mainBox.add(Box.createVerticalStrut(10));
        Box boxH = Box.createHorizontalBox();
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(lbl);
        boxH.add(Box.createHorizontalStrut(5));
        boxH.add(txtf);
        boxH.add(Box.createHorizontalStrut(offset));
        mainBox.add(boxH);
    }

    private void setUpTwoLblTxtf(Box mainBox, JLabel lbl_1, JTextField txtf_1, JLabel lbl_2, JTextField txtf_2, int offset){
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

    private void layoutInputSettings(){
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

        boxVInputPanel.add(Box.createVerticalStrut((int)Double.POSITIVE_INFINITY));
    }

    private void layoutOutputSettings(){
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
        boxVOutputPanel.add(Box.createVerticalStrut((int)Double.POSITIVE_INFINITY));
    }

    private void layoutDrawingPanelSettings(){

        drawingPanel = new DrawingPanel(graph, txtfNode);
        drawingPanel.setPreferredSize(new Dimension(1000, 1000));
        drawingPanel.setBackground(new Color(230, 230, 230));
    }

    private void parseGraph(File file) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file))
        {
            JSONArray nodesList = (JSONArray) jsonParser.parse(reader);
            try {
                nodesList.forEach(emp -> parseNodeObject((JSONObject) emp));
            }
            catch (Throwable ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
                btnResetInput.doClick();
                return;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "PARSE ERROR");
        }
    }

    private void parseNodeObject(JSONObject node)
    {
        String name = (String) node.get("name");
        graph.addNode(name.charAt(0));

        JSONArray location = (JSONArray) node.get("location");
        Long xl = (Long)(location.get(0));
        int x = xl.intValue();
        Long yl = (Long)(location.get(1));
        int y = yl.intValue();
        if(x >  BOUND_WIDTH || y > BOUND_HEIGHT || x < 0 || y < 0)
            throw new IndexOutOfBoundsException("location don`t in bounds");
        graph.getNodeByIndex(graph.nodeCount()-1).setLocation(new Point(x, y));

        JSONArray adjacencyList = (JSONArray) node.get("adjacencyList");
        ArrayList<Edge> adjacencyListForSetUp = new ArrayList<Edge>();
        Iterator adjListItr = adjacencyList.iterator();
        boolean isThrowEx = false;
        while ((adjListItr.hasNext())){
            JSONArray currentEdge = (JSONArray) adjListItr.next();
            Long  wl = (Long) currentEdge.get(1);
            int weight = wl.intValue();
            if(weight <= 0) {
                adjacencyListForSetUp.add(new Edge(String.valueOf(currentEdge.get(0)).charAt(0), weight));
            } else
                isThrowEx = true;
        }
        if(isThrowEx)
            throw new IndexOutOfBoundsException("Weight <= 0");
        graph.getNodeByIndex(graph.nodeCount() - 1).setAdjacencyList(adjacencyListForSetUp);

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

    public void nextStep(){
        if (drawingPanel.isAlgorithm){
            if(algorithmStepNum != graphStates.size() - 1) {
                graph = graphStates.get(++algorithmStepNum).getGraph();
                drawingPanel.updateGraph(graph);
                txtaLog.setText("");
                for(int i = 0; i <= algorithmStepNum; ++i){
                    txtaLog.append(graphStates.get(i).getStr() + '\n' + '\n');
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Algorithm`s work end!");
        }
        else
            JOptionPane.showMessageDialog(null, "Algorithm don`t start!");
    }

    public void setUpCloseMainThreadAlgorithm(){
        closeMainThreadAlgorithm = !closeMainThreadAlgorithm;
    }

    public void updateTimer(){
        timer = new Timer();
    }

    public void unblockAlgorithm(){
        isAlgorithhBlock = false;
    }
}

