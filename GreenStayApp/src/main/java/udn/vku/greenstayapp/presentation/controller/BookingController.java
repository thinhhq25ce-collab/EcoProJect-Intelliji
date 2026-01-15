package udn.vku.greenstayapp.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import udn.vku.greenstayapp.model.Room;
import udn.vku.greenstayapp.model.User;
import udn.vku.greenstayapp.model.Booking; // Cần thêm import này
import udn.vku.greenstayapp.service.BookingService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// --- CÁC IMPORT PHỤC VỤ VẼ LƯỚI ---
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import java.util.List;

public class BookingController {

    @FXML private Label lbRoomName;
    @FXML private Label lbPricePerNight;
    @FXML private Label lbTotalPrice;
    @FXML private DatePicker dpCheckIn;
    @FXML private DatePicker dpCheckOut;

    // --- LINK VỚI GRID TRONG FXML ---
    @FXML private GridPane gridAvailability;

    private User currentUser;
    private Room currentRoom;
    private BookingService bookingService;
    private double finalTotalPrice = 0;

    private Runnable onSuccessCallback;

    public void initialize() {
        bookingService = new BookingService();

        // Tự động tính tiền khi chọn ngày
        dpCheckIn.valueProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
        dpCheckOut.valueProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
    }

    public void setBookingData(User user, Room room) {
        this.currentUser = user;
        this.currentRoom = room;
        if (room != null) {
            lbRoomName.setText(room.getName());
            lbPricePerNight.setText(String.format("%,.0f VNĐ/đêm", room.getPricePerNight()));

            // --- GỌI CÁC HÀM NÂNG CẤP KHI MÀN HÌNH MỞ LÊN ---
            renderAvailabilityGrid();
            disableBookedDates();
        }
    }

    // 1. Hàm vẽ lưới hiển thị 7 ngày tới (Giống ảnh mẫu bạn muốn)
    private void renderAvailabilityGrid() {
        if (gridAvailability == null) return;

        gridAvailability.getChildren().clear();
        List<Booking> bookedSlots = bookingService.getBookingsByRoom(currentRoom.getId());
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);

            VBox cell = new VBox(2);
            cell.setAlignment(Pos.CENTER);
            cell.setPrefSize(50, 50);

            Label lbDate = new Label(date.getDayOfMonth() + "/" + date.getMonthValue());
            lbDate.setStyle("-fx-font-size: 10px;");
            Label lbStatus = new Label();

            // Kiểm tra xem ngày này có nằm trong khoảng đã đặt nào không
            boolean isBooked = bookedSlots.stream().anyMatch(b ->
                    (date.isEqual(b.getCheckIn()) || date.isAfter(b.getCheckIn())) && date.isBefore(b.getCheckOut())
            );

            if (isBooked) {
                lbStatus.setText("HẾT");
                cell.setStyle("-fx-background-color: #e74c3c; -fx-background-radius: 5;");
                lbDate.setTextFill(Color.WHITE);
                lbStatus.setTextFill(Color.WHITE);
                lbStatus.setStyle("-fx-font-weight: bold; -fx-font-size: 9px;");
            } else {
                lbStatus.setText("TRỐNG");
                cell.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-background-radius: 5;");
                lbStatus.setTextFill(Color.web("#27ae60"));
                lbStatus.setStyle("-fx-font-size: 9px;");
            }

            cell.getChildren().addAll(lbDate, lbStatus);
            gridAvailability.add(cell, i, 0);
        }
    }

    // 2. Hàm gạch bỏ những ngày đã bận ngay trên lịch DatePicker
    private void disableBookedDates() {
        List<Booking> bookedSlots = bookingService.getBookingsByRoom(currentRoom.getId());

        javafx.util.Callback<DatePicker, DateCell> dayCellFactory = d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Khóa các ngày trong quá khứ
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }

                // Khóa các ngày đã có người khác đặt
                for (Booking b : bookedSlots) {
                    if ((item.isEqual(b.getCheckIn()) || item.isAfter(b.getCheckIn())) && item.isBefore(b.getCheckOut())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc4c4;"); // Màu đỏ nhạt báo bận
                    }
                }
            }
        };

        dpCheckIn.setDayCellFactory(dayCellFactory);
        dpCheckOut.setDayCellFactory(dayCellFactory);
    }

    private void calculateTotal() {
        LocalDate in = dpCheckIn.getValue();
        LocalDate out = dpCheckOut.getValue();

        if (in != null && out != null && currentRoom != null) {
            long days = ChronoUnit.DAYS.between(in, out);
            if (days > 0) {
                this.finalTotalPrice = days * currentRoom.getPricePerNight();
                lbTotalPrice.setText(String.format("Tổng cộng: %,.0f VNĐ (%d đêm)", finalTotalPrice, days));
                lbTotalPrice.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                lbTotalPrice.setText("Ngày về phải sau ngày đến!");
                lbTotalPrice.setStyle("-fx-text-fill: red;");
                this.finalTotalPrice = 0;
            }
        }
    }

    @FXML
    public void handleConfirmBooking() {
        if (finalTotalPrice <= 0) {
            showAlert("Lỗi", "Vui lòng chọn ngày hợp lệ!");
            return;
        }

        // --- KIỂM TRA TRÙNG LỊCH TRƯỚC KHI LƯU ---
        if (!bookingService.isRoomAvailable(currentRoom.getId(), dpCheckIn.getValue(), dpCheckOut.getValue())) {
            showAlert("Trùng lịch", "Ngày bạn chọn đã có người đặt trước đó. Vui lòng kiểm tra lại lịch!");
            renderAvailabilityGrid(); // Load lại lưới để cập nhật thông tin mới nhất
            return;
        }

        boolean success = bookingService.bookRoom(currentUser.getId(), currentRoom.getId(), dpCheckIn.getValue(), dpCheckOut.getValue(), finalTotalPrice);

        if (success) {
            showAlert("Thành công", "Đặt phòng thành công!");
            if (onSuccessCallback != null) onSuccessCallback.run();
            closeWindow();
        } else {
            showAlert("Thất bại", "Lỗi hệ thống, vui lòng thử lại.");
        }
    }

    public void setOnSuccess(Runnable action) { this.onSuccessCallback = action; }
    @FXML public void handleCancel() { closeWindow(); }
    private void closeWindow() { ((Stage) lbRoomName.getScene().getWindow()).close(); }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}