package sort;

/*************************************************
* to select the kth smallest value from one array
**************************************************/
public class QuickSelect { 
    public static <T extends Comparable<? super T>>
    T quickSelect(T[] a, int k) {
        quickSelect(a, 0, a.length - 1, k);
        return a[k - 1];
    }

    private static <T extends Comparable<? super T>>
    void quickSelect(T[] a, int left, int right, int k) {
        T pivot = a[right];

        int i = left - 1, j = left;
        for ( ; j < right; j++) {
            if (a[j].compareTo(pivot) < 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);

        if (k - 1 < i + 1) {
            quickSelect(a, left, i, k);
        } else if (k - 1 > i + 1) {
            quickSelect(a, i + 2, right, k);
        }
    }

    private static <T extends Comparable<? super T>>
    void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }



    public static void main(String[] args) {
        Integer[] a = {34, 8, 64, 51, 34, 32, 21};
        System.out.println(quickSelect(a, 4));
    }
}