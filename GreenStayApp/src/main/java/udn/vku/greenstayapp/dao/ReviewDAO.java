package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public List<Review> getReviewsByHomestayId(int homestayId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE homestay_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("homestay_id"),
                        rs.getString("user_name"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getDate("review_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addReview(Review r, int homestayId) {
        String sql = "INSERT INTO Review (homestay_id, user_name, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);
            stmt.setString(2, r.getUserName());
            stmt.setInt(3, r.getRating());
            stmt.setString(4, r.getComment());
            stmt.setDate(5, r.getReviewDate());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}