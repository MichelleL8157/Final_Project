public class Food {
    private String name;
    private double price;
    private int energy;

    public Food(String name, double price, int energy) {
        this.name = name;
        this.price = price;
        this.energy = energy;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getEnergy() { return energy; }
}
