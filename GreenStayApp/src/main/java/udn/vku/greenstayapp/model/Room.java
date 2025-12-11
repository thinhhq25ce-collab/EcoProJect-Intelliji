package udn.vku.greenstayapp.model;

public class Room {
    private int id;
    private String name;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int id, String name, double pricePerNight, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    public String getName() { return name; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return isAvailable; }

    @Override
    public String toString() {
        return name + " - " + pricePerNight + " VND";
    }
}