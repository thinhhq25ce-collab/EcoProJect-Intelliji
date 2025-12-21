package udn.vku.greenstayapp.model;

public class Homestay {
    private int id;
    private String name;
    private String address;
    private double price;
    private boolean isEcoCertified;

    public Homestay() { }

    // Constructor CHUẨN: Giá tiền (double) đứng trước Eco (boolean)
    public Homestay(int id, String name, String address, double price, boolean isEcoCertified) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.isEcoCertified = isEcoCertified;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean getIsEcoCertified() { return isEcoCertified; }
    public void setIsEcoCertified(boolean ecoCertified) { isEcoCertified = ecoCertified; }
}