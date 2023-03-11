package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class ShowInfoCommand implements Command{
    private String name = "show";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s, int isCsv, int isPrint) {
        for(Organization o: mySet){
            Static.txt(colorize(o.toString(), Attribute.BLUE_TEXT()), isPrint);
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
