package commands;

import сlasses.Organization;

import java.util.HashSet;

public class ClearCommand implements Command{
    private String name = "clear";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        mySet.clear();
        return true;
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
