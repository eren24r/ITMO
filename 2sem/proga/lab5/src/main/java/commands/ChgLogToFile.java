package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class ChgLogToFile implements Command{
    private String name = "change_print_logic_file";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        Static.txt("Changed!", Attribute.NONE());
        Static.isPrint = 0;
        return true;
    }

    @Override
    public String des() {
        return "change_print_logic_file : записивать вывод в log файл";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
