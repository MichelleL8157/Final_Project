import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Generator {
    public void createName(String inputName, String inputPass) {
        try {
            FileOutputStream fos = new FileOutputStream("src/names.data");
            String input = inputName + "; " + inputPass;
            fos.write(input.getBytes());
            fos.close();
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
        }
    }


    public void generateProg(String userName) {
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
            System.out.println("Generate progress error");
        }
    }
}
