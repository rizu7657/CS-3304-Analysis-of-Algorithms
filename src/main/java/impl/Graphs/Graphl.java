package impl.Graphs;

import adt.Graph;

import java.util.LinkedList;

/**
 * Adjacency list graph implementation
 */
public class Graphl implements Graph {
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