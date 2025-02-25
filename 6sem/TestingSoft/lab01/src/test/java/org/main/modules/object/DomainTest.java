package org.main.modules.object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {
    public Author smplAuth = null;


    @BeforeEach
    public void setUp() {
        this.smplAuth = new Author("Оолон Коллупхид", "@mail", "+75656");
    }

    @Test
    void AuthorTest(){
        assertEquals("Оолон Коллупхид", smplAuth.getName());
        assertEquals("@mail", smplAuth.getEmail());
        assertEquals("+75656", smplAuth.getNumber());
    }

    @Test
    void testBookCreation() {
        Book book = new Book("Ну вот, пожалуй, и все о Боге",  2020, smplAuth);
        assertEquals("Ну вот, пожалуй, и все о Боге", book.title);
        assertEquals(2020, book.year);
        assertEquals("Ну вот, пожалуй, и все о Боге by Оолон Коллупхид, published in 2020", book.getSummary());
    }

    @Test
    void testBookCreationNullYear() {
        Book book = new Book("Ну вот, пожалуй, и все о Боге",  -1, smplAuth);
        assertThrows(IllegalArgumentException.class, book::isVariableYear);
    }

    @Test
    void testClassicBook() {
        Book classicBook = new Book("Old Book", 1980, smplAuth);
        assertTrue(classicBook.isClassic());
    }

    @Test
    void testNonClassicBook() {
        Book modernBook = new Book("Modern Book", 2021, smplAuth);
        assertFalse(modernBook.isClassic());
    }



}