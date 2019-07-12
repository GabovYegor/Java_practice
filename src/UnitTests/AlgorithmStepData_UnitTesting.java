package UnitTests;

import DataClasses.AlgorithmStepData;
import DataClasses.Graph;

public class AlgorithmStepData_UnitTesting {
    public static void main(String[] args) {
        Graph graph = new Graph();
        String str = new String();
        AlgorithmStepData var = new AlgorithmStepData(graph, str);

        assert graph == var.getGraph() && str == var.getStr() : "AlgorithmStepData() error";

        System.out.println("AlgorithmStepData_UnitTesting is completed.");
    }
}
