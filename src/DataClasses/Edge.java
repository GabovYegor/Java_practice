package DataClasses;

// Ребро графа для списка смежности adjacencyList.
public class Edge {
    private char endNodeName; // имя вершины, в которую ведёт ребро
    private int weight; // вес ребра
    private Color color; 

    // Конструктор.
    public Edge(char endNodeName, int weight) {
        this.endNodeName = endNodeName;
        this.weight = weight;
    }

    public char getEndNodeName(){
        return endNodeName;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int newValue){
        weight = newValue;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
