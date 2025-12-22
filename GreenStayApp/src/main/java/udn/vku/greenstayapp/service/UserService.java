package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.UserDAO;
import udn.vku.greenstayapp.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Xử lý logic Đăng nhập
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        return userDAO.checkLogin(username, password);
    }

    // Xử lý logic Đăng ký
    public boolean register(String username, String password, String fullName, String phone) {
        // 1. Validation: Kiểm tra dữ liệu trống
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            System.out.println("Lỗi: Thông tin không được để trống.");
            return false;
        }

        // 2. Validation: Mật khẩu quá ngắn
        if (password.length() < 6) {
            System.out.println("Lỗi: Mật khẩu phải từ 6 ký tự trở lên.");
            return false;
        }

        // 3. Logic: Kiểm tra trùng tên đăng nhập
        if (userDAO.isUsernameTaken(username)) {
            System.out.println("Lỗi: Tên đăng nhập đã tồn tại.");
            return false;
        }

        // 4. Gọi DAO để lưu vào Database
        return userDAO.register(username, password, fullName, phone);
    }
}