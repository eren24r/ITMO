package org.main.modules.hashTable;

public class HashTable {
    private String[] table;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.table = new String[size];
    }

    public void insert(String key) {
        int index = hash(key);
        while (table[index] != null) {
            index = (index + 1) % size;
        }
        table[index] = key;
    }

    public boolean search(String key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) return true;
            index = (index + 1) % size;
        }
        return false;
    }

    public void delete(String key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = null;
                return;
            }
            index = (index + 1) % size;
        }
    }

    public int hash(String key) {
        return Math.abs(key.hashCode()) % size;
    }
}