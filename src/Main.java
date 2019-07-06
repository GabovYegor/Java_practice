import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        MainWindow mainWindow = new MainWindow("Dijkstra", graph);
        mainWindow.setVisible(true);
    }
}

/* TODO
    * FromFile
    * Подсчет положение вершины
    *  с помощью векторной алгебры сделать:
    * 1)  ребра идут не из центра, а от края вершины
    * 2)  Вес ребра на середине ребра
    * Данные для визуализации - возможно все таки добавить цвет
    * Система сборки
    * Пересмотреть класс графа
 */