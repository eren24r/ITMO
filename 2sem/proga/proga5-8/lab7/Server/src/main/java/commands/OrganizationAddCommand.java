package commands;

import bdMng.BdMng;
import dataParseIng.BDReader;
import objectResAns.ObjectResAns;
import org.postgresql.geometric.PGpoint;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganizationAddCommand implements Command {
    private String name = "add";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws SQLException {
        String nm;
        Long crX = 0L;
        float crY = 0f;
        Float annualTurnover = 0F;
        String street = "", zipCode = "";
        boolean isPr = false;
        if (Static.isPrint == 0) {
            isPr = true;
            Static.isPrint = 1;
        } else {
            isPr = false;
        }

        Pattern pt = Pattern.compile("\"([^\"]*)\"");
        Matcher mt = pt.matcher(s);
        List<String> dt = new ArrayList<>();
        while(mt.find()){
            dt.add(mt.group(1));
        }

        if (dt.size() >= 7) {
            String nameNew = dt.get(0);
            Coordinates cr = new Coordinates(Long.parseLong(dt.get(1)), Float.parseFloat(dt.get(2)));
            /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
            Float anT = Float.parseFloat(dt.get(3));
            OrganizationType ortp = OrganizationType.PUBLIC;
            OrganizationType o_type = ortp.getTypeByName(dt.get(4).toUpperCase());
            Address ad = new Address(dt.get(5), dt.get(6));
            Organization data = null;
            try {

                BdMng bd = new BdMng();
                Connection cnt = bd.cnt();
                String sssRe = "SELECT id FROM users WHERE name = ?";
                PreparedStatement st1 = cnt.prepareStatement(sssRe);
                st1.setString(1, user);
                ResultSet re = st1.executeQuery();

                int userId = -1;
                if(re.next()) {
                    userId = re.getInt(1);
                }
                System.out.println("userIDGiven");

                String sql = "INSERT INTO dblab (name, coordinates, creationDate, annualTurnover, type, postalAddressStreet, postalAddressZipCoder, userId) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = cnt.prepareStatement(sql);
                statement.setString(1, nameNew);
                statement.setObject(2, new PGpoint(cr.getX(), cr.getY()));
                statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                statement.setBigDecimal(4, new BigDecimal(anT));
                statement.setString(5, o_type.toString());
                statement.setString(6, ad.getStreet());
                statement.setString(7, ad.getZipCode());
                statement.setInt(8, userId);
                statement.executeUpdate();
                statement.close();
                cnt.close();

                if (isPr) {
                    Static.isPrint = 0;
                }
                return new ObjectResAns("Организация " + nameNew + " добавлена!\n", true, user);
            } catch (IOException e) {
                return new ObjectResAns("Данные Организации Некорректно!\n", false, user);
            }
        } else {
            if (isPr) {
                Static.isPrint = 0;
            }
            return new ObjectResAns("Название Организации Некорректно!\n", false, user);
        }
    }

    @Override
    public String des() {
        return "add element_name : добавить новый элемент в коллекцию";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
