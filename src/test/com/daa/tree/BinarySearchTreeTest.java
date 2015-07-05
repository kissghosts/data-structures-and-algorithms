package com.daa.tree;

public class BinarySearchTreeTest{
    public static void main(String[] args) {
        int[] a = {6, 2, 8, 1, 4, 3, 7};
        BinarySearchTree<Integer> t = new BinarySearchTree<>();

        for (int each : a) {
            System.out.println(each);
            t.insert(each);
        }

        t.printTreePreOrder();


        t.remove(7);
        t.printTreePreOrder();

        t.remove(2);
        t.printTreePreOrder();
    }
}