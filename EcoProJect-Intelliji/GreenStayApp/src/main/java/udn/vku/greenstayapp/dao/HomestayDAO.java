package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Homestay;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomestayDAO {

    public List<Homestay> getAllHomestays() {
        List<Homestay> list = new ArrayList<>();
        String sql = "SELECT * FROM Homestay";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Homestay h = new Homestay(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getDouble("price"),
                        rs.getBoolean("isEcoCertified")
                );
                list.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean addHomestay(Homestay h) {
        String sql = "INSERT INTO Homestay (name, address, price, isEcoCertified) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, h.getName());
            stmt.setString(2, h.getAddress());
            stmt.setDouble(3, h.getPrice());
            stmt.setBoolean(4, h.isEcoCertified());

            int row = stmt.executeUpdate();
            return row > 0;

        } catch (Exception e) {
            System.out.println("Lỗi Thêm: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteHomestay(int id) {
        String sql = "DELETE FROM Homestay WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public void updateHomestay(Homestay h) {
        String sql = "UPDATE homestay SET name = ?, address = ?, price = ?, isEcoCertified = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, h.getName());
            pstmt.setString(2, h.getAddress());
            pstmt.setDouble(3, h.getPrice());
            pstmt.setBoolean(4, h.isEcoCertified());
            pstmt.setInt(5, h.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("!!! Warning: No homestay found with ID " + h.getId());
            }
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }
}