package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

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
                        rs.getInt("homestay_id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM room";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getInt("homestay_id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addRoom(Room r) {
        String sql = "INSERT INTO room (homestay_id, name, type, price, is_available) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getHomestayId());
            stmt.setString(2, r.getName());
            stmt.setString(3, r.getType());
            stmt.setDouble(4, r.getPricePerNight());
            stmt.setBoolean(5, r.isAvailable());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRoom(Room r) {
        String sql = "UPDATE room SET homestay_id=?, name=?, type=?, price=?, is_available=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getHomestayId());
            stmt.setString(2, r.getName());
            stmt.setString(3, r.getType());
            stmt.setDouble(4, r.getPricePerNight());
            stmt.setBoolean(5, r.isAvailable());
            stmt.setInt(6, r.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM room WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}