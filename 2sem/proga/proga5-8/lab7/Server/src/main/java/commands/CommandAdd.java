package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class CommandAdd implements Command{
    private String name = "add_command";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        String[] dt = s.split(" ");
        String allRes = "";
        if(dt.length > 1) {
            Static.listOfNewCommand.put(new NewCommand(dt[1]).getName(), new NewCommand(dt[1]));
            allRes = allRes + "Команда " + dt[1] + " Добавлена!" + "\n";

            try (BufferedWriter writter = new BufferedWriter(new FileWriter(Static.cmdFileName, true))) {
                writter.write(dt[1] + "\n");
            } catch (IOException e) {
                allRes = allRes + "Ошибка в файле или неправильный путь!" + "\n";
                return new ObjectResAns(Static.txt(allRes), false, user);
            }
            return new ObjectResAns(Static.txt(allRes), true, user);
        }else {
            allRes = allRes + "Название Команды Некорректно!\n";
            return new ObjectResAns(Static.txt(allRes), false, user);
        }
    }

    @Override
    public String des() {
        return "add_command name: добавить команду";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
