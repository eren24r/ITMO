package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class NewCommand implements Command{

    private String name;
    public NewCommand(String name){
        this.name = name;
    }

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        Static.txt(this.des(), Attribute.NONE());
        return true;
    }

    @Override
    public String des() {
        return (this.name + " : simple command");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
