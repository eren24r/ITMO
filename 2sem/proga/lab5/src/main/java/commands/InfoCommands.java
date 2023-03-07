package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.Set;

import static com.diogonunes.jcolor.Ansi.colorize;

public class InfoCommands {
    public boolean info(Set<Organization> s){
        Static.txt("Класс:    Organization");
        Static.txt("id - identification number");
        Static.txt("name - Название Огранизации");
        Static.txt("coordinates - кординаты огранизации");
        Static.txt("creationDate - дата создания");
        Static.txt("annualTurnover - годовой оборот");
        Static.txt("type - тип организации");
        Static.txt("postalAddress - адрес");

        Static.txt(colorize("Количество элементов Колекции: " + s.size(), Attribute.GREEN_TEXT()));
        return true;
    }
}
