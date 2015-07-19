package heap;

public class LeftistHeap<T extends Comparable<? super T>> {
    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;
        int npl;  // null path length

        Node(T x) {
            this(x, null, null);
        }

        Node(T x, Node<T> l, Node<T> r) {
            element = x;
            left = l;
            right = r;
            npl = 0;
        }
    }

    private Node<T> root;

    public LeftistHeap() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public void merge(LeftistHeap r) {

        root = merge(root, r.root);
        r.clear();
    }

    public void insert(T x) {
        merge(root, new Node<>(x));
    }

    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        return root.element;
    }

    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }

        T minItem = root.element;
        root = merge(root.left, root.right);

        return minItem;
    }

    private Node<T> merge(Node<T> h1, Node<T> h2) {
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }

        if (h1.element.compareTo(h2.element) < 0) {
            return mergeRoutine(h1, h2);
        } else {
            return mergeRoutine(h2, h1);
        }
    }

    private Node<T> mergeRoutine(Node<T> h1, Node<T> h2) {
        if (h1.left == null) {
            h1.left = h2;
        } else {
            h1.right = merge(h1.right, h2);
            if (h1.left.npl < h1.right.npl) {
                swapChildren(h1);
            }
            h1.npl = h1.right.npl + 1;
        }

        return h1;
    }

    private void swapChildren(Node<T> h) {
        Node<T> tmp = h.right;
        h.right = h.left;
        h.left = tmp;
    }

}