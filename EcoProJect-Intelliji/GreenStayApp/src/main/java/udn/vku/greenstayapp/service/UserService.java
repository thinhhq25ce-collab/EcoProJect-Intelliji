package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.UserDAO;
import udn.vku.greenstayapp.model.Customer;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String username, String password, String fullName, String phone) {
        if (username == null || password == null) return false;

        Customer newCus = new Customer(0, username, password, fullName);
        newCus.setPhoneNumber(phone); // Set thêm số điện thoại

        return userDAO.registerCustomer(newCus);
    }
}