package mainProgram;

import com.diogonunes.jcolor.Attribute;
import commands.*;
import commandsEditor.CommandNew;
import dataParseIng.CsvJson;
import dataParseIng.ParseIng;
import startMain.Stating;
import statics.Static;
import сlasses.Organization;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Программа
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Command> listCommand = new LinkedHashMap<String, Command>();
        listCommand.put(new InfoCommands().getName(), new InfoCommands());

        HashSet<Organization> mySet = new HashSet<>();
        ParseIng parseCol = new ParseIng();
        Commands cmd = new Commands();
        SaveCommand svCmd = new SaveCommand();
        ExecuteScriptCommand exSrCmd = new ExecuteScriptCommand();
        CsvJson csvJson = new CsvJson();

        CommandNew cmdEd = new CommandNew();
        cmdEd.newCommadsReader();

        Stating st = new Stating();
        st.saveDate();

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
        String outputFileName = Static.fileName;
        boolean isScript = false;

        if(Static.isCsv == 1) {
            try {
                mySet = parseCol.getOrganizationFromCsv();
                Static.txt("Готова!", Attribute.GREEN_TEXT());
            } catch (FileNotFoundException e) {
                Static.txt("Ошибка в файле или неправильный путь!", Attribute.RED_TEXT());
            }
        }else if(Static.isCsv == 0){
            try {
                mySet = parseCol.getOrganizationFromJson();
                Static.txt("Готова!", Attribute.GREEN_TEXT());
            } catch (FileNotFoundException e) {
                Static.txt("Ошибка в файле или неправильный путь!", Attribute.RED_TEXT());
            }
        }
        System.out.print(colorize("> ", Attribute.BOLD(), Attribute.BLUE_TEXT()));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName, true))) {
                String line;
                while (!(line = reader.readLine()).equals("exit")) {
                    cmd.commandsEditor(mySet, line);
                    if (line.equals("save")){
                        if(Static.isCsv == 1) {
                            mySet = parseCol.getOrganizationFromCsv();
                        }
                        if(Static.isCsv == 0){
                            mySet = parseCol.getOrganizationFromJson();
                        }
                    }

                    System.out.print(colorize("> ", Attribute.BOLD(), Attribute.BLUE_TEXT()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}