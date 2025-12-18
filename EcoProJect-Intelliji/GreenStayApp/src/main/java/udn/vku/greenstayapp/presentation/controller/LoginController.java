package udn.vku.greenstayapp.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import udn.vku.greenstayapp.dao.UserDAO;
import udn.vku.greenstayapp.model.User;

import java.io.IOException;

public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    private UserDAO userDAO;

    public void initialize() {
        // Khởi tạo lớp kết nối dữ liệu
        userDAO = new UserDAO();
    }

    @FXML
    public void handleLogin() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Gọi sang UserDAO để kiểm tra (KHÔNG tự viết vòng lặp ở đây)
        User user = userDAO.checkLogin(username, password);

        if (user != null) {
            System.out.println("Đăng nhập thành công! Role: " + user.getRole());

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                switchScene("/udn/vku/greenstayapp/AdminDashboard.fxml", "Trang Quản Trị (Admin)");
            } else {
                switchScene("/udn/vku/greenstayapp/CatalogView.fxml", "Danh sách Homestay");
            }

        } else {
            showAlert("Thất bại", "Sai tên đăng nhập hoặc mật khẩu!");
        }
    }

    @FXML
    public void handleRegister() {
        // Chuyển sang màn hình Đăng ký
        switchScene("/udn/vku/greenstayapp/RegisterView.fxml", "Đăng ký tài khoản");
    }

    // --- Các hàm phụ trợ ---

    private void switchScene(String fxmlPath, String title) {
        try {
            Stage stage = (Stage) tfUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("GreenStay - " + title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi hệ thống", "Không tìm thấy file giao diện: " + fxmlPath);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}