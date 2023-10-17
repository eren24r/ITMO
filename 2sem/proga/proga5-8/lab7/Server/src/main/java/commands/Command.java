package commands;

import objectResAns.ObjectResAns;
import сlasses.Organization;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashSet;

public interface Command {
    ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws IOException, SQLException, NoSuchAlgorithmException;
    String des();
    String getName();
}
