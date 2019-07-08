package DataClasses;

import java.awt.*;

// Ребро графа для списка смежности adjacencyList.
public class Edge {
    private char endNodeName; // имя вершины, в которую ведёт ребро
    private int weight; // вес ребра
    public static final int MAX_WEIGHT = 1000000;
    private Color color;

    // Конструктор.
    public Edge(char endNodeName, int weight) {
        this.endNodeName = endNodeName;
        this.weight = weight;
        color = Color.black;
    }

    public char getEndNodeName(){
        return endNodeName;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Edge clone() {
        Edge cloneEdge = new Edge(endNodeName, weight);
        cloneEdge.color = color;
        return cloneEdge;
    }

}
