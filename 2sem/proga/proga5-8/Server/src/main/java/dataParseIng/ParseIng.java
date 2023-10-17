package dataParseIng;

import statics.Static;
import сlasses.Organization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Парсинг
 */
public class ParseIng {
    CsvReader csvRd = new CsvReader();
    JsonReader jsnRd = new JsonReader();

    public HashSet<Organization> getOrganizationFromCsv() throws IOException {
        HashSet<Organization> all = new HashSet<>();
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Static.fileName));
        String line;
        int sz = 0;
        while (((line = reader.readLine()) != null)) {
            if(sz != 0) {
                Organization tmp = csvRd.csvReader(line);
                all.add(tmp);
            }
            sz = sz + 1;
        }
        return all;
    }

    public HashSet<Organization> getOrganizationFromJson() throws IOException {
        HashSet<Organization> all = new HashSet<>();
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Static.fileName));
        String line;
        while (((line = reader.readLine()) != null)) {
            Organization tmp = jsnRd.jsonReader(line);
            all.add(tmp);
        }
        return all;
    }
}
