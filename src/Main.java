import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addNode('d');
        graph.addEdge('e', 'g',12);
        MainWindow mainWindow = new MainWindow("Dijkstra", graph);
        mainWindow.setVisible(true);
    }
}

// Один из алгоритмов визуализации - плохой
// Перерисовать граф 100 раз. Запомнить, где самая лучшая планарнасть - это и нарисовать
// Проблема: определение пересечения ребер - векторная алгебра?
// рисование графа предыдущий вариант - следующий (если не получится сделать нормальный алгоритм)

/* TODO
    * переделать покрасивее веса

    * Все константы в одном месте - можно в интерфейсе

    * Перетаскивать вершины мышкой?
 */