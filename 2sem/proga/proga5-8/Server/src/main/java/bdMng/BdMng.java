package bdMng;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class BdMng {

    public Connection cnt() throws IOException {
        Properties properties = new Properties();

        properties.load(new FileReader("Datas/config.properties"));

        String jdbcUrl = "jdbc:postgresql://localhost:5432/studs";
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Успешное подключение к базе данных!");
            return connection;
        } catch (SQLException ex) {
            System.err.println("Ошибка подключения к базе данных.");
            return null;
        }
    }

    public ResultSet giveResOfQuery(Connection connection, String query){
        try {
            String sql = query;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            return result;
        } catch (SQLException e) {
            System.err.println("Ошибка Запроса!");
            return null;
        }
    }

    public static void main(String[] args) throws IOException {

    }
}
