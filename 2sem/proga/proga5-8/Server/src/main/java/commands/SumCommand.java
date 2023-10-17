package commands;

import com.diogonunes.jcolor.Attribute;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class SumCommand implements Command{
    private String name = "sum_of_annual_turnover";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        double ss = 0;
        if(mySet.size() > 0) {
            ss = mySet.stream().mapToDouble(Organization::getAnnualTurnover).sum();
            return new ObjectResAns(Static.txt(ss + "\n"), true, user);
        }else{
            return new ObjectResAns(Static.txt("0\n"), true, user);
        }

    }

    @Override
    public String des() {
        return "sum_of_annual_turnover : сумма значений поля annualTurnover для всех элементов коллекции";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
