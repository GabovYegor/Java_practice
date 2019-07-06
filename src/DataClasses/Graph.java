package DataClasses;


import Visualiazation.Edge;
import Visualiazation.Node;

import java.awt.*;
import java.util.ArrayList;


public class Graph {

    private ArrayList<Node> nodeList; // Список вершин графа.

    // Конструктор.
    public Graph() {
        nodeList = new ArrayList<>();
    }

    // Добавить вершину в граф (если такой вершины ещё нет) и вернуть её индекс в nodeList.
    public int addNode(char name) {
        int index = nodeIndex(name);
        if (index == -1) {
            nodeList.add(new Node(name));
            return nodeCount() - 1;
        }
        return index;
    }

    // Добавить ребро в граф (предварительно создав вершины start и end, если их ещё нет).
    public void addEdge(char start, char end, int weight) {
        nodeList.get(addNode(start)).addEdge(end, weight);
        addNode(end);
    }

    // Количество вершин графа.
    public int nodeCount() {
        return nodeList.size();
    }

    // Возвращает индекс вершины с именем name в nodeList или -1, если такой вершины не существует.
    public int nodeIndex(char name) {
        for (int i = 0; i < nodeCount(); i++) {
            if (nodeList.get(i).getName() == name)
                return i;
        }
        return -1;
    }

    // Возвращает имя вершины с индексом index.
    public char nodeName(int index) {
        return nodeList.get(index).getName();
    }

    // Проверяет, существует ли вершина с именем name.
    public boolean isNodeExists(char name) {
        return nodeIndex(name) >= 0;
    }

    // Количество рёбер исходящих из вершины с индексом index.
    public int countOfEdgesFromNode(int index) {
        return nodeList.get(index).edgeCount();
    }

    // Вернуть вершину по индексу
    public Node getNodeByIndex(int index){ return nodeList.get(index);}

    public Node getNodeByName(char name){
        for(Node node: nodeList){
            if(node.getName() == name)
                return node;
        }
        return new Node('\0');
    }


}


