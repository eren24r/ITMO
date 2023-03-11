package commands;

import dataParseIng.CsvJson;
import dataParseIng.ParseIng;
import сlasses.Organization;

import java.io.IOException;
import java.util.HashSet;

public class CngSertpCSV implements Command{

    private String name = "change_serialization_type_CSV";
    @Override
    public boolean doo(HashSet<Organization> mySet, String s, int isCsv, int isPrint) {
        if(isCsv != 1){
            isCsv = 1;
            ParseIng parseCol = new ParseIng();
            CsvJson csvJson = new CsvJson();
            SaveCommand svCmd = new SaveCommand();
            try {
                HashSet<Organization> tmp = parseCol.getOrganizationFromJson();
                svCmd.doo(tmp, s, isCsv, isPrint);
                csvJson.saveIsCsv(isCsv);
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
        return "change_serialization_type_CSV - Convert Serialization Type to CSV";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
