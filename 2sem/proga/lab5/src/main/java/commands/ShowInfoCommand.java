package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class ShowInfoCommand implements Command{
    private String name = "show";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        for(Organization o: mySet){
            Static.txt(o.toStringJson(), Attribute.BLUE_TEXT());
        }
        return true;
    }

    @Override
    public String des() {
        return "show : все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
