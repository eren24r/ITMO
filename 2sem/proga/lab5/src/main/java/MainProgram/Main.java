package MainProgram;

import Classes.Commands;
import Classes.Organization;
import Datas.ParseIng;
import com.diogonunes.jcolor.Attribute;

import java.io.*;
import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Программа
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HashSet<Organization> mySet = new HashSet<>();
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
        ParseIng.fileName = ("Datas/" + args[0]);
        try {
            mySet = ParseIng.getOrganizationFromCsv();
            System.out.println("Готова!");
        } catch (FileNotFoundException e) {
            System.out.println(colorize(("Ошибка в файле или неправильный путь!"), Attribute.RED_TEXT()));
        }
        nl();
        String outputFileName = ParseIng.fileName;
        boolean isScript = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName, true))) {
                String line;
                while (!(line = reader.readLine()).equals("exit")) {
                    Commands.commandsEditor(mySet, line, isScript);
                    if (line.equals("save")){
                        Commands.save(mySet);
                        mySet = ParseIng.getOrganizationFromCsv();
                        Commands.sizeOfSetNotSaved = 0;
                        nl();
                    }
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
    public static void nl(){
        System.out.println();
    }
}
