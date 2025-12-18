package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.UserDAO;
import udn.vku.greenstayapp.model.Customer;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String username, String password, String fullName, String phone) {
        // 1. Kiểm tra logic cơ bản (nếu cần)
        if (username == null || password == null) return false;

        // 2. Tạo đối tượng Customer (dùng constructor 4 tham số không có ID)
        // Lưu ý: Class Customer phải có constructor này như đã sửa ở các bước trước
        Customer newCus = new Customer(0, username, password, fullName);
        newCus.setPhoneNumber(phone); // Set thêm số điện thoại

        // 3. Gọi DAO để lưu vào Database
        return userDAO.registerCustomer(newCus);
    }
}