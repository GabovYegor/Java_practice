package UnitTests;

import DataClasses.Edge;

public class Edge_UnitTesting {
    public static void main(String[] args) {
        Edge edge = new Edge('a', 5);
        assert edge.getEndNodeName() == 'a' && edge.getWeight() == 5 : "Edge() error.";

        edge.setWeight(10);
        assert edge.getWeight() == 10 : "setWeight() error.";

        boolean exceptionTest = false;
        try {
            edge.setWeight(-5);
        } catch (IllegalArgumentException e) {
            exceptionTest = true;
        }
        assert exceptionTest : "setWeight() error.";
        assert edge.getWeight() == 10 : "setWeight() error.";

        Edge edgeClone = edge.clone();
        assert edge != edgeClone && edge.getEndNodeName() == edgeClone.getEndNodeName()
                && edge.getWeight() == edgeClone.getWeight() && edge.getColor() == edgeClone.getColor()
                : "clone() error.";

        System.out.println("Edge_UnitTesting is completed.");
    }
}
