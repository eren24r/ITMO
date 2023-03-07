package commands;

import com.diogonunes.jcolor.Attribute;
import dataParseIng.ParseIng;
import statics.Static;
import сlasses.Organization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class ExecuteScriptCommand {
    ParseIng pr = new ParseIng();
    SaveCommand svCm = new SaveCommand();
    Commands cmd = new Commands();

    public boolean execute_script(HashSet<Organization> mySet, String s, int isCsv){
        String[] xY = s.split(" ");
        boolean b = false;
        boolean isScript = true;
        try {
            if (xY[1].length()!=0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(("Scripts/" + xY[1])))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        cmd.commandsEditor(mySet, line, isCsv);
                        if (line.equals("save")){
                            svCm.save(mySet, isCsv);
                            mySet = pr.getOrganizationFromCsv();
                            Static.nl();
                        }
                    }
                } catch (IOException e) {
                    Static.txt(colorize("Ошибка в файле или неправильный путь!", Attribute.RED_TEXT()));
                    b = false;
                    return b;
                }
            }
        } catch (Exception e) {
            Static.txt(colorize("Ошибка формата!", Attribute.RED_TEXT()));
            b = false;
            return b;
        }
        return b;
    }
}
