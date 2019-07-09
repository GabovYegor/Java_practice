import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("Dijkstra", new Graph());
        mainWindow.setVisible(true);
    }
}

// границы спауна вершин