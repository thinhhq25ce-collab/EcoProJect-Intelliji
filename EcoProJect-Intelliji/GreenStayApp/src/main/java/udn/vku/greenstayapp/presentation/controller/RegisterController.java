package udn.vku.greenstayapp.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import udn.vku.greenstayapp.service.UserService;

import java.io.IOException;

public class RegisterController {

    @FXML private TextField tfFullName;
    @FXML private TextField tfPhone;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfConfirmPassword;

    private UserService userService;

    public void initialize() {
        userService = new UserService();
    }

    @FXML
    public void handleRegister() {
        // 1. Lấy dữ liệu từ form
        String fullName = tfFullName.getText();
        String phone = tfPhone.getText();
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        String confirmPass = pfConfirmPassword.getText();

        // 2. Kiểm tra dữ liệu (Validation)
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin bắt buộc!", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPass)) {
            showAlert("Lỗi", "Mật khẩu xác nhận không khớp!", Alert.AlertType.ERROR);
            return;
        }

        // 3. Gọi Service để đăng ký
        // (Hàm này bạn đã viết ở các bước trước trong UserService)
        boolean isSuccess = userService.register(username, password, fullName, phone);

        if (isSuccess) {
            showAlert("Thành công", "Đăng ký tài khoản thành công! Vui lòng đăng nhập.", Alert.AlertType.INFORMATION);
            handleBackToLogin(); // Tự động quay về trang đăng nhập
        } else {
            showAlert("Thất bại", "Tên đăng nhập đã tồn tại hoặc có lỗi hệ thống.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleBackToLogin() {
        switchScene("/udn/vku/greenstayapp/LoginView.fxml", "Đăng nhập");
    }

    // Hàm chuyển cảnh chung
    private void switchScene(String fxmlPath, String title) {
        try {
            Stage stage = (Stage) tfUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("GreenStay - " + title);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}