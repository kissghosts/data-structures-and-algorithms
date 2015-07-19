package heap;

public class BinaryHeap<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;
    private T[] array;

    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    public BinaryHeap(int s) {
        currentSize = 0;
        array = (T[]) new Comparable[s];
    }

    public BinaryHeap(T[] items) {
        currentSize = items.length;
        array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];

        for (int i = 1; i <= currentSize; i++) {
            array[i] = items[i];
        }

        // re-allocate items in the array
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void clear() {
        currentSize = 0;
    }

    public void insert(T x) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2);
        }

        int hole = ++currentSize;
        for (array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /=  2) {
            array[hole] = array[hole / 2];
        }
        array[hole] = x;
    }

    public T findMin() {
        return array[1];
    }

    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }

        T minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    private void percolateDown(int hole) {
        T tmp = array[hole];
        int child;
        for ( ; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize
                && array[child].compareTo(array[child + 1]) > 0) {
                child++;
            }
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        array[hole] = tmp;
    }

    private void enlargeArray(int s) {
        T[] oldArray = array;
        array = (T[]) new Comparable[s];
        for (int i = 1; i < oldArray.length; i++) {
            array[i] = oldArray[i];
        }
    }

    // only for debug in the following test
    public String toString() {
        String result = "";
        for (int i = 1; i <= currentSize; i++) {
            if (i == 1) {
                result += array[i].toString();
            } else {
                result += ", " + array[i].toString();
            }
        }
        return result;
    }

    public int size() {
        return currentSize;
    }



    /*********************************
    * Testing
    *********************************/
    public static void main(String[] args) {
        int[] numItems = {13, 68, 14, 21, 19, 16, 19, 65, 26, 32, 31};
        BinaryHeap<Integer> heap = new BinaryHeap<>();

        for ( int item : numItems) {
            heap.insert(item);
            // System.out.println(heap.size());
        }
        System.out.println(heap.toString());

        heap.deleteMin();
        System.out.println(heap.toString());
        heap.deleteMin();
        System.out.println(heap.toString());
    }

}