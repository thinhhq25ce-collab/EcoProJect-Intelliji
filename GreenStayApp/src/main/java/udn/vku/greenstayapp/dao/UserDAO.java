package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Customer;
import udn.vku.greenstayapp.model.User;
import java.sql.*;

public class UserDAO {

    // 1. Hàm kiểm tra đăng nhập (Kết nối Database thật)
    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Lấy thông tin từ Database trả về
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy User
    }

    // 2. Hàm đăng ký khách hàng (Kết nối Database thật)
    public boolean registerCustomer(Customer customer) {
        String sqlUser = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
        String sqlCustomer = "INSERT INTO Customer (user_id, full_name, phone_number, email, address) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtCustomer = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // Bước 1: Tạo User
            stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, customer.getUsername());
            stmtUser.setString(2, customer.getPassword());
            stmtUser.setString(3, "CUSTOMER");

            int affectedRows = stmtUser.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Tạo User thất bại.");

            // Lấy ID vừa tạo
            int userId = 0;
            try (ResultSet generatedKeys = stmtUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Không lấy được ID User.");
                }
            }

            // Bước 2: Tạo Customer
            stmtCustomer = conn.prepareStatement(sqlCustomer);
            stmtCustomer.setInt(1, userId);
            stmtCustomer.setString(2, customer.getFullName());
            stmtCustomer.setString(3, customer.getPhoneNumber());
            stmtCustomer.setString(4, customer.getEmail());
            stmtCustomer.setString(5, customer.getAddress());

            stmtCustomer.executeUpdate();

            conn.commit(); // Lưu thay đổi
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            // Đóng kết nối
            try { if(stmtUser != null) stmtUser.close(); } catch(SQLException e) {}
            try { if(stmtCustomer != null) stmtCustomer.close(); } catch(SQLException e) {}
            try { if(conn != null) conn.close(); } catch(SQLException e) {}
        }
    }
}