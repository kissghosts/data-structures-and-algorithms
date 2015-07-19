package sort;

public class Sort {
    public static <T extends Comparable<? super T>> void insertionSort(T[] a) {
        for (int i = 1; i < a.length; i++) {
            T tmp = a[i];
            int j = i - 1;
            for ( ; j >= 0 && tmp.compareTo(a[j]) < 0; j--) {
                a[j + 1] = a[j];
            }
            a[j + 1] = tmp;
        }
    }

    public static <T extends Comparable<? super T>> void heapSort(T[] a) {
        // build the max-heap
        for (int i = a.length / 2 - 1; i > 0; i--) {
            percolateDown(a, i, a.length);
        }

        // heap sort
        for (int i = a.length - 1; i > 0; i--) {
            T tmp = a[i]; // swap a[i] and a[0]
            a[i] = a[0];
            a[0] = tmp;
            percolateDown(a, 0, i);
        }

    }

    /*
    * @param a the array of items
    * @param i the start position
    * @param n the current heap size of array a
    */
    private static <T extends Comparable<? super T>> 
    void percolateDown(T[] a, int i, int n) {
        T tmp = a[i];
        int child;
        for (; i * 2 + 1 < n; i = child) {
            child = i * 2 + 1; // left child
            if (child + 1 < n && a[child].compareTo(a[child + 1]) < 0) {
                child++;
            }
            if(a[i].compareTo(a[child]) < 0) {
                a[i] = a[child];
            } else {
                break;
            }
        }
        a[i] = tmp;
    }

    public static <T extends Comparable<? super T>> 
    void mergeSort(T[] a) {
        T[] tmpArray = (T[]) new Comparable[a.length];
        mergeSort(a, tmpArray, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> 
    void mergeSort(T[] a, T[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tmpArray, left, center);
            mergeSort(a, tmpArray, center + 1, right);
            merge(a, tmpArray, left, center, right);
        }

    }

    private static <T extends Comparable<? super T>> 
    void merge(T[] a, T[] tmpArray, int left, int leftEnd, int rightEnd) {
        int i = left;
        int j = leftEnd + 1;
        int k = left;

        for (; i <= leftEnd && j <= rightEnd; k++) {
            if (a[i].compareTo(a[j]) <= 0) {
                tmpArray[k] = a[i++];
            } else {
                tmpArray[k] = a[j++];
            }
        }

        while (i <= leftEnd) {
            tmpArray[k++] = a[i++];
        }
        while (j <= rightEnd) {
            tmpArray[k++] = a[j++];
        }

        for (i = left; i <= rightEnd; i++) {
            a[i] = tmpArray[i];
        }

    }




    /*****************************************
    *               Testing
    ******************************************/
    public static void main(String[] args) {
        Integer[] a = {34, 8, 64, 51, 32, 21};
        mergeSort(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }
}