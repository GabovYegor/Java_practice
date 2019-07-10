import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("Dijkstra", new Graph());
        mainWindow.setVisible(true);
    }
}

// очищать вес
// если нажимать кнопки next/prev будет плохо