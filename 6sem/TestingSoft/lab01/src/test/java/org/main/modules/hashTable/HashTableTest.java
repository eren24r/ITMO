package org.main.modules.hashTable;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class HashTableTest {

    HashTable hashTable;

    public HashTableTest() {
        hashTable = new HashTable(10);
    }

    @Test
    @BeforeEach
    void testInsert(){
        assertTrue(hashTable.insert("test"));
        assertTrue(hashTable.insert("example"));
        assertTrue(hashTable.insert("hashing"));
    }

    @Test
    void testSearch() {
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

    @Test
    void testInsertNull(){
        assertThrowsExactly(IllegalArgumentException.class, () -> hashTable.insert(null));
    }

    @Test
    void testSearchNull() {
        assertThrowsExactly(IllegalArgumentException.class, () -> hashTable.search(null));
    }

    @Test
    void testDeleteNull() {
        assertThrowsExactly(IllegalArgumentException.class, () -> hashTable.delete(null ));
    }

}