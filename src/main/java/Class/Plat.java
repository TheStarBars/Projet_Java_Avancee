package Class;

public class Plat {
    private String name;
    private String description;
    private double price;
    private double cost;
    private String image;

    public Plat(String name, String description, double price, double cost, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getCost() {
        return cost;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

