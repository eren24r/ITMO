package statics;

import com.diogonunes.jcolor.Attribute;
import commands.*;
import dataParseIng.CsvJson;
import сlasses.OrganizationType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.diogonunes.jcolor.Ansi.colorize;

public class Static {
    public static OrganizationType orTp = OrganizationType.PUBLIC;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static String fileName = "Datas/";
    public static String cmdFileName = "Commands/cmds";
    public static String logFileName = "Logs/log";
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
        listOfCommand.put(new CommandAdd().getName(), new CommandAdd());
        listOfCommand.put(new PrintLogic().getName(), new PrintLogic());
        listOfCommand.put(new ChgLogToConsole().getName(), new ChgLogToConsole());
        listOfCommand.put(new ChgLogToFile().getName(), new ChgLogToFile());
    }

    public static Map<String, Command> listOfNewCommand;

    static {
        listOfNewCommand = new LinkedHashMap<>();
    }

    public static void txt(String s, Attribute art){
        if(Static.isPrint == 1) {
            System.out.println(colorize(s, art));
        }else{
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(Static.logFileName, true))) {
                PrintWriter printWritter = new PrintWriter(writter);
                printWritter.println(s);
            } catch (Exception e) {
                System.out.println(("Ошибка log файла или неправильный путь!"));
            }
        }
    }

    public static void nl(){
        System.out.println();
    }

}