package dataParseIng;

import com.diogonunes.jcolor.Attribute;
import statics.Static;

import java.io.*;

public class DataParse {

    public boolean dataEraserCsv(String fileName) {
        String tmp = "";
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName))) {
            writter.write("id,name,coordinates.x,coordinates.y,creationDate,annualTurnover,type,postalAddressStreet,postalAddressZipCoder,userId" + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка в файле или неправильный путь!");
            return false;
        }
        return true;
    }

    public boolean dataEraserJson(String fileName) {
        String tmp = "";
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName))) {
            writter.write("");
        } catch (IOException e) {
            System.err.println("Ошибка в файле или неправильный путь!");
            return false;
        }
        return true;
    }

    public int getSize(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int sz = 0;
            while ((line = reader.readLine()) != null) {
                sz++;
            }
            return sz - 1;
        } catch (IOException e) {
            System.err.println("Ошибка в файле или неправильный путь!");
        }
        return 0;
    }
}
