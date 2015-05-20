package com.daa.list;

public class LinkedList<T> implements Iterable<T> {
    
    private static class Node<T> {
        public Node<T> prev;
        public Node<T> next;
        public T value;

        public Node(Node<T> p, Node<T> n, T d) {
            prev = p;
            next = n;
            value = d;
        }
    }

    private int length;
    private int modCount = 0;
    private Node<T> head;
    private Node<T> tail;


    public LinkedList() {
        clear();
    }

    public void clear() {
        length = 0;
        head = new Node<T>(null, null, null);
        tail = new Node<T>(head, null, null);
        head.next = tail;

        modCount++; 
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return (size() > 0);
    }

    public T get(int index) {
        return getNode(index).value;
    }

    public T set(int index, T value) {
        Node<T> p = getNode(index);
        T old = p.value;
        p.value = value;

        return old;
    }

    public void add(T value) {
        add(size(), value);
    }

    public void add(int index, T value) {
        Node<T> p = getNode(index);
        Node<T> n = new Node<>(p, p.next, value);
        p.next = n;
        n.next.prev = n;

        modCount++;
        length++;
    }

    public T remove(int index) {
        Node<T> p = getNode(index);
        return remove(p);
    }



    private T remove(Node<T> p) {
        p.prev.next = p.next;
        p.next.prev = p.prev;
        length--;
        modCount++;

        return p.value;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> p;

        if (index < size() / 2) {
            p = head.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        } else {
            p = tail.prev;
            for (int i = size() - 1; i > index; i--) {
                p = p.prev;
            }
        }

        return p;
    }




    public java.util.Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<T> {
        private Node<T> current = head.next;
        private int modNum = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            return current != tail;
        }

        public T next() {
            if (modNum != modNum) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            T value = current.value;
            current = current.next;
            // okToRemove = true;
            return value;
        }
        
        public void remove() {
            if (modNum != modNum) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }

            LinkedList.this.remove(current.prev);
            modNum++;
            okToRemove = false;
        }
        
    }
}
