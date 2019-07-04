package DataClasses;

import java.util.ArrayList;

public class Graph {

    private ArrayList<Node> nodeList; // Список вершин графа.

    // Конструктор.
    public Graph() {
        nodeList = new ArrayList<>();
    }

    // Добавить вершину в граф и вернуть её индекс в nodeList.
    public int addNode(char name) {
        int index = nodeIndex(name);
        if (index == -1) {
            nodeList.add(new Node(name));
            return nodeCount() - 1;
        }
        return index;
    }

    // Добавить ребро в граф (предварительно создав вершины start и end, если их ещё нет).
    public void addEdge(char start, char end, double weight) {
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
            if (nodeList.get(i).name == name)
                return i;
        }
        return -1;
    }

    // Проверяет, существует ли вершина с именем name.
    public boolean isNodeExists(char name) {
        return nodeIndex(name) >= 0;
    }

    // Вершина графа.
    private class Node {

        private char name;
        private ArrayList<Edge> adjacencyList; // Список смежности.

        // Конструктор.
        private Node(char name) {
            this.name = name;
            adjacencyList = new ArrayList<>();
        }

        // Добавить ребро в список смежности (если такое ребро уже есть, вес ребра будет заменён на новый).
        private void addEdge(char endNodeName, double weight) {
            for (int i = 0; i < edgeCount(); i++) {
                if (adjacencyList.get(i).endNodeName == endNodeName) {
                    adjacencyList.get(i).weight = weight;
                    return;
                }
            }
            adjacencyList.add(new Edge(endNodeName, weight));
        }

        // Количество рёбер, исходящих из данной вершины.
        private int edgeCount() {
            return adjacencyList.size();
        }




        // Ребро графа для списка смежности adjacencyList.
        private class Edge {
            private char endNodeName; // имя вершины, в которую ведёт ребро
            private double weight; // вес ребра

            // Конструктор.
            private Edge(char endNodeName, double weight) {
                this.endNodeName = endNodeName;
                this.weight = weight;
            }
        }
    }

}
