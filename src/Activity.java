import java.util.Scanner;

public class Activity extends Inventory{
    private Food[] foodShop;
    private String[] actions;

    public Activity() {
        foodShop = new Food[5];
        actions = new String[6];
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
        }
    }

    public void nextDay() {
        addDaysPassed();
        System.out.println("")
    }

    public void feed() {
        setActionCount(getActionCount() - 1);
        System.out.println("Inventory: # - name - energy refuel");
        for (int i = 0; i != getFoods().size(); i++) {
            Food food = getFoods().get(i);
            System.out.println((i + 1) + " - " + food.getName() + " - " + food.getEnergy());
        }
        System.out.println("Which do you want to use?\nChoice #: ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        while (choice > getFoods().size() || choice < 1) {
            System.out.println("Please choose a valid option.\nChoice #: ");
            choice = s.nextInt();
        }
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
        if (getActionCount() > 0) {
            action();
        } else {
            nextDay();
        }
    }
}
