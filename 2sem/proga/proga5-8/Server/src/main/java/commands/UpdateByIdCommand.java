package commands;

import bdMng.BdMng;
import com.diogonunes.jcolor.Attribute;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateByIdCommand implements Command {

    private String name = "update";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws IOException, SQLException {
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

        if (dt.size() >= 8) {
            String nameNew = dt.get(1);
            Coordinates cr = new Coordinates(Long.parseLong(dt.get(2)), Float.parseFloat(dt.get(3)));
            /*OrganizationType o_type = ortp.getTypeById(Integer.parseInt(dt.get(6)));*/
            Float anT = Float.parseFloat(dt.get(4));
            OrganizationType ortp = OrganizationType.PUBLIC;
            OrganizationType o_type = ortp.getTypeByName(dt.get(5).toUpperCase());
            Address ad = new Address(dt.get(6), dt.get(7));

            BdMng bd = new BdMng();
            Connection cnt = bd.cnt();
            String sqlReUserId = "SELECT COUNT(*) FROM dblab WHERE id = ?";
            PreparedStatement st1 = cnt.prepareStatement(sqlReUserId);
            st1.setInt(1, Integer.parseInt(dt.get(0)));
            ResultSet re = st1.executeQuery();

            if(re.next() && re.getInt(1) > 0) {
                String sUserId = "SELECT userid FROM dblab WHERE id = ?";
                PreparedStatement stt1 = cnt.prepareStatement(sUserId);
                stt1.setInt(1, Integer.parseInt(dt.get(0)));
                ResultSet re2 = stt1.executeQuery();
                int userId = -1;
                if(re2.next()){userId = re2.getInt(1);}

                String userName = "SELECT name FROM users WHERE id = ?";
                PreparedStatement sttUN = cnt.prepareStatement(userName);
                sttUN.setInt(1, userId);
                ResultSet reUN = sttUN.executeQuery();
                String userN = null;
                if(reUN.next()) {
                    userN = reUN.getString(1);
                }
                if(user.equals(userN)) {
                    String sql = "UPDATE dblab SET name = ?, coordinates = POINT(?, ?), " +
                            "annualTurnover = ?, type = ?, postalAddressStreet = ?, " +
                            "postalAddressZipCoder = ? WHERE id = ?";
                    PreparedStatement statement = cnt.prepareStatement(sql);
                    statement.setString(1, nameNew);
                    statement.setDouble(2, (double) cr.getX());
                    statement.setDouble(3, (double) cr.getY());
                    statement.setDouble(4, (double) anT);
                    statement.setString(5, o_type.toString());
                    statement.setString(6, ad.getStreet());
                    statement.setString(7, ad.getZipCode());
                    statement.setInt(8, Integer.parseInt(dt.get(0)));

                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated > 0) {
                        return new ObjectResAns("Организация " + nameNew + " изменена!\n", true, user);
                    } else {
                        return new ObjectResAns("Wrong Response!\n", false, user);
                    }
                }else{
                    return new ObjectResAns("У вас нет доступа!\n", false, user);
                }
            }else {
                return new ObjectResAns("Нету такого элемента!\n", true, user);
            }
        } else {
            if (isPr) {
                Static.isPrint = 0;
            }
            return new ObjectResAns("Данные Организации Некорректно!\n", false, user);
        }
    }

    @Override
    public String des() {
        return "update id new_element : обновить значение элемента коллекции";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
