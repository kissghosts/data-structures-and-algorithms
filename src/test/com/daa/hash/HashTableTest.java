package com.daa.hash;

public class HashTableTest {
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