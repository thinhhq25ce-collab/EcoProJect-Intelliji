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

    // --- Khai báo các thành phần giao diện (phải trùng fx:id bên FXML) ---
    @FXML private TableView<Homestay> tableHomestay;
    @FXML private TableColumn<Homestay, Integer> colId;
    @FXML private TableColumn<Homestay, String> colName;
    @FXML private TableColumn<Homestay, String> colAddress;
    @FXML private TableColumn<Homestay, Double> colPrice;
    @FXML private TableColumn<Homestay, Boolean> colEco;

    // Các trường nhập liệu
    @FXML private TextField tfName;
    @FXML private TextField tfAddress;
    @FXML private TextField tfPrice;
    @FXML private CheckBox cbEco;

    // Service để gọi xuống tầng dưới
    private HomestayService service;
    private ObservableList<Homestay> homestayList;

    // --- Hàm khởi chạy (tự động chạy khi mở màn hình) ---
    public void initialize() {
        service = new HomestayService();
        homestayList = FXCollections.observableArrayList();

        // 1. Cấu hình các cột của bảng (Map với tên thuộc tính trong Model Homestay)
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEco.setCellValueFactory(new PropertyValueFactory<>("ecoCertified"));

        // 2. Tải dữ liệu lên bảng
        loadData();
    }

    // --- Hàm xử lý sự kiện nút "Thêm" ---
    @FXML
    public void handleAdd() {
        // Lấy dữ liệu từ ô nhập
        String name = tfName.getText();
        String address = tfAddress.getText();
        String priceText = tfPrice.getText();
        boolean isEco = cbEco.isSelected();

        // Kiểm tra dữ liệu trống
        if (name.isEmpty() || address.isEmpty() || priceText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.ERROR);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);

            // Tạo đối tượng mới (ID để 0 vì Database tự tăng)
            Homestay newHomestay = new Homestay(0, name, address, price, isEco);

            // Gọi Service để lưu
            service.addHomestay(newHomestay); // Bạn cần đảm bảo Service có hàm này

            // Thông báo và tải lại bảng
            showAlert("Thành công", "Đã thêm Homestay mới!", Alert.AlertType.INFORMATION);
            clearInputs();
            loadData();

        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Giá tiền phải là số!", Alert.AlertType.ERROR);
        }
    }

    // --- Hàm xử lý sự kiện nút "Xóa" ---
    @FXML
    public void handleDelete() {
        // Lấy dòng đang chọn
        Homestay selected = tableHomestay.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Cảnh báo", "Vui lòng chọn Homestay cần xóa!", Alert.AlertType.WARNING);
            return;
        }

        // Hiện hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa: " + selected.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Gọi service xóa (Bạn cần thêm hàm deleteHomestay vào Service/DAO nếu chưa có)
            // service.deleteHomestay(selected.getId());

            // Tạm thời xóa trên giao diện để demo
            homestayList.remove(selected);
            System.out.println("Đã xóa Homestay ID: " + selected.getId());
        }
    }

    // --- Hàm xử lý nút "Làm mới" ---
    @FXML
    public void handleRefresh() {
        loadData();
    }

    // --- Các hàm phụ trợ ---
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
            // Hiển thị hộp thoại xác nhận (Tùy chọn)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Đăng xuất");
            alert.setHeaderText("Bạn có chắc muốn đăng xuất?");
            if (alert.showAndWait().get() != ButtonType.OK) {
                return; // Nếu chọn Cancel thì dừng lại
            }

            // 1. Lấy cửa sổ (Stage) hiện tại thông qua một phần tử bất kỳ (ví dụ bảng)
            Stage currentStage = (Stage) tableHomestay.getScene().getWindow();

            // 2. Tải file LoginView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/udn/vku/greenstayapp/LoginView.fxml"));
            Parent root = loader.load();

            // 3. Chuyển cảnh
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("GreenStay - Đăng nhập");
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}