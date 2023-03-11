package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class AverageCommand implements Command{

    private String name = "average_of_annual_turnover";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        double av = 0;
        if(mySet.size() > 0) {
            for(Organization o: mySet){
                av = av + o.getAnnualTurnover();
            }
            av = av / mySet.size();
            Static.txt(("average of annual turnover: " + av), Attribute.NONE());
            return true;
        }
        return false;
    }

    @Override
    public String des() {
        return "average_of_annual_turnover : среднее значение поля annualTurnover для всех элементов коллекции";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
