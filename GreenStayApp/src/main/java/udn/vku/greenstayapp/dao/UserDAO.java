package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    private List<Customer> users = new ArrayList<>();

    public UserDAO() {
        // Dữ liệu giả để test đăng nhập
        users.add(new Customer(1, "admin", "123", "ADMIN"));
        users.add(new Customer(2, "khach", "123", "CUSTOMER"));
    }

    public Customer checkLogin(String username, String password) {
        for (Customer u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null; // Đăng nhập thất bại
    }
}