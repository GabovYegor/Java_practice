import DataClasses.Graph;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge('a', 'b', 10);
        System.out.println(graph.nodeCount());
    }
}
