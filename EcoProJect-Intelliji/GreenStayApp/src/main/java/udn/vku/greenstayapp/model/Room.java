package udn.vku.greenstayapp.model;

public class Room {
    private int id;
    private String name;
    private String type;
    private double pricePerNight;
    private boolean isAvailable;

    public Room() { }

    // Constructor dùng trong CatalogController
    public Room(int id, String name, String type, double pricePerNight, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}