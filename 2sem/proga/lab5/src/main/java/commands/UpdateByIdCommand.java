package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;
import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;

public class UpdateByIdCommand implements Command {

    private String name = "update";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s, int isCsv, int isPrint) {
        Scanner scr = new Scanner(System.in);
        String[] xY = s.split(" ");
        boolean b = false;
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
                    Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()), isPrint);
                }
            }
            if(b == true){
                Static.txt(colorize("Объект изменено!", Attribute.GREEN_TEXT(), Attribute.BOLD()), isPrint);
            }
        } catch (Exception e) {
            Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()), isPrint);
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
