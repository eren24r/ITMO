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
    void doo() {
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            smCmd.doo(s, "2");
            Float sum = 0F;
            for(Organization o: s){
                sum = sum + o.getAnnualTurnover();
            }
            Assertions.assertEquals(13062F, sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}