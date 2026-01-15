package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.BookingDAO;
import udn.vku.greenstayapp.model.Booking;
import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    public boolean bookRoom(int userId, int roomId, LocalDate checkIn, LocalDate checkOut, double totalPrice) {

        if (checkIn == null || checkOut == null) {
            System.err.println("Lỗi: Ngày đặt không được để trống.");
            return false;
        }

        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            System.err.println("Lỗi: Ngày trả phòng phải sau ngày nhận phòng.");
            return false;
        }

        if (checkIn.isBefore(LocalDate.now())) {
            System.err.println("Lỗi: Không thể đặt phòng cho ngày trong quá khứ.");
            return false;
        }

        if (totalPrice <= 0) {
            System.err.println("Lỗi: Tổng tiền không hợp lệ.");
            return false;
        }
        Booking newBooking = new Booking(userId, roomId, checkIn, checkOut, totalPrice);
            return bookingDAO.createBooking(newBooking);
    }
    // Thêm vào class BookingService
    public List<Booking> getBookingsByRoom(int roomId) {
        return bookingDAO.getBookingsByRoom(roomId);
    }

    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> existingBookings = bookingDAO.getBookingsByRoom(roomId);
        for (Booking b : existingBookings) {
            // Logic: (Ngày bắt đầu mới < Ngày kết thúc cũ) VÀ (Ngày kết thúc mới > Ngày bắt đầu cũ)
            if (checkIn.isBefore(b.getCheckOut()) && checkOut.isAfter(b.getCheckIn())) {
                return false; // Bị trùng
            }
        }
        return true;
    }
}