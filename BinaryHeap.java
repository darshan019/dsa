public class BinaryHeap {
    int capacity;
    int size;

    int arr[];

    BinaryHeap(int cap) {
        this.capacity = cap;
        this.size = 0;
        this.arr = new int[capacity];
    }

    int parent(int i) {
        return (i - 1) / 2;
    }

    int left(int i) {
        return 2 * i + 1;
    }

    int right(int i) {
        return 2 * i + 2;
    }

    void insert(int x) {
        if (this.capacity == this.size) {
            System.out.println("Overflow");
            return;
        }

        arr[size] = x;
        int k = size;

        size++;

        while (k != 0 && arr[parent(k)] > arr[k]) {
            swap(parent(k), k);
            k = parent(k);
        }
    }

    void swap(int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    int extractMin() {
        if (size <= 0)
            return Integer.MAX_VALUE;

        if (size == 1) {
            size--;
            return arr[0];
        }

        int min = arr[0];
        arr[0] = arr[this.size - 1];
        this.size--;

        heapify(0);

        return min;
    }

    void decreaseKey(int i, int value) {
        arr[i] = value;
        while (i != 0 && arr[parent(i)] > arr[i]) {
            swap(parent(i), i);
            i = parent(i);
        }
    }

    void heapify(int index) {
        int left = left(index);
        int right = right(index);
        int small = index;

        if (left < this.size && arr[left] < arr[small])
            small = left;
        if (right < size && arr[right] < arr[small])
            small = right;

        if (small != index) {
            swap(index, small);
            heapify(small);
        }

    }

    void delete(int index) {
        decreaseKey(index, Integer.MIN_VALUE);
        extractMin();
    }

    void print() {
        for (int i = 0; i < this.arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    static void KthLargest(String[] args) {
        // calc kth largest elem
        BinaryHeap minHeap = new BinaryHeap(2);

        int[] arr = { 1, 2, 3, 4, 5 };

        for (int i = 0; i < arr.length; i++) {
            if (minHeap.size < 2)
                minHeap.insert(arr[i]);
            else if (arr[i] > minHeap.arr[0]) {
                minHeap.delete(0);
                minHeap.insert(arr[i]);
            }

        }

        minHeap.print();
    }
}