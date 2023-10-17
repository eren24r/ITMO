package objectResAns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvReader {

    public ObservableList<Organization> getOrganizationFromCsv(String lines) throws IOException {
        ObservableList<Organization> all = FXCollections.observableArrayList();
        BufferedReader reader = null;
        reader = new BufferedReader(new StringReader(lines));
        String line;
        while (((line = reader.readLine()) != null)) {
            Organization tmp = csvReader(line);
            all.addAll(tmp);
        }
        return all;
    }

    public HashSet<Organization> getOrganizationFromCsvToOb(String lines) throws IOException {
        HashSet<Organization> all = new HashSet<>();
        BufferedReader reader = null;
        reader = new BufferedReader(new StringReader(lines));
        String line;
        while (((line = reader.readLine()) != null)) {
            Organization tmp = csvReader(line);
            all.add(tmp);
        }
        return all;
    }

    public Organization csvReader(String s){
        OrganizationType ortp = OrganizationType.PUBLIC;
        Pattern pt = Pattern.compile("\"([^\"]*)\"");
        Matcher mt = pt.matcher(s);
        List<String> dt = new ArrayList<>();
        while(mt.find()){
            dt.add(mt.group(1));
        }

        /*String[] dt = s.split("\",");
        int id = Integer.parseInt(dt[0]);
        LocalDateTime time = LocalDateTime.parse(dt[4], Static.dateTimeFormatter);
        Coordinates cr = new Coordinates(Long.parseLong(dt[2]), Float.parseFloat(dt[3]));
        OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt[6]));
        Address ad = new Address(dt[7], dt[8]);*/

        int id = Integer.parseInt(dt.get(0));
        LocalDateTime time = LocalDateTime.parse(dt.get(4), Static.dateTimeFormatter);
        Long x = (long) Float.parseFloat(dt.get(2));
        float y = Float.parseFloat(dt.get(3));
        Coordinates cr = new Coordinates(x, y);
        /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
        OrganizationType o_type = ortp.getTypeByName(dt.get(6));
        Address ad = new Address(dt.get(7), dt.get(8));

        Organization data = new Organization(id, dt.get(1), cr, time, Float.parseFloat(dt.get(5)), o_type, ad, Integer.parseInt(dt.get(9)));
        return data;
    }
}
