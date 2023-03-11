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
    public boolean doo(HashSet<Organization> set, String s, int isCsv, int isPrint) {
        Static.txt("Класс:    Organization", isPrint);
        Static.txt("id - identification number", isPrint);
        Static.txt("name - Название Огранизации", isPrint);
        Static.txt("coordinates - кординаты огранизации", isPrint);
        Static.txt("creationDate - дата создания", isPrint);
        Static.txt("annualTurnover - годовой оборот", isPrint);
        Static.txt("type - тип организации", isPrint);
        Static.txt("postalAddress - адрес", isPrint);

        Static.txt(colorize("Количество элементов Колекции: " + set.size(), Attribute.GREEN_TEXT()), isPrint);
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
