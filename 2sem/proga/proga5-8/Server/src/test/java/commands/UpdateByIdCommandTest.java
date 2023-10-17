package commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import collEdit.*;

import java.io.IOException;
import java.util.HashSet;

class UpdateByIdCommandTest {
    HashSet<Organization> s = new HashSet<>();
    UpdateByIdCommand updateById = new UpdateByIdCommand();
    GetSizeOfCollection szCol = new GetSizeOfCollection();
    GetById getId = new GetById();

    @Test
    void doo() {
        int a = szCol.getSizeofSet(s);
        try {
            s.add(new Organization("dsdf", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            s.add(new Organization("dsfsdfsdff", new Coordinates(4365L,546), 4354F, Static.orTp.getTypeById(2), new Address("345435", "543543")));
            updateById.doo(s, "id 49 hi");
            Assertions.assertEquals("hi", getId.getById(s, 49).getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}