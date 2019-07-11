import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {

        /** Создание класса адаптера для MainWindow
         *  Так как само gui не может работать с Graph необходимо создать адаптер
         */

        AdapterMainWindow adapterMainWindow = new AdapterMainWindow("Graph", new Graph());
        adapterMainWindow.setVisible(true);
    }
}
