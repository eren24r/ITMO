package commands;

import dataParseIng.CsvJson;
import dataParseIng.DataParse;
import dataParseIng.JsonWriter;
import objectResAns.ObjectResAns;
import org.gradle.internal.impldep.com.google.api.client.json.Json;
import statics.Static;
import сlasses.Organization;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashSet;

public class CngSertpJSON implements Command{

    private String name = "change_serialization_type_JSON";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        if(Static.isCsv != 0){
            Static.isCsv = 0;
            try {
                CsvJson csvJson = new CsvJson();
                csvJson.saveIsCsv(Static.isCsv);
                return new ObjectResAns(Static.txt("Converted Serialization Type to JSON!\n"), true, user);
            } catch (Exception e) {
                return new ObjectResAns(Static.txt("Error!\n"), false, user);
            }
        }else {
            return new ObjectResAns(Static.txt("Serialization Type is JSON!\n"), true, user);
        }
    }

    @Override
    public String des() {
        return "change_serialization_type_JSON : Convert Serialization Type to JSON";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
