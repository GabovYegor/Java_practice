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

// Один из алгоритмов визуализации
// Перерисовать граф 100 раз. Запомнить, где самая лучшая планарнасть - это и нарисовать
// Проблема: определение пересечения ребер - векторная алгебра?

/* TODO
    * FromFile
    * парсер файла

    * переделать покрасивее веса

    * рисование графа предыдущий вариант - следующий (если не получится сделать нормальный алгоритм)

    * Все константы в одном месте - можно в интерфейсе

    * Выводить данные для алгоритма

    * разные JPanel для ввода данных и для алгоритма
    * один наследовать от другого
 */