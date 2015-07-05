package com.daa.tree;

public class TreeSetTest {
    public static void main (String[] args) {
        TreeSet<Integer> t = new TreeSet<>();

        System.out.println("currnt size: " + t.size());

        int[] a = {6, 2, 8, 1, 4, 3, 7};
        for (int each : a) {
            t.add(each);
        }
        System.out.println("current size: " + t.size());

        t.remove(6);

        System.out.println("current size: " + t.size());

        t.remove(100);

        System.out.println("current size: " + t.size());
    }
}