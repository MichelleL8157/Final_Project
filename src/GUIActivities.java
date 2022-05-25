import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GUIActivities implements ActionListener { //has activity
    private final JTextArea INFO_SCREEN;
    private final Inventory INFO;
    private JFrame frame;
    private JPanel entry;
    private JPanel entry2;
    private JPanel continueButton;
    private JPanel foodBuy;
    private JPanel foodUse;
    private JTextField choiceField;

    public GUIActivities(Inventory info) {
        INFO_SCREEN = new JTextArea(20, 30);
        choiceField = new JTextField();
        this.INFO = info;
        setupGUI();
        loadInfoScreen();
    }

    public void setupGUI() {
        JFrame frame = new JFrame("Homeless Simulation");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcome = new JLabel("Homeless Simulation");
        welcome.setFont(new Font("Courier", Font.BOLD, 20));
        welcome.setForeground(Color.cyan);
        JPanel logoWelcome = new JPanel();
        logoWelcome.add(welcome);

        JPanel inventoryPanel = new JPanel();
        INFO_SCREEN.setText("inventory loading...");
        INFO_SCREEN.setFont(new Font("Courier", Font.PLAIN, 15));
        INFO_SCREEN.setWrapStyleWord(true);
        INFO_SCREEN.setLineWrap(true);
        inventoryPanel.add(INFO_SCREEN);

        JPanel entry = new JPanel();
        JButton begButton = new JButton("Beg");
        JButton scavengeButton = new JButton("Scavenge");
        JButton showerButton = new JButton("Shower");
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton saveButton = new JButton("Eat");
        entry.add(begButton);
        entry.add(scavengeButton);
        entry.add(showerButton);
        entry.add(buyButton);
        entry.add(sellButton);
        entry.add(saveButton);
        this.entry = entry;

        JPanel entry2 = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        entry2.add(yesButton);
        entry2.add(noButton);
        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        frame.add(entry2, BorderLayout.SOUTH);
        entry2.setVisible(false);
        this.entry2 = entry2;

        JPanel continueButton = new JPanel();
        JButton continueButtonPhysical = new JButton("Continue");
        continueButton.add(continueButtonPhysical);
        continueButtonPhysical.addActionListener(this);
        frame.add(continueButton, BorderLayout.SOUTH);
        continueButton.setVisible(false);
        this.continueButton = continueButton;

        JLabel choiceBox = new JLabel("Choice #: ");
        JPanel foodBuy = new JPanel();
        JTextField choiceField = new JTextField(3);
        this.choiceField = choiceField;
        JButton purchaseButton = new JButton("Purchase");
        JButton stopButton = new JButton("Stop");
        foodBuy.add(choiceBox);
        foodBuy.add(choiceField);
        foodBuy.add(purchaseButton);
        foodBuy.add(stopButton);
        purchaseButton.addActionListener(this);
        frame.add(foodBuy, BorderLayout.SOUTH);
        foodBuy.setVisible(false);
        this.foodBuy = foodBuy;

        JPanel foodUse = new JPanel();
        JButton consumeButton = new JButton("Consume");
        foodUse.add(choiceBox);
        foodUse.add(choiceField);
        foodUse.add(consumeButton);
        foodUse.add(stopButton);
        consumeButton.addActionListener(this);
        frame.add(foodUse, BorderLayout.SOUTH);
        foodUse.setVisible(false);
        this.foodUse = foodUse;

        frame.add(logoWelcome, BorderLayout.NORTH);
        frame.add(inventoryPanel,BorderLayout.CENTER);
        frame.add(entry, BorderLayout.SOUTH);

        begButton.addActionListener(this);
        scavengeButton.addActionListener(this);
        showerButton.addActionListener(this);
        buyButton.addActionListener(this);
        sellButton.addActionListener(this);
        saveButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void loadInfoScreen() {
        entry.setVisible(true);
        String status = "Days Passed: " + INFO.getDaysPassed() + "\n";
        status += "Money: $" + INFO.getMoney() + "\n";
        status += "Appeal: " + INFO.getAppeal() + "\n";
        status += "Energy: " + INFO.getEnergy() + "\n";
        status += "Cat Energy: " + INFO.getCatEnergy() + "\n";
        status += "Actions Left: " + INFO.getActionCount();
        status += "\n\nWhat do you want to do? Choose an action.";
        INFO_SCREEN.setText(status);
    }

    public void save() {
        entry.setVisible(false);
        String ending = "";
        if ((INFO.getActionCount() == 0 && INFO.getEnergy() < 0)) {
            ending += "It's the end of the day, but you don't have any energy left...\nCongrats on surviving for " + INFO.getDaysPassed() + " days!\n";
        }
        ending += "Do you want to save your data?";
        INFO_SCREEN.setText(ending);
        entry2.setVisible(true);
    }

    public void transition() {
        INFO_SCREEN.setText("");
        if (INFO.getActionCount() == 0 && INFO.getEnergy() < 1) {
            save();
        } else if (INFO.getActionCount() < 1 && INFO.getEnergy() > 0) {
            INFO.addDaysPassed();
            INFO.setActionCount(3);
            INFO.changeEnergy(-2);
            loadInfoScreen();
        } else {
            loadInfoScreen();
        }
    }

    public void beg() {
        INFO.decreaseActionCount();
        INFO.changeEnergy(-2);
        entry.setVisible(false);
        int earningsWhole = (int) (Math.random() * 100) + 1;
        DecimalFormat dF = new DecimalFormat("#.##");
        dF.setRoundingMode(RoundingMode.DOWN);
        String earningsString = dF.format(earningsWhole / 100.0);
        double earnings = Double.parseDouble(earningsString);
        INFO.changeMoney(earnings);
        INFO_SCREEN.setText("You wait for a few hours...\nYou earned $" + earningsString + "!");
        continueButton.setVisible(true);
    }

    public void scavenge() {
        INFO.setEnergy(0);
        INFO.setActionCount(0);
        transition();
    }

    public void shower() {
        INFO.decreaseActionCount();
        INFO.changeEnergy(-3);
        entry.setVisible(false);
        int appeal = (int) (Math.random() * 3) + 1;
        INFO.changeAppeal(appeal);
        INFO_SCREEN.setText("You take a shower in the park's public restroom...\nYou gained " + appeal + " appeal point!");
        continueButton.setVisible(true);
    }

    public void buy() {
        entry.setVisible(false);
        String screenText = "Shop:\n# - name - energy refuel - price\n";
        for (int i = 0; i != INFO.getFOOD_SHOP().length; i++) {
            Food food = INFO.getFOOD_SHOP()[i];
            screenText += (i + 1) + " - " + food.getName() + " - " + food.getEnergy() + " - $" + food.getPrice() + "\n";
        }
        screenText += "\nWhich do you want to buy? You have $" + INFO.getMoney();
        INFO_SCREEN.setText(screenText);
        foodUse.setVisible(true);
    }

    public void continuePurchase() {
        String foodChoiceString = choiceField.getText();
        int foodChoice = Integer.parseInt(foodChoiceString);
        Food food = INFO.getFOOD_SHOP()[foodChoice - 1];
        String screenText = "";
        if (INFO.getMoney() < food.getPrice()) {
            screenText += "Purchase Denied:\nYou are short $" + (food.getPrice() - INFO.getMoney());
        } else {
            screenText += "Purchase Successful:\nYou spent $" + food.getPrice();
            INFO.changeMoney(-food.getPrice());
        }
        INFO_SCREEN.setText(screenText + " for a " + food.getName().toLowerCase() + "." );
        continueButton.setVisible(true);
    }

    public void eat() {
        INFO.decreaseActionCount();
        INFO.changeEnergy(-2);
        entry.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();
        switch (text) {
            case "Beg" -> beg();
            case "Scavenge" -> scavenge();
            case "Shower" -> shower();
            case "Buy" -> buy();
            case "Eat" -> eat();
            case "Save" -> save();
            case "Continue" -> {
                transition();
                continueButton.setVisible(false);
            }
            case "Purchase" -> {
                continuePurchase();
                foodBuy.setVisible(false);
            }
            case "Stop" -> {
                transition();
                foodBuy.setVisible(false);
                foodUse.setVisible(false);
            }
            case "Yes" -> {
                INFO.save();
                entry2.setVisible(false);
                INFO_SCREEN.setText("Progress saved;\nCome again!");
            }
            case "No" -> {
                entry2.setVisible(false);
                INFO_SCREEN.setText("Progress not saved.\nCome again!");
            }
        }
    }
}
