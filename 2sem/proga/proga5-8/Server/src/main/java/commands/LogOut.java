package commands;

import bdMng.BdMng;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class LogOut implements Command{
    String name = "Logout";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws IOException, SQLException, NoSuchAlgorithmException {
        if(user!=null){
            return new ObjectResAns(Static.txt("User: "  + user + " logouted!"), true, null);
        }else{
            return new ObjectResAns(Static.txt("Not login!"), true, user);
        }
    }

    @Override
    public String des() {
        return "Logout: Logout Username Password";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
