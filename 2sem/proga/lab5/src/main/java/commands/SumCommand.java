package commands;

import сlasses.Organization;

import java.util.HashSet;

public class SumCommand {
    public Float sum_of_annual_turnover(HashSet<Organization> set){
        Float s = 0F;
        for(Organization o: set){
            s = s + o.getAnnualTurnover();
        }
        return s;
    }

    public Float average_of_annual_turnover(HashSet<Organization> set){
        if(set.size() > 0) {
            return this.sum_of_annual_turnover(set) / set.size();
        }
        return null;
    }
}
