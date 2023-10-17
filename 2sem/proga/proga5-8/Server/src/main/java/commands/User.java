package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashSet;

public class User implements Command{
    String name = "User";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws IOException, SQLException, NoSuchAlgorithmException {
        if(user!=null){
            return new ObjectResAns(Static.txt("User: "  + user), true, user);
        }else{
            return new ObjectResAns(Static.txt("Not login!"), true, user);
        }
    }

    @Override
    public String des() {
        return "User: Now User";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
