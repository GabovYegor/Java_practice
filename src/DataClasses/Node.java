package DataClasses;

import DataClasses.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// Вершина графа.
public class Node {

    private char name;
    private ArrayList<Edge> adjacencyList; // Список смежности.
    private int distance; // Расстояние от стартовой вершины.
    private ArrayList<Character> path; // Пусть от стартовой вершины.
    private Point location;
    private Color color;
    public static final int BIGRADIUS = 30;
    public static final int BOUND = 500;

    // Конструктор.
    public Node(char name) {
        this.name = name;
        adjacencyList = new ArrayList<>();
        distance = Integer.MAX_VALUE; // Integer.MAX_VALUE считается бесконечностью.
        path = new ArrayList<>();
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

    public ArrayList<Edge> getAdjacencyList(){
        return adjacencyList;
    }

    public Edge getEdgeByIndex(int index) {
        if (index < 0 || index >= edgeCount())
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        return adjacencyList.get(index);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPath(ArrayList<Character> path) {
        this.path = (ArrayList<Character>) path.clone();
    }

    public void addInPath(char name) {
        path.add(name);
    }

    public void clearPath() {
        path.clear();
    }

    public ArrayList<Character> getPath() {
        return path;
    }

    public String pathToString() {
        StringBuilder strBuilder = new StringBuilder("");
        for (int i = 0; i < path.size(); i++)
            strBuilder.append(path.get(i));
        return strBuilder.toString();
    }

    public Node clone() {
        Node cloneNode = new Node(name);
        for (int i = 0; i < edgeCount(); i++) {
            cloneNode.adjacencyList.add(adjacencyList.get(i).clone());
        }
        cloneNode.distance = distance;
        // ДОБАВИТЬ ПАТЧ, КОГДА БУДЕТ ГОТОВО!!!!!!!
        cloneNode.location = location.getLocation();
        cloneNode.color = color;
        return cloneNode;
    }
}
