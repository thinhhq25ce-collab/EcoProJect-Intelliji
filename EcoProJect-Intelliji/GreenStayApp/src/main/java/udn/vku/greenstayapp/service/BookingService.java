package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.BookingDAO;
import udn.vku.greenstayapp.model.Booking;
import java.time.LocalDate;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    /**
     * Xử lý logic đặt phòng
     * @param userId ID người dùng
     * @param roomId ID phòng
     * @param checkIn Ngày nhận
     * @param checkOut Ngày trả
     * @param totalPrice Tổng tiền (MỚI THÊM)
     * @return true nếu đặt thành công
     */
    public boolean bookRoom(int userId, int roomId, LocalDate checkIn, LocalDate checkOut, double totalPrice) {
        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (checkIn == null || checkOut == null) {
            System.err.println("Lỗi: Ngày check-in/check-out không được để trống!");
            return false;
        }

        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            System.err.println("Lỗi: Ngày trả phòng phải sau ngày nhận phòng!");
            return false;
        }

        if (checkIn.isBefore(LocalDate.now())) {
            System.err.println("Lỗi: Không thể đặt phòng trong quá khứ!");
            return false;
        }

        if (totalPrice <= 0) {
            System.err.println("Lỗi: Giá tiền không hợp lệ!");
            return false;
        }

        // 2. Tạo đối tượng Booking với Constructor mới (có totalPrice)
        Booking newBooking = new Booking(userId, roomId, checkIn, checkOut, totalPrice);

        // 3. Gọi DAO để lưu xuống Database
        return bookingDAO.createBooking(newBooking);
    }
}