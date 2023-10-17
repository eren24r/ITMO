package commands;

import collEdit.Sort;
import com.diogonunes.jcolor.Attribute;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.util.HashMap;
import java.util.HashSet;

public class PrintDescendingCommand implements Command {
    Sort srCmd = new Sort();
    public String name = "print_descending";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        StringBuilder ss = new StringBuilder();
        if(mySet.size() > 0) {
            HashMap<Integer, Organization> al = srCmd.sort(mySet);
            al.values().stream().map(p -> p.getName() + "\n").forEach(ss::append);
            return new ObjectResAns(Static.txt(ss.toString()), true, user);
        }else {
            return new ObjectResAns(Static.txt("Коллекция пуста!\n"), true, user);
        }
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
