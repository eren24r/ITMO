package dataParseIng;

import statics.Static;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter{
    public boolean jsonWriter(String s, String fileName) {
        String tmp = "";
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName, true))) {
            writter.write(s + "\n");
        } catch (IOException e) {
            Static.txt("Ошибка в файле или неправильный путь!");
            return false;
        }
        return true;
    }
}
