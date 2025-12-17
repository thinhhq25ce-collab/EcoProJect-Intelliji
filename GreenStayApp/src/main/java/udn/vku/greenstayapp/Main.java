package udn.vku.greenstayapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            String fxmlPath = "/udn/vku/greenstayapp/LoginView.fxml";


            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            if (loader.getLocation() == null) {
                System.err.println("❌ LỖI NGHIÊM TRỌNG: Không tìm thấy file FXML tại: " + fxmlPath);
                System.err.println("👉 Hãy kiểm tra lại thư mục 'resources' và tên file.");
                return;
            }

            Parent root = loader.load();
            Scene scene = new Scene(root);


            primaryStage.setTitle("GreenStay - Hệ thống đặt phòng Homestay Xanh");
            primaryStage.setScene(scene);


            primaryStage.show();

        } catch (IOException e) {
            System.err.println("❌ LỖI KHI TẢI GIAO DIỆN:");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("❌ LỖI ĐƯỜNG DẪN HOẶC CONTROLLER:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}