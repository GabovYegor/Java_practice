package Visualiazation;

import java.awt.*;
import java.util.ArrayList;

// Вершина графа.
public class Node {

    private char name;
    private ArrayList<Edge> adjacencyList; // Список смежности.
    private Point location;
    private Color color;

    // Конструктор.
    public Node(char name) {
        this.name = name;
        adjacencyList = new ArrayList<>();
        location = new Point(0, 0);
        color = Color.white;
    }

    // Добавить ребро в список смежности (если такое ребро уже есть, вес ребра будет заменён на новый).
    public void addEdge(char endNodeName, int weight) {
        for (int i = 0; i < edgeCount(); i++) {
            if (adjacencyList.get(i).getEndNodeName() == endNodeName) {
                adjacencyList.get(i).setWeight(weight);
                return;
            }
        }
        adjacencyList.add(new Edge(endNodeName, weight));
    }

    // Количество рёбер, исходящих из данной вершины.
    public int edgeCount() {
        return adjacencyList.size();
    }

    public void setLocation(Point location){
        this.location = location;
    }

    public Point getLocation(){
        return location;
    }

    public char getName() {
        return name;
    }

    public ArrayList<Edge> getAdjacencyList(){ return adjacencyList; }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
