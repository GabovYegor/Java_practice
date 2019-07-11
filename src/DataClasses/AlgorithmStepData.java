package DataClasses;

// снимок итерации алгоритма дейкстры
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
