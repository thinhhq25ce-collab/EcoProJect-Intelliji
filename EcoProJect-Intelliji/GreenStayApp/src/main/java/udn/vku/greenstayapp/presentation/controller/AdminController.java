package udn.vku.greenstayapp.presentation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import udn.vku.greenstayapp.model.Homestay;
import udn.vku.greenstayapp.service.HomestayService;

import java.io.IOException;
import java.util.Optional;

public class AdminController {

    @FXML private TableView<Homestay> tableHomestay;
    @FXML private TableColumn<Homestay, Integer> colId;
    @FXML private TableColumn<Homestay, String> colName;
    @FXML private TableColumn<Homestay, String> colAddress;
    @FXML private TableColumn<Homestay, Double> colPrice;
    @FXML private TableColumn<Homestay, Boolean> colEco;

    @FXML private TextField tfName;
    @FXML private TextField tfAddress;
    @FXML private TextField tfPrice;
    @FXML private CheckBox cbEco;

    private HomestayService service;
    private ObservableList<Homestay> homestayList;

    public void initialize() {
        service = new HomestayService();
        homestayList = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEco.setCellValueFactory(new PropertyValueFactory<>("ecoCertified"));

        loadData();
    }

    @FXML
    public void handleAdd() {
        String name = tfName.getText();
        String address = tfAddress.getText();
        String priceText = tfPrice.getText();
        boolean isEco = cbEco.isSelected();

        if (name.isEmpty() || address.isEmpty() || priceText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.ERROR);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);

            Homestay newHomestay = new Homestay(0, name, address, price, isEco);

            service.addHomestay(newHomestay); // Bạn cần đảm bảo Service có hàm này

            showAlert("Thành công", "Đã thêm Homestay mới!", Alert.AlertType.INFORMATION);
            clearInputs();
            loadData();

        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Giá tiền phải là số!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleDelete() {
        Homestay selected = tableHomestay.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Cảnh báo", "Vui lòng chọn Homestay cần xóa!", Alert.AlertType.WARNING);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa: " + selected.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            homestayList.remove(selected);
            System.out.println("Đã xóa Homestay ID: " + selected.getId());
        }
    }

    @FXML
    public void handleRefresh() {
        loadData();
    }

    private void loadData() {
        homestayList.clear();
        homestayList.addAll(service.getAllHomestays());
        tableHomestay.setItems(homestayList);
    }

    private void clearInputs() {
        tfName.clear();
        tfAddress.clear();
        tfPrice.clear();
        cbEco.setSelected(false);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void handleLogout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Đăng xuất");
            alert.setHeaderText("Bạn có chắc muốn đăng xuất?");
            if (alert.showAndWait().get() != ButtonType.OK) {
                return;
            }

            Stage currentStage = (Stage) tableHomestay.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/LoginView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("GreenStay - Đăng nhập");
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}