import java.util.Scanner;
import java.util.ArrayList;

public class Generator {
    public static void main(String[] args) {
        System.out.println("Welcome to the Homeless Simulation. What do you want to do?\n" +
                "1. Make new profile");
        Scanner s = new Scanner(System.in);
        String userName = s.nextLine();
        Filing files = new Filing();
        files.newUser(userName);
    }
}
