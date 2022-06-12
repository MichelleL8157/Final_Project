public class Food {
    private static final Food[] FOOD_SHOP = {new Food("Water                      ", 0.35, 2),
            new Food("Bread                      ",0.4, 3), new Food("Bread Crust            ", 0.15, 1),
            new Food("Soda                       ", 0.45, 3), new Food("Candy                     ", 0.5, 3),
            new Food("Potato                     ", 0.2, 1), new Food("Apple                      ", 0.7, 5),
            new Food("Chocolate               ", 0.6, 4), new Food("Chips                      ", 0.25, 1),
            new Food("Cigarettes             ", 1, 7)};
    private static final Food[] TRASH_PILE = {new Food("Yogurt                     ", 0.1, 1),
            new Food("Chicken Bones       ", 0.15, 1), new Food("Old Apple                ", 0.2, 2),
            new Food("Half-Bottled Water  ", 0.15, 1), new Food("Stale Chips             ", 0.2, 1)};
    private String name;
    private double price;
    private int energy;

    public Food() {}

    public Food(String name, double price, int energy) {
        this.name = name;
        this.price = price;
        this.energy = energy;
    }

    public Food[] getFOOD_SHOP() { return FOOD_SHOP; }
    public Food[] getTRASH_PILE() { return TRASH_PILE; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getEnergy() { return energy; }
}
