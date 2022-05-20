/**import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity {
    private Inventory info;

    public Activity(Inventory info) {
        this.info = info;
    }
    public void nextDay() {
        info.addDaysPassed();
        System.out.println("Day: " + info.getDaysPassed());
        action();
    }

    public void transition() {
        if (info.getActionCount() > 0) {
            action();
        } else {
            nextDay();
        }
    }

    public void action() {}

    public void beg() {
        System.out.println("You wait for a few hours...");
        int earningsWhole = (int) (Math.random() * 100) + 1;
        double earnings = earningsWhole / 100.0;
        info.setMoney(info.getMoney() + earnings);
        System.out.println("You gained: $" + earnings + ".\nCurrent Savings: $" + info.getMoney());
        transition();
    }

    public void scavenge() {
        System.out.println("You go to the local dumpster and start scavenging for a few hours...");
        int itemCount = (int) (Math.random() * 3);
        ArrayList<Food> scavenged = new ArrayList<Food>();
        for (int i = 0; i != itemCount; i++) {
            int randomIndex = (int) (Math.random() * +TRASH_PILE.length);
            scavenged.add(TRASH_PILE[i]);
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
        for (int i = 0; i != info.getFoods().size(); i++) {
            Food food = info.getFoods().get(i);
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getEnergy());
        }
        System.out.println("Which do you want to use?\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > info.getFoods().size() || choice < 0) {
            System.out.println("Please choose a valid option or 0 to exit.\nChoice #: ");
            choice = s.nextInt();
        }
        if (choice == 0) {
            System.out.println("Exit Feeding Screen");
        } else {
            Food toUse = info.getFoods().get(choice - 1);
            System.out.println("Is this for you(1) or for the cat(2)?\nChoice #: ");
            int feedWho = s.nextInt();
            while (feedWho < 1 || feedWho > 2) {
                System.out.println("Please choose a valid option.\nChoice #: ");
                feedWho = s.nextInt();
            }
            if (feedWho == 1) {
                info.setEnergy(info.getEnergy() + toUse.getEnergy());
                System.out.println("Current Energy: " + info.getEnergy());
            } else {
                info.setEnergy(info.getEnergy() - 1);
                info.setCatEnergy(info.getCatEnergy() + toUse.getEnergy());
                System.out.println("Cat's Current Energy: " + info.getCatEnergy());
            }
        }
        transition();
    }

    public void buy() {
        System.out.println("Shop: # - name - energy refuel - price");
        for (int i = 0; i != FOOD_SHOP.length; i++) {
            Food food = FOOD_SHOP[i];
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getEnergy() + " - " + food.getPrice());
        }
        System.out.println("Which do you want to buy? You have $" + info.getMoney() + "\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > FOOD_SHOP.length || choice < 0) {
            System.out.println("Please choose a valid option or 0 to exit.\nChoice #: ");
            choice = s.nextInt();
        }
        if (choice == 0) {
            System.out.println("Exit Purchase Screen");
        } else {
            info.setActionCount(info.getActionCount() - 1);
            Food toBuy = FOOD_SHOP[choice - 1];
            if (info.getMoney() < toBuy.getPrice()) {
                System.out.println("You are short $" + (info.getMoney() - toBuy.getPrice()) + " for a " + toBuy.getName());
            } else {
                info.setMoney(info.getMoney() - toBuy.getPrice());
                System.out.println("Transaction completed!\nMoney Left: $" + info.getMoney());
            }
        }
        transition();
    }

    public void sell() {
        info.setActionCount(info.getActionCount() - 1);
        System.out.println("Inventory: # - name - price");
        for (int i = 0; i != info.getFoods().size(); i++) {
            Food food = info.getFoods().get(i);
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getPrice());
        }
        System.out.println("Which do you want to sell? You have $" + info.getMoney() + "\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > info.getFoods().size() || choice < 0) {
            System.out.println("Please choose a valid option or 0 to exit.\nChoice #: ");
            choice = s.nextInt();
        }
        if (choice == 0) {
            System.out.println("Exit Trade Screen");
        } else {
            Food toSell = info.getFoods().get(choice - 1);
            info.setMoney(info.getMoney() + toSell.getPrice());

        }
        transition();
    }
}
*/