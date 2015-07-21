package sort;

import java.util.ArrayList;

public class RadixSort {
    public static final int BUCKETS = 256;

    // using additional Arraylist<String>[]
    public static void listSort(String[] arr, int strLen) {
        ArrayList<ArrayList<String>> buckets = new ArrayList<>();

        for (int i = 0; i < BUCKETS; i++) {
            buckets.add(i, new ArrayList<String>());
        }

        for (int pos = strLen - 1; pos >= 0; pos--) {
            for (String s : arr) {
                buckets.get(s.charAt(pos)).add(s);
            }

            int index = 0;
            for (ArrayList<String> eachBucket : buckets) {
                for (String s: eachBucket) {
                    arr[index++] = s;
                }

                eachBucket.clear();
            }
        }
    }

    public static void countingSort(String[] arr, int strLen) {
        String[] out = new String[arr.length];
        // String[] in = arr;

        for (int pos = strLen - 1; pos >= 0; pos--) {
            int[] counts = new int[BUCKETS];
            
            for (String s : arr) {
                counts[s.charAt(pos)]++;
            }
            int sum = 0;
            for (int i = 1; i < BUCKETS; i++) {
                int tmp = sum;
                sum += counts[i - 1];
                counts[i - 1] = tmp;
            }

            for (String s: arr) {
                out[counts[s.charAt(pos)]++] = s;
            }

            for (int i = 0; i < arr.length; i++) {
                arr[i] = out[i];
            }
        }

    }



    public static void main(String[] args) {
        String[] a = {"arrays", "string", "resume", "permit"};
        countingSort(a, 6);
        for (String s : a) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}