package practice.graphs.knownalgorithms;

import impl.Graphs.Graphl;
import practice.graphs.knownalgorithms.GraphImpl;

import java.util.Arrays;

public class GraphTest {
    public static void main(String[] args) {
        // Creating a sample graph
        Graphl graph = new Graphl(10);
        graph.setEdge(1, 2, 5);
        graph.setEdge(1, 3, 4);
        graph.setEdge(2, 3, 2);
        graph.setEdge(2, 4, 3);
        graph.setEdge(3, 5, 5);
        graph.setEdge(4, 5, 2);
        graph.setEdge(5, 6, 1);
        graph.setEdge(4, 7, 6);
        graph.setEdge(6, 7, 8);
        graph.setEdge(7, 8, 2);

        int[] D = new int[graph.n()];
        int[] V = {1,2,3,4,5,6,7,8};

        // Testing Kruskal's algorithm
        GraphImpl.Prim(graph, 0, D, V);

        System.out.println(Arrays.toString(D));
    }
}
