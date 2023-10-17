package commands;

import bdMng.BdMng;
import com.diogonunes.jcolor.Attribute;
import dataParseIng.BDReader;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;

public class RemoveByIdCommand implements Command{

    private String name = "remove_by_id";

    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) {
        if (user != null) {
            String[] xY = s.split(" ");
            boolean b = false;
            boolean isPr = false;
            if (Static.isPrint == 0) {
                isPr = true;
                Static.isPrint = 1;
            } else {
                isPr = false;
            }
            try {
                if (Integer.parseInt(xY[1]) >= 0) {
                    BdMng bd = new BdMng();
                    Connection cnt = bd.cnt();
                    String sqlReUserId = "SELECT id FROM users WHERE name = ?";
                    PreparedStatement st1 = cnt.prepareStatement(sqlReUserId);
                    st1.setString(1, user);
                    ResultSet re = st1.executeQuery();

                    int userId = -1;
                    if(re.next()){
                        userId = re.getInt(1);
                    }

                    for(Organization o: mySet){
                        if(o.getId() == Integer.parseInt(xY[1])){
                            if(o.getUser() == userId){
                                b = true;
                            }else{
                                return new ObjectResAns(Static.txt("У вас нет доступа!\n"), false, user);
                            }
                        }
                    }
                }
                if (b == true) {
                    BdMng bd = new BdMng();
                    Connection cnt = bd.cnt();
                    String sql = "DELETE FROM dblab WHERE id = ?";
                    PreparedStatement statement = cnt.prepareStatement(sql);
                    statement.setInt(1, Integer.parseInt(xY[1]));
                    statement.executeUpdate();

                    statement.close();
                    cnt.close();

                    if (isPr) {
                        Static.isPrint = 0;
                    }
                    return new ObjectResAns(Static.txt("Объект удалено!\n"), true, user);
                } else {
                    if (isPr) {
                        Static.isPrint = 0;
                    }
                    return new ObjectResAns(Static.txt("Нету такого Объекта!\n"), false, user);
                }
            } catch (Exception e) {
                if (isPr) {
                    Static.isPrint = 0;
                }
                return new ObjectResAns("Ошибка формата!\n", true, user);
            }
        }else{
            return new ObjectResAns(Static.txt("У вас нет доступа!\n"), true, user);
        }
    }

    @Override
    public String des() {
        return "remove_by_id id: удалить элемент из коллекции по его id";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
