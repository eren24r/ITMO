package org.main.modules.hashTable;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HashTableTest {
    @Test
    void testInsertAndSearch() {
        HashTable hashTable = new HashTable(10);
        hashTable.insert("test");
        hashTable.insert("example");
        hashTable.insert("hashing");
        assertTrue(hashTable.search("test"));
        assertTrue(hashTable.search("example"));
        assertFalse(hashTable.search("not_present"));
    }

    @Test
    void testDelete() {
        HashTable hashTable = new HashTable(10);
        hashTable.insert("delete_me");
        assertTrue(hashTable.search("delete_me"));
        hashTable.delete("delete_me");
        assertFalse(hashTable.search("delete_me"));
    }
}