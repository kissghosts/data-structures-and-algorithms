package rmq;

import java.util.Scanner;

public class SparseTable {
    private int[] data;
    private int[][] index;

    public SparseTable(int[] d) {
        data = d;
        int n = data.length;
        index = new int[n][n];

        for (int i = 0; i < n; i++) {
            index[i][0] = i;
        }

        for (int j = 1; 1 << j <= n; j++) {
            for (int i = 0; i + (1 << j) - 1 < n; i++) {
                if (data[index[i][j - 1]] < data[index[i + (1 << (j - 1))][j - 1]]) {
                    index[i][j] = index[i][j - 1];
                } else {
                    index[i][j] = index[i + (1 << (j - 1))][j - 1];
                }
            }
        }
    }

    public int query(int i, int j) {
        if (i < 0 || j > data.length || i > j) {
            throw new IndexOutOfBoundsException("Illegal input range!");
        }

        int k = (int) (Math.log(j - i + 1) / Math.log(2));
        if (data[index[i][k]] <= data[index[j - (1 << k) + 1][k]]) {
            return index[i][k];
        } else {
            return index[j - (1 << k) + 1][k];
        }
    }

    /******************************
    * Testing
    *******************************/
    public static void main(String[] args) {
 
        int[] a = {8, 7, 3, 9, 5, 1, 10};
        SparseTable stable = new SparseTable(a);
        Scanner sc = new Scanner(System.in);
        while (true) {
            int i = sc.nextInt();
            int j = sc.nextInt();
            System.out.println(stable.query(i, j));
        }
    }
}