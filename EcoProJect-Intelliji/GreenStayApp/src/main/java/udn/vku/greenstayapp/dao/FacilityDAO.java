package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Facility;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacilityDAO {

    public List<Facility> getFacilitiesByHomestayId(int homestayId) {
        List<Facility> list = new ArrayList<>();
        String sql = "SELECT * FROM Facility WHERE homestay_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Facility(
                            rs.getInt("id"),
                            rs.getInt("homestay_id"),
                            rs.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addFacility(Facility f) {
        String sql = "INSERT INTO Facility (homestay_id, name) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, f.getHomestayId());
            stmt.setString(2, f.getName());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Đã thêm tiện nghi thành công: " + f.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi thêm tiện nghi: " + e.getMessage());
        }
    }
}