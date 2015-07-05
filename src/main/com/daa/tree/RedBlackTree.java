package com.daa.tree;

/*
 non-recursive insertion and deletion are referenced from 
 "Introduction to algorithms"
*/

public class RedBlackTree<T extends Comparable<? super T>> {

    private static class RedBlackTreeNode<T extends Comparable<? super T>> {
        T element;
        RedBlackTreeNode<T> left;
        RedBlackTreeNode<T> right;
        RedBlackTreeNode<T> parent;
        int color;

        RedBlackTreeNode(T x, RedBlackTreeNode<T> l, RedBlackTreeNode<T> r, 
            RedBlackTreeNode<T> p) {
            
            element = x;
            left = l;
            right = r;
            parent = p;
            color = RedBlackTree.BLACK; // defualt color
        }

        RedBlackTreeNode(T x) {
            this(x, null, null, null);
        }

        boolean isNull() {
            return element == null;
        }

        boolean equals(RedBlackTreeNode<T> n) {
            return element.compareTo(n.element) == 0;
        }
    }

    private static final int BLACK = 1;
    private static final int RED = 0;

    private RedBlackTreeNode<T> root;
    private RedBlackTreeNode<T> nullNode;

    public RedBlackTree() {
        nullNode = new RedBlackTreeNode<>(null);
        nullNode.left = nullNode.right = nullNode;
        root = new RedBlackTreeNode<>(null, nullNode, nullNode, null);
        root.left.parent = root.right.parent = root;
    }

    public void clear() {
        root = new RedBlackTreeNode<>(null, nullNode, nullNode, null);
        root.left.parent = root.right.parent = root;
    }

    public boolean isEmpty() {
        return root.isNull();
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    private boolean contains(T x, RedBlackTreeNode<T> n) {
        if (n.isNull()) {
            return false;
        }

        int result = x.compareTo(n.element);
        if (result < 0) {
            return contains(x, n.left);
        } else if (result > 0) {
            return contains(x, n.right);
        } else {
            return true;
        }
    }

    public T findMin() {
        return findMin(root);
    }

    private T findMin(RedBlackTreeNode<T> n) {
        if (n.isNull()) {
            return null;
        }

        if (n.left.isNull()) {
            return n.element;
        } else {
            return findMin(n.left);
        }
    }

    public T findMax() {
        return findMax(root);
    }

    private T findMax(RedBlackTreeNode<T> n) {
        if (n.isNull()) {
            return null;
        }

        if (n.right.isNull()) {
            return n.element;
        } else {
            return findMax(n.right);
        }
    }

    public void printTreeInOrder() {
        printTreeInOrder(root);
    }

    private void printTreeInOrder(RedBlackTreeNode<T> n) {
        if (!n.isNull()) {
            printTreeInOrder(n.left);
            System.out.print(n.element);
            System.out.println(": " + (n.color == RED ? "red" : "black"));
            printTreeInOrder(n.right);
        }
    }


    // for debug only
    public T getRootValue() {
        return root.element;
    }


    public void insert(T data) {
        RedBlackTreeNode<T> x = root;
        RedBlackTreeNode<T> y = null;

        while (!x.isNull()) {
            y = x;
            if (data.compareTo(x.element) < 0) {
                x = x.left;
            } else if (data.compareTo(x.element) > 0) {
                x = x.right;
            } else {
                // duplicated, do noting
                return;
            }
        }

        RedBlackTreeNode<T> z = new RedBlackTreeNode<>(data, nullNode, nullNode, y);
        z.left.parent = z.right.parent = z;
        z.color = RED;
        if (y == null) {
            root = z;
        } else if (data.compareTo(y.element) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }

        insertFixUp(z);

    }


    // k1 < k2
    private void leftRotate(RedBlackTreeNode<T> k1) {
        RedBlackTreeNode<T> k2 = k1.right;

        k1.right = k2.left;
        k1.right.parent = k1;

        k2.parent = k1.parent;
        if (root.element.compareTo(k1.element) == 0)  {
            root = k2;
        } else if (!k1.parent.left.isNull() 
            && k1.parent.left.element.compareTo(k1.element) == 0) {
            k1.parent.left = k2;
        } else {
            k1.parent.right = k2;
        }

        k2.left = k1;
        k1.parent = k2;
    }

    private void rightRotate(RedBlackTreeNode<T> k2) {
        RedBlackTreeNode<T> k1 = k2.left;

        k2.left = k1.right;
        k2.left.parent = k2;

        k1.parent = k2.parent;
        if (root.element.compareTo(k2.element) == 0) {
            root = k1;
        } else if (!k2.parent.left.isNull() 
            && k2.parent.left.element.compareTo(k2.element) == 0) {
            k2.parent.left = k1;
        } else {
            k2.parent.right = k1;
        }

        k1.right = k2;
        k2.parent = k1;
    }

    private void insertFixUp(RedBlackTreeNode<T> n) {
        while (n.parent != null && n.parent.color == RED) {
            RedBlackTreeNode<T> parent = n.parent;
            RedBlackTreeNode<T> uncle;
            RedBlackTreeNode<T> grand = n.parent.parent;

            if (grand != null && !grand.left.isNull()
                && parent.element.compareTo(grand.left.element) == 0 ) {

                uncle = grand.right;

                // re-color or rotate
                if (grand.color == BLACK && uncle.color == RED) {
                    // case 1
                    n.parent.parent.color = RED;
                    n.parent.parent.left.color = n.parent.parent.right.color = BLACK;
                    n = n.parent.parent;

                } else if (!parent.right.isNull() 
                    && n.element.compareTo(parent.right.element) == 0) {
                    // case 2
                    n = n.parent;
                    leftRotate(n);
                } else {
                    // case 3
                    n.parent.color = BLACK;
                    n.parent.parent.color = RED;
                    rightRotate(n.parent.parent);
                }             
            } else if (grand != null && !grand.right.isNull()
                && parent.element.compareTo(grand.right.element) == 0 ) {
                    // symmetric image
                uncle = grand.left;

                // re-color or rotate
                if (grand.color == BLACK && uncle.color == RED) {
                    // case 1
                    n.parent.parent.color = RED;
                    n.parent.parent.left.color = n.parent.parent.right.color = BLACK;
                    n = n.parent.parent;

                } else if (!parent.left.isNull() 
                    && n.element.compareTo(parent.left.element) == 0) {
                    // case 2
                    n = n.parent;
                    rightRotate(n);
                } else {
                    // case 3
                    n.parent.color = BLACK;
                    n.parent.parent.color = RED;
                    leftRotate(n.parent.parent);
                }             
            }
            
        }

        root.color = BLACK;
    }

    // replace the subtree rooted at u with the subtree rooted at v
    private void transplant(RedBlackTreeNode<T> u, RedBlackTreeNode<T> v) {
        if (u.isNull()) {
            // do nothing
            return;
        }

        if (u.parent == null) {
            root = v;
        } else if (!u.parent.left.isNull() && u.equals(u.parent.left)) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        v.parent = u.parent;
    }

    private RedBlackTreeNode<T> getNode(T value, RedBlackTreeNode<T> n) {
        if (n.isNull()) { // no such node
            return null;
        }

        int result = value.compareTo(n.element);
        if (result < 0) {
            return getNode(value, n.left);
        } else if (result > 0) {
            return getNode(value, n.right);
        } else {
            return n;
        }
    }

    private RedBlackTreeNode<T> getMinNode(RedBlackTreeNode<T> n) {
        if (n.isNull()) {
            return null;
        }

        if (n.left.isNull()) {
            return n;
        } else {
            return getMinNode(n.left);
        }
    }

    public void delete(T value) {
        RedBlackTreeNode<T> z = getNode(value, root);  //the node to be removed
        if (z == null) { // not found
            return;
        }

        RedBlackTreeNode<T> x;
        RedBlackTreeNode<T> y = z;
        int yOriginalColor = y.color;
        if (z.left.isNull()) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right.isNull()) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = getMinNode(z.right); // update y
            yOriginalColor = y.color;
            x = y.right;
            
            if (y.parent.equals(z)) { // y is z's right child, only update x
                x.parent = y;
            } else { // remove y from z's right subtree
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = z;
            }

            // replace z with node y
            transplant(z, y);
            y.left = z.left; 
            z.right.parent = y;
            y.color = z.color; 
        }

        if (yOriginalColor == BLACK) {
            deleteFixUp(x);
        }

    }

    private void deleteFixUp(RedBlackTreeNode<T> x) {
        RedBlackTreeNode<T> w;
        while (!x.isNull() && x.parent != null && x.color == BLACK) {
            if (!x.parent.left.isNull() && x.equals(x.parent.left)) {
                w = x.parent.right;
                if (w.color == RED) { // case 1
                    x.parent.color = RED;
                    w.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right; // update w, change it to another case
                }
                // now w must be black

                if (w.right.color == BLACK && w.left.color == BLACK) { // case 2
                    w.color = RED;
                    x = x.parent;
                } else if (w.left.color == RED && w.right.color == BLACK) {
                    // case 3
                    w.left.color = BLACK;
                    w.color = RED;
                    rightRotate(w);
                    w = x.parent.right;
                }

                if (w.right.color == RED) { // case 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x.parent = null;
                }
            } else {
                w = x.parent.left;
                if (w.color == RED) {
                    x.parent.color = RED;
                    w.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    x = x.parent;
                } else if (w.right.color == RED && w.left.color == BLACK) {
                    w.right.color = BLACK;
                    w.color = RED;
                    leftRotate(w);
                    w = x.parent.left;
                }

                if (w.left.color == RED) {
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x.parent = null;
                }
            }
        }
        x.color = BLACK;
    }

}