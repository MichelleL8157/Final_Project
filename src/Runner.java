import java.io.IOException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws IOException {
        Generator files = new Generator();
        System.out.print("Welcome to the Homeless Simulation. What do you want to do?\n" +
                "1. Make new profile\n2. Open saved profile\nChoice #: ");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        while (!(input.equals("1") || input.equals("2"))) {
            System.out.print("Please choose a valid number: ");
            input = s.nextLine();
        }
        System.out.print("Username: ");
        String userName = s.nextLine();
        System.out.print("Password: ");
        String password = s.nextLine();
        if (input.equals("1")) {
            files.createName(userName, password);
        } else {
            files.generateName(userName, password);
        }

    }
}
