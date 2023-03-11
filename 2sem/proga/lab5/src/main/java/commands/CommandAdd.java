package commands;

import com.diogonunes.jcolor.Attribute;
import statics.Static;
import сlasses.Organization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class CommandAdd implements Command{
    private String name = "add_command";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        String[] dt = s.split(" ");
        if(dt.length > 1) {
            Static.listOfNewCommand.put(new NewCommand(dt[1]).getName(), new NewCommand(dt[1]));
            Static.txt("Команда " + dt[1] + " Добавлена!", Attribute.NONE());

            try (BufferedWriter writter = new BufferedWriter(new FileWriter(Static.cmdFileName, true))) {
                writter.write(dt[1] + "\n");
            } catch (IOException e) {
                Static.txt("Ошибка в файле или неправильный путь!", Attribute.NONE());
                return false;
            }

            return true;
        }else {
            Static.txt("Название Команды Некорректно!", Attribute.NONE());
            return false;
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
