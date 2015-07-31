package hash;

import java.util.List;
import java.util.LinkedList;

public class HashTable<K, V> {
    private List<HashEntry<K, V>>[] lists;
    private static final int DEFAULT_SIZE = 101;

    private int currentSize;

    private class HashEntry<K, V> {
        public K key;
        public V value;

        HashEntry(K k, V v) {
            key = k;
            value = v;
        }
    }

    public HashTable() {
        this(DEFAULT_SIZE);
    }

    public HashTable(int s) {
        lists = new LinkedList[HashUtility.nextPrime(s)];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<HashEntry<K, V>>();
        }
    }

    public void put(K key, V value) {
        for (HashEntry each : lists[hash(key)]) {
            if (each.key == key) {
                each.value = value;
                return;
            }
        }

        lists[hash(key)].add(new HashEntry<>(key, value));
    }

    public V get(K key) {
        for (HashEntry each : lists[hash(key)]) {
            if (each.key == key) {
                return (V)each.value;
            }
        }

        return null;
    }

    private int hash(K key) {
        return key.hashCode() % lists.length;
    }

}