import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory {
    private final String USER_NAME;
    private final Food[] FOOD_SHOP;
    private final Food[] TRASH_PILE;
    private ArrayList<Food> foods;
    private double money;
    private int appeal;
    private int energy;
    private int catEnergy;
    private int daysPassed;
    private int actionCount;

    public Inventory(String userName) {
        Food[] foodShop = {new Food("Water", 0.35, 2), new Food("Bread", 0.4, 3), new Food("Bread Crust", 0.15, 1),
                new Food("Soda", 0.45, 3), new Food("Candy", 0.5, 3), new Food("Potato", 0.2, 1),
                new Food("Apple", 0.7, 5), new Food("Chocolate", 0.6, 4),
                new Food("Chips", 0.25, 1), new Food("Cigarettes", 1, 7)};
        Food[] trashPile = {new Food("Yogurt", 0.1, 1), new Food("Chicken Bones", 0.15, 1),new Food("Old Apple", 0.2, 2),
                new Food("Half-Bottled Water", 0.15, 1), new Food("Stale Chips", 0.2, 1)};
        FOOD_SHOP = foodShop;
        TRASH_PILE = trashPile;
        this.USER_NAME = userName;
        foods = new ArrayList<Food>();
        money = 0.0;
        appeal = 3;
        energy = 5;
        catEnergy = 5;
        daysPassed = 0;
        actionCount = 3;
    }

    public ArrayList<Food> getFoods() { return foods; }
    public Food[] getFOOD_SHOP() { return FOOD_SHOP; }
    public Food[] getTRASH_PILE() { return TRASH_PILE; }
    public String getUserName() { return USER_NAME; }
    public double getMoney() { return money; }
    public int getAppeal() { return appeal; }
    public int getEnergy() { return energy; }
    public int getCatEnergy() { return catEnergy; }
    public int getDaysPassed() { return daysPassed; }
    public int getActionCount() { return actionCount; }

    public void setFoods(ArrayList<Food> foodList) { foods = foodList; }

    public void setMoney(double amt) { money = amt; }
    public void changeMoney(double amt) { money += amt; }

    public void setAppeal(int amt) { appeal = amt; }
    public void changeAppeal(int amt) { appeal += amt; }

    public void setEnergy(int amt) { energy = amt; }
    public void changeEnergy(int amt) { energy += amt; }

    public void setCatEnergy(int amt) { catEnergy = amt; }
    public void changeCatEnergy(int amt) { catEnergy += amt; }

    public void setDaysPassed(int amt) { daysPassed = amt; }
    public void addDaysPassed() { daysPassed++; }

    public void setActionCount(int amt) { actionCount = amt; }
    public void decreaseActionCount() { actionCount--; }

    public void addFood(Food food) { foods.add(food); }
    public void removeFood(int index) { foods.remove(index); }

    public String toString() {
        String i = "Day: " + (daysPassed - 1);
        String ii = "Money: $" + money;
        String iii = "Appeal: " + appeal;
        String iv = "Energy: ";
        for (int k = 0; k != energy; k++) {
            System.out.println();
        }
        return i;
    }

    public void save() {
        try {
            File f = new File("src/" + USER_NAME + ".data");
            f.createNewFile();
            FileWriter fileWriter = new FileWriter("src/" + USER_NAME + ".data");
            fileWriter.write(getMoney() + "; ");
            fileWriter.write(getAppeal() + "; ");
            fileWriter.write(getEnergy() + "; ");
            fileWriter.write(getCatEnergy() + "; ");
            fileWriter.write(getDaysPassed() + "; ");
            fileWriter.write(getActionCount() + "");
            for (Food food: getFoods()) {
                fileWriter.write("\n" + food.getName() + "; " + food.getPrice() + food.getEnergy());
            }
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }
}