package сlasses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {
    @Test
    void testToString() {
        Coordinates cr = new Coordinates(7675L, -657);
        assertEquals("(7675,-657.0)", cr.toString());
        Coordinates cr1 = new Coordinates(0L, -657);
        assertEquals("(0,-657.0)", cr1.toString());
    }
}