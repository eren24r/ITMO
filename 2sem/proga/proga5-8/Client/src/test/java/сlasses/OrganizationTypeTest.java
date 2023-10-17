package сlasses;

import org.junit.jupiter.api.Test;
import statics.Static;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganizationTypeTest {

    @Test
    void getId() {
        assertEquals(3, OrganizationType.TRUST.getId());
        assertEquals(1, OrganizationType.PUBLIC.getId());
        assertEquals(2, OrganizationType.GOVERNMENT.getId());
    }

    @Test
    void getTypeById() {
        assertEquals(OrganizationType.PUBLIC, Static.orTp.getTypeById(1));
        assertEquals(OrganizationType.TRUST, Static.orTp.getTypeById(3));
        assertEquals(OrganizationType.GOVERNMENT, Static.orTp.getTypeById(2));
    }

    @Test
    void getTypeByName() {
        assertEquals(OrganizationType.GOVERNMENT, Static.orTp.getTypeByName("GOVERNMENT"));
        assertEquals(OrganizationType.PUBLIC, Static.orTp.getTypeByName("PUBLIC"));
        assertEquals(OrganizationType.TRUST, Static.orTp.getTypeByName("TRUST"));
    }

}