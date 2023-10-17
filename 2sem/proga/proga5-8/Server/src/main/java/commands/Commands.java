package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Команды
 */


public class Commands {

    public ObjectResAns commandsEditor(HashSet<Organization> mySet, String line, String user) throws IOException, SQLException, NoSuchAlgorithmException {
        String[] cmdStr = line.split(" ");
        ObjectResAns obs = Static.listOfCommand.get(cmdStr[0]).doo(mySet, line, user);
        /*for(Command c: Static.listOfCommand.values()){
            if(c.getName().toString().equals(cmdStr[0])){
                c.doo(mySet, line);
            }
        }*/
        Static.listOfCommand.putAll(Static.listOfNewCommand);
        Static.listOfNewCommand.clear();
        return obs;
    }
}
