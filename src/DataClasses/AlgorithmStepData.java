package DataClasses;

public class AlgorithmStepData {
    private Graph graph;
    private String str;

    public AlgorithmStepData(Graph graph, String str) {
        this.graph = graph;
        this.str = str;
    }

    public Graph getGraph() {
        return graph;
    }

    public String getStr() {
        return str;
    }

}
