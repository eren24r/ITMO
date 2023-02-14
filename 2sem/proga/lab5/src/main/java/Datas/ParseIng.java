package Datas;

import Classes.*;
import java.io.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

/**
 * Парсинг
 */
public class ParseIng {
    public static String fileName = "Datas/data.csv";
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void csvWriter(String s) {
        String tmp = "";
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName, true))) {
            writter.write(s + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка в файле или неправильный путь!");
        }
    }

    public static void dataEraser() {
        String tmp = "";
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName))) {
            writter.write("id,name,coordinates.x,coordinates.y,creationDate,annualTurnover,type,postalAddressStreet,postalAddressZipCoder" + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка в файле или неправильный путь!");
        }
    }

    public static int getSize() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int sz = 0;
            while ((line = reader.readLine()) != null) {
                sz++;
            }
            return sz - 1;
        } catch (IOException e) {
            System.out.println("Ошибка в файле или неправильный путь!");
        }
        return 0;
    }

    public static HashSet<Organization> getOrganizationFromCsv() throws IOException {
        HashSet<Organization> all = new HashSet<>();
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(fileName));
        String line;
        int sz = 0;
        while (((line = reader.readLine()) != null)) {
            if(sz != 0) {
                Organization tmp = csvReader(line);
                all.add(tmp);
            }
            sz = sz + 1;
        }
        return all;
    }

    public static Organization csvReader(String s){
        String[] dt = s.split(",");
        int id = Integer.parseInt(dt[0]);
        LocalDateTime time = LocalDateTime.parse(dt[4], dateTimeFormatter);
        Coordinates cr = new Coordinates(Long.parseLong(dt[2]), Float.parseFloat(dt[3]));
        OrganizationType o_type = OrganizationType.getTypeById(Integer.parseInt(dt[6]));
        Address ad = new Address(dt[7], dt[8]);
        Organization data = new Organization(id, dt[1], cr, time, Float.parseFloat(dt[5]), o_type, ad);
        return data;
    }
}
