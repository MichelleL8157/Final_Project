import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity extends Inventory {
    private final Food[] foodShop;
    private final Food[] trashPile;
    private final String[] actions;

    public Activity() {
        Food[] foodShop = {new Food("Water", 0.35, 2), new Food("Bread", 0.4, 3), new Food("Bread Crust", 0.15, 1),
                    new Food("Soda", 0.45, 3), new Food("Candy", 0.5, 3), new Food("Potato", 0.2, 1),
                    new Food("Apple", 0.7, 5), new Food("Chocolate", 0.6, 4),
                    new Food("Chips", 0.25, 1), new Food("Cigarettes", 1, 7)};
        Food[] trashPile = {new Food("Yogurt", 0.1, 1), new Food("Chicken Bones", 0.15, 1),new Food("Old Apple", 0.4, 2),
                    new Food("Half-Bottled Water", 0.15, 1), new Food("Stale Chips", 0.2, 1)};
        String[] actions = {"Feed", "Buy", "Sell"};
        this.foodShop = foodShop;
        this.trashPile = trashPile;
        this.actions = actions;
    }

    public void action() {
        System.out.println(getActionCount() + " left- What do you want to do?:");
        for (int i = 0; i != actions.length; i++) {
            System.out.println(i + ". " + actions[i]);
        }
        System.out.println("Choice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        if (choice == 1) {
            //feed
        }
        else if (choice == 2) {
            //buy
        } else if (choice == 3) {
            //sell
        }
    }

    public void nextDay() {
        addDaysPassed();
        System.out.println("Day: " + getDaysPassed());
        action();
    }

    public void transition() {
        if (getActionCount() > 0) {
            action();
        } else {
            nextDay();
        }
    }

    public void beg() {
        System.out.println("You wait for a few hours...");
        int earningsWhole = (int) (Math.random() * 100) + 1;
        double earnings = earningsWhole / 100.0;
        setMoney(getMoney() + earnings);
        System.out.println("You gained: $" + earnings + ".\nCurrent Savings: $" + getMoney());
        transition();
    }

    public void scavenge() {
        System.out.println("You go to the local dumpster and start scavenging for a few hours...");
        int itemCount = (int) (Math.random() * 3);
        ArrayList<Food> scavenged = new ArrayList<Food>();
        for (int i = 0; i != itemCount; i++) {
            int randomIndex = (int) (Math.random() * trashPile.length);
            scavenged.add(trashPile[i]);
        }
        if (itemCount == 0) {
            System.out.println("Unfortunately, you found nothing.");
        } else {
            System.out.print("You successfully found a");
            if (itemCount == 1) { System.out.println(scavenged.get(0)); }
            else { System.out.println(scavenged.get(0) + " and a" + scavenged.get(1)); }
        }
        transition();
    }

    public void shower() {
        System.out.println("You go to the local park and start showering in the vacant bathroom...");
        int appealGain = (int) (Math.random() * 3);
    }

    public void feed() {
        System.out.println("Inventory: # - name - energy refuel");
        for (int i = 0; i != getFoods().size(); i++) {
            Food food = getFoods().get(i);
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getEnergy());
        }
        System.out.println("Which do you want to use?\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > getFoods().size() || choice < 0) {
            System.out.println("Please choose a valid option or 0 to exit.\nChoice #: ");
            choice = s.nextInt();
        }
        if (choice == 0) {
            System.out.println("Exit Feeding Screen");
        } else {
            Food toUse = getFoods().get(choice - 1);
            System.out.println("Is this for you(1) or for the cat(2)?\nChoice #: ");
            int feedWho = s.nextInt();
            while (feedWho < 1 || feedWho > 2) {
                System.out.println("Please choose a valid option.\nChoice #: ");
                feedWho = s.nextInt();
            }
            if (feedWho == 1) {
                setEnergy(getEnergy() + toUse.getEnergy());
                System.out.println("Current Energy: " + getEnergy());
            } else {
                setEnergy(getEnergy() - 1);
                setCatEnergy(getCatEnergy() + toUse.getEnergy());
                System.out.println("Cat's Current Energy: " + getCatEnergy());
            }
        }
        transition();
    }

    public void buy() {
        System.out.println("Shop: # - name - energy refuel - price");
        for (int i = 0; i != foodShop.length; i++) {
            Food food = foodShop[i];
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getEnergy() + " - " + food.getPrice());
        }
        System.out.println("Which do you want to buy? You have $" + getMoney() + "\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > foodShop.length || choice < 0) {
            System.out.println("Please choose a valid option or 0 to exit.\nChoice #: ");
            choice = s.nextInt();
        }
        if (choice == 0) {
            System.out.println("Exit Purchase Screen");
        } else {
            setActionCount(getActionCount() - 1);
            Food toBuy = foodShop[choice - 1];
            if (getMoney() < toBuy.getPrice()) {
                System.out.println("You are short $" + (getMoney() - toBuy.getPrice()) + " for a " + toBuy.getName());
            } else {
                setMoney(getMoney() - toBuy.getPrice());
                System.out.println("Transaction completed!\nMoney Left: $" + getMoney());
            }
        }
        transition();
    }

    public void sell() {
        setActionCount(getActionCount() - 1);
        System.out.println("Inventory: # - name - price");
        for (int i = 0; i != getFoods().size(); i++) {
            Food food = getFoods().get(i);
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getPrice());
        }
        System.out.println("Which do you want to sell? You have $" + getMoney() + "\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > getFoods().size() || choice < 0) {
            System.out.println("Please choose a valid option or 0 to exit.\nChoice #: ");
            choice = s.nextInt();
        }
        if (choice == 0) {
            System.out.println("Exit Trade Screen");
        } else {
            Food toSell = getFoods().get(choice - 1);
            setMoney(getMoney() + toSell.getPrice());

        }
        transition();
    }
}
