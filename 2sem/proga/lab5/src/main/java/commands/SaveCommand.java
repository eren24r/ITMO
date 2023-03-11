package commands;

import collEdit.Sort;
import com.diogonunes.jcolor.Attribute;
import dataParseIng.CsvWriter;
import dataParseIng.DataParse;
import dataParseIng.JsonWriter;
import statics.Static;
import сlasses.Organization;

import java.util.HashMap;
import java.util.HashSet;

public class SaveCommand implements Command{
    DataParse dt = new DataParse();
    CsvWriter csvWr = new CsvWriter();
    JsonWriter jsnWr = new JsonWriter();
    Sort sr = new Sort();
    private String name = "save";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        if(Static.isCsv == 1) {
            dt.dataEraserCsv(Static.fileName);
            HashMap<Integer, Organization> al = sr.sort(mySet);
            for (Organization o : al.values()) {
                csvWr.csvWriter(o.toStringCSV(), Static.fileName);
            }
            Static.txt("Коллекция успешно сохранено!",Attribute.BOLD());
            return true;
        }
        if(Static.isCsv == 0) {
            dt.dataEraserJson(Static.fileName);
            HashMap<Integer, Organization> al = sr.sort(mySet);
            for (Organization o : al.values()) {
                jsnWr.jsonWriter(o.toStringJson(), Static.fileName);
            }
            Static.txt("Коллекция успешно сохранено!",Attribute.BOLD());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String des() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
