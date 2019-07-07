package DataClasses;

import java.util.ArrayList;


public class Graph {

    private ArrayList<Node> nodeList; // Список вершин графа.

    // Конструктор.
    public Graph() {
        nodeList = new ArrayList<>();
    }

    // Добавить вершину в граф (если такой вершины ещё нет) и вернуть её индекс в nodeList.
    public int addNode(char name) {
        int index = getIndexByName(name);
        if (index == -1) {
            nodeList.add(new Node(name));
            return nodeCount() - 1;
        }
        return index;
    }

    // Возвращает индекс вершины с именем name в nodeList или -1, если такой вершины не существует.
    private int getIndexByName(char name) {
        for (int i = 0; i < nodeCount(); i++) {
            if (nodeList.get(i).getName() == name)
                return i;
        }
        return -1;
    }

    // Добавить ребро в граф (предварительно создав вершины start и end, если их ещё нет).
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

    // Количество вершин графа.
    public int nodeCount() {
        return nodeList.size();
    }

    // Вернуть вершину по индексу.
    public Node getNodeByIndex(int index) {
        if (index < 0 || index >= nodeCount())
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        return nodeList.get(index);
    }

    // Вернуть вершину по имени.
    public Node getNodeByName(char name) {
        if (getIndexByName(name) == -1)
            throw new IllegalArgumentException("В графе нет вершины с именем " + name + ".");
        return getNodeByIndex(getIndexByName(name));
    }
    
    // Очистить граф.
    public void clear() {
        nodeList = new ArrayList<>();
    }

}


