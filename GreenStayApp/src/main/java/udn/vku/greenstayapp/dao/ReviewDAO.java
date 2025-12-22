package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public boolean addReview(Review r) {
        String sql = "INSERT INTO review (user_id, homestay_id, rating, comment, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getUserId());
            stmt.setInt(2, r.getHomestayId());
            stmt.setInt(3, r.getRating());
            stmt.setString(4, r.getComment());
            stmt.setTimestamp(5, Timestamp.valueOf(r.getCreatedAt()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Review> getReviewsByHomestay(int homestayId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.username " +
                "FROM review r " +
                "JOIN user u ON r.user_id = u.id " +
                "WHERE r.homestay_id = ? " +
                "ORDER BY r.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("homestay_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getAverageRating(int homestayId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM review WHERE homestay_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}