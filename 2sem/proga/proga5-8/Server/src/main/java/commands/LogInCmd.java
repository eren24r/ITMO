package commands;

import bdMng.BdMng;
import objectResAns.ObjectResAns;
import statics.Static;
import сlasses.Organization;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class LogInCmd implements Command{
    String name = "Login";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws IOException, SQLException, NoSuchAlgorithmException {
        String[] arg = s.split(" ");
        String name = arg[1];
        String psswd = arg[2];
        BdMng bd = new BdMng();
        Connection cnt = bd.cnt();

        String sqlStr = "SELECT COUNT(*) FROM users WHERE name = ?";
        PreparedStatement statement = cnt.prepareStatement(sqlStr);
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        if(res.next() && res.getInt(1)==1){
            String sql = "SELECT password FROM users WHERE name = ?";
            statement = cnt.prepareStatement(sql);
            statement.setString(1, name);

            // хэшируем пароль алгоритмом SHA-1
            String passwordToHash = psswd;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordBytes = passwordToHash.getBytes();
            byte[] hash = md.digest(passwordBytes);
            String passwordHash = Static.bytesToHex(hash);

            ResultSet resPass = statement.executeQuery();
            if(resPass.next()){
                if(resPass.getString("password").equals(passwordHash)){
                    return new ObjectResAns(Static.txt("User " + name + " logined!\n"), true, name);
                }else{
                    return new ObjectResAns(Static.txt("User or password is wrong!\n"), false, user);
                }
            }
        }else{
            return new ObjectResAns(Static.txt("User " + name + " not found!\n"), false, user);
        }
        return null;
    }

    @Override
    public String des() {
        return "Login: Login Username Password";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
