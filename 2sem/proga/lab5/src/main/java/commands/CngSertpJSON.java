package commands;

import dataParseIng.CsvJson;
import dataParseIng.ParseIng;
import statics.Static;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

public class CngSertpJSON implements Command{

    private String name = "change_serialization_type_JSON";

    @Override
    public boolean doo(HashSet<Organization> mySet, String s) {
        if(Static.isCsv != 0){
            Static.isCsv = 0;
            ParseIng parseCol = new ParseIng();
            SaveCommand svCmd = new SaveCommand();
            try {
                HashSet<Organization> tmp = parseCol.getOrganizationFromCsv();
                svCmd.doo(tmp, s);
                CsvJson csvJson = new CsvJson();
                csvJson.saveIsCsv(Static.isCsv);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            return false;
        }
    }

    @Override
    public String des() {
        return "change_serialization_type_JSON : Convert Serialization Type to Json";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
