package Tests;

import Classes.Address;
import Classes.Coordinates;
import Classes.Organization;
import Classes.OrganizationType;
import Datas.ParseIng;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseIngTest {
    @Test
    void csvParse(){
        LocalDateTime time = LocalDateTime.parse("14.02.2023 22:16:37", ParseIng.dateTimeFormatter);
        Organization o = new Organization(0, "sdfds", new Coordinates(4365L, 546.0F), time, 4354.0F,OrganizationType.GOVERNMENT, new Address("345435","543543"));
        String s = "0,sdfds,4365,546.0,14.02.2023 22:16:37,4354.0,2,345435,543543";
        assertEquals(s, o.toStringCSV());
    }
}
