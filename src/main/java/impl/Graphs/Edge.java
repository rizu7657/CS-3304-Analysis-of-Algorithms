package impl.Graphs;

/**
 * Edge class for Adjacency List graph representation
 */
public class Edge {
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