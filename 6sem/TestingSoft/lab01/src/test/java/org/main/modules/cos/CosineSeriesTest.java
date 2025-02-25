package org.main.modules.cos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosineSeriesTest {
    @Test
    void testFact(){
        assertEquals(1, CosineSeries.factorial(1));
        assertEquals(2, CosineSeries.factorial(2));
        assertEquals(6, CosineSeries.factorial(3));
        assertEquals(24, CosineSeries.factorial(4));
    }

    @Test
    void testCosineApproximation() {
        assertEquals(Math.cos(0), CosineSeries.cos(0, 10), 1e-6);
        assertEquals(Math.cos(Math.PI / 4), CosineSeries.cos(Math.PI / 4, 15), 1e-6);
        assertEquals(Math.cos(Math.PI / 2), CosineSeries.cos(Math.PI / 2, 20), 1e-6);
        assertEquals(Math.cos(Math.PI), CosineSeries.cos(Math.PI, 25), 1e-6);
        assertEquals(Math.cos(2 * Math.PI), CosineSeries.cos(2 * Math.PI, 30), 1e-6);
    }

    @Test
    void testNegativeValues() {
        assertEquals(Math.cos(-Math.PI / 4), CosineSeries.cos(-Math.PI / 4, 15), 1e-6);
        assertEquals(Math.cos(-Math.PI / 2), CosineSeries.cos(-Math.PI / 2, 20), 1e-6);
    }

    @Test
    void testLargeValues() {
        assertEquals(Math.cos(10), CosineSeries.cos(10, 50), 1e-6);
        assertEquals(Math.cos(20), CosineSeries.cos(20, 50), 1e-6);
    }
}