package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.User;
import java.sql.*;

public class UserDAO {

    // 1. Kiểm tra đăng nhập
    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Ánh xạ đầy đủ 10 cột từ Database vào Model User mới
                // Thứ tự tham số phải khớp với Constructor trong file User.java
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),       // Có thể null trong DB
                        rs.getString("address"),     // Có thể null trong DB
                        rs.getString("employeeCode"),// Chỉ có ở Admin, khách sẽ là null
                        rs.getString("department")   // Chỉ có ở Admin, khách sẽ là null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Kiểm tra tên đăng nhập đã tồn tại chưa (Dùng cho Đăng ký)
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT id FROM user WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu đã tìm thấy user này (đã tồn tại)
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Chặn đăng ký nếu lỗi DB để an toàn
        }
    }

    // 3. Đăng ký tài khoản mới (Mặc định role là 'customer')
    public boolean register(String username, String password, String fullName, String phone) {
        // Câu lệnh SQL chỉ insert các trường cơ bản, các trường khác để NULL
        String sql = "INSERT INTO user (username, password, role, fullName, phoneNumber) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "customer"); // Mặc định là Khách hàng
            stmt.setString(4, fullName);
            stmt.setString(5, phone);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public java.util.List<User> getAllUsers() {
        java.util.List<User> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        "******", // Ẩn mật khẩu
                        rs.getString("role"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("employeeCode"),
                        rs.getString("department")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}