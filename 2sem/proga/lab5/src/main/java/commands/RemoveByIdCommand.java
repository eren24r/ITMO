package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class RemoveByIdCommand implements Command{

    private String name = "remove_by_id";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        String[] xY = s.split(" ");
        Object bb = null;
        boolean b = false;
        boolean isPr = false;
        if(Static.isPrint == 0){
            isPr = true;
            Static.isPrint = 1;
        }else {
            isPr = false;
        }
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
                if(isPr){
                    Static.isPrint = 0;
                }
                Static.txt("Объект удалено!", Attribute.BOLD());
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
        return "remove_by_id id: удалить элемент из коллекции по его id";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
