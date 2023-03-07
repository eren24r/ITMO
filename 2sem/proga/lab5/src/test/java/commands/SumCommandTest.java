package commands;

import org.junit.jupiter.api.Assertions;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

class SumCommandTest {
    public HashSet<Organization> s = new HashSet<>();
    SumCommand smCmd = new SumCommand();

    @org.junit.jupiter.api.Test
    void sum_of_annual_turnover() {
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            Assertions.assertEquals(13062F, smCmd.sum_of_annual_turnover(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.jupiter.api.Test
    void average_of_annual_turnover() {
        HashSet<Organization> s = new HashSet<>();
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F,Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            Assertions.assertEquals(4354F, smCmd.average_of_annual_turnover(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}