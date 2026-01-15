package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.RoomDAO;
import udn.vku.greenstayapp.model.Room;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService {
    private RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
    }

    public List<Room> getRoomsByHomestayId(int homestayId) {
        return roomDAO.getRoomsByHomestay(homestayId);
    }

    // Lấy chỉ những phòng còn trống (Available = true)
    public List<Room> getAvailableRooms(int homestayId) {
        List<Room> allRooms = roomDAO.getRoomsByHomestay(homestayId);
        // Lọc danh sách bằng Java Stream
        return allRooms.stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }
}