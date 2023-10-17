package commands;

import org.junit.jupiter.api.Assertions;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

class AverageCommandTest {
    @org.junit.jupiter.api.Test
    void doo() {
        HashSet<Organization> s = new HashSet<>();
        AverageCommand smCmd = new AverageCommand();
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            smCmd.doo(s, "average");
            Float avr = 0F;
            for(Organization o: s){
                avr = avr + o.getAnnualTurnover();
            }
            avr = avr / s.size();
            Assertions.assertEquals(4354F, avr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}