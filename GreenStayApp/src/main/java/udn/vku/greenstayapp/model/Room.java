package udn.vku.greenstayapp.model;

public class Room {
    private int id;
    private int homestayId; // <--- THÊM MỚI
    private String name;
    private String type;
    private double pricePerNight;
    private boolean isAvailable;

    public Room() {}

    // Constructor ĐẦY ĐỦ (Dùng khi lấy từ DB lên)
    public Room(int id, int homestayId, String name, String type, double pricePerNight, boolean isAvailable) {
        this.id = id;
        this.homestayId = homestayId;
        this.name = name;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    // Constructor NGẮN GỌN (Giữ lại để tương thích với code cũ, mặc định homestayId = 0)
    public Room(int id, String name, String type, double pricePerNight, boolean isAvailable) {
        this(id, 0, name, type, pricePerNight, isAvailable);
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHomestayId() { return homestayId; } // <--- Getter mới
    public void setHomestayId(int homestayId) { this.homestayId = homestayId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}