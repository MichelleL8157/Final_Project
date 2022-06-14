import java.io.*;
import java.util.ArrayList;

public class Inventory extends Food implements Serializable {
    private final String USER_NAME;
    private ArrayList<Food> foods;
    private double money;
    private int appeal;
    private int energy;
    private int catEnergy;
    private int daysPassed;
    private int actionCount;

    public Inventory(String userName) { //default stats
        super();
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

    public void save() {
        try {
            File file = new File("src/ProgressSave/" + USER_NAME + ".data");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter("src/ProgressSave/" + USER_NAME + ".data");
            fileWriter.write(getMoney() + "; ");
            fileWriter.write(getAppeal() + "; ");
            fileWriter.write(getEnergy() + "; ");
            fileWriter.write(getCatEnergy() + "; ");
            fileWriter.write(getDaysPassed() + "; ");
            fileWriter.write(getActionCount() + "");
            File fileFood = new File("src/ProgressSave/" + USER_NAME + "Food.data");
            fileFood.createNewFile();
            FileOutputStream saveFoods = new FileOutputStream("src/ProgressSave/" + USER_NAME + "Food.data");
            ObjectOutputStream saveFoodsOut = new ObjectOutputStream(saveFoods);
            for (Food food: foods) {
                saveFoodsOut.writeObject(food);
            }
            saveFoods.close();
            saveFoodsOut.close();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }
}