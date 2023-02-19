package Tests;
import Classes.Coordinates;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;


public class CordinatesTest {
    @Test
    void testToString() {
        Coordinates cr = new Coordinates(7675L, -657);
        assertEquals("(7675,-657.0)", cr.toString());
        Coordinates cr1 = new Coordinates(0L, -657);
        assertEquals("(0,-657.0)", cr1.toString());
    }
}
