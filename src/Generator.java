import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Generator {
    public static void main(String[] args) {
        try {
            File f = new File("src/inventory.data");
            Scanner s = new Scanner(f);
            ArrayList<Food> foods = new ArrayList<Food>();
            while (!s.nextLine().equals("")) {
                String values = s.nextLine();
                String[] valuesArr = values.split(";\\s*");
                Food food = new Food(valuesArr[0], Double.parseDouble(valuesArr[1]), Integer.parseInt(valuesArr[2]));
                foods.add(food);
            }
            s.nextLine();
            Inventory info = new Inventory();
            info.setFoods(foods);
            info.setMoney(s.nextDouble());
            info.setAppeal(s.nextInt());
            info.setEnergy(s.nextInt());
            info.setCatEnergy(s.nextInt());
            info.setActionCount(s.nextInt());
            s.close();
            Activity act = new Activity();
            GUIController gui = new GUIController(info);
        }
        catch (FileNotFoundException e) {
            Inventory info = new Inventory();
            System.out.println("Welcome!\nYou're playing as a homeless man who needs to manage his resources carefully to survive each day.\nAre you ready?");
            Scanner s = new Scanner(System.in);
            String ans = s.nextLine();
            ans = ans.toLowerCase();
            if (ans.equals("yes") || ans.equals("y")) {
                info.save();
                GUIController gui = new GUIController(info);
            } else {
                System.out.println("Come back next time!");
            }
        }
    }
}
