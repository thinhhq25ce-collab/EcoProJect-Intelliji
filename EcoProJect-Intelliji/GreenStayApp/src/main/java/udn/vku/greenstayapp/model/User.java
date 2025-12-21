package udn.vku.greenstayapp.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;           // 'admin' hoặc 'customer'
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String employeeCode;   // Chỉ dùng cho Admin
    private String department;     // Chỉ dùng cho Admin

    public User() { }

    public User(int id, String username, String password, String role, String fullName, String phoneNumber, String email, String address, String employeeCode, String department) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.employeeCode = employeeCode;
        this.department = department;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}