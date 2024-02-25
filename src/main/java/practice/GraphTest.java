package practice;

import impl.Graphl;

import static practice.knownalgorithms.GraphImpl.Kruskal;

public class GraphTest {
    public static void main(String[] args) {
        // Creating a sample graph
        Graphl graph = new Graphl(6);
        graph.setEdge(0, 1, 7);
        graph.setEdge(0, 2, 9);
        graph.setEdge(1, 3, 5);
        graph.setEdge(1, 4, 2);
        graph.setEdge(1, 5, 1);
        graph.setEdge(3, 4, 6);
        graph.setEdge(5, 4, 2);
        graph.setEdge(4, 2, 1);

        // Testing Kruskal's algorithm
        Kruskal(graph);
    }
}
