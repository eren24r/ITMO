package dataParseIng;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonReader {
    public Organization jsonReader(String mcls){
        ObjectMapper objmp = new ObjectMapper();
        OrganizationType ortp = OrganizationType.PUBLIC;
        List<String> dt = new ArrayList<>();
        Pattern pt = Pattern.compile("\"([^\"]*)\"");

        try {
            for(JsonNode i: objmp.readTree(mcls)){
                dt.add(i.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            int id = Integer.parseInt(dt.get(0));

            Matcher tmtt = pt.matcher(dt.get(3));
            List<String> tlill = new ArrayList<>();
            while(tmtt.find()){
                tlill.add(tmtt.group(1));
            }
            LocalDateTime time = LocalDateTime.parse(tlill.get(0), Static.dateTimeFormatter);
            Coordinates cr = objmp.readValue(dt.get(2), Coordinates.class);
            Address ad = objmp.readValue(dt.get(6), Address.class);

            Matcher mt = pt.matcher(dt.get(1));
            List<String> lil = new ArrayList<>();
            while(mt.find()){
                lil.add(mt.group(1));
            }

            OrganizationType typeOrg = OrganizationType.PUBLIC;
            Matcher mtt = pt.matcher(dt.get(5));
            List<String> lill = new ArrayList<>();
            while(mtt.find()){
                lill.add(mtt.group(1));
            }
            OrganizationType o_type = typeOrg.getTypeByName(lill.get(0).toString());

            Organization data = new Organization(id, lil.get(0), cr, time, Float.parseFloat(dt.get(4)), o_type, ad);

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
