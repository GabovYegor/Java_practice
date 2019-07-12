package Algorithm;

import DataClasses.AlgorithmStepData;
import DataClasses.Graph;

import java.awt.*;
import java.util.ArrayList;

// класс для алгоритма Дейкстры
public class Dijkstra {
    // метод для алгоритма Дейктры
    public static ArrayList<AlgorithmStepData> dijkstra(Graph graph, char startNodeName) {
        Graph aGraph = graph.clone();

        if (aGraph.getIndexByName(startNodeName) == -1)
            throw new IllegalArgumentException("Алгоритм не содержит вершины с именем '" + startNodeName + "'.");

        ArrayList<AlgorithmStepData> result = new ArrayList<>();
        ArrayList<Integer> queue = new ArrayList<>();
        StringBuilder strBuilder;

        for (int i = 0; i < aGraph.nodeCount(); i++) {
            aGraph.getNodeByIndex(i).setDistance(Integer.MAX_VALUE);
            aGraph.getNodeByIndex(i).setColor(Color.gray);
            aGraph.getNodeByIndex(i).clearPath();
        }
        aGraph.getNodeByName(startNodeName).setDistance(0);
        aGraph.getNodeByName(startNodeName).setColor(Color.orange);
        aGraph.getNodeByName(startNodeName).addInPath(startNodeName);
        queue.add(aGraph.getIndexByName(startNodeName));
        strBuilder = new StringBuilder("Шаг 0:\nИнициализация.");
        result.add(new AlgorithmStepData(aGraph.clone(), strBuilder.toString()));

        int m = 1;
        while (queue.size() > 0) {
            int currentNodeIndex = queue.get(0);
            int indexInQueue = 0;
            for (int i = 1; i < queue.size(); i++) {
                if (aGraph.getNodeByIndex(queue.get(i)).getDistance() < aGraph.getNodeByIndex(currentNodeIndex).getDistance()) {
                    currentNodeIndex = queue.get(i);
                    indexInQueue = i;
                }
            }
            queue.remove(indexInQueue);

            aGraph.getNodeByIndex(currentNodeIndex).setColor(Color.red);
            strBuilder = new StringBuilder("Шаг " + m + ":\nРаскрытие вершины '" + aGraph.getNodeByIndex(currentNodeIndex).getName() + "'.");
            if (aGraph.getNodeByIndex(currentNodeIndex).edgeCount() == 0) {
                strBuilder.append("\nУ вершины нет исходящих рёбер.");
            }
            result.add(new AlgorithmStepData(aGraph.clone(), strBuilder.toString()));

            for (int i = 0; i < aGraph.getNodeByIndex(currentNodeIndex).edgeCount(); i++) {
                int currentEndOfEdgeIndex = aGraph.getIndexByName(aGraph.getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getEndNodeName());
                aGraph.getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.green);
                if ( aGraph.getNodeByIndex(currentEndOfEdgeIndex).getDistance() == Integer.MAX_VALUE ) {
                    aGraph.getNodeByIndex(currentEndOfEdgeIndex).setDistance(aGraph.getNodeByIndex(currentNodeIndex).getDistance() + aGraph.getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight());
                    aGraph.getNodeByIndex(currentEndOfEdgeIndex).setPath(aGraph.getNodeByIndex(currentNodeIndex).getPath());
                    aGraph.getNodeByIndex(currentEndOfEdgeIndex).addInPath(aGraph.getNodeByIndex(currentEndOfEdgeIndex).getName());
                    strBuilder = new StringBuilder("Вершина '" + aGraph.getNodeByIndex(currentEndOfEdgeIndex).getName() + "' ещё не посещалась.\n");
                    strBuilder.append("Назначен новый путь: " + aGraph.getNodeByIndex(currentEndOfEdgeIndex).pathToString() + " (расстояние — " + aGraph.getNodeByIndex(currentEndOfEdgeIndex).getDistance() + ").");
                    result.add(new AlgorithmStepData(aGraph.clone(), strBuilder.toString()));
                    queue.add(currentEndOfEdgeIndex);
                    aGraph.getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.orange);
                } else {
                    boolean isInQueue = false;
                    for (int j = 0; j < queue.size(); j++) {
                        if (currentEndOfEdgeIndex == queue.get(j)) {
                            isInQueue = true;
                            break;
                        }
                    }
                    if (isInQueue) {
                        strBuilder = new StringBuilder("Вершина '" + aGraph.getNodeByIndex(currentEndOfEdgeIndex).getName() + "' уже посещалась.\n");
                        strBuilder.append("Текущий путь до вершины: " + aGraph.getNodeByIndex(currentEndOfEdgeIndex).pathToString() + " (расстояние — " + aGraph.getNodeByIndex(currentEndOfEdgeIndex).getDistance() + ").\n");
                        if (aGraph.getNodeByIndex(currentEndOfEdgeIndex).getDistance() > aGraph.getNodeByIndex(currentNodeIndex).getDistance() + aGraph.getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight()) {
                            aGraph.getNodeByIndex(currentEndOfEdgeIndex).setDistance(aGraph.getNodeByIndex(currentNodeIndex).getDistance() + aGraph.getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight());
                            aGraph.getNodeByIndex(currentEndOfEdgeIndex).setPath(aGraph.getNodeByIndex(currentNodeIndex).getPath());
                            aGraph.getNodeByIndex(currentEndOfEdgeIndex).addInPath(aGraph.getNodeByIndex(currentEndOfEdgeIndex).getName());
                            strBuilder.append("Найден более короткий путь: " + aGraph.getNodeByIndex(currentEndOfEdgeIndex).pathToString() + " (расстояние — " + aGraph.getNodeByIndex(currentEndOfEdgeIndex).getDistance() + ").");
                        } else {
                            strBuilder.append("Путь из вершины '" + aGraph.getNodeByIndex(currentNodeIndex).getName() + "' (расстояние — " + (aGraph.getNodeByIndex(currentNodeIndex).getDistance() + aGraph.getNodeByIndex(currentNodeIndex).getEdgeByIndex(i).getWeight()) + ") не является более коротким.");
                        }
                        result.add(new AlgorithmStepData(aGraph.clone(), strBuilder.toString()));
                        aGraph.getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.orange);
                    } else {
                        strBuilder = new StringBuilder("До вершины '" + aGraph.getNodeByIndex(currentEndOfEdgeIndex).getName() + "' уже был найден кратчайший путь.");
                        result.add(new AlgorithmStepData(aGraph.clone(), strBuilder.toString()));
                        aGraph.getNodeByIndex(currentEndOfEdgeIndex).setColor(Color.black);
                    }
                }
            }

            aGraph.getNodeByIndex(currentNodeIndex).setColor(Color.black);
            m++;
        }

        for (int i = 0; i < aGraph.nodeCount(); i++) {
            for (int j = 0; j < aGraph.getNodeByIndex(i).edgeCount(); j++) {
                char c = aGraph.getNodeByIndex(i).getEdgeByIndex(j).getEndNodeName();
                if (aGraph.getNodeByName(c).getPath().size() < 2 ||
                        aGraph.getNodeByIndex(i).getName() != aGraph.getNodeByName(c).getPath().get(aGraph.getNodeByName(c).getPath().size() - 2)){
                    aGraph.getNodeByIndex(i).removeEdge(c);
                    j--;
                }
            }
        }

        strBuilder = new StringBuilder("Результат:\n");
        strBuilder.append("Путь от вершины '" + startNodeName + "' до вершины\n");
        for (int i = 0; i < aGraph.nodeCount(); i++) {
            strBuilder.append("'" + aGraph.getNodeByIndex(i).getName() + "': ");
            if (aGraph.getNodeByIndex(i).getDistance() == Integer.MAX_VALUE) {
                strBuilder.append("вершина недостежима.\n");
            } else {
                strBuilder.append(aGraph.getNodeByIndex(i).pathToString() + " (расстояние — " + aGraph.getNodeByIndex(i).getDistance() + ").\n");
            }
        }
        result.add(new AlgorithmStepData(aGraph.clone(), strBuilder.toString()));

        return result;
    }
}
