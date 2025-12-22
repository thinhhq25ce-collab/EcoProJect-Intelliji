package udn.vku.greenstayapp.presentation.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import udn.vku.greenstayapp.dao.BookingDAO;
import udn.vku.greenstayapp.dao.UserDAO;
import udn.vku.greenstayapp.model.Booking;
import udn.vku.greenstayapp.model.Homestay;
import udn.vku.greenstayapp.model.User;
import udn.vku.greenstayapp.service.HomestayService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class AdminController {

    @FXML private BorderPane mainLayout;
    @FXML private Button btnHomestay, btnBooking, btnUser, btnRevenue;

    // Inputs Homestay
    @FXML private TextField tfName, tfAddress, tfPrice;
    @FXML private CheckBox cbEco;

    // Table Homestay
    @FXML private TableView<Homestay> tableHomestay;
    @FXML private TableColumn<Homestay, Integer> colID;
    @FXML private TableColumn<Homestay, String> colName, colAddress;
    @FXML private TableColumn<Homestay, Double> colPrice;
    @FXML private TableColumn<Homestay, Boolean> colEco;

    private HomestayService homestayService;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    private Node homestayView;

    public void initialize() {
        homestayService = new HomestayService();
        bookingDAO = new BookingDAO();
        userDAO = new UserDAO();

        if (mainLayout.getCenter() != null) {
            homestayView = mainLayout.getCenter();
        }

        setupHomestayTable();
        loadHomestayData();
        showHomestayManager();
    }

    private void setSidebarActive(Button activeButton) {
        Button[] buttons = {btnHomestay, btnBooking, btnUser, btnRevenue};
        for (Button btn : buttons) {
            if (btn == activeButton) {
                if (!btn.getStyleClass().contains("sidebar-btn-active"))
                    btn.getStyleClass().add("sidebar-btn-active");
            } else {
                btn.getStyleClass().remove("sidebar-btn-active");
            }
        }
    }

    @FXML
    public void showHomestayManager() {
        setSidebarActive(btnHomestay);
        mainLayout.setCenter(homestayView);
    }

    private void setupHomestayTable() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEco.setCellValueFactory(new PropertyValueFactory<>("isEcoCertified"));

        tableHomestay.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfName.setText(newVal.getName());
                tfAddress.setText(newVal.getAddress());
                tfPrice.setText(String.valueOf((int)newVal.getPrice()));
                cbEco.setSelected(newVal.getIsEcoCertified());
            }
        });
    }

    private void loadHomestayData() {
        tableHomestay.setItems(FXCollections.observableArrayList(homestayService.getAllHomestays()));
    }

    @FXML
    public void handleAdd() {
        if (validateInput()) {
            try {
                String name = tfName.getText();
                String address = tfAddress.getText();
                double price = Double.parseDouble(tfPrice.getText());
                boolean isEco = cbEco.isSelected();

                // KHẮC PHỤC LỖI Ở ĐÂY: Thứ tự tham số đã khớp (price trước, isEco sau)
                if (homestayService.addHomestay(new Homestay(0, name, address, price, isEco))) {
                    showAlert("Thành công", "Đã thêm Homestay mới!");
                    loadHomestayData();
                    handleClear();
                } else {
                    showAlert("Lỗi", "Không thể thêm vào CSDL.");
                }
            } catch (NumberFormatException e) {
                showAlert("Lỗi nhập liệu", "Giá tiền phải là số!");
            }
        }
    }

    @FXML
    public void handleUpdate() {
        Homestay selected = tableHomestay.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Chưa chọn", "Vui lòng chọn dòng cần sửa!");
            return;
        }

        if (validateInput()) {
            try {
                selected.setName(tfName.getText());
                selected.setAddress(tfAddress.getText());
                selected.setPrice(Double.parseDouble(tfPrice.getText()));
                selected.setIsEcoCertified(cbEco.isSelected());

                if (homestayService.updateHomestay(selected)) {
                    showAlert("Thành công", "Cập nhật thành công!");
                    loadHomestayData();
                    handleClear();
                } else {
                    showAlert("Lỗi", "Cập nhật thất bại.");
                }
            } catch (NumberFormatException e) {
                showAlert("Lỗi nhập liệu", "Giá tiền phải là số!");
            }
        }
    }

    @FXML
    public void handleDelete() {
        Homestay selected = tableHomestay.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Chưa chọn", "Vui lòng chọn dòng cần xóa!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa '" + selected.getName() + "'?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (homestayService.deleteHomestay(selected.getId())) {
                showAlert("Thành công", "Đã xóa.");
                loadHomestayData();
                handleClear();
            } else {
                showAlert("Lỗi", "Không thể xóa (có thể do ràng buộc dữ liệu).");
            }
        }
    }

    @FXML public void handleRefresh() { loadHomestayData(); }
    @FXML public void handleClear() {
        tfName.clear(); tfAddress.clear(); tfPrice.clear(); cbEco.setSelected(false);
        tableHomestay.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (tfName.getText().isEmpty() || tfAddress.getText().isEmpty() || tfPrice.getText().isEmpty()) {
            showAlert("Thiếu thông tin", "Vui lòng nhập đủ Tên, Địa chỉ, Giá!");
            return false;
        }
        return true;
    }

    // --- CÁC TAB KHÁC (Booking, User, Revenue) ---
    @FXML
    public void showBookingManager() {
        setSidebarActive(btnBooking);
        TableView<Booking> table = new TableView<>();

        TableColumn<Booking, Integer> colId = new TableColumn<>("Mã");
        colId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colId.setPrefWidth(50);

        TableColumn<Booking, String> colUser = new TableColumn<>("Khách");
        colUser.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Booking, String> colRoom = new TableColumn<>("Phòng");
        colRoom.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        TableColumn<Booking, String> colDate = new TableColumn<>("Ngày đến");
        colDate.setCellValueFactory(new PropertyValueFactory<>("checkIn"));

        TableColumn<Booking, Double> colPrice = new TableColumn<>("Tổng tiền");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colPrice.setStyle("-fx-font-weight: bold; -fx-text-fill: #27ae60;");

        TableColumn<Booking, String> colStatus = new TableColumn<>("Trạng thái");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(colId, colUser, colRoom, colDate, colPrice, colStatus);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(FXCollections.observableArrayList(bookingDAO.getAllBookings()));

        VBox layout = new VBox(15, new Label("DANH SÁCH ĐẶT PHÒNG"), table);
        layout.setStyle("-fx-padding: 20; -fx-background-color: white;");
        ((Label)layout.getChildren().get(0)).setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        mainLayout.setCenter(layout);
    }

    @FXML
    public void showUserManager() {
        setSidebarActive(btnUser);
        TableView<User> table = new TableView<>();

        TableColumn<User, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> colName = new TableColumn<>("Tài khoản");
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> colFull = new TableColumn<>("Họ tên");
        colFull.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        table.getColumns().addAll(colId, colName, colFull);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(FXCollections.observableArrayList(userDAO.getAllUsers()));

        VBox layout = new VBox(15, new Label("DANH SÁCH NGƯỜI DÙNG"), table);
        layout.setStyle("-fx-padding: 20; -fx-background-color: white;");
        ((Label)layout.getChildren().get(0)).setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        mainLayout.setCenter(layout);
    }

    @FXML
    public void showRevenueStats() {
        setSidebarActive(btnRevenue);
        BarChart<String, Number> chart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        chart.setTitle("Doanh Thu Năm " + LocalDate.now().getYear());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Doanh thu (VNĐ)");

        Map<Integer, Double> data = bookingDAO.getMonthlyRevenue(LocalDate.now().getYear());
        double total = 0;
        for (int i = 1; i <= 12; i++) {
            double val = data.getOrDefault(i, 0.0);
            series.getData().add(new XYChart.Data<>("Tháng " + i, val));
            total += val;
        }
        chart.getData().add(series);

        Label lbTotal = new Label("TỔNG: " + String.format("%,.0f VNĐ", total));
        lbTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red;");

        VBox layout = new VBox(15, lbTotal, chart);
        layout.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-alignment: center;");
        mainLayout.setCenter(layout);
    }

    @FXML
    public void handleLogout() {
        try {
            Stage stage = (Stage) mainLayout.getScene().getWindow();
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