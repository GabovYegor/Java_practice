package DataClasses;

import java.awt.*;

public class Edge {
    private char endNodeName;
    private int weight;
    private Color color;

    public Edge(char endNodeName, int weight) {
        this.endNodeName = endNodeName;
        if(weight <= 0)
            throw new IndexOutOfBoundsException();
        this.weight = weight;
        color = Color.black;
    }

    public char getEndNodeName(){
        return endNodeName;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
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
