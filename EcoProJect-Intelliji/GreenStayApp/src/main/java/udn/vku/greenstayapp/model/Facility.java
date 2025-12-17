package udn.vku.greenstayapp.model;

public class Facility {
    private int id;
    private int homestayId;
    private String name;

    public Facility(int id, int homestayId, String name) {
        this.id = id;
        this.homestayId = homestayId;
        this.name = name;
    }

    public int getHomestayId() {
        return homestayId;
    }

    // Getters
    public String getName() { return name; }

    @Override
    public String toString() { return name; }
}