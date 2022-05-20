import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Filing {



    public void generate(String userName) {
        try {
            File f = new File("src/" + userName + ".data");
            Scanner s = new Scanner(f);
            Inventory info = new Inventory();
            String data = s.nextLine();
            String[] dataArr = data.split(";\\s*");
            info.setMoney(Double.parseDouble(dataArr[0]));
            info.setAppeal(Integer.parseInt(dataArr[1]));
            info.setEnergy(Integer.parseInt(dataArr[2]));
            info.setCatEnergy(Integer.parseInt(dataArr[3]));
            info.setActionCount(Integer.parseInt(dataArr[4]));
            ArrayList<Food> foods = new ArrayList<Food>();
            while (s.hasNextLine()) {
                String values = s.nextLine();
                String[] valuesArr = values.split(";\\s*");
                Food food = new Food(valuesArr[0], Double.parseDouble(valuesArr[1]), Integer.parseInt(valuesArr[2]));
                foods.add(food);
            }
            info.setFoods(foods);
            s.close();
            GUIController gui = new GUIController(info);
        }
        catch (FileNotFoundException e) {
            Inventory info = new Inventory();
            System.out.print("Welcome!\nYou're playing as a homeless man who needs to manage his resources carefully to survive each day.\nAre you ready? ");
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
