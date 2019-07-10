package DataClasses;

import java.awt.*;

public class Edge {
    private char endNodeName;
    private int weight;
    public static final int MAX_WEIGHT = 1000000;
    private Color color;

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

    public Edge clone() {
        Edge cloneEdge = new Edge(endNodeName, weight);
        cloneEdge.color = color;
        return cloneEdge;
    }

}
