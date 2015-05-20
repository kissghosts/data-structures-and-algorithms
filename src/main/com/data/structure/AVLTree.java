package com.data.structure;

public class AVLTree<T extends Comparable<? super T>> {
    private static class AVLNode<T> {
        T data;
        int height;
        AVLNode<T> left;
        AVLNode<T> right;

        AVLNode(T x, AVLNode<T> l, AVLNode<T> r) {
            data = x;
            left = l;
            right = r;
            height = 0;
        }
    }

    private int ALLOWED_IMBALANCE = 1;
    private AVLNode<T> root;

    public AVLTree () {
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

    /*
    public T findMin() {
    }

    public T findMax() {
    }
    */

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    private int height(AVLNode<T> n) {
        return n == null ? -1: n.height;
    }

    private boolean contains(T x, AVLNode<T> n) {
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

    private T findMin(AVLNode<T> n) {

        if (n == null) {
            return null;
        }

        if (n.left == null) {
            return n.data;
        } else {
            return findMin(n.left);
        }

    }

    private AVLNode<T> insert(T x, AVLNode<T> n) {
        if (n == null) {
            return new AVLNode<>(x, null, null);
        }

        int result = x.compareTo(n.data);
        if (result < 0) {
            n.left = insert(x, n.left);
        } else if (result > 0) {
            n.right = insert(x, n.right);
        } else {
            // duplicated, do nothing
        }

        return balance(n);
    }

    private AVLNode<T> remove(T x, AVLNode<T> n) {
        if (n == null) {
            return n;
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
            n = n.left != null ? n.left : n.right;
        }

        return balance(n);

    }

    private AVLNode<T> balance(AVLNode<T> n) {
        if (n == null) {
            return n;
        }

        if (height(n.left) - height(n.right) > ALLOWED_IMBALANCE) {
            if (height(n.left.left) >= height(n.left.right)) {
                n = rotateWithLeftChild(n);
            } else {
                n = doubleWithLeftChild(n);
            }
        }

        n.height = 1 + Math.max(height(n.left), height(n.right));
        return n;
    }

    // by default k1 < k2 < k3
    private AVLNode<T> rotateWithLeftChild(AVLNode<T> k2) {
        AVLNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k2)) + 1;

        return k1;
    }

    private AVLNode<T> rotateWithRightChild(AVLNode<T> k1) {
        AVLNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k1), height(k2.right)) + 1;

        return k2;
    }

    private AVLNode<T> doubleWithLeftChild(AVLNode<T> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AVLNode<T> doubleWithRightChild(AVLNode<T> k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
}