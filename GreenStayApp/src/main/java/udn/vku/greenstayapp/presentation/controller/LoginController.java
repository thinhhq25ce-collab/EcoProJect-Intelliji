package udn.vku.greenstayapp.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import udn.vku.greenstayapp.model.User;
import udn.vku.greenstayapp.service.UserService;

import java.io.IOException;

public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    private UserService userService;

    public void initialize() {
        userService = new UserService();
    }

    @FXML
    public void handleLogin() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        User user = userService.login(username, password);

        if (user != null) {
            // Đăng nhập thành công -> Kiểm tra quyền
            if ("admin".equalsIgnoreCase(user.getRole())) {
                switchScene("/udn/vku/greenstayapp/AdminDashboard.fxml", "Quản trị hệ thống");
            } else {
                // QUAN TRỌNG: Truyền user sang màn hình Khách hàng
                openCustomerScreen(user);
            }
        } else {
            showAlert("Lỗi", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

    @FXML
    public void handleRegister() {
        switchScene("/udn/vku/greenstayapp/RegisterView.fxml", "Đăng ký tài khoản");
    }

    // --- HÀM MỚI: Mở màn hình khách và truyền dữ liệu User ---
    private void openCustomerScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/CatalogView.fxml"));
            Parent root = loader.load();

            // Lấy Controller của màn hình Catalog để truyền dữ liệu
            CatalogController controller = loader.getController();

            if (controller != null) {
                controller.setCurrentUser(user); // <--- DÒNG QUAN TRỌNG NHẤT
            }

            Stage stage = (Stage) tfUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GreenStay - Xin chào " + user.getFullName());
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi hệ thống", "Không thể tải màn hình Catalog: " + e.getMessage());
        }
    }

    // Hàm chuyển cảnh thông thường (dùng cho Admin/Register)
    private void switchScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) tfUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không tìm thấy file: " + fxmlPath);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}