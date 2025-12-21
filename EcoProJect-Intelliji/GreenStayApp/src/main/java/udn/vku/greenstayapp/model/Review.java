package udn.vku.greenstayapp.model;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private String userName;   // Lưu thẳng tên người review
    private int homestayId;
    private double rating;     // decimal(2,1)
    private String comment;    // text
    private LocalDateTime reviewDate;

    public Review(int id, String userName, int homestayId, double rating, String comment, LocalDateTime reviewDate) {
        this.id = id;
        this.userName = userName;
        this.homestayId = homestayId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters setters...
    public int getId() { return id; }
    public String getUserName() { return userName; }
    public int getHomestayId() { return homestayId; }
    public double getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getReviewDate() { return reviewDate; }
}