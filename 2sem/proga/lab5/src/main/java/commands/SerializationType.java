package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class SerializationType implements Command{

    private String name = "Serialization_type";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        if(Static.isCsv == 1){
            Static.txt("CSV", Attribute.BOLD());
        }
        if(Static.isCsv == 0){
            Static.txt("JSON",Attribute.BOLD());
        }
        return true;
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
