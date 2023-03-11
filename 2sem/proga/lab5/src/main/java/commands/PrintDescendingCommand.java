package commands;

import statics.Static;
import сlasses.Organization;

import java.util.HashMap;
import java.util.HashSet;

public class PrintDescendingCommand implements Command {
    Sort srCmd = new Sort();
    public String name = "print_descending";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s, int isCsv, int isPrint) {
        HashMap<Integer, Organization> al = srCmd.sort(mySet);
        String ss = "";
        for(Organization o: al.values()){
            ss = o.getName() + "\n" + ss;
        }
        Static.txt(s,1);
        return true;
    }

    @Override
    public String des() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
