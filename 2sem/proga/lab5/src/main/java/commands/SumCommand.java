package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class SumCommand implements Command{
    private String name = "sum_of_annual_turnover";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        double ss = 0;
        for(Organization o: mySet){
            ss = ss + o.getAnnualTurnover();
        }
        Static.txt(("sum of annual turnover: " + ss), Attribute.NONE());
        return true;
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
