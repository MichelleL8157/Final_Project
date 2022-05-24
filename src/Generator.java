import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class Generator {
    public void createName(String inputName, String inputPass) {
        try {
            Writer addName = new BufferedWriter(new FileWriter("src/names.data", true));
            addName.append("\n").append(inputName).append("; ").append(inputPass);
            addName.close();
            generateProg(inputName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateName(String inputName, String inputPass) {
        try {
            File f = new File("src/names.data");
            Scanner s = new Scanner(f);
            int line = 1;
            String userName = "";
            String userPass = "";
            while (s.hasNextLine()) {
                String usedProf = s.nextLine();
                String usedName = usedProf.substring(0, usedProf.indexOf(";"));
                if (usedName.equals(inputName)) {
                    userName = usedName;
                    userPass = usedProf.substring(usedProf.indexOf(";") + 2);
                    break;
                }
            }
            if (userName.equals("")) {
                System.out.println("There is no profile with the username: " + inputName);
            }
            if (inputPass.equals(userPass)) {
                generateProg(userName);
            } else {
                System.out.println("Wrong password!");
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("No names.data file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void generateProg(String userName) throws IOException {
        try {
            File f = new File("src/" + userName + ".data");
            Scanner s = new Scanner(f);
            Inventory info = new Inventory(userName);
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
            GUIActivities gui = new GUIActivities(info);
        }
        catch (FileNotFoundException e) {
            File f = new File("src/" + userName + ".data");
            f.createNewFile();
            FileWriter fileWriter = new FileWriter("src/" + userName + ".data");
            Inventory info = new Inventory(userName);
            fileWriter.write(info.getMoney() + "; ");
            fileWriter.write(info.getAppeal() + "; ");
            fileWriter.write(info.getEnergy() + "; ");
            fileWriter.write(info.getCatEnergy() + "; ");
            fileWriter.write(info.getDaysPassed() + "; ");
            fileWriter.write(info.getActionCount() + "");
            for (Food food: info.getFoods()) {
                fileWriter.write("\n" + food.getName() + "; " + food.getPrice() + food.getEnergy());
            }
            fileWriter.close();
            info.save();
        }
    }
}
