package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class ShowInfoCommand {
    public boolean show(HashSet<Organization> s){
        for(Organization o: s){
            Static.txt(colorize(o.toString(), Attribute.BLUE_TEXT()));
        }
        return true;
    }
}
