package udn.vku.greenstayapp.model;

public class User {
    protected int id;
    protected String username;
    protected String password;
    protected String role; // "ADMIN" hoặc "CUSTOMER"

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}