package statics;

import commands.*;
import dataParseIng.CsvJson;
import сlasses.OrganizationType;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class Static {
    public static OrganizationType orTp = OrganizationType.PUBLIC;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static String fileName = "Datas/";
    public static int isPrint = 1;
    public static CsvJson csvJson;
    public static int isCsv = csvJson.getIsCsv();

    public static Map<String, Command> listOfCommand;

    static
    {
        listOfCommand = new LinkedHashMap<>();
        listOfCommand.put(new HelpCommand().getName(), new HelpCommand());
        listOfCommand.put(new InfoCommands().getName(), new InfoCommands());
        listOfCommand.put(new ShowInfoCommand().getName(), new ShowInfoCommand());
        listOfCommand.put(new ClearCommand().getName(), new ClearCommand());
        listOfCommand.put(new SaveCommand().getName(), new SaveCommand());
        listOfCommand.put(new SumCommand().getName(), new SumCommand());
        listOfCommand.put(new AverageCommand().getName(), new AverageCommand());
        listOfCommand.put(new PrintDescendingCommand().getName(), new PrintDescendingCommand());
        listOfCommand.put(new CngSertpJSON().getName(), new CngSertpJSON());
        listOfCommand.put(new CngSertpCSV().getName(), new CngSertpCSV());
        listOfCommand.put(new RemoveByIdCommand().getName(), new RemoveByIdCommand());
        listOfCommand.put(new UpdateByIdCommand().getName(), new UpdateByIdCommand());
        listOfCommand.put(new OrganizationAddCommand().getName(), new OrganizationAddCommand());
        listOfCommand.put(new SerializationType().getName(), new SerializationType());
        listOfCommand.put(new ExecuteScriptCommand().getName(), new ExecuteScriptCommand());
    }


    public static void txt(String s){
        System.out.println(s);
    }

    public static void nl(){
        System.out.println();
    }
}