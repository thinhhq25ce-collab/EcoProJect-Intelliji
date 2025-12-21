package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // Lấy danh sách phòng theo ID Homestay
    public List<Room> getRoomsByHomestay(int homestayId) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE homestay_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getString("homestay_id"),
                        rs.getString("name"), // Lưu ý: Trong DB có thể cột name là int hoặc varchar, check lại
                        rs.getDouble("pricePerNight"),
                        rs.getBoolean("isAvailable")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}