package UnitTests;

import Algorithm.Dijkstra;
import DataClasses.AlgorithmStepData;
import DataClasses.Graph;

import java.util.ArrayList;

public class Dijkstra_UnitTest {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge('a','c',6);
        graph.addEdge('a','d',8);
        graph.addEdge('a','e',10);
        graph.addEdge('c','f',13);
        graph.addEdge('d','c',10);
        graph.addEdge('d','f',11);
        graph.addEdge('b','f',6);
        graph.addEdge('e','b',8);
        graph.addEdge('d','e',2);
        graph.addNode('g');

        boolean exceptionTest = false;
        try {
            Dijkstra.dijkstra(graph, 'u');
        } catch (IllegalArgumentException e) {
            exceptionTest = true;
        }
        assert exceptionTest;

        ArrayList<AlgorithmStepData> result = Dijkstra.dijkstra(graph, 'a');

        assert result.size() == 18;
        assert result.get(result.size() - 1).getStr().equals("Результат:\n" +
                "Путь от вершины 'a' до вершины\n" +
                "'a': a (расстояние — 0).\n" +
                "'c': a->c (расстояние — 6).\n" +
                "'d': a->d (расстояние — 8).\n" +
                "'e': a->e (расстояние — 10).\n" +
                "'f': a->c->f (расстояние — 19).\n" +
                "'b': a->e->b (расстояние — 18).\n" +
                "'g': вершина недостежима.\n");
        Graph graph1 = result.get(result.size() - 1).getGraph();
        assert graph1.nodeCount() == 7;
        assert graph1.getNodeByName('b').edgeCount() == 0 && graph1.getNodeByName('b').getDistance() == 18
                && graph1.getNodeByName('b').pathToString().equals("a->e->b");
        assert graph1.getNodeByName('a').edgeCount() == 3 && graph1.getNodeByName('a').getDistance() == 0
                && graph1.getNodeByName('a').pathToString().equals("a");
        assert graph1.getNodeByName('g').edgeCount() == 0 && graph1.getNodeByName('g').getDistance() == Integer.MAX_VALUE
                && graph1.getNodeByName('g').pathToString().equals("");
        assert graph1.getNodeByName('d').edgeCount() == 0 && graph1.getNodeByName('d').getDistance() == 8
                && graph1.getNodeByName('d').pathToString().equals("a->d");

        System.out.println("Dijkstra_UnitTesting is completed.");
    }
}
