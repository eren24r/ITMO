package commands;

import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ExecuteScriptCommand implements Command {

    private String name = "execute_script";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        String[] xY = s.split(" ");
        boolean b = false;
        boolean isScript = true;
        String allRes = "execute_script ";
        if (xY[1].length()!=0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(("Scripts/" + xY[1])))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.split(" ").length >= 2 && line.split(" ")[0].equals("execute_script") && line.split(" ")[1].equals(xY[1])) {
                        allRes = allRes + "\"Рекурсия!\",";
                    } else if(line.split(" ").length >= 2 && line.split(" ")[0].equals("execute_script")){
                        if(!Static.execute.contains(line.split(" ")[1])) {
                            ExecuteScriptCommand ex = new ExecuteScriptCommand();
                            ObjectResAns resOb = null;
                            if((resOb = ex.doo(null, line, user)).isResAns() == true) {
                                allRes = allRes + resOb.getResTesxt();
                            }
                        }else{
                            Static.execute.add(line.split(" ")[1]);
                            allRes = allRes + "\"Рекурсия!\",";
                        }
                    }
                    else if (line.length() != 0) {
                        try {
                            allRes = allRes + "\"" + line + "\",";
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
                return new ObjectResAns(allRes, true, Static.user);
            } catch (IOException e) {
                System.out.println("Ошибка в файле или неправильный путь!");
                return new ObjectResAns("", false, Static.user);
            }
        }else{
            System.out.println("Ошибка формата!");
            return new ObjectResAns("", false, Static.user);
        }
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
