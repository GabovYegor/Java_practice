import DataClasses.Graph;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge('s', 'r', 5);
        System.out.println(graph.nodeCount());
    }
}
