package dataParseIng;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    public boolean csvWriter(String s, String fileName) {
        String tmp = "";
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName, true))) {
            writter.write(s + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка в файле или неправильный путь!");
            return false;
        }
        return true;
    }
}
