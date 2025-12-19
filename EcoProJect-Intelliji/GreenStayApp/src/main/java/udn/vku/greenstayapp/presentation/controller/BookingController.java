package udn.vku.greenstayapp.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import udn.vku.greenstayapp.model.Room;
import udn.vku.greenstayapp.service.BookingService;

public class BookingController {

    @FXML private TextField tfCustomerName;
    @FXML private ComboBox<Room> cbRooms; // ComboBox chọn phòng
    @FXML private TextField tfDate;

    private BookingService bookingService;

    public void initialize() {
        bookingService = new BookingService();

        // Giả lập load danh sách phòng vào ComboBox
        cbRooms.getItems().add(new Room(1, "Phòng Deluxe View Núi", 500000, true));
        cbRooms.getItems().add(new Room(2, "Phòng Standard", 300000, true));
    }

    @FXML
    public void handleBooking() {
        String name = tfCustomerName.getText();
        Room selectedRoom = cbRooms.getValue();
        String date = tfDate.getText();

        if (selectedRoom == null) {
            showAlert("Lỗi", "Vui lòng chọn phòng!");
            return;
        }

        boolean success = bookingService.createBooking(name, selectedRoom.getName(), date);

        if (success) {
            showAlert("Thành công", "Đặt phòng thành công!");
        } else {
            showAlert("Thất bại", "Vui lòng kiểm tra lại thông tin.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}