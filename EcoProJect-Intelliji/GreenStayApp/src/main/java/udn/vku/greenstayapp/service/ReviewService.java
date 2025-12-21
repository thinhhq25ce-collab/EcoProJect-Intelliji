package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.ReviewDAO;
import udn.vku.greenstayapp.model.Review;
import java.util.List;

public class ReviewService {
    private ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }

    public List<Review> getReviews(int homestayId) {
        return reviewDAO.getReviewsByHomestay(homestayId);
    }

    // Tính điểm đánh giá trung bình của Homestay này (VD: 4.5 sao)
    public double getAverageRating(int homestayId) {
        List<Review> reviews = reviewDAO.getReviewsByHomestay(homestayId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }

        // Làm tròn đến 1 chữ số thập phân
        double avg = sum / reviews.size();
        return Math.round(avg * 10.0) / 10.0;
    }
}