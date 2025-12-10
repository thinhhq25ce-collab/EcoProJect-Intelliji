package udn.vku.greenstayapp.dao;

import udn.vku.greenstayapp.model.Homestay;
import java.util.ArrayList;
import java.util.List;

public class HomestayDAO {
    // Trong thực tế, bạn sẽ dùng Connection của JDBC để Query SQL ở đây
    private List<Homestay> mockDatabase = new ArrayList<>();

    public HomestayDAO() {
        // Dữ liệu giả mẫu
        mockDatabase.add(new Homestay(1, "Green Bamboo Hut", "Sapa", 500000, true));
        mockDatabase.add(new Homestay(2, "City Concrete Home", "Hanoi", 300000, false));
        mockDatabase.add(new Homestay(3, "Eco Palm House", "Da Lat", 700000, true));
    }

    public List<Homestay> getAllHomestays() {
        return mockDatabase;
    }

    public void addHomestay(Homestay homestay) {
        mockDatabase.add(homestay);
        // Code SQL: INSERT INTO Homestay...
    }
}
