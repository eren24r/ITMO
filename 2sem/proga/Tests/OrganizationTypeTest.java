package Tests;

import Classes.OrganizationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
