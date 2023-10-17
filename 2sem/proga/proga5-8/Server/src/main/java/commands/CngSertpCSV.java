package commands;

import dataParseIng.CsvJson;
import dataParseIng.CsvWriter;
import dataParseIng.DataParse;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

public class CngSertpCSV implements Command{

    private String name = "change_serialization_type_CSV";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        if(Static.isCsv != 1){
            Static.isCsv = 1;
            CsvJson csvJson = new CsvJson();
            try {
                csvJson.saveIsCsv(Static.isCsv);
                return new ObjectResAns(Static.txt("Converted Serialization Type to CSV!\n"), true, user);
            } catch (Exception e) {
                return new ObjectResAns(Static.txt("Error!\n"), false, user);
            }
        }else {
            return new ObjectResAns(Static.txt("Serialization Type is CSV!\n"), true, user);
        }
    }

    @Override
    public String des() {
        return "change_serialization_type_CSV : Convert Serialization Type to CSV";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
