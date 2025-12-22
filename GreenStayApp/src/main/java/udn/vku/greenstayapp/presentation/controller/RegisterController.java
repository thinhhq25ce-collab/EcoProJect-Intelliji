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
    @FXML private PasswordField pfConfirmPass;

    private UserService userService;

    public void initialize() {
        userService = new UserService();
    }

    @FXML
    public void handleRegister() {
        String fullName = tfFullName.getText();
        String phone = tfPhone.getText();
        String username = tfUsername.getText();
        String pass = pfPassword.getText();
        String confirmPass = pfConfirmPass.getText();

        // 1. Kiểm tra nhập liệu
        if (fullName.isEmpty() || username.isEmpty() || pass.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin bắt buộc!", Alert.AlertType.WARNING);
            return;
        }

        // 2. Kiểm tra mật khẩu khớp nhau
        if (!pass.equals(confirmPass)) {
            showAlert("Lỗi", "Mật khẩu xác nhận không khớp!", Alert.AlertType.ERROR);
            return;
        }

        // 3. Gọi Service đăng ký
        if (userService.register(username, pass, fullName, phone)) {
            showAlert("Thành công", "Đăng ký tài khoản thành công! Vui lòng đăng nhập.", Alert.AlertType.INFORMATION);
            handleBackToLogin(); // Tự động quay về trang login
        } else {
            showAlert("Thất bại", "Tên đăng nhập đã tồn tại hoặc có lỗi hệ thống.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleBackToLogin() {
        try {
            Stage stage = (Stage) tfUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/LoginView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("GreenStay - Đăng nhập");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}