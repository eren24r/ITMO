package Tests;

import Classes.OrganizationType;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class OrganizationTypeTest {
    @Test
    void getTypeById() {
        assertEquals(OrganizationType.PUBLIC, OrganizationType.getTypeById(1));
    }

    @Test
    void getId(){
        assertEquals(3, OrganizationType.TRUST.getId());
    }
}
