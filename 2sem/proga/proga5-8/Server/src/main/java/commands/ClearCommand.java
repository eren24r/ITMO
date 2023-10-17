package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class ClearCommand implements Command{
    private String name = "clear";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        mySet.clear();
        return new ObjectResAns(Static.txt("Удалено!\n"), true, user);
    }

    @Override
    public String des() {
        return "clear : очистить коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
