package udn.vku.greenstayapp.model;

public class Admin extends User {
    private String employeeCode; // Mã nhân viên
    private String department;   // Phòng ban (VD: Sales, Manager)

    public Admin(int id, String username, String password, String employeeCode, String department) {
        super(id, username, password, "ADMIN");
        this.employeeCode = employeeCode;
        this.department = department;
    }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Admin: " + getUsername() + " [" + department + "]";
    }
}