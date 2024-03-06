package practice.dynamic;

import adt.Graph;
import impl.Graphs.Graphl;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class FloydsAlgorithm {
    public static void main(String[] args) {
        Graphl graph = new Graphl(4);
        graph.setEdge(0, 1, 1);
        graph.setEdge(0, 2, Integer.MAX_VALUE);
        graph.setEdge(0, 3, 12);
        graph.setEdge(1, 0, 4);
        graph.setEdge(1, 2, Integer.MAX_VALUE);
        graph.setEdge(1, 3, 7);
        graph.setEdge(2, 0, 2);
        graph.setEdge(2, 1, 5);
        graph.setEdge(2, 3, 11);
        graph.setEdge(3, 0, Integer.MAX_VALUE);
        graph.setEdge(3, 1, 3);
        graph.setEdge(3, 2, Integer.MAX_VALUE);

        int[][] D = new int[graph.n()][graph.n()];

        StopWatch watch = new StopWatch();
        watch.start();
        Floyd(graph, D); // Calculate all pairs shortest path
        watch.stop();

        long time = watch.getTime(TimeUnit.MICROSECONDS);
        System.out.printf("\nFloyd's algorithm took %s microseconds (%s milliseconds).\n", time, time/1000d);

        for (int[] d : D) {
            System.out.println(Arrays.toString(d));
        }
    }

    public static void Floyd(Graph graph, int[][] D) {
        for (int i = 0; i < graph.n(); i++) {

            for (int j = 0; j < graph.n(); j++) {

                if (graph.weight(i, j) != 0) {
                    D[i][j] = graph.weight(i, j);
                }
            }
        }

        for (int[] d : D) {
            System.out.println(Arrays.toString(d));
        }

        for (int k = 0; k < graph.n(); k++) {

            for (int i = 0; i < graph.n(); i++) {

                for (int j = 0; j < graph.n(); j++) {

                    if ((D[i][k] != Integer.MAX_VALUE)
                            && (D[k][j] != Integer.MAX_VALUE)
                            && (D[i][j] > (D[i][k] + D[k][j]))) {
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }
    }
}
