package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

public class InfoCommands implements Command{
    private String name = "info";

    @Override
    public String des() {
        return "info : информация о коллекции";
    }

    @Override
    public boolean doo(HashSet<Organization> set, String s) {
        Static.txt("Класс:    Organization", Attribute.NONE());
        Static.txt("id - identification number", Attribute.NONE());
        Static.txt("name - Название Огранизации", Attribute.NONE());
        Static.txt("coordinates - кординаты огранизации", Attribute.NONE());
        Static.txt("creationDate - дата создания", Attribute.NONE());
        Static.txt("annualTurnover - годовой оборот", Attribute.NONE());
        Static.txt("type - тип организации", Attribute.NONE());
        Static.txt("postalAddress - адрес", Attribute.NONE());

        Static.txt("Количество элементов Колекции: " + set.size(), Attribute.GREEN_TEXT());
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
