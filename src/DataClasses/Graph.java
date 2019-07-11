package DataClasses;

import java.awt.*;
import java.util.ArrayList;


public class Graph {

    private ArrayList<Node> nodeList;

    public Graph() {
        nodeList = new ArrayList<>();
    }

    public int addNode(char name) {
        int index = getIndexByName(name);
        if (index == -1) {
            nodeList.add(new Node(name));
            return nodeCount() - 1;
        }
        return index;
    }

    public void removeNode(char name) {
        int index = getIndexByName(name);
        if (index >= 0) {
            for (int i = 0; i < nodeCount(); i++) {
                getNodeByIndex(i).removeEdge(name);
            }
            nodeList.remove(index);
        }
    }

    public void addEdge(char start, char end, int weight) {
        if (start == end)
            throw new IllegalArgumentException("Граф не может содержать петель.");
        if (weight <= 0)
            throw new IllegalArgumentException("Граф может содержать только рёбра с положительным весом.");
        if (weight > Edge.MAX_WEIGHT)
            throw new IllegalArgumentException("Вес ребра не может превышать " + Edge.MAX_WEIGHT + ".");
        nodeList.get(addNode(start)).addEdge(end, weight);
        addNode(end);
    }

    public int nodeCount() {
        return nodeList.size();
    }

    public int getIndexByName(char name) {
        for (int i = 0; i < nodeCount(); i++)
            if (nodeList.get(i).getName() == name)
                return i;
        return -1;
    }

    public Node getNodeByIndex(int index) {
        if (index < 0 || index >= nodeCount())
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        return nodeList.get(index);
    }

    public Node getNodeByName(char name) {
        if (getIndexByName(name) == -1)
            throw new IllegalArgumentException("В графе нет вершины с именем " + name + ".");
        return getNodeByIndex(getIndexByName(name));
    }

    public Graph clone() {
        Graph cloneGraph = new Graph();
        for (int i = 0; i < nodeCount(); i++)
            cloneGraph.nodeList.add(nodeList.get(i).clone());
        return cloneGraph;
    }

}


