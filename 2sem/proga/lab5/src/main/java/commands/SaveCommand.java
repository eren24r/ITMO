package commands;

import com.diogonunes.jcolor.Attribute;
import dataParseIng.CsvWriter;
import dataParseIng.DataParse;
import dataParseIng.JsonWriter;
import statics.Static;
import сlasses.Organization;

import java.util.HashMap;
import java.util.HashSet;

import static com.diogonunes.jcolor.Ansi.colorize;

public class SaveCommand {
    DataParse dt = new DataParse();
    CsvWriter csvWr = new CsvWriter();
    JsonWriter jsnWr = new JsonWriter();
    Sort sr = new Sort();

    public boolean save(HashSet<Organization> set, int isCsv){
        if(isCsv == 1) {
            dt.dataEraserCsv(Static.fileName);
            HashMap<Integer, Organization> al = sr.sort(set);
            for (Organization o : al.values()) {
                csvWr.csvWriter(o.toStringCSV(), Static.fileName);
            }
            Static.txt(colorize("Коллекция успешно сохранено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            return true;
        }
        if(isCsv == 0) {
            dt.dataEraserJson(Static.fileName);
            HashMap<Integer, Organization> al = sr.sort(set);
            for (Organization o : al.values()) {
                jsnWr.jsonWriter(o.toStringJson(), Static.fileName);
            }
            Static.txt(colorize("Коллекция успешно сохранено!", Attribute.GREEN_TEXT(), Attribute.BOLD()));
            return true;
        }else {
            return false;
        }
    }
}
