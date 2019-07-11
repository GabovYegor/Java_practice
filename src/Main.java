import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        AdapterMainWindow adapterMainWindow = new AdapterMainWindow("Graph", new Graph());
        adapterMainWindow.setVisible(true);
    }
}
