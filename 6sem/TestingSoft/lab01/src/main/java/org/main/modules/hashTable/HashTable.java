package org.main.modules.hashTable;

public class HashTable {
    private String[] table;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.table = new String[size];
    }

    public boolean insert(String key) {
        if(key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        int index = hash(key);
        while (table[index] != null) {
            index = (index + 1) % size;
        }
        table[index] = key;

        return true;
    }

    public boolean search(String key) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key cannot be null or empty");

        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) return true;
            index = (index + 1) % size;
        }
        return false;
    }

    public boolean delete(String key) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key cannot be null or empty");

        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                table[index] = null;
                return false;
            }
            index = (index + 1) % size;
        }

        return true;
    }

    public int hash(String key) {
        return Math.abs(key.hashCode()) % size;
    }
}