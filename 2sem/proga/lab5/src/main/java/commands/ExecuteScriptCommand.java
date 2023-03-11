package commands;

import com.diogonunes.jcolor.Attribute;
import dataParseIng.ParseIng;
import statics.Static;
import сlasses.Organization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ExecuteScriptCommand implements Command {

    private String name = "execute_script";
    ParseIng parseCol = new ParseIng();
    Commands cmd = new Commands();

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        String[] xY = s.split(" ");
        boolean b = false;
        boolean isScript = true;
        try {
            if (xY[1].length()!=0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(("Scripts/" + xY[1])))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        cmd.commandsEditor(mySet, line);
                        if (line.equals("save")){
                            if(Static.isCsv == 1) {
                                mySet = parseCol.getOrganizationFromCsv();
                            }
                            if(Static.isCsv == 0){
                                mySet = parseCol.getOrganizationFromJson();
                            }
                        }
                    }
                    b = true;
                } catch (IOException e) {
                    Static.txt("Ошибка в файле или неправильный путь!", Attribute.RED_TEXT());
                    b = false;
                }
                return b;
            }b = false;
        } catch (Exception e) {
            Static.txt("Ошибка формата!", Attribute.RED_TEXT());

            return b;
        }
        return b;
    }

    @Override
    public String des() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
