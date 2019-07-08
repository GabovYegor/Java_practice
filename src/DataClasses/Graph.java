package DataClasses;

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
        int index = getIndexByName(name);
        if (index == -1) {
            nodeList.add(new Node(name));
            return nodeCount() - 1;
        }
        return index;
    }

    // Возвращает индекс вершины с именем name в nodeList или -1, если такой вершины не существует.
    private int getIndexByName(char name) {
        for (int i = 0; i < nodeCount(); i++)
            if (nodeList.get(i).getName() == name)
                return i;
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

    public Graph clone() {
        Graph cloneGraph = new Graph();
        for (int i = 0; i < nodeCount(); i++)
            cloneGraph.nodeList.add(nodeList.get(i).clone());
        return cloneGraph;
    }


    public ArrayList<AlgorithmStepData> Dijkstra(char startNodeName) {
        if (getIndexByName(startNodeName) == -1)
            throw new IllegalArgumentException("Алгоритм не содержит вершины с именем '" + startNodeName + "'.");

        ArrayList<AlgorithmStepData> result = new ArrayList<>();
        ArrayList<Integer> queue = new ArrayList<>();
        StringBuilder strBuilder;


        // Шаг 0: Инициализация.
        for (int i = 0; i < nodeCount(); i++) {
            getNodeByIndex(i).setDistance(Integer.MAX_VALUE);
            getNodeByIndex(i).setColor(Color.gray);
            getNodeByIndex(i).clearPath();
        }
        getNodeByName(startNodeName).setDistance(0);
        getNodeByName(startNodeName).setColor(Color.orange);
        getNodeByName(startNodeName).addInPath(startNodeName);
        queue.add(getIndexByName(startNodeName));
        strBuilder = new StringBuilder("Шаг 0:\nИнициализация.");
        result.add(new AlgorithmStepData(this.clone(), strBuilder.toString()));


        // Шаг m: раскрытие вершин.
        int m = 1;
        while (queue.size() > 0) {
            int currentNodeIndex = queue.get(0);
            int indexInQueue = 0;
            for (int i = 1; i < queue.size(); i++) {
                if (getNodeByIndex(queue.get(i)).getDistance() < getNodeByIndex(currentNodeIndex).getDistance()) {
                    currentNodeIndex = queue.get(i);
                    indexInQueue = i;
                }
            }
            queue.remove(indexInQueue);

            getNodeByIndex(currentNodeIndex).setColor(Color.red);
            strBuilder = new StringBuilder("Шаг " + m + ":\nРаскрытие вершины '" + getNodeByIndex(currentNodeIndex).getName() + "'.");
            if (getNodeByIndex(currentNodeIndex).edgeCount() == 0) {
                strBuilder.append("\nУ вершины нет исходящих рёбер.");
            }
            result.add(new AlgorithmStepData(this.clone(), strBuilder.toString()));

            for (int i = 0; i < getNodeByIndex(currentNodeIndex).edgeCount(); i++) {
                int currentEndOfEdgeIndex = getIndexByName(getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getEndNodeName());
                getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.green);
                if ( getNodeByIndex(currentEndOfEdgeIndex).getDistance() == Integer.MAX_VALUE ) {
                    getNodeByIndex(currentEndOfEdgeIndex).setDistance(getNodeByIndex(currentNodeIndex).getDistance() + getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight());
                    getNodeByIndex(currentEndOfEdgeIndex).setPath(getNodeByIndex(currentNodeIndex).getPath());
                    getNodeByIndex(currentEndOfEdgeIndex).addInPath(getNodeByIndex(currentEndOfEdgeIndex).getName());
                    strBuilder = new StringBuilder("Вершина '" + getNodeByIndex(currentEndOfEdgeIndex).getName() + "' ещё не посещалась.\n");
                    strBuilder.append("Назначен новый путь: " + getNodeByIndex(currentEndOfEdgeIndex).pathToString() + " (расстояние — " + getNodeByIndex(currentEndOfEdgeIndex).getDistance() + ").");
                    result.add(new AlgorithmStepData(this.clone(), strBuilder.toString()));
                    queue.add(currentEndOfEdgeIndex);
                    getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.orange);
                } else {
                    strBuilder = new StringBuilder("Вершина '" + getNodeByIndex(currentEndOfEdgeIndex).getName() + "' уже посещалась.\n");
                    strBuilder.append("Текущий путь до вершины: " + getNodeByIndex(currentEndOfEdgeIndex).pathToString() + " (расстояние — " + getNodeByIndex(currentEndOfEdgeIndex).getDistance() + ").\n");
                    if ( getNodeByIndex(currentEndOfEdgeIndex).getDistance() >= getNodeByIndex(currentNodeIndex).getDistance() + getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight() ) {
                        getNodeByIndex(currentEndOfEdgeIndex).setDistance(getNodeByIndex(currentNodeIndex).getDistance() + getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight());
                        getNodeByIndex(currentEndOfEdgeIndex).setPath(getNodeByIndex(currentNodeIndex).getPath());
                        getNodeByIndex(currentEndOfEdgeIndex).addInPath(getNodeByIndex(currentEndOfEdgeIndex).getName());
                        strBuilder.append("Найден более короткий путь: " + getNodeByIndex(currentEndOfEdgeIndex).pathToString() + " (расстояние — " + getNodeByIndex(currentEndOfEdgeIndex).getDistance() + ").");
                    } else {
                        strBuilder.append("Путь из вершины '" + getNodeByIndex(currentNodeIndex).getName() + "' (расстояние — " + (getNodeByIndex(currentNodeIndex).getDistance() + getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight()) + ") не является более коротким.");
                    }
                    result.add(new AlgorithmStepData(this.clone(), strBuilder.toString()));
                    getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.black);
                }
            }

            getNodeByIndex(currentNodeIndex).setColor(Color.black);
            m++;
        }


        // Итоговый результат.
        strBuilder = new StringBuilder("Результат:\n");
        strBuilder.append("Путь от вершины '" + startNodeName + "' до вершины\n");
        for (int i = 0; i < nodeCount(); i++) {
            strBuilder.append("'" + getNodeByIndex(i).getName() + "': ");
            if (getNodeByIndex(i).getDistance() == Integer.MAX_VALUE) {
                strBuilder.append("вершина недостежима.\n");
            } else {
                strBuilder.append(getNodeByIndex(i).pathToString() + " (расстояние — " + getNodeByIndex(i).getDistance() + ").\n");
            }
        }
        result.add(new AlgorithmStepData(this.clone(), strBuilder.toString()));

        return result;
    }
}


