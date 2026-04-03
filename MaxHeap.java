public class MaxHeap {
    int capacity, size;
    int[] arr;

    public MaxHeap(int size, int cap) {
        this.capacity = cap;
        this.size = size;
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

    void insert(int value) {
        if (this.capacity == this.size) {
            System.out.println("Overflow");
            return;
        }

        arr[this.size] = value;
        int k = this.size;
        this.size++;

        while (k != 0 && arr[parent(k)] < arr[k]) {
            int t = arr[parent(k)];
            arr[parent(k)] = arr[k];
            arr[k] = t;
            k = parent(k);
        }
    }

    void heapify(int index) {
        int left = left(index);
        int right = right(index);
        int big = index;

        if (left < this.size && arr[left] > arr[big])
            big = left;
        if (right < this.size && arr[right] > arr[big])
            big = right;

        if (big != index) {
            int tmp = arr[index];
            arr[index] = arr[big];
            arr[big] = tmp;
            heapify(big);
        }
    }
}
