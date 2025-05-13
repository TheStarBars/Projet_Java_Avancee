package Class;

public class Plat {
    private String name;
    private String description;
    private double price;
    private String image;

    public Plat(String name, String description, double price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
    public String getName() {
        return name;
    }
}
