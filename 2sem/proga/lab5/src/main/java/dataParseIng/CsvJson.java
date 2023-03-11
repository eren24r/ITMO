package dataParseIng;

import com.diogonunes.jcolor.Attribute;
import statics.Static;

import java.io.*;

public class CsvJson {
    public static int getIsCsv(){
        int sz;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(".tmp/files/tmp.txt"));
            String line;
            line = reader.readLine();
            sz = Integer.parseInt(line.split(" ")[0]);
            return sz;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveIsCsv(int isCsv){
        DataParse dp = new DataParse();
        dp.dataEraserJson(".tmp/files/tmp.txt");
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(".tmp/files/tmp.txt"))) {
            writter.write(String.valueOf(isCsv));
        } catch (IOException e) {
            Static.txt("Ошибка!", Attribute.RED_TEXT());
            return false;
        }
        return true;
    }
}
