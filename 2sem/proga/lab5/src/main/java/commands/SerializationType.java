package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class SerializationType implements Command{

    private String name = "Serialization_type";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        if(Static.isCsv == 1){
            Static.txt(colorize("CSV", Attribute.GREEN_TEXT(), Attribute.BOLD()));
        }
        if(Static.isCsv == 0){
            Static.txt(colorize("JSON", Attribute.GREEN_TEXT(), Attribute.BOLD()));
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
