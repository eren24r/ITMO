package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;
import java.util.stream.Stream;

public class HelpCommand implements Command{

    private String name = "help";
    @Override
    public String des() {
        return "help : справка по доступным командам";
    }

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        StringBuilder alldes = new StringBuilder();
        Static.listOfCommand.values().stream().map(p -> p.des() + "\n").forEach(alldes::append);
        return new ObjectResAns(Static.txt(alldes.toString()), true, user);
   }

    @Override
    public String getName() {
        return this.name;
    }
}
