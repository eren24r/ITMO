package Tests;

import Classes.*;
import org.testng.annotations.Test;

import java.util.HashSet;
import static org.testng.AssertJUnit.assertEquals;

public class CommandsTest {

    @Test
    void sum_of_annual_turnove(){
        HashSet<Organization> s = new HashSet<>();
        s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        assertEquals(13062, Commands.sum_of_annual_turnover(s));
    }

    @Test
    void averageofSet(){
        HashSet<Organization> s = new HashSet<>();
        s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        assertEquals(4354, Commands.average_of_annual_turnover(s));
    }

    @Test
    void update_by_id(){
        HashSet<Organization> s = new HashSet<>();
        int a = Commands.sizeOfSetNotSaved;
        s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        Commands.sizeOfSetNotSaved += 1;
        s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        Commands.sizeOfSetNotSaved += 1;
        s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        Commands.sizeOfSetNotSaved += 1;
        Commands.sizeOfSetNotSaved = a;
        Commands.updateById(s, "id 2 hi");
        assertEquals("hi", Commands.getById(s, 2).getName());
    }

    @Test
    void getSizeOfSet(){
        HashSet<Organization> s = new HashSet<>();
        s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, OrganizationType.getTypeById(2), new Address("345435", "543543")));
        assertEquals(3, Commands.getSizeofSet(s));
    }
}
