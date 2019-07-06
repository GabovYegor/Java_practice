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
        if(start == end)
            return;
        nodeList.get(addNode(start)).addEdge(end, weight);
        addNode(end);
    }

    // Количество вершин графа.
    public int nodeCount() {
        return nodeList.size();
    }

    // Вернуть вершину по индексу
    public Node getNodeByIndex(int index){ return nodeList.get(index);}

    public Node getNodeByName(char name){
        return getNodeByIndex(getIndexByName(name));
    }

    // Для графа не актуально, если нужно для алгоритма оставить, иначе - убрать
//    // Возвращает имя вершины с индексом index.
//    private char nodeName(int index) {
//        return nodeList.get(index).getName();
//    }

    // Для графа не актуально, если нужно для алгоритма оставить, иначе - убрать
//    // Проверяет, существует ли вершина с именем name.
//    public boolean isNodeExists(char name) {
//        return nodeIndex(name) >= 0;
//    }

    // Для графа не актуально, если нужно для алгоритма оставить, иначе -  убрать
//    // Количество рёбер исходящих из вершины с индексом index.
//    public int countOfEdgesFromNode(int index) {
//        return nodeList.get(index).edgeCount();
//    }

}


