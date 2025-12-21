package udn.vku.greenstayapp.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import udn.vku.greenstayapp.model.Room;
import udn.vku.greenstayapp.model.User;
import udn.vku.greenstayapp.service.BookingService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingController {

    @FXML private Label lbRoomName;
    @FXML private Label lbPricePerNight;
    @FXML private Label lbTotalPrice;
    @FXML private DatePicker dpCheckIn;
    @FXML private DatePicker dpCheckOut;

    private User currentUser;
    private Room currentRoom;
    private BookingService bookingService;
    private double finalTotalPrice = 0;

    // Callback để báo cho màn hình danh sách biết là đã đặt xong
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
        }
    }

    public void setOnSuccess(Runnable action) {
        this.onSuccessCallback = action;
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

        boolean success = bookingService.bookRoom(currentUser.getId(), currentRoom.getId(), dpCheckIn.getValue(), dpCheckOut.getValue(), finalTotalPrice);

        if (success) {
            showAlert("Thành công", "Đặt phòng thành công!");
            if (onSuccessCallback != null) onSuccessCallback.run();
            closeWindow();
        } else {
            showAlert("Thất bại", "Lỗi hệ thống, vui lòng thử lại.");
        }
    }

    @FXML public void handleCancel() { closeWindow(); }

    private void closeWindow() { ((Stage) lbRoomName.getScene().getWindow()).close(); }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}