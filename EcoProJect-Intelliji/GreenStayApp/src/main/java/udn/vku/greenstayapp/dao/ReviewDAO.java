package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public List<Review> getReviewsByHomestay(int homestayId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE homestayId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, homestayId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Review(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getInt("homestayId"),
                        rs.getDouble("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("reviewDate").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}