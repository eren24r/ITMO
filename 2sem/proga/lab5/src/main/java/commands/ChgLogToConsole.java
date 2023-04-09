package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class ChgLogToConsole implements Command{
    private String name = "change_print_logic_console";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        Static.txt("Changed!", Attribute.NONE());
        Static.isPrint = 1;
        return true;
    }

    @Override
    public String des() {
        return "change_print_logic_console : печатаеть вывод в консол";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
