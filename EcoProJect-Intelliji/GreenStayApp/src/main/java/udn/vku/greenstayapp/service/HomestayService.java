package udn.vku.greenstayapp.service;

import udn.vku.greenstayapp.dao.HomestayDAO;
import udn.vku.greenstayapp.model.Homestay;
import java.util.List;
import java.util.stream.Collectors;

public class HomestayService {
    private HomestayDAO homestayDAO;

    public HomestayService() {
        this.homestayDAO = new HomestayDAO();
    }

    public List<Homestay> getAllHomestays() {
        return homestayDAO.getAllHomestays();
    }

    public List<Homestay> getEcoFriendlyHomestays() {
        return homestayDAO.getAllHomestays().stream()
                .filter(Homestay::isEcoCertified)
                .collect(Collectors.toList());
    }
    public void addHomestay(Homestay h) {
        homestayDAO.addHomestay(h);
    }
}
