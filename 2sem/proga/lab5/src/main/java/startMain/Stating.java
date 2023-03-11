package startMain;

import com.diogonunes.jcolor.Attribute;
import statics.Static;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Stating {
    public boolean saveDate(){
        try (PrintWriter writter = new PrintWriter(new BufferedWriter(new FileWriter(Static.logFileName, true)))) {
            writter.write("\n" + LocalDateTime.now().format(Static.dateTimeFormatter).toString() + "\n\n");
            return true;
        } catch (IOException e) {
            Static.txt("Ошибка в файле или неправильный путь!", Attribute.RED_TEXT());
            return false;
        }
    }
}
