package commands;

import com.diogonunes.jcolor.Attribute;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class SerializationType implements Command{

    private String name = "Serialization_type";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        if(Static.isCsv == 1){
            return new ObjectResAns(Static.txt("CSV\n"),true, user);
        } else{
            return new ObjectResAns(Static.txt("JSON\n"),true, user);
        }
    }

    @Override
    public String des() {
        return "Serialization_type: Current Serialization_type";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
