package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class RemoveByIdCommand {
    public boolean remove_by_id(HashSet<Organization> set, String s){
        String[] xY = s.split(" ");
        Object bb = null;
        boolean b = false;
        try {
            if (Integer.parseInt(xY[1]) >= 0) {
                for(Organization o: set){
                    if(o.getId() == Integer.parseInt(xY[1])){
                        b = true;
                        bb = o;
                        break;
                    }
                }
            }
            if(b == true){
                set.remove(bb);
                Static.txt(colorize("Объект удалено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            }
        } catch (Exception e) {
            Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
        }
        return b;
    }
}
