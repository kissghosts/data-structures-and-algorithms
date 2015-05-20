package com.data.structure;


public class BinarySearchTree<T extends Comparable<? super T>> {

    private static class BinaryNode<T> {
        T data;
        BinaryNode<T> left;
        BinaryNode<T> right;

        BinaryNode(T x, BinaryNode<T> l, BinaryNode<T> r) {
            data = x;
            left = l;
            right = r;
        }
    }
    
    private BinaryNode<T> root;

    public BinarySearchTree () {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    public T findMin() {
        return findMin(root);
    }

    public T findMax() {
        return findMax(root);
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    public void printTreeInOrder() {
        printTreeInOrder(root);
    }

    public void printTreePreOrder() {
        printTreePreOrder(root);
    }

    public int depth(T x) {
        BinaryNode<T> n = root;
        int result;
        int depth = 0;

        while (n != null) {
            result = x.compareTo(n.data);
            if (result < 0) {
                n = n.left;
                depth++;
            } else if (result > 0) {
                n = n.right;
                depth++;
            } else {
                return depth; 
            }
        }

        return -1;

    }

    private void printTreeInOrder(BinaryNode<T> n) {
        if (n != null) {
            printTreeInOrder(n.left);
            System.out.println(n.data);
            printTreeInOrder(n.right);
        }
    }

    private void printTreePreOrder(BinaryNode<T> n) {
        if (n != null) {
            for (int i = 0; i < depth(n.data); i++) {
                System.out.print(" ");
            }

            System.out.println(n.data);
            printTreePreOrder(n.left);
            printTreePreOrder(n.right);
        }
    }

    private boolean contains(T x, BinaryNode<T> n) {
        // n is the root node for searching
        if (n == null) {
            return false;
        }

        int result = x.compareTo(n.data);

        if (result < 0) {
            return contains(x, n.left);
        } else if (result > 0) {
            return contains(x, n.right);
        } else {
            return true;
        }
    }

    private T findMin(BinaryNode<T> n) {

        if (n == null) {
            return null;
        }

        if (n.left == null) {
            return n.data;
        } else {
            return findMin(n.left);
        }

    }

    private T findMax(BinaryNode<T> n) {
        if (n != null) {
            while (n.right != null) {
                n = n.right;
            }
        }

        return n.data;
    }

    private BinaryNode<T> insert(T x, BinaryNode<T> n) {
        if (n == null) {
            return new BinaryNode<> (x, null, null);
        }

        int result = x.compareTo(n.data);
        if (result < 0) {
            n.left = insert(x, n.left);
        } else if (result > 0) {
            n.right = insert(x, n.right);
        } else {
            // duplicated, do nothing
        }

        return n;
    }

    private BinaryNode<T> remove(T x, BinaryNode<T> n) {
        if (n == null) {
            return null;
        }

        int result = x.compareTo(n.data);
        if (result < 0) {
            n.left = remove(x, n.left);
        } else if (result > 0) {
            n.right = remove(x, n.right);
        } else if (n.left != null && n.right != null) {
            n.data = findMin(n.right);
            n.right = remove(n.data, n.right);
        } else {
            n = (n.left != null) ? n.left : n.right;
        }

        return n;
    }
}