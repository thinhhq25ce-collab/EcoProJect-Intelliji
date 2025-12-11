package udn.vku.greenstayapp.model;

public class Customer {
    private int id;
    private String username;
    private String password;
    private String role; // "ADMIN" hoặc "CUSTOMER"

    public Customer(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    // ... thêm setter nếu cần
}