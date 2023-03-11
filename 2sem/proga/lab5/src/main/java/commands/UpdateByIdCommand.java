package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;
import java.util.Scanner;

public class UpdateByIdCommand implements Command {

    private String name = "update";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        Scanner scr = new Scanner(System.in);
        String[] xY = s.split(" ");
        boolean b = false;
        boolean isPr = false;
        if(Static.isPrint == 0){
            isPr = true;
            Static.isPrint = 1;
        }else {
            isPr = false;
        }
        try {
            if (Integer.parseInt(xY[1]) >= 0 && xY[2].length() != 0) {
                if(xY[2].indexOf('"') < 0){
                    for (Organization o : mySet) {
                        if (o.getId() == Integer.parseInt(xY[1])) {
                            b = o.updateName(xY[2]);
                            break;
                        }
                    }
                }else{
                    Static.txt("Ошибка формата!", Attribute.RED_TEXT());
                    if(isPr){
                        Static.isPrint = 0;
                    }
                }
            }
            if(b == true){
                if(isPr){
                    Static.isPrint = 0;
                }
                Static.txt("Объект изменено!", Attribute.BOLD());
            }
            if(b==false){
                if(isPr){
                    Static.isPrint = 0;
                }
            }
        } catch (Exception e) {
            Static.txt("Ошибка формата!", Attribute.RED_TEXT());
            if(isPr){
                Static.isPrint = 0;
            }
        }
        return b;
    }

    @Override
    public String des() {
        return "update id new_element : обновить значение элемента коллекции";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
