package rmq;

import java.util.Scanner;
import java.util.Arrays;

public class SegmentTree {
    private int[] indexArray; 
    private int[] data;

    public SegmentTree(int[] d) {
        int n = d.length;
        data = d;
        indexArray = new int[(int)Math.pow(2, Math.log(n) / Math.log(2) + 2)];
        buildSegmentTree(1, 0, n - 1); // like a heap, count from index 1
    }

    private void buildSegmentTree(int index, int start, int end) {
        if (start == end) {
            indexArray[index] = start;
        } else {
            // recursively compute index values for the left and right subtrees
            buildSegmentTree(2 * index, start, (start + end) / 2);
            buildSegmentTree(2 * index + 1, (start + end) / 2 + 1, end);
            
            // search for minimum value
            if (data[indexArray[2 * index]] < data[indexArray[2 * index + 1]]) {
                indexArray[index] = indexArray[2 * index];
            } else {
                indexArray[index] = indexArray[2 * index + 1];
            }
        }
    }

    public int getRangeMin(int start, int end) {
        if (start < 0 || end >= data.length || start > end) {
            throw new IndexOutOfBoundsException("illegal range query: " + start + " " + end);
        }

        return data[getRangeMinIndex(1, 0, data.length - 1, start, end)];
    }

    private int getRangeMinIndex(int index, int left, int right, int start, int end) {
        if (start > right || end < left) {
            return Integer.MIN_VALUE;
        }

        // current interval is in between [start:end]
        if (left >= start && right <= end) {
            return indexArray[index];
        }

        // search minimum position
        int p1 = getRangeMinIndex(2 * index, left, (left + right) / 2, start, end);
        int p2 = getRangeMinIndex(2 * index + 1, (left + right) / 2 + 1, right, start, end);

        if (p1 == Integer.MIN_VALUE) {
            return p2;
        } else if (p2 == Integer.MIN_VALUE) {
            return p1;
        } else if (data[p1] <= data[p2]) {
            return p1;
        } else {
            return p2;
        }
    }
   


    /******************************
    * Testing
    *******************************/
    public static void main(String[] args) {
 
        int[] a = {8, 7, 3, 9, 5, 1, 10};
        SegmentTree stree = new SegmentTree(a);
        Scanner sc = new Scanner(System.in);
        while (true) {
            int i = sc.nextInt();
            int j = sc.nextInt();
            System.out.println(stree.getRangeMin(i, j));
        }
    }


}