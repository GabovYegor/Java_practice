package DataClasses;

import java.awt.*;

// класс ребра
public class Edge {
    private char endNodeName;
    private int weight;
    private Color color;

    public Edge(char endNodeName, int weight) {
        this.endNodeName = endNodeName;
        if (weight <= 0)
            throw new IllegalArgumentException("Ребро должно иметь положительный вес.");
        this.weight = weight;
        color = Color.black;
    }

    // получить вершину "куда"
    public char getEndNodeName(){
        return endNodeName;
    }


    public void setWeight(int weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("Ребро должно иметь положительный вес.");
        this.weight = weight;
    }

    // получить вес ребра
    public int getWeight(){
        return weight;
    }

    // установить цвет ребра
    public Color getColor() {
        return color;
    }

    // склонировать значения ребра в новое ребро
    public Edge clone() {
        Edge cloneEdge = new Edge(endNodeName, weight);
        cloneEdge.color = color;
        return cloneEdge;
    }

}
