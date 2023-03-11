package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class InfoCommands implements Command{
    private String name = "info";

    @Override
    public String des() {
        return "info : информация о коллекции";
    }

    @Override
    public boolean doo(HashSet<Organization> set, String s) {
        Static.txt("Класс:    Organization");
        Static.txt("id - identification number");
        Static.txt("name - Название Огранизации");
        Static.txt("coordinates - кординаты огранизации");
        Static.txt("creationDate - дата создания");
        Static.txt("annualTurnover - годовой оборот");
        Static.txt("type - тип организации");
        Static.txt("postalAddress - адрес");

        Static.txt(colorize("Количество элементов Колекции: " + set.size(), Attribute.GREEN_TEXT()));
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
