package DataClasses;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Node {

    private char name;
    private ArrayList<Edge> adjacencyList;
    private int distance;
    private ArrayList<Character> path;
    private Point location;
    private Color color;
    public static final int BIGRADIUS = 30;
    public static final int BOUND_WIGHT = 500;
    public static final int BOUND_HEIGHT = 400;

    public Node(char name) {
        this.name = name;
        adjacencyList = new ArrayList<>();
        distance = Integer.MAX_VALUE;
        path = new ArrayList<>();
        Random random = new Random();
        location = new Point(random.nextInt(BOUND_WIGHT) + BIGRADIUS, random.nextInt(BOUND_HEIGHT) + BIGRADIUS);
        color = Color.black;
    }

    public void addEdge(char endNodeName, int weight) {
        for (int i = 0; i < edgeCount(); i++) {
            if (adjacencyList.get(i).getEndNodeName() == endNodeName) {
                adjacencyList.get(i).setWeight(weight);
                return;
            }
        }
        adjacencyList.add(new Edge(endNodeName, weight));
    }

     public void removeEdge(char endNodeName) {
        for (int i = 0; i < edgeCount(); i++) {
            if (adjacencyList.get(i).getEndNodeName() == endNodeName) {
                adjacencyList.remove(i);
                break;
            }
        }
    }

    public int edgeCount() {
        return adjacencyList.size();
    }

    public char getName() {
        return name;
    }

    public void setAdjacencyList(ArrayList<Edge> adjacencyList){
        this.adjacencyList = adjacencyList;
    }

    public void setLocation(Point location){
        this.location = location;
    }

    public Point getLocation(){
        return location;
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

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
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
        if(path.size() == 0)
            return strBuilder.toString();
        for (int i = 0; i < path.size() - 1; i++)
            strBuilder.append(path.get(i) + "->");
        strBuilder.append(path.get(path.size() - 1));
        return strBuilder.toString();
    }

    public Node clone() {
        Node cloneNode = new Node(name);
        for (int i = 0; i < edgeCount(); i++) {
            cloneNode.adjacencyList.add(adjacencyList.get(i).clone());
        }
        cloneNode.distance = distance;
        cloneNode.path = (ArrayList<Character>) path.clone();
        cloneNode.location = location.getLocation();
        cloneNode.color = color;
        return cloneNode;
    }
}
