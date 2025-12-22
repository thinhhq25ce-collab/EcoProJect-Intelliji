package udn.vku.greenstayapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Tải giao diện Đăng nhập đầu tiên
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/LoginView.fxml"));
        Parent root = loader.load();

        // Thiết lập Scene
        Scene scene = new Scene(root);

        // Cấu hình cửa sổ
        stage.setTitle("GreenStay - Hệ thống đặt phòng Homestay Xanh");
        stage.setScene(scene);
        stage.setResizable(false); // Không cho phép kéo giãn cửa sổ đăng nhập cho đẹp
        stage.centerOnScreen();    // Căn giữa màn hình
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}