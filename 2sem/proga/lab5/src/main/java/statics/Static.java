package statics;

import commands.*;
import сlasses.OrganizationType;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class Static {
    public static OrganizationType orTp = OrganizationType.PUBLIC;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static String fileName = "Datas/";

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
    }


    public static void txt(String s, int isPrint){
        System.out.println(s);
    }

    public static void nl(){
        System.out.println();
    }
}