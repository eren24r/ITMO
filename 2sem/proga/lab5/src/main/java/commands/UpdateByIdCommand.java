package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;

public class UpdateByIdCommand {
    public boolean updateById(HashSet<Organization> set, String s){
        Scanner scr = new Scanner(System.in);
        String[] xY = s.split(" ");
        boolean b = false;
        try {
            if (Integer.parseInt(xY[1]) >= 0 && xY[2].length() != 0) {
                for(Organization o: set){
                    if(o.getId() == Integer.parseInt(xY[1])){
                        b = o.updateName(xY[2]);
                        break;
                    }
                }
            }
            if(b == true){
                Static.txt(colorize("Объект изменено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            }
        } catch (Exception e) {
            Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
        }
        return b;
    }
}
