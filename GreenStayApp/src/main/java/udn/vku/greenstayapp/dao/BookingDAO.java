package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Booking; // Bạn cần tạo model Booking tương tự User
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private List<String> bookings = new ArrayList<>(); // Demo lưu String cho đơn giản

    public void addBooking(String customerName, String roomName, String date) {
        String bookingInfo = "Khách: " + customerName + " - Phòng: " + roomName + " - Ngày: " + date;
        bookings.add(bookingInfo);
        System.out.println("Đã lưu vào DB: " + bookingInfo);
    }

    public List<String> getAllBookings() {
        return bookings;
    }
}