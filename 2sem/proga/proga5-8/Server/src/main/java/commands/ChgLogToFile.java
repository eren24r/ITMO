package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class ChgLogToFile implements Command{
    private String name = "change_print_logic_file";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        Static.isPrint = 0;
        return new ObjectResAns("Changed!\n", true, user);
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
