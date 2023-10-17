package bdMng;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Testing {
    public static void main(String[] args) throws IOException, SQLException {
        Properties properties = new Properties();

        properties.load(new FileReader("Datas/config.properties"));

        String jdbcUrl = "jdbc:postgresql://localhost:5432/studs";
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Успешное подключение к базе данных!");
        } catch (SQLException ex) {
            System.err.println("Ошибка подключения к базе данных.");
        }

        String searchName = "ker"; // имя пользователя для поиска
        String sql = "SELECT password FROM users WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchName);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            System.out.println(result.getString("password"));
        }


    }
}
