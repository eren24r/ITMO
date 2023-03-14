package сlasses;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganizationTest {
    public HashSet<Organization> s = new HashSet<>();
    Organization myOrg;

    {
        try {
            myOrg = new Organization("hello", new Coordinates(1L, 2), 456F, OrganizationType.TRUST, new Address("hi", "hello"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void toStringCSV() {
        String time = myOrg.getCreationDate().toString();
        String csv = "\"62\",\"hello\",\"1\",\"2.0\",\"" + time + "\",\"456.0\",\"TRUST\",\"hi\",\"hello\"";
        assertEquals(csv, myOrg.toStringCSV());
    }

    @Test
    void toStringJson() {
        String time = myOrg.getCreationDate().toString();
        String json = "{\"id\":63,\"name\":\"hello\",\"coordinates\":{\"x\":1,\"y\":2.0},\"creationDate\":\""+time+"\",\"annualTurnover\":456.0,\"type\":\"TRUST\",\"postalAddress\":{\"street\":\"hi\",\"zipCode\":\"hello\"}}";
        assertEquals(json, myOrg.toStringJson());
    }
}