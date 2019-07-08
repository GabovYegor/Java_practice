import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addEdge('f', 'v', 5);
        MainWindow mainWindow = new MainWindow("Dijkstra", g);
        mainWindow.setVisible(true);
    }
}
