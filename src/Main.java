import DataClasses.Graph;
import Visualiazation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("Dijkstra", new Graph());
        mainWindow.setVisible(true);
    }
}
