package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.BookingDAO;
import udn.vku.greenstayapp.model.Booking;
import java.time.LocalDate;

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
}