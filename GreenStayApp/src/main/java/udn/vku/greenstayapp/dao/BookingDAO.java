package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class BookingDAO {

    public boolean createBooking(Booking b) {
        String sql = "INSERT INTO booking (user_id, room_id, check_in, check_out, total_price, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, b.getUserId());
            stmt.setInt(2, b.getRoomId());
            stmt.setDate(3, Date.valueOf(b.getCheckIn()));
            stmt.setDate(4, Date.valueOf(b.getCheckOut()));
            stmt.setDouble(5, b.getTotalPrice());
            stmt.setString(6, b.getStatus());
            stmt.setTimestamp(7, Timestamp.valueOf(b.getCreatedAt()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, u.username, r.name as room_name " +
                "FROM booking b " +
                "JOIN user u ON b.user_id = u.id " +
                "JOIN room r ON b.room_id = r.id " +
                "ORDER BY b.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Map<Integer, Double> getMonthlyRevenue(int year) {
        Map<Integer, Double> revenueMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) revenueMap.put(i, 0.0);

        String sql = "SELECT MONTH(check_in) as month, SUM(total_price) as total " +
                "FROM booking " +
                "WHERE YEAR(check_in) = ? " +
                "GROUP BY MONTH(check_in)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                revenueMap.put(rs.getInt("month"), rs.getDouble("total"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return revenueMap;
    }
    public List<Booking> getBookingsByRoom(int roomId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT check_in, check_out FROM booking WHERE room_id = ? AND status != 'CANCELLED'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setCheckIn(rs.getDate("check_in").toLocalDate());
                b.setCheckOut(rs.getDate("check_out").toLocalDate());
                list.add(b);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}