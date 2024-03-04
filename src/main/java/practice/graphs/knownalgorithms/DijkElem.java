package practice.graphs.knownalgorithms;

public class DijkElem implements Comparable<DijkElem> {
    private final int vertex;
    private final int weight;

    public DijkElem(int inv, int inw) {
        vertex = inv;
        weight = inw;
    }

    public DijkElem() {
        vertex = 0;
        weight = 0;
    }

    public int key() {
        return weight;
    }

    public int vertex() {
        return vertex;
    }

    public int compareTo(DijkElem that) {
        return Integer.compare(weight, that.key());
    }
}