package commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RemoveByIdCommandTest {
    SumCommand smCmd = new SumCommand();
    HashSet<Organization> s = new HashSet<>();
    GetSizeOfCollection szCol = new GetSizeOfCollection();
    RemoveByIdCommand rmById = new RemoveByIdCommand();

    @Test
    void remove_by_id() {
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("sdfds", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            rmById.remove_by_id(s, "remove_by_id 1");
            Assertions.assertEquals(2, szCol.getSizeofSet(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}