package dataParseIng;

import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvReader {
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
        Coordinates cr = new Coordinates(Long.parseLong(dt.get(2)), Float.parseFloat(dt.get(3)));
        /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
        OrganizationType o_type = ortp.getTypeByName(dt.get(6));
        Address ad = new Address(dt.get(7), dt.get(8));

        Organization data = new Organization(id, dt.get(1), cr, time, Float.parseFloat(dt.get(5)), o_type, ad);
        return data;
    }
}
