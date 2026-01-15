package udn.vku.greenstayapp.presentation.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import udn.vku.greenstayapp.model.Homestay;
import udn.vku.greenstayapp.model.Room;
import udn.vku.greenstayapp.model.User;
import udn.vku.greenstayapp.service.HomestayService;

import java.io.IOException;

public class CatalogController {

    @FXML private TableView<Homestay> tableHomestay;
    @FXML private TableColumn<Homestay, String> colName;
    @FXML private TableColumn<Homestay, String> colAddress;
    @FXML private TableColumn<Homestay, Double> colPrice;
    @FXML private TableColumn<Homestay, Boolean> colEco;
    @FXML private Label lbWelcome;

    private HomestayService homestayService;
    private User currentUser;

    public void initialize() {
        homestayService = new HomestayService();
        setupTable();
        loadData();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user != null) {
            lbWelcome.setText("Xin chào, " + user.getFullName() + "!");
        }
    }

    private void setupTable() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Format cột Eco (True -> "Có", False -> "Không")
        colEco.setCellValueFactory(new PropertyValueFactory<>("isEcoCertified"));
        colEco.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "🌿 Đạt chuẩn" : "");
                    setStyle(item ? "-fx-text-fill: #27ae60; -fx-font-weight: bold;" : "");
                }
            }
        });
    }

    private void loadData() {
        tableHomestay.setItems(FXCollections.observableArrayList(homestayService.getAllHomestays()));
    }

    @FXML
    public void handleBook() {
        Homestay selected = tableHomestay.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Chưa chọn phòng", "Vui lòng chọn một Homestay trong bảng để đặt!");
            return;
        }

        // Chuyển đổi Homestay sang Room để khớp với form đặt phòng
        Room tempRoom = new Room(selected.getId(), selected.getName(), "Standard", selected.getPrice(), true);
        openBookingForm(tempRoom);
    }

    private void openBookingForm(Room room) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/BookingForm.fxml"));
            Parent root = loader.load();

            BookingController controller = loader.getController();
            controller.setBookingData(currentUser, room);

            controller.setOnSuccess(() -> {
                tableHomestay.refresh();
            });

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Đặt phòng: " + room.getName());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở form đặt phòng: " + e.getMessage());
        }
    }

    @FXML
    public void handleLogout() {
        try {
            Stage stage = (Stage) tableHomestay.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/LoginView.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.centerOnScreen();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}