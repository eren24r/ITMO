import commands.HelpCommand;
import сlasses.Organization;

import java.util.HashSet;

public class Testing {
    public static void main(String[] args) {
        HashSet<String> s = new HashSet<>();
        s.add("sdfsdf");
        System.out.println(s.size());
        Testing ts = new Testing();
        ts.doo(s, "Dfgdf", 1, 1);
        System.out.println(s.size());
        HelpCommand hp = new HelpCommand();
        HashSet<Organization> e = null;
        hp.doo(e, "fdd", 1, 1);
    }
    public static boolean doo(HashSet< String > mySet, String s, int isCsv, int isPrint) {
        mySet.clear();
        return true;
    }
}
