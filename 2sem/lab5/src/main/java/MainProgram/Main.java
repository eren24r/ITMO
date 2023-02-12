package MainProgram;
import Classes.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import Datas.*;

import javax.annotation.processing.FilerException;

public class Main {
    public static void main(String[] args) throws IOException {
        HashSet<Organization> mySet = new HashSet<>();
        Scanner scr = new Scanner(System.in);
        while (mySet.isEmpty()) {
            System.out.println("Укажите название файла! (data.csv)");
            ParseIng.fileName = ("Datas/" + scr.nextLine());
            try {
                mySet = ParseIng.getOrganizationFromCsv();
            }catch (FileNotFoundException e) {
                System.out.println("Ошибка в файле или неправильный путь!");
            }
        }
        System.out.println("Готова!"); nl();
        String outputFileName = ParseIng.fileName;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName, true))) {
                String line;
                while (!(line = reader.readLine()).equals("exit")) {
                    System.out.println(line);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /*Organization s = new Organization("s", new Coordinates(14L,1), 45F, OrganizationType.PUBLIC, new Address("Lomo", "5858"));
        ParseIng.csvWriter(s.toStringCSV());
        Organization a = new Organization("a", new Coordinates(45L, 23), 4657F, OrganizationType.TRUST, new Address("Kron", "35435435"));
        ParseIng.csvWriter(a.toStringCSV());*/

        for(Organization o: mySet){
            System.out.println(o.getCreationDate());
        }
    }


    public static void nl(){
        System.out.println();
    }
}
