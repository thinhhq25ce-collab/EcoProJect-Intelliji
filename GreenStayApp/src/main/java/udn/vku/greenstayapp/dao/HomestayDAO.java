package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Homestay;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomestayDAO {

    public List<Homestay> getAllHomestays() {
        List<Homestay> list = new ArrayList<>();
        String sql = "SELECT * FROM homestay";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

                list.add(new Homestay(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_eco")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addHomestay(Homestay h) {
        String sql = "INSERT INTO homestay (name, address, price, is_eco) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, h.getName());
            stmt.setString(2, h.getAddress());
            stmt.setDouble(3, h.getPrice());
            stmt.setBoolean(4, h.getIsEcoCertified());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateHomestay(Homestay h) {
        String sql = "UPDATE homestay SET name=?, address=?, price=?, is_eco=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, h.getName());
            stmt.setString(2, h.getAddress());
            stmt.setDouble(3, h.getPrice());
            stmt.setBoolean(4, h.getIsEcoCertified());
            stmt.setInt(5, h.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteHomestay(int id) {
        String sql = "DELETE FROM homestay WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}