package commands;

import objectResAns.ObjectResAns;
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
    public ObjectResAns doo(HashSet<Organization> set, String s, String user) {
        String allRes = "";
        allRes = allRes + "Класс:    Organization\n" +
                "id - identification number\n" +
                "name - Название Огранизации\n" +
                "coordinates - кординаты огранизации\n" +
                "creationDate - дата создания\n" +
                "annualTurnover - годовой оборот\n" +
                "type - тип организации\n" +
                "postalAddress - адрес\n\n" +
                "Количество элементов Колекции: " + set.size() + "\n";

        return new ObjectResAns(Static.txt(set.size() + "\n"), true, user);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
