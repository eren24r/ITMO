package org.main.modules.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {
    @Test
    void testBookCreation() {
        Book book = new Book("Ну вот, пожалуй, и все о Боге", "Оолон Коллупхид", 2020);
        assertEquals("Ну вот, пожалуй, и все о Боге", book.title);
        assertEquals("Оолон Коллупхид", book.author);
        assertEquals(2020, book.year);
        assertEquals("Ну вот, пожалуй, и все о Боге by Оолон Коллупхид, published in 2020", book.getSummary());
    }

    @Test
    void testClassicBook() {
        Book classicBook = new Book("Old Book", "Some Author", 1980);
        assertTrue(classicBook.isClassic());
    }

    @Test
    void testNonClassicBook() {
        Book modernBook = new Book("Modern Book", "Another Author", 2021);
        assertFalse(modernBook.isClassic());
    }

    @Test
    void testTheologian() {
        Theologian theologian = new Theologian("John Doe", "Believes in free will", 55);
        assertEquals("John Doe", theologian.name);
        assertEquals("Believes in free will", theologian.opinion);
        assertEquals(55, theologian.age);
        assertTrue(theologian.isSenior());
    }

    @Test
    void testYoungTheologian() {
        Theologian youngTheologian = new Theologian("Jane Doe", "Believes in destiny", 30);
        assertFalse(youngTheologian.isSenior());
    }
}