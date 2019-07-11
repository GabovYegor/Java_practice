package Visualiazation;

import DataClasses.AlgorithmStepData;
import DataClasses.Graph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.Timer;

import static Algorithm.Dijkstra.dijkstra;

public class AdapterMainWindow extends MainWindow{
    private static final int BIGRADIUS = 30;
    private static final int BOUND_WIDTH = 500;
    private static final int BOUND_HEIGHT = 400;
    private DrawingPanel drawingPanel;

    private Graph graph;
    private ArrayList<AlgorithmStepData> graphStates;
    private Integer algorithmStepNum;
    private java.util.Timer timer = new Timer();
    private boolean isAlgorithhBlock;
    private boolean closeMainThreadAlgorithm;
    private ScheduledTask task;
    private AdapterMainWindow thisAdapter;

    public AdapterMainWindow(String title, Graph graph) {
        super(title);
        this.graph = graph;
        initVariables();
        layoutDrawingPanelSettings();
        buttonsSettings();
    }

    // Вынесение инициализации за пределы конструктора,
    // в отдельныю функцию чтобы не засорять конструктор
    private void initVariables(){
        algorithmStepNum = 0;
        timer = new Timer();
        isAlgorithhBlock = false;
        closeMainThreadAlgorithm = false;
        graphStates = new ArrayList<>();
        drawingPanel = new DrawingPanel(graph, txtfNode);
        thisAdapter = this;
    }

    // Инициализация положения поля для вывода графа
    private void layoutDrawingPanelSettings() {
        drawingPanel = new DrawingPanel(graph, txtfNode);
        drawingPanel.setPreferredSize(new Dimension(1000, 1000));
        drawingPanel.setBackground(new Color(230, 230, 230));
        getContentPane().add(drawingPanel);
    }

    // Настройка слушателей для пользовательских кнопок
    private void buttonsSettings(){

        // Вывести справочную информацию
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

        // ввести граф из файла
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
                repaint();
            }
        });

        // Сохранить граф в файл
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

        // Добавление вершины в граф
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
                repaint();
            }
        });

        // удаление вершины из графа
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
                repaint();
            }
        });

        // добавление ребра в граф
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
                repaint();
            }
        });

        // отрисовка графа в новых координатах
        btnRepaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                for(int i = 0; i < graph.nodeCount(); ++i){
                    graph.getNodeByIndex(i).setLocation(new Point(random.nextInt(BOUND_WIDTH) + BIGRADIUS, random.nextInt(BOUND_HEIGHT) + BIGRADIUS));
                }
                repaint();
            }
        });

        // переход к алгоритму
        // при этом сменится панель с панели ввода графа на панель работы алгоритма
        btnGoToAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(boxVInputPanel);
                getContentPane().add(boxVOutputPanel, BorderLayout.WEST);
                validate();
                repaint();
            }
        });

        // Очистить поле ввода алгоритма и начать ввод наново
        btnResetInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph = new Graph();
                drawingPanel.updateGraph(graph);
            }
        });

        // Кнопка запускает алгоритм
        // создает новый поток, который будет вызываться каждую секунду и выполнять шаг алгоритма
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
                    setIsAlgorithmBlock(true);
                    setUpCloseMainThreadAlgorithm();
                    if(graphStates.size() != 0) {
                        graph = graphStates.get(graphStates.size() - 2).getGraph(); // if restart
                        drawingPanel.updateGraph(graph);
                    }

                    algorithmStepNum = 0;
                    graphStates = dijkstra(graph, txtfStartNode.getText().charAt(0));
                    graph = graphStates.get(0).getGraph(); // инициализируемся графом на 0-вом шаге
                    drawingPanel.updateGraph(graph);
                    drawingPanel.setTrueIsAlgorithmValue(); // for upper text
                    txtaLog.setText("");
                    txtaLog.append(graphStates.get(0).getStr());

                    // задаем задание, которое будет периодически выполнятся
                    task = new ScheduledTask(timer, graphStates.size() - 1, 0, thisAdapter);
                    timer.schedule(task, 0, 1000);
                }
                else
                    JOptionPane.showMessageDialog(null, "Algorithm in process!!!");
            }
        });

        // Переход к последнему шагу алгоритма
        btnFinishAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isAlgorithhBlock) {
                    algorithmStepNum = graphStates.size() - 1;
                    task.setStepCount(graphStates.size() - 1);
                    drawingPanel.updateGraph(graphStates.get(graphStates.size() - 1).getGraph());
                    setIsAlgorithmBlock(false);
                    updateTimer();
                }
                else {
                    JOptionPane.showMessageDialog(null,"You must start algorithm!");
                }
            }
        });

        // подсчитать длину до вершины от стартовой + вывести путь
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

        // перейти к следующему шагу алгоритма
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

        // перейти к предыдущему шагу алгоритма
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

        // начать выполнение программы заново - с ввода графа
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

    // парсер для файла формата .json
    private void parseGraph(File file) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file))
        {
            JSONArray nodesList = (JSONArray) jsonParser.parse(reader);
            try {
                nodesList.forEach(emp -> parseNodeObject((JSONObject) emp));
            }
            catch (Throwable ex){
                if(ex.getMessage().isEmpty())
                    JOptionPane.showMessageDialog(null, "ERROR");
                else
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

    // парсить один элемент массива вершин
    private void parseNodeObject(JSONObject node) {
        String name = (String) node.get("name");
        graph.addNode(name.charAt(0));

        JSONArray location = (JSONArray) node.get("location");
        Long xl = (Long)(location.get(0));
        int x = xl.intValue();
        Long yl = (Long)(location.get(1));
        int y = yl.intValue();
        if(x > BOUND_WIDTH || y > BOUND_HEIGHT || x < 0 || y < 0)
            throw new IndexOutOfBoundsException("location don`t in bounds");
        graph.getNodeByIndex(graph.nodeCount()-1).setLocation(new Point(x, y));

        JSONArray adjacencyList = (JSONArray) node.get("adjacencyList");
        Iterator adjListItr = adjacencyList.iterator();
        while ((adjListItr.hasNext())){
            JSONArray currentEdge = (JSONArray) adjListItr.next();
            Long  wl = (Long) currentEdge.get(1);
            int weight = wl.intValue();
            if(weight > 0) {
                graph.addEdge(name.charAt(0), String.valueOf(currentEdge.get(0)).charAt(0), weight);
            } else
                throw new IndexOutOfBoundsException("Weight <= 0");
        }
    }

    // сохранить в файл
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

    // парсить int из текстового поля
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

    // следующий шаг
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

    // закрыть/открыть главный поток
    public void setUpCloseMainThreadAlgorithm(){
        closeMainThreadAlgorithm = !closeMainThreadAlgorithm;
    }

    // обновить таймер
    public void updateTimer(){
        timer = new Timer();
    }

    // установить блок на смену шага по клавише
    public void setIsAlgorithmBlock(boolean value){
        isAlgorithhBlock = value;
    }
}
