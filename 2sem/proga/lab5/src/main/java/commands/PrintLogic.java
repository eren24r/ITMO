package commands;

import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class PrintLogic implements Command{
    private String name = "print_logic_type";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        if(Static.isPrint == 1) {
            System.out.println("Console");
        }
        if(Static.isPrint == 0){
            System.out.println("Log File");
        }
        return true;
    }

    @Override
    public String des() {
        return "print_logic_type: тип вывода!";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
