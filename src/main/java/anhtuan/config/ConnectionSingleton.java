package anhtuan.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static Connection connection;

    private ConnectionSingleton(){
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookManager",
                        "root",
                        "tuanbenguyen23101997");
                System.out.println("Ket noi thanh cong");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Ket noi khong thanh cong");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
