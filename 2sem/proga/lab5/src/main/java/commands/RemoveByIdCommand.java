package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class RemoveByIdCommand implements Command{

    private String name = "remove_by_id";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s, int isCsv, int isPrint) {
        String[] xY = s.split(" ");
        Object bb = null;
        boolean b = false;
        try {
            if (Integer.parseInt(xY[1]) >= 0) {
                for(Organization o: mySet){
                    if(o.getId() == Integer.parseInt(xY[1])){
                        b = true;
                        bb = o;
                        break;
                    }
                }
            }
            if(b == true){
                mySet.remove(bb);
                Static.txt(colorize("Объект удалено!", Attribute.GREEN_TEXT(), Attribute.BOLD()), isPrint);
            }
        } catch (Exception e) {
            Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()), isPrint);
        }
        return b;
    }

    @Override
    public String des() {
        return "remove_by_id id: удалить элемент из коллекции по его id";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
