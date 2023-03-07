package mainProgram;

import com.diogonunes.jcolor.Attribute;
import commands.Commands;
import commands.ExecuteScriptCommand;
import commands.SaveCommand;
import dataParseIng.CsvJson;
import dataParseIng.ParseIng;
import statics.Static;
import сlasses.Organization;

import java.io.*;
import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Программа
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HashSet<Organization> mySet = new HashSet<>();
        ParseIng parseCol = new ParseIng();
        Commands cmd = new Commands();
        SaveCommand svCmd = new SaveCommand();
        ExecuteScriptCommand exSrCmd = new ExecuteScriptCommand();
        CsvJson csvJson = new CsvJson();
        /*Scanner scr = new Scanner(System.in);
        while (mySet.isEmpty())  {
            System.out.println("Укажите название файла! (data.csv)");
            ParseIng.fileName = ("Datas/" + scr.nextLine());
            try {
                mySet = ParseIng.getOrganizationFromCsv();
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка в файле или неправильный путь!");
            }
        }*/
        Static.fileName = ("Datas/" + args[0]);
        int isCsv = csvJson.getIsCsv();
        String outputFileName = Static.fileName;
        boolean isScript = false;

        if(isCsv == 1) {
            try {
                mySet = parseCol.getOrganizationFromCsv();
                System.out.println("Готова!");
            } catch (FileNotFoundException e) {
                System.out.println(colorize(("Ошибка в файле или неправильный путь!"), Attribute.RED_TEXT()));
            }
        }else if(isCsv == 0){
            try {
                mySet = parseCol.getOrganizationFromJson();
                System.out.println("Готова!");
            } catch (FileNotFoundException e) {
                System.out.println(colorize(("Ошибка в файле или неправильный путь!"), Attribute.RED_TEXT()));
            }
        }
        Static.nl();
        System.out.print(colorize("> ", Attribute.BOLD(), Attribute.BLUE_TEXT()));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName, true))) {
                String line;
                while (!(line = reader.readLine()).equals("exit")) {
                    cmd.commandsEditor(mySet, line, isCsv);
                    if (line.equals("save")){
                        svCmd.save(mySet, isCsv);
                        if(isCsv == 1) {
                            mySet = parseCol.getOrganizationFromCsv();
                        }
                        if(isCsv == 0){
                            mySet = parseCol.getOrganizationFromJson();
                        }
                        Static.nl();
                    }
                    if(!isScript && line.contains("execute_script")){
                        exSrCmd.execute_script(mySet, line, isCsv);
                        Static.nl();
                    }
                    if(isCsv != 0 && line.equals("change_serialization_type JSON")){
                        isCsv = 0;
                        HashSet<Organization> tmp = parseCol.getOrganizationFromCsv();
                        svCmd.save(tmp, isCsv);
                        csvJson.saveIsCsv(isCsv);
                    }
                    if(isCsv != 1 && line.equals("change_serialization_type CSV")){
                        isCsv = 1;
                        HashSet<Organization> tmp = parseCol.getOrganizationFromJson();
                        svCmd.save(tmp, isCsv);
                        csvJson.saveIsCsv(isCsv);
                    }
                    System.out.print(colorize("> ", Attribute.BOLD(), Attribute.BLUE_TEXT()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Organization s = new Organization("s", new Coordinates(14L,1), 45F, OrganizationType.PUBLIC, new Address("Lomo", "5858"));
        ParseIng.csvWriter(s.toStringCSV());
        Organization a = new Organization("a", new Coordinates(45L, 23), 4657F, OrganizationType.TRUST, new Address("Kron", "35435435"));
        ParseIng.csvWriter(a.toStringCSV());*/

        /*for (Organization o : mySet) {
            System.out.println(o.toStringCSV());
        }*/

    }

}