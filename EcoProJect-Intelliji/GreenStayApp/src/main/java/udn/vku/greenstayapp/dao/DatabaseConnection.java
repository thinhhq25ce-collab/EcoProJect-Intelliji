package udn.vku.greenstayapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/greenstay";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối Database thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối Database thất bại!");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection testConnection = getConnection();
    }
}