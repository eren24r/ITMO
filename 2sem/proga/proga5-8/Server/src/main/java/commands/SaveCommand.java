package commands;

import collEdit.Sort;
import com.diogonunes.jcolor.Attribute;
import dataParseIng.CsvWriter;
import dataParseIng.DataParse;
import dataParseIng.JsonWriter;
import objectResAns.ObjectResAns;
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
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        if(Static.isCsv == 1) {
            dt.dataEraserCsv(Static.fileName);
            HashMap<Integer, Organization> al = sr.sort(mySet);
            for (Organization o : al.values()) {
                csvWr.csvWriter(o.toStringCSV(), Static.fileName);
            }
            return new ObjectResAns(Static.txt("Коллекция успешно сохранено!\n"), true, user);
        }else{
            dt.dataEraserJson(Static.fileName);
            HashMap<Integer, Organization> al = sr.sort(mySet);
            for (Organization o : al.values()) {
                jsnWr.jsonWriter(o.toStringJson(), Static.fileName);
            }
            return new ObjectResAns(Static.txt("Коллекция успешно сохранено!\n"), true, user);
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
