package com.daa.tree;

// java requires O(log(n)) on add, remove, and contains for TreeSet
// so a balanced tree is required (red-black tree, avl tree)

// iterator is not implemented yet!!!

public class TreeSet<T extends Comparable<? super T>> {

    private BinarySearchTree<T> tree;
    private int treeSize;

    public TreeSet() {
        tree = new BinarySearchTree<>();
        treeSize = 0;
    }

    public void clear() {
        tree.makeEmpty();
        treeSize = 0;
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public boolean contains(T x) {
        return tree.contains(x);
    }

    public boolean add(T x) {
        if (tree.contains(x)) {
            return false;
        } 

        tree.insert(x);
        treeSize++;
        return true;
    }

    public boolean remove(T x) {
        if (!tree.contains(x)) {
            return false;
        } else {
            tree.remove(x);
            treeSize--;
            return true;
        }
    }

    public int size() {
        return treeSize;
    }

    // first(), last()

}