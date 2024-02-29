package practice.knownalgorithms;

import adt.Graph;
import impl.Edge;
import impl.MinHeap;

import java.util.ArrayList;
import java.util.List;

public class GraphImpl {
    private static final int VISITED = 1;
    private static final int UNVISITED = 0;

    /**
     * Dijkstra’s shortest-paths: priority queue version
     */
    static void Dijkstra(Graph G, int s, int[] D) {
        int v;                              // The current vertex
        DijkElem[] E = new DijkElem[G.e()]; // Heap for edges
        E[0] = new DijkElem(s, 0);          // Initial vertex

        MinHeap<DijkElem> H = new MinHeap<DijkElem>(E, 1, G.e());

        for (int i = 0; i < G.n(); i++)         // Initialize distance
            D[i] = Integer.MAX_VALUE;
        D[s] = 0;

        for (int i = 0; i < G.n(); i++) {       // For each vertex
            do {
                v = (H.removemin()).vertex();
            }    // Get position
            while (G.getMark(v) == VISITED);

            G.setMark(v, VISITED);

            if (D[v] == Integer.MAX_VALUE) return;  // Unreachable

            for (int w = G.first(v); w < G.n(); w = G.next(v, w))
                if (D[w] > (D[v] + G.weight(v, w))) { // Update D
                    D[w] = D[v] + G.weight(v, w);
                    H.insert(new DijkElem(w, D[w]));
                }
        }
    }

    /**
     * Compute a minimal-cost spanning tree
     */
    public static void PrimO(Graph G, int s, int[] D, int[] V) {
        List<Edge> mst = new ArrayList<>();

        for (int i = 0; i < G.n(); i++)   // Initialize
            D[i] = Integer.MAX_VALUE;

        D[s] = 0;

        for (int i = 0; i < G.n(); i++) { // Process the vertices
            int v = minVertex(G, D);

            G.setMark(v, VISITED);

            if (v != s) AddEdgetoMST(V[v], v, mst);

            if (D[v] == Integer.MAX_VALUE) return; // Unreachable

            for (int w = G.first(v); w < G.n(); w = G.next(v, w))
                if (D[w] > G.weight(v, w)) {
                    D[w] = G.weight(v, w);
                    V[w] = v;
                }
        }
    }

    /**
     * Prims’s MST algorithm: priority queue version
     */
    public static void Prim(Graph G, int s, int[] D, int[] V) {
        int mst = Integer.MAX_VALUE;

        int v; // The current vertex

        DijkElem[] E = new DijkElem[G.e()]; // Heap for edges

        MinHeap<DijkElem> H = new MinHeap<DijkElem>(E, G.n(), G.e());

        for (int i = 0; i < G.n(); i++) // Initialize
            D[i] = Integer.MAX_VALUE; // distances

        D[s] = 0;

        for (int i = 0; i < G.n(); i++) { // Now, get distances

            do {
                v = (H.removemin()).vertex();
            } while (G.getMark(v) == VISITED);

            G.setMark(v, VISITED);

            if (v != s) {
                mst = mst + G.weight(V[v], v);
            }

            if (D[v] == Integer.MAX_VALUE) return; // Unreachable

            for (int w = G.first(v); w < G.n(); w = G.next(v, w)) {

                if (D[w] > G.weight(v, w)) { // Update D
                    D[w] = G.weight(v, w);
                    V[w] = v; // Where it came from
                    H.insert(new DijkElem(w, D[w]));
                }
            }
        }
    }

    static int minVertex(Graph G, int[] D) {
        int v = 0;  // Initialize v to any unvisited vertex;
        for (int i = 0; i < G.n(); i++)
            if (G.getMark(i) == UNVISITED) {
                v = i;
                break;
            }
        for (int i = 0; i < G.n(); i++)  // Now find smallest value
            if ((G.getMark(i) == UNVISITED) && (D[i] < D[v]))
                v = i;
        return v;
    }

    public static void Kruskal(Graph G) {
        List<Edge> mst = new ArrayList<>();

        ParPtrTree A = new ParPtrTree(G.n()); // Equivalence array

        KruskalElem[] E = new KruskalElem[G.e()]; // Minheap array

        int edgecnt = 0; // Count of edges

        for (int i = 0; i < G.n(); i++)    // Put edges in the array
            for (int w = G.first(i); w < G.n(); w = G.next(i, w))
                E[edgecnt++] = new KruskalElem(G.weight(i, w), i, w);

        MinHeap<KruskalElem> H = new MinHeap<KruskalElem>(E, edgecnt, edgecnt);

        int numMST = G.n();            // Initially n classes

        for (int i = 0; numMST > 1; i++) { // Combine equiv classes
            KruskalElem temp = H.removemin(); // Next cheapest
            int v = temp.v1();
            int u = temp.v2();

            if (A.differ(v, u)) {        // If in different classes
                A.UNION(v, u);             // Combine equiv classes
                AddEdgetoMST(v, u, mst);  // Add this edge to MST
                numMST--;                  // One less MST
            }
        }
    }

    public static void AddEdgetoMST(int v, int w, List<Edge> mst) {
        // Add code here to perform operations needed to add edge to MST
        System.out.println("Adding edge between " + v + " and " + w + " to MST");
        Edge edge = new Edge(v, w);
        mst.add(edge);
    }

    public static class KruskalElem implements Comparable<KruskalElem> {
        private final int v;
        private final int w;
        private final int weight;

        public KruskalElem(int inweight, int inv, int inw) {
            weight = inweight;
            v = inv;
            w = inw;
        }

        public int v1() {
            return v;
        }

        public int v2() {
            return w;
        }

        public int key() {
            return weight;
        }

        public int compareTo(KruskalElem that) {
            return Integer.compare(weight, that.key());
        }
    }

    public static class ParPtrTree {
        private int[] parent;

        public ParPtrTree(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int FIND(int x) {
            if (parent[x] == x)
                return x;
            else
                return parent[x] = FIND(parent[x]);
        }

        public boolean differ(int x, int y) {
            return FIND(x) != FIND(y);
        }

        public void UNION(int x, int y) {
            parent[FIND(y)] = FIND(x);
        }
    }
}