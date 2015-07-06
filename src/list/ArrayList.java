package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements Iterable<T> {
    static final int CAPACITY = 10;

    private int len;
    private T[] items;

    public ArrayList() {
        clear();
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public void clear() {
        len = 0;
        setCapacity(CAPACITY);
    }

    public void setCapacity(int newCapacity) {
        // at least 10
        if (newCapacity < CAPACITY) {
            return;
        }

        T[] old = items;
        items = (T[])new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            items[i] = old[i];
        }
    }

    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return items[index];
    }

    public T set(int index, T value) {
        T old = get(index);
        items[index] = value;
        return old;
    }

    public void add(T value) {
        add(size(), value);
    }

    public void add(int index, T value) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (items.length == size()) {
            setCapacity(2 * size());
        }
        for (int i = size(); i > index; i--) {
            items[i] = items[i - 1];
        }
        items[index] = value;
        len++;
    }

    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        T value = get(index);
        for (int i = index; i < size() - 1; i++) {
            items[i] = items[i + 1];
        }
        len--;

        return T;
    }

    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int current = 0;

        public boolean hasNext() {
            return current < size();
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[current++];
        }

        public void remove() {
            ArrayList.this.remove(--current);
        }
    }
}