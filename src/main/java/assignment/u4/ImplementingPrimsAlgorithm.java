package assignment.u4;

import java.util.LinkedList;

public class ImplementingPrimsAlgorithm {

    public void main(String[] args) {
        Graphl graph = new Graphl(6);
        graph.setEdge(0, 1, 7);
        graph.setEdge(0, 2, 9);
        graph.setEdge(1, 3, 5);
        graph.setEdge(1, 4, 2);
        graph.setEdge(1, 5, 1);
        graph.setEdge(3, 4, 6);
        graph.setEdge(5, 4, 2);
        graph.setEdge(4, 2, 1);

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
            Edge it = vertices[v].getFirst();

            // Check if j is the current neighbor in the list
            if ((it != null) && (it.vertex() == w)) {
                return true;
            }

            // Check whole list
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

    private class Edge {
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

