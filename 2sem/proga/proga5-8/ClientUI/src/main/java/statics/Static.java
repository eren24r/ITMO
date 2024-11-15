package statics;

import clientMng.ClientMngUI;
import commands.Command;
import сlasses.OrganizationType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
public class Static {
    public static OrganizationType orTp = OrganizationType.PUBLIC;
    public static HashSet<String> execute = new HashSet<>();
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String fileName = "Datas/";
    public static String cmdFileName = "Commands/cmds";
    public static String logFileName = "Logs/log";
    public static int isPrint = 1;
    public static String user = null;

    public static Map<String, Command> listOfCommand;


    public static Map<String, Command> listOfNewCommand;

    static {
        listOfNewCommand = new LinkedHashMap<>();
    }

    public static void txt(String s){
        if(Static.isPrint == 1) {
            System.out.println(s);
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