package udn.vku.greenstayapp.model;

public class Admin { private int adminId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private List<Homestay> managedHomestays;

    public Admin() {
        this.managedHomestays = new ArrayList<>();
    }

    public Admin(int adminId, String username, String password, String email, String fullName, String phone) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.managedHomestays = new ArrayList<>();
    }

    // Getters and Setters
    public int getAdminId() { return adminId; }
    public void setAdminId(int adminId) { this.adminId = adminId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Homestay> getManagedHomestays() { return managedHomestays; }
    public void setManagedHomestays(List<Homestay> managedHomestays) { this.managedHomestays = managedHomestays; }

    public void addHomestay(Homestay homestay) {
        this.managedHomestays.add(homestay);
    }

    public void removeHomestay(Homestay homestay) {
        this.managedHomestays.remove(homestay);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
