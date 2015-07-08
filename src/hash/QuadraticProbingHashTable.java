package hash;

public class QuadraticProbingHashTable<T> {
    private class HashEntry<T> {
        public T element;
        public boolean isActive;

        public HashEntry(T e) {
            this(e, true);
        }

        public HashEntry(T e, boolean b) {
            element = e;
            isActive = b;
        }
    }

    private HashEntry<T>[] array;
    private int currentSize;

    private static final int DEFAULT_TABLE_SIZE = 101;

    public QuadraticProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public QuadraticProbingHashTable(int size) {
        allocateArray(size);
        clear();
    }

    public void clear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    private void allocateArray(int size) {
        array = new HashEntry[HashUtility.nextPrime(size)];
    }

    public boolean contains(T x) {
        int index = findPosition(x);
        return isActive(index);
    }

    // only for testing and debuging
    public int size() {
        return array.length;
    }

    // quadratic probing
    private int findPosition(T x) {
        int offset = 1;
        int index = myHash(x);
        while (array[index] != null && !array[index].element.equals(x)) {
            index += offset;
            offset += 2;
            if (index > array.length) {
                index -= array.length;
            }
        }

        return index;
    }

    private int myHash(T x) {
        int val = x.hashCode(); // java inherent method

        val %= array.length;
        if (val < 0) {
            val += array.length;
        }

        return val;
    }

    private boolean isActive(int index) {
        return array[index] != null && array[index].isActive;
    }

    public void insert(T x) {
        int index = findPosition(x);
        if (isActive(index)) {
            return;
        }

        array[index] = new HashEntry<>(x, true);
        if (++currentSize > array.length / 2) {
            rehash();
        }
    }

    public void remove(T x) {
        int index = findPosition(x);
        if (isActive(index)) {
            array[index].isActive = false;
        }
    }

    private void rehash() {
        HashEntry<T>[] oldArray = array;
        allocateArray(HashUtility.nextPrime(array.length * 2));
        currentSize = 0;

        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null && oldArray[i].isActive) {
                insert(oldArray[i].element);
            }
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
        QuadraticProbingHashTable<Employee> h = new QuadraticProbingHashTable<>(4);

        String[] a = {"hao", "mu", "xiao8", "burning"};
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
    }

}