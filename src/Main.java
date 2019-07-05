import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge('a', 'b', 12);
        System.out.println(graph.nodeCount());
        MainWindow mainWindow = new MainWindow("Dijkstra", graph);
        mainWindow.setVisible(true);
        System.out.println(graph.nodeCount());
    }
}
