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

public class AddUserCmd implements Command{

    public String name = "addUser";
    @Override
    public ObjectResAns doo(HashSet<Organization> mySet, String s, String user) throws IOException, SQLException, NoSuchAlgorithmException {
        String[] arg = s.split(" ");
        String name = arg[1];
        String psswd = arg[2];
        BdMng bd = new BdMng();
        Connection cnt = bd.cnt();

        String sqlStr = "SELECT COUNT(*) FROM users WHERE name = ?";
        PreparedStatement statement = cnt.prepareStatement(sqlStr);
        System.out.println("dsf");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        if(res.next() && res.getInt(1)==0){
            String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
            statement = cnt.prepareStatement(sql);
            statement.setString(1, name);

            // хэшируем пароль алгоритмом SHA-1
            String passwordToHash = psswd;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordBytes = passwordToHash.getBytes();
            byte[] hash = md.digest(passwordBytes);
            String passwordHash = Static.bytesToHex(hash);

            statement.setString(2, passwordHash);
            statement.executeUpdate();

            return new ObjectResAns(Static.txt("User " + name + " added!\n"), true, user);
        }else{
            return new ObjectResAns(Static.txt("User " + name + " already added!\n"), false, user);
        }
    }

    @Override
    public String des() {
        return "addUser: adding new user";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
