package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.HomestayDAO;
import udn.vku.greenstayapp.model.Homestay;
import java.util.List;

public class HomestayService {
    private HomestayDAO homestayDAO;

    public HomestayService() {
        this.homestayDAO = new HomestayDAO();
    }

    public List<Homestay> getAllHomestays() {
        return homestayDAO.getAllHomestays();
    }

    public boolean addHomestay(Homestay h) {
        if (h.getName().isEmpty() || h.getPrice() < 0) return false;
        return homestayDAO.addHomestay(h);
    }

    // --- MỚI ---
    public boolean updateHomestay(Homestay h) {
        if (h.getId() == 0 || h.getName().isEmpty()) return false;
        return homestayDAO.updateHomestay(h);
    }

    public boolean deleteHomestay(int id) {
        return homestayDAO.deleteHomestay(id);
    }
}