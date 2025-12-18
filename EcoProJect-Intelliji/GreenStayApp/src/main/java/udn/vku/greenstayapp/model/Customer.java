package udn.vku.greenstayapp.model;

public class Customer extends User {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer(int id, String username, String password, String fullName, String phoneNumber, String email, String address) {
        // Gọi constructor của lớp cha User (role mặc định là CUSTOMER)
        super(id, username, password, "CUSTOMER");
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Customer(String username, String password, String fullName, String phoneNumber) {
        super(0, username, password, "CUSTOMER");
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }
    public Customer(int id, String username, String password, String fullName) {
        super(id, username, password, "CUSTOMER"); // Gọi lớp cha User với ID
        this.fullName = fullName;
        this.phoneNumber = "";
        this.email = "";
        this.address = "";
    }

    public String getFullName() {
        return fullName; }
    public void setFullName(String fullName) {
        this.fullName = fullName; }

    public String getPhoneNumber() {
        return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; }

    public String getEmail() {
        return email; }
    public void setEmail(String email) {
        this.email = email; }

    public String getAddress() {
        return address; }
    public void setAddress(String address) {
        this.address = address; }

    @Override
    public String toString() {
        return fullName + " (" + phoneNumber + ")";
    }
}