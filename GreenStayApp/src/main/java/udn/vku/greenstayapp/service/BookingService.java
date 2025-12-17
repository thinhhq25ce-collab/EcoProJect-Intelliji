package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.BookingDAO;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    public boolean createBooking(String username, String roomName, String date) {

        if (username == null || roomName == null || date.isEmpty()) {
            return false;
        }
        bookingDAO.addBooking(username, roomName, date);
        return true;
    }
}