package UnitTests;

import DataClasses.Graph;

public class Graph_UnitTesting {
    public static void main(String[] args) {
        Graph graph = new Graph();
        assert graph.nodeCount() == 0 : "Graph() error.";

        graph.addNode('a');
        graph.addNode('b');
        graph.addNode('c');
        assert graph.nodeCount() == 3 && graph.getNodeByIndex(0).getName() == 'a'
                && graph.getNodeByIndex(2).getName() == 'c' && graph.getNodeByIndex(1).edgeCount() == 0
                : "addNode() error";

        graph.addEdge('a','b', 4);
        graph.addEdge('c', 'd',10);
        assert graph.nodeCount() == 4 && graph.getNodeByIndex(0).edgeCount() == 1
                && graph.getNodeByIndex(0).getEdgeByIndex(0).getEndNodeName() == 'b'
                && graph.getNodeByIndex(0).getEdgeByIndex(0).getWeight() == 4
                : "addEdge() error";
        graph.addEdge('e','f',15);
        assert graph.nodeCount() == 6 : "addEdge() error";

        boolean exceptionTest = false;
        try {
            graph.addEdge('a','a', 5);
        } catch (IllegalArgumentException e) {
            exceptionTest = true;
        }
        assert exceptionTest && graph.getNodeByIndex(0).edgeCount() == 1 : "addEdge() error";

        exceptionTest = false;
        try {
            graph.addEdge('a','y', -10);
        } catch (IllegalArgumentException e) {
            exceptionTest = true;
        }
        assert exceptionTest && graph.nodeCount() == 6 : "addEdge() error";

        assert graph.getIndexByName('b') == 1 && graph.getIndexByName('p') == -1
                : "getIndexByName() error";

        exceptionTest = false;
        try {
            graph.getNodeByIndex(-100);
        } catch (IndexOutOfBoundsException e) {
            exceptionTest = true;
        }
        assert exceptionTest : "getNodeByIndex() error.";
        exceptionTest = false;
        try {
            graph.getNodeByIndex(99);
        } catch (IndexOutOfBoundsException e) {
            exceptionTest = true;
        }
        assert exceptionTest : "getNodeByIndex() error.";

        graph.addEdge('n','a', 5);
        graph.addEdge('m','a', 8);
        graph.removeNode('a');
        assert graph.getNodeByName('n').edgeCount() == 0 && graph.getNodeByName('m').edgeCount() == 0
                && graph.nodeCount() == 7 && graph.getIndexByName('a') == -1
                : "removeNode() error.";

        Graph graphClone = graph.clone();
        assert graph != graphClone && graph.nodeCount() == graphClone.nodeCount()
                && graph.getNodeByIndex(0).getName() == graphClone.getNodeByIndex(0).getName()
                && graph.getNodeByIndex(0) != graphClone.getNodeByIndex(0)
                : "clone() error";

        System.out.println("Graph_UnitTesting is completed.");
    }
}
