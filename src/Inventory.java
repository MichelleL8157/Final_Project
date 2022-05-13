import java.util.ArrayList;

public class Inventory {
    private ArrayList<Food> foods;
    private double money;
    private int appeal;
    private int energy;
    private int catEnergy;
    private int daysPassed;
    private int actionCount;

    public ArrayList<Food> getFoods() { return foods; }
    public double getMoney() { return money; }
    public int getAppeal() { return appeal; }
    public int getEnergy() { return energy; }
    public int getCatEnergy() { return catEnergy; }
    public int getDaysPassed() { return daysPassed; }
    public int getActionCount() { return actionCount; }

    public void setMoney(double amt) { money = amt; }
    public void setAppeal(int amt) { appeal = amt; }
    public void setEnergy(int amt) { energy = amt; }
    public void setCatEnergy(int amt) { catEnergy = amt; }
    public void addDaysPassed() { daysPassed++; }
    public void setActionCount(int amt) { actionCount = amt; }

    public String toString() {
        String i = "Day: " + (daysPassed - 1);
        String ii = "Money: $" + money;
        String iii = "Appeal: " + appeal;
        String iv = "Energy: ";
        for (int i = 0; i != energy; i++) {
            System.out.println();
        }
        return i;
    }


}