package impl;

public class MinHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private int capacity;

    public MinHeap(T[] array, int size, int capacity) {
        this.heap = array;
        this.size = size;
        this.capacity = capacity;
        buildHeap();
    }

    private void buildHeap() {
        for (int i = (size - 1) / 2; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void heapifyUp(int i) {
        while (i > 0 && heap[parent(i)].compareTo(heap[i]) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(int i) {
        int minIndex = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left].compareTo(heap[minIndex]) < 0) {
            minIndex = left;
        }

        if (right < size && heap[right].compareTo(heap[minIndex]) < 0) {
            minIndex = right;
        }

        if (i != minIndex) {
            swap(i, minIndex);
            heapifyDown(minIndex);
        }
    }

    public void insert(T value) {
        if (size == capacity) {
            System.out.println("Heap is full. Cannot insert.");
            return;
        }
        heap[size++] = value;
        heapifyUp(size - 1);
    }

    public T removemin() {
        if (size <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        T min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
