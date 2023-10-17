package commands;

import com.diogonunes.jcolor.Attribute;
import objectResAns.ObjectResAns;
import org.gradle.internal.file.Stat;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class ShowInfoCommand implements Command{
    private String name = "show";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        StringBuilder allRes = new StringBuilder();
        if(Static.isCsv == 1){
            mySet.stream().map(p -> p.toStringCSV() + "\n").forEach(allRes::append);
        }else{
            mySet.stream().map(p -> p.toStringCSV() + "\n").forEach(allRes::append);
        }
        return new ObjectResAns(Static.txt(allRes.toString()), true, user);
    }

    @Override
    public String des() {
        return "show : все элементы коллекции в строковом представлении";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
