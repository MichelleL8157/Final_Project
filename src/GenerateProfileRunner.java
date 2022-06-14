import java.util.Scanner;

public class GenerateProfileRunner {
    public static void main(String[] args) {
        GenerateProfile files = new GenerateProfile();
        System.out.print("Welcome to the Homeless Simulation. What do you want to do?\n" +
                "1. Make new profile\n2. Open saved profile\nChoice #: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!(input.equals("1") || input.equals("2"))) {
            System.out.print("Please choose a valid number: ");
            input = scanner.nextLine();
        }
        System.out.print("Username: ");
        String userName = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (input.equals("1")) files.createName(userName, password);
        else files.generateName(userName, password);
    }
}
