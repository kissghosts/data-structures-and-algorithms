package hash;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

// using linked-list
public class SeparateChainingHashTable<T> implements Iterable<T> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private List<T> [] lists;
    private int currentSize;


    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int s) {
        lists = new LinkedList[nextPrime(s)];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<>();
        }
        currentSize = 0;
    }

    public void clear() {
        for (int i = 0; i < lists.length ; i++) {
            lists[i].clear();
        }
        currentSize = 0;
    }

    public int size() {
        return currentSize;
    }

    public boolean contains(T x) {
        List<T> hashedList = lists[hash(x)];
        return hashedList.contains(x);
    }

    public void insert(T x) {
        List<T> hashedList = lists[hash(x)];
        if (!hashedList.contains(x)) {
            hashedList.add(x);

            if (++currentSize > lists.length) {
                rehash();
            }
        }   
    }

    public void remove(T x) {
        List<T> hashedList = lists[hash(x)];
        if (hashedList.contains(x)) {
            hashedList.remove(x);
            currentSize--;
        }
    }

    private int hash(T x) {
        int val = x.hashCode(); // java inherent method

        val %= lists.length;
        if (val < 0) {
            val += lists.length;
        }

        return val;
    }

    private static int nextPrime(int n) {
        if (n % 2 == 0) {
            n++;
        }

        while (!isPrime(n)) {
            n += 2;
        }

        return n;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }

        if (n == 1 || n % 2 == 0) {
            return false;
        }

        for (int i = 3; i * i <= n ; i += 2 ) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private void rehash() {
        List<T>[] oldLists = lists; 
        lists = new LinkedList[nextPrime(2 * lists.length)];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<>();
        }

        currentSize = 0;
        for(int i = 0; i < oldLists.length; i++) {
            for (T item : oldLists[i]) {
                insert(item);
            }
        }
    }

    public Iterator<T> iterator() {
        return new InternalIterator<T>();
    }

    private class InternalIterator<T> implements Iterator<T> {
        int current;
        int listIndex;

        public InternalIterator() {
            listIndex = 0;
            skipNullList();
        }

        public boolean hasNext() {
            if (listIndex >= lists.length) {
                return false;
            }

            return true;
        }

        public T next() {
            T currentItem = ((LinkedList<T>)lists[listIndex]).get(current++);
            if (current == ((LinkedList<T>)lists[listIndex]).size()) {
                listIndex++;
                skipNullList();
            }

            return currentItem;
        }

        private void skipNullList() {
            while(listIndex < lists.length && ((LinkedList<T>)lists[listIndex]).isEmpty()) {
                listIndex++;
            }

            if (listIndex != lists.length) {
                current = 0;
            }
        }

        public void remove() {
            // not defined yet
        }
    }


    /********************************
    * Testing
    *********************************/
    public static class Employee {
        public String name;

        public Employee(String n) {
            name = n;
        }

        public boolean equals(Object e) {
            return (e instanceof Employee && name.equals(((Employee)e).name));
        }

        public int hashCode() {
            return name.hashCode();
        }
    }
    
    public static void main(String[] args) {
        SeparateChainingHashTable<Employee> h = new SeparateChainingHashTable<>(7);

        String[] a = {"laNm", "rotk", "mushi", "hao", "mu", "xiao8", "burning", "zsmj"};
        for (String item : a) {
            h.insert(new Employee(item));
            System.out.println(h.size());
        }

        System.out.println(h.contains(new Employee("yao")));
        Employee xiao8 = new Employee("xiao8");
        System.out.println(h.contains(xiao8));
        h.remove(xiao8);
        System.out.println(h.contains(xiao8));
        System.out.println(h.size());

        for (Employee item : h) {
            System.out.println(item.name);
        }
    }
}