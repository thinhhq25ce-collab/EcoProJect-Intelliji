package udn.vku.greenstayapp.model;

import java.sql.Date;

public class Review {
    private int id;
    private int homestayId;
    private String userName;
    private int rating;
    private String comment;
    private Date reviewDate;

    public Review(int id, int homestayId, String userName, int rating, String comment, Date reviewDate) {
        this.id = id;
        this.homestayId = homestayId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public String getUserName() { return userName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public Date getReviewDate() { return reviewDate; }
}