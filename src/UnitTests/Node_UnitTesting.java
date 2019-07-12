package UnitTests;

import DataClasses.Node;

import java.awt.*;

public class Node_UnitTesting {
    public static void main(String[] args) {
        Node node = new Node('a');
        assert node.getName() == 'a' && node.edgeCount() == 0 && node.getDistance() == Integer.MAX_VALUE
                && node.getPath().size() == 0 && node.getColor() == Color.black
                : "Node() error.";

        node.addEdge('b', 10);
        assert node.edgeCount() == 1 && node.getEdgeByIndex(0).getEndNodeName() == 'b'
                && node.getEdgeByIndex(0).getWeight() == 10
                : "addEdge() error.";

        node.addEdge('b',5);
        assert node.edgeCount() == 1 && node.getEdgeByIndex(0).getWeight() == 5
                : "addEdge() error.";

        boolean exceptionTest = false;
        try {
            node.addEdge('c', -10);
        } catch (IllegalArgumentException e) {
            exceptionTest = true;
        }
        assert exceptionTest && node.edgeCount() == 1 : "addEdge() error.";

        node.addEdge('c',3);
        node.removeEdge('d');
        assert node.edgeCount() == 2 : "removeEdge() error.";
        node.removeEdge('c');
        assert node.edgeCount() == 1 : "removeEdge() error.";

        node.addEdge('c', 3);
        assert node.getEdgeByIndex(1).getEndNodeName() == 'c' : "getEdgeByIndex() error.";
        exceptionTest = false;
        try {
            node.getEdgeByIndex(2);
        } catch (IndexOutOfBoundsException e) {
            exceptionTest = true;
        }
        assert exceptionTest : "getEdgeByIndex() error.";

        node.setDistance(23);
        assert node.getDistance() == 23 : "setDistance() error.";

        node.addInPath('i');
        node.addInPath('j');
        node.addInPath('k');
        assert node.getPath().size() == 3 : "addInPath() error.";
        assert "i->j->k".equals(node.pathToString()) : "pathToString() error.";

        Node nodeClone = node.clone();
        assert node != nodeClone && node.getName() == nodeClone.getName() && node.getDistance() == nodeClone.getDistance()
                && node.getColor() == nodeClone.getColor() && node.edgeCount() == nodeClone.edgeCount()
                && node.getAdjacencyList() != nodeClone.getAdjacencyList() && node.getEdgeByIndex(0).getEndNodeName() == nodeClone.getEdgeByIndex(0).getEndNodeName()
                && node.getPath() != nodeClone.getPath() && node.pathToString().equals(nodeClone.pathToString())
                : "clone() error.";

        System.out.println("Node_UnitTesting is completed.");
    }
}
