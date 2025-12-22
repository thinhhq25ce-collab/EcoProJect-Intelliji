package udn.vku.greenstayapp.model;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int userId;
    private String username;
    private int homestayId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public Review() {}


    public Review(int id, int userId, String username, int homestayId, int rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.homestayId = homestayId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }


    public Review(int userId, int homestayId, int rating, String comment) {
        this.userId = userId;
        this.homestayId = homestayId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getHomestayId() { return homestayId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}