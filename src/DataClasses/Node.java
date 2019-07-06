package DataClasses;

import DataClasses.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// Вершина графа.
public class Node {

    private char name;
    private ArrayList<Edge> adjacencyList; // Список смежности.
    private Point location;
    private Color color;
    public static final int BIGRADIUS = 30;
    public static final int BOUND = 500;

    // Конструктор.
    public Node(char name) {
        this.name = name;
        adjacencyList = new ArrayList<>();
        Random random = new Random();
        location = new Point(random.nextInt(BOUND) + BIGRADIUS, random.nextInt(BOUND) + BIGRADIUS);
        color = Color.black;
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
