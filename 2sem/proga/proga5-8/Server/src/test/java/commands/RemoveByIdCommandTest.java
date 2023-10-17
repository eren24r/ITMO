package commands;

import collEdit.GetSizeOfCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

class RemoveByIdCommandTest {
    SumCommand smCmd = new SumCommand();
    HashSet<Organization> s = new HashSet<>();
    GetSizeOfCollection szCol = new GetSizeOfCollection();
    RemoveByIdCommand rmById = new RemoveByIdCommand();

    @Test
    void doo() {
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L, 546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("sdfds", new Coordinates(4365L, 546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L, 546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            rmById.doo(s, "remove_by_id 34");
            Assertions.assertEquals(2, szCol.getSizeofSet(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}