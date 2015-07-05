package com.daa.tree;

public class RedBlackTreeTest {
    public static void main(String[] args) {
        int[] a = {11, 2, 14, 15, 1, 7, 5, 8, 4};
        RedBlackTree<Integer> t = new RedBlackTree<>();

        for (int each : a) {
            t.insert(each);
        }
        System.out.println("current root: " + t.getRootValue());

        t.printTreeInOrder();

        t.delete(4);
        System.out.println("current root: " + t.getRootValue());
        t.printTreeInOrder();

        t.delete(5);
        System.out.println("current root: " + t.getRootValue());
        t.printTreeInOrder();
        
        t.delete(14);
        System.out.println("current root: " + t.getRootValue());
        t.printTreeInOrder();
        
        t.delete(11);
        System.out.println("current root: " + t.getRootValue());
        t.printTreeInOrder();
    }
}