package commands;

import statics.Static;
import сlasses.Organization;

import java.util.HashMap;
import java.util.HashSet;

public class PrintDescendingCommand {
    Sort srCmd = new Sort();

    public boolean print_descending(HashSet<Organization> set){
        HashMap<Integer, Organization> al = srCmd.sort(set);
        String s = "";
        for(Organization o: al.values()){
            s = o.getName() + "\n" + s;
        }
        Static.txt(s);
        return true;
    }
}
