package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Homestay;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class HomestayDAO {
    private List<Homestay> mockDatabase = new ArrayList<>();

    public HomestayDAO() {
        // Dữ liệu giả mẫu
        mockDatabase.add(new Homestay(1, "Green Bamboo Hut", "Sapa", 500000, true));
        mockDatabase.add(new Homestay(2, "City Concrete Home", "Hanoi", 300000, false));
        mockDatabase.add(new Homestay(3, "Eco Palm House", "Da Lat", 700000, true));
    }

    public List<Homestay> getAllHomestays() {
        return mockDatabase;
    }

    public void addHomestay(Homestay homestay) {
        mockDatabase.add(homestay);
        String sql = "INSERT INTO Homestay (name, address, price, isEcoCertified) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, homestay.getName());
            stmt.setString(2, homestay.getAddress());
            stmt.setDouble(3, homestay.getPrice());
            stmt.setBoolean(4, homestay.isEcoCertified());

            stmt.executeUpdate(); // Lưu vào DB

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Xóa Homestay (Để dùng cho nút Xóa)
    public void deleteHomestay(int id) {
        String sql = "DELETE FROM Homestay WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Code SQL: INSERT INTO Homestay...
    }
}
