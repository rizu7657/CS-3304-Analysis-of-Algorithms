package assignment.u4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ImplementingPrimsAlgorithm {

    public static void main(String[] args) {
        // Given a graph, this class should calculate the total cost from the MST created by PRIM's algorithm
        Graphl graph = new Graphl(8);
        graph.setEdge(0, 1, 5); // Adjust from graph.setEdge(1, 2, 5);
        graph.setEdge(0, 2, 4); // Adjust from graph.setEdge(1, 3, 4);
        graph.setEdge(1, 2, 2); // Adjust from graph.setEdge(2, 3, 2);
        graph.setEdge(1, 3, 3); // Adjust from graph.setEdge(2, 4, 3);
        graph.setEdge(2, 4, 5); // Adjust from graph.setEdge(3, 5, 5);
        graph.setEdge(3, 4, 2); // Adjust from graph.setEdge(4, 5, 2);
        graph.setEdge(4, 5, 1); // Adjust from graph.setEdge(5, 6, 1);
        graph.setEdge(3, 6, 6); // Adjust from graph.setEdge(4, 7, 6);
        graph.setEdge(5, 6, 8); // Adjust from graph.setEdge(6, 7, 8);
        graph.setEdge(6, 7, 2); // Adjust from graph.setEdge(7, 8, 2);

        int[] D = new int[graph.n()];
        int[] V = {1,2,3,4,5,6,7,8};

        Prim(graph);
    }

    private static void Prim(Graph g) {
        int n = g.n(); // Number of vertices
        boolean[] inMST = new boolean[n]; // To keep track of vertices included in MST
        int[] parent = new int[n]; // To store constructed MST
        int[] key = new int[n]; // Key values used to pick minimum weight edge in cut

        // Initialize all keys as INFINITE
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1); // Initially set all parents to -1

        // Custom comparator for the PriorityQueue
        Comparator<Integer> vertexComparator = Comparator.comparingInt(v -> key[v]);

        // PriorityQueue to store vertices based on key values
        PriorityQueue<Integer> pq = new PriorityQueue<>(vertexComparator);

        // Start from the first vertex. Let's assume 0 as the starting vertex.
        key[0] = 0;
        pq.add(0);

        while (!pq.isEmpty()) {
            // Extracting vertex with minimum key value
            int u = pq.poll();
            inMST[u] = true; // Include this vertex in MST

            // Iterate through all adjacent vertices of u and update their keys
            for (int v = 0; v < n; v++) {
                if (g.isEdge(u, v) && !inMST[v] && g.weight(u, v) < key[v]) {
                    // Update key value and parent of v
                    key[v] = g.weight(u, v);
                    parent[v] = u;
                    if (pq.contains(v)) {
                        pq.remove(v); // Update the priority queue
                    }
                    pq.add(v);
                }
            }
        }

        // Print the constructed MST
        printMST(parent, n, g);
    }

    // A utility function to find the vertex with minimum key value,
    // from the set of vertices not yet included in MST
    private static int minKey(int[] key, boolean[] inMST, int n) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < n; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // A utility function to print the constructed MST stored in parent[]
    private static void printMST(int[] parent, int n, Graph g) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < n; i++) {
            if (parent[i] != -1) { // Check if parent exists
                System.out.println((parent[i] + 1) + " - " + (i + 1) + "\t" + g.weight(parent[i], i));
            }
        }
    }

    private static class Graphl implements Graph {
        private LinkedList<Edge>[] vertices;
        private int numEdge;
        private int[] Mark;

        public Graphl() {
        }

        public Graphl(int n) {
            Init(n);
        }

        public void Init(int n) {
            Mark = new int[n];
            vertices = new LinkedList[n];

            for (int i = 0; i < n; i++) {
                vertices[i] = new LinkedList<Edge>();
                Mark[i] = 0; // Initialize all marks to 0
            }
            numEdge = 0;
        }

        public int n() {
            return Mark.length;
        }

        public int e() {
            return numEdge;
        }

        /**
         * @return v’s first neighbor
         */
        public int first(int v) {
            if (vertices[v].isEmpty())
                return Mark.length;   // No neighbor
            return vertices[v].getFirst().vertex();
        }

        /**
         * @return v’s next neighbor after w
         */
        public int next(int v, int w) {

            if (isEdge(v, w)) {

                for (int i = 0; i < vertices[v].size(); i++) {
                    Edge edge = vertices[v].get(i);

                    if (edge.vertex() == w && (i + 1) < vertices[v].size()) {
                        return vertices[v].get(i + 1).vertex();
                    }
                }
            }
            return Mark.length; // No neighbor
        }

        /**
         * Set the weight for an edge
         */
        public void setEdge(int i, int j, int weight) {
            assert weight != 0 : "May not set weight to 0";

            LinkedList<Edge> vertex = vertices[i];
            Edge currEdge = new Edge(j, weight);

            Edge edge = vertex.stream().filter(e -> e.vertex() == j).findFirst().orElse(null);

            if (edge != null) {
                vertex.add(vertex.indexOf(edge), currEdge);
                vertex.remove(edge);
            } else {
                vertex.add(currEdge);
                numEdge++;
            }
        }

        /**
         * Delete an edge
         */
        public void delEdge(int i, int j) {
            vertices[i].stream()
                    .filter(e -> e.vertex() == j)
                    .findFirst()
                    .ifPresent(edge -> {
                        vertices[i].remove(edge);
                        numEdge--;
                    });
        }

        /**
         * Determine if an edge is in the graph
         */
        public boolean isEdge(int v, int w) {
            if (vertices[v].isEmpty()) {
                return false;
            }

            // Check if j is the current neighbor in the list
            Edge it = vertices[v].getFirst();
            if (it != null && it.vertex() == w) {
                return true;
            }

            // Check the whole list
            for (Edge edge : vertices[v]) {
                if (edge.vertex() == w) {
                    return true;
                }
            }
            return false;
        }

        /**
         * @return an edge’s weight
         */
        public int weight(int i, int j) {
            Edge edge = vertices[i].stream()
                    .filter(e -> e.vertex() == j)
                    .findFirst()
                    .orElse(null);

            if (edge != null) {
                return edge.weight();
            }
            return 0;
        }

        /**
         * Set/Get the mark value for a vertex
         */
        public void setMark(int v, int val) {
            Mark[v] = val;
        }

        public int getMark(int v) {
            return Mark[v];
        }
    }

    private static class Edge {
        private int vert, wt;

        public Edge(int v, int w) {
            vert=v;
            wt=w;
        }

        public int vertex() {
            return vert;
        }

        public int weight() {
            return wt;
        }
    }

    private interface Graph {         // Graph class ADT
        /**
         * Initialize the graph
         *
         * @param n The number of vertices
         */
        public void Init(int n);

        /**
         * @return The number of vertices
         */
        public int n();

        /**
         * @return The current number of edges
         */
        public int e();

        /**
         * @return v’s first neighbor
         */
        public int first(int v);

        /**
         * @return v’s next neighbor after w
         */
        public int next(int v, int w);

        /**
         * Set the weight for an edge
         *
         * @param i,j  The vertices
         * @param wght Edge weight
         */
        public void setEdge(int i, int j, int wght);

        /**
         * Delete an edge
         *
         * @param i,j The vertices
         */
        public void delEdge(int i, int j);

        /**
         * Determine if an edge is in the graph
         *
         * @param i,j The vertices
         * @return true if edge i,j has non-zero weight
         */
        public boolean isEdge(int i, int j);

        /**
         * @param i,j The vertices
         * @return The weight of edge i,j, or zero
         */
        public int weight(int i, int j);

        /**
         * Set the mark value for a vertex
         *
         * @param v   The vertex
         * @param val The value to set
         */
        public void setMark(int v, int val);

        /**
         * Get the mark value for a vertex
         *
         * @param v The vertex
         * @return The value of the mark
         */
        public int getMark(int v);
    }
}

