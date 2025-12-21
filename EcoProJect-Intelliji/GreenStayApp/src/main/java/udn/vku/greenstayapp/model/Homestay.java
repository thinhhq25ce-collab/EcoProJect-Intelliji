package udn.vku.greenstayapp.model;

public class Homestay {
    private int id;
    private String name;
    private String address;
    private double price;
    private boolean isEcoCertified;

    public Homestay(int id, String name, String address, double price, boolean isEcoCertified) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.isEcoCertified = isEcoCertified;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getPrice() { return price; }
    public boolean isEcoCertified() { return isEcoCertified; }

    @Override
    public String toString() {
        return name + " (Eco: " + (isEcoCertified ? "Yes" : "No") + ")";
    }
}
