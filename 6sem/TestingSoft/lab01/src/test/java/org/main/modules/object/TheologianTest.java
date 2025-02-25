package org.main.modules.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TheologianTest {
    @Test
    void testYoungTheologian() {
        Theologian youngTheologian = new Theologian("Jane Doe", "Believes in destiny", 30);
        assertFalse(youngTheologian.isSenior());
    }

    @Test
    void isTheologianFake() {
        Theologian youngTheologian = new Theologian("Jane Doe", "Believes in destiny", 30);
        assertFalse(youngTheologian.isFake());
    }
    @Test
    void testTheologian() {
        Theologian theologian = new Theologian("John Doe", "Believes in free will", 55);
        assertEquals("John Doe", theologian.name);
        assertEquals("Believes in free will", theologian.opinion);
        assertEquals(55, theologian.age);
        assertTrue(theologian.isSenior());
    }
}
