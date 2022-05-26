import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GUISimulationActivities implements ActionListener { //has activity
    private final JTextArea INFO_SCREEN;
    private final Inventory INFO;
    private JLabel daysPassed;
    private JFrame frame;
    private JPanel actionsPanel;
    private JPanel continuePanel;
    private JPanel savePanel;
    private JPanel foodBuyPanel;
    private JPanel foodUsePanel;
    private JPanel foodGivePanel;
    private JTextField choiceField;

    public GUISimulationActivities(Inventory info) {
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
        JLabel daysPassed = new JLabel("Days Passed: 0");
        daysPassed.setFont(new Font("Courier", Font.BOLD, 20));
        daysPassed.setForeground(Color.black);
        this.daysPassed = daysPassed;
        JPanel logoWelcome = new JPanel();
        logoWelcome.add(daysPassed);

        JPanel inventoryPanel = new JPanel();
        INFO_SCREEN.setText("inventory loading...");
        INFO_SCREEN.setFont(new Font("Courier", Font.PLAIN, 15));
        INFO_SCREEN.setWrapStyleWord(true);
        INFO_SCREEN.setLineWrap(true);
        inventoryPanel.add(INFO_SCREEN);

        JPanel actionsPanel = new JPanel();
        JButton begButton = new JButton("Beg");
        JButton scavengeButton = new JButton("Scavenge");
        JButton showerButton = new JButton("Shower");
        JButton shopButton = new JButton("Shop");
        JButton sellButton = new JButton("Sell");
        JButton eatButton = new JButton("Eat");
        JButton feedCatButton = new JButton("Feed Cat");
        JButton saveButton = new JButton("Save");
        begButton.addActionListener(this);
        scavengeButton.addActionListener(this);
        showerButton.addActionListener(this);
        shopButton.addActionListener(this);
        sellButton.addActionListener(this);
        eatButton.addActionListener(this);
        feedCatButton.addActionListener(this);
        saveButton.addActionListener(this);
        actionsPanel.add(begButton);
        actionsPanel.add(scavengeButton);
        actionsPanel.add(showerButton);
        actionsPanel.add(shopButton);
        actionsPanel.add(sellButton);
        actionsPanel.add(eatButton);
        actionsPanel.add(feedCatButton);
        actionsPanel.add(saveButton);
        this.actionsPanel = actionsPanel;
        frame.add(logoWelcome, BorderLayout.NORTH);
        frame.add(inventoryPanel,BorderLayout.CENTER);
        frame.add(actionsPanel, BorderLayout.SOUTH);

        foodBuyPanel = new JPanel();
        foodUsePanel = new JPanel();
        foodGivePanel = new JPanel();

        frame.pack();
        frame.setVisible(true);
    }

    public void continueOption() {
        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");
        continuePanel.add(continueButton);
        continueButton.addActionListener(this);
        frame.add(continuePanel, BorderLayout.SOUTH);
        continuePanel.setVisible(true);
        this.continuePanel = continuePanel;
    }

    private void loadInfoScreen() {
        daysPassed = new JLabel("Days Passed: " + INFO.getDaysPassed());
        actionsPanel.setVisible(true);
        String status = "Money: $" + INFO.getMoney() + "\n";
        status += "Appeal: " + INFO.getAppeal() + "\n";
        status += "Energy: " + INFO.getEnergy() + "\n";
        status += "Cat Energy: " + INFO.getCatEnergy() + "\n";
        status += "Actions Left: " + INFO.getActionCount();
        status += "\n\nWhat do you want to do? Choose an action.";
        INFO_SCREEN.setText(status);
    }

    public void save() {
        actionsPanel.setVisible(false);
        String ending = "";
        if ((INFO.getActionCount() == 0 && INFO.getEnergy() < 0)) {
            ending += "It's the end of the day, but you don't have any energy left...\nCongrats on surviving for " + INFO.getDaysPassed() + " days!\n";
        }
        ending += "Do you want to save your data?";
        INFO_SCREEN.setText(ending);
        JPanel savePanel = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        savePanel.add(yesButton);
        savePanel.add(noButton);
        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        frame.add(savePanel, BorderLayout.SOUTH);
        this.savePanel = savePanel;
        savePanel.setVisible(true);
    }

    public void transition() {
        INFO_SCREEN.setText("");
        if (INFO.getActionCount() == 0 && INFO.getEnergy() < 1) {
            save();
        } else if (INFO.getActionCount() < 1 && INFO.getEnergy() > 0) {
            INFO.addDaysPassed();
            INFO.setActionCount(3);
            INFO.changeEnergy(-2);
            INFO.changeCatEnergy(-1);
            if (INFO.getCatEnergy() == 0) {
                INFO.changeCatEnergy(-1);
                String badMews = "It's the end of the day, but your cat doesn't have any energy left...\nBut you must go on.";
                INFO_SCREEN.setText(badMews);
                continueOption();
            } else {
                loadInfoScreen();
            }
        } else {
            loadInfoScreen();
        }
    }

    public void beg() {
        INFO.decreaseActionCount();
        INFO.changeEnergy(-2);
        actionsPanel.setVisible(false);
        int earningsWhole = (int) (Math.random() * 50) + 1;
        for (int i = 3; i < INFO.getAppeal(); i++) {
            int bonus = (int) (Math.random() * 10) + 1;
        }
        DecimalFormat dF = new DecimalFormat("#.##");
        dF.setRoundingMode(RoundingMode.DOWN);
        String earningsString = dF.format(earningsWhole / 100.0);
        double earnings = Double.parseDouble(earningsString);
        INFO.changeMoney(earnings);
        INFO_SCREEN.setText("You wait for a few hours...\nYou earned $" + earningsString + "!");
        continueOption();
    }

    public void scavenge() {
        INFO.setEnergy(30);
        INFO.setActionCount(3);
        transition();
    }

    public void shower() {
        INFO.decreaseActionCount();
        INFO.changeEnergy(-3);
        actionsPanel.setVisible(false);
        int appealGain = (int) (Math.random() * 3) + 1;
        INFO.changeAppeal(appealGain);
        String screenText = "You take a shower in the park's public restroom...\nYou gained ";
        if (appealGain == 1) { screenText += "an appeal point."; }
        else { screenText += appealGain + " appeal points!"; }
        INFO_SCREEN.setText(screenText);
        continueOption();
    }

    public void shop() {
        actionsPanel.setVisible(false);
        String screenText = "Shop:\n# - name - energy refuel - price\n";
        for (int i = 0; i != INFO.getFOOD_SHOP().length; i++) {
            Food food = INFO.getFOOD_SHOP()[i];
            screenText += (i + 1) + " - " + food.getName() + " - " + food.getEnergy() + " - $" + food.getPrice() + "\n";
        }
        screenText += "\nWhich do you want to buy? You have $" + INFO.getMoney();
        INFO_SCREEN.setText(screenText);
        JLabel choiceBox = new JLabel("Choice #: ");
        JPanel foodBuyPanel = new JPanel();
        JTextField choiceField = new JTextField(3);
        this.choiceField = choiceField;
        JButton buyButton = new JButton("Buy");
        JButton stopButton = new JButton("Stop");
        foodBuyPanel.add(choiceBox);
        foodBuyPanel.add(choiceField);
        foodBuyPanel.add(buyButton);
        foodBuyPanel.add(stopButton);
        buyButton.addActionListener(this);
        stopButton.addActionListener(this);
        frame.add(foodBuyPanel, BorderLayout.SOUTH);
        foodBuyPanel.setVisible(true);
        this.foodBuyPanel = foodBuyPanel;
    }

    public void continuePurchase() {
        foodBuyPanel.setVisible(false);
        String foodChoiceString = choiceField.getText();
        int foodChoice = Integer.parseInt(foodChoiceString);
        Food food = INFO.getFOOD_SHOP()[foodChoice - 1];
        String screenText = "";
        if (INFO.getMoney() < food.getPrice()) {
            screenText += "Purchase Denied:\nYou are short $" + (food.getPrice() - INFO.getMoney());
        } else {
            screenText += "Purchase Successful:\nYou spent $" + food.getPrice();
            INFO.addFood(food);
            INFO.changeMoney(-food.getPrice());
        }
        INFO_SCREEN.setText(screenText + " for a " + food.getName().toLowerCase() + "." );
        continueOption();
    }

    public void eat() {
        INFO.decreaseActionCount();
        INFO.changeEnergy(-2);
        actionsPanel.setVisible(false);
        String screenText = "Inventory:\n# - name - energy refuel\n";
        for (int i = 0; i != INFO.getFoods().size(); i++) {
            Food food = INFO.getFoods().get(i);
            screenText += (i + 1) + " - " + food.getName() + " - " + food.getEnergy() + " - $" + food.getPrice() + "\n";
        }
        screenText += "\nWhich do you want to eat? You have " + INFO.getEnergy() + " energy.";
        INFO_SCREEN.setText(screenText);
        JLabel choiceBox = new JLabel("Choice #: ");
        JTextField choiceField = new JTextField(3);
        this.choiceField = choiceField;
        JButton consumeButton = new JButton("Eat");
        JButton stopButton = new JButton("Stop");
        JPanel foodUsePanel = new JPanel();
        foodUsePanel.add(choiceBox);
        foodUsePanel.add(choiceField);
        foodUsePanel.add(consumeButton);
        foodUsePanel.add(stopButton);
        consumeButton.addActionListener(this);
        stopButton.addActionListener(this);
        frame.add(foodUsePanel, BorderLayout.SOUTH);
        foodUsePanel.setVisible(true);
        this.foodUsePanel = foodUsePanel;
    }

    public void use() {
        foodUsePanel.setVisible(false);
        String foodChoiceString = choiceField.getText();
        int foodChoice = Integer.parseInt(foodChoiceString);
        Food food = INFO.getFoods().get(foodChoice - 1);
        INFO.changeEnergy(food.getEnergy());
        String screenText = "You used a " + food.getName().toLowerCase() + " and gained " + food.getEnergy() + " energy!";
        INFO_SCREEN.setText(screenText);
        continueOption();
    }

    public void feedCat() {
        JLabel choiceBox = new JLabel("Choice #: ");
        JTextField choiceField = new JTextField(3);
        this.choiceField = choiceField;
        JPanel foodGivePanel = new JPanel();
        JButton feedButton = new JButton("Feed");
        JButton stopButton = new JButton("Stop");
        foodGivePanel.add(choiceBox);
        foodGivePanel.add(choiceField);
        foodGivePanel.add(feedButton);
        foodGivePanel.add(stopButton);
        feedButton.addActionListener(this);
        stopButton.addActionListener(this);
        frame.add(foodGivePanel, BorderLayout.SOUTH);
        foodGivePanel.setVisible(false);
        this.foodGivePanel = foodGivePanel;

    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();
        switch (text) {
            case "Beg" -> beg();
            case "Scavenge" -> scavenge();
            case "Shower" -> shower();
            case "Shop" -> shop();
            case "Eat" -> eat();
            case "Use" -> use();
            case "Feed Cat" -> feedCat();
            case "Save" -> save();
            case "Continue" -> {
                transition();
                continuePanel.setVisible(false);
            }
            case "Buy" -> { continuePurchase(); }
            case "Stop" -> {
                foodBuyPanel.setVisible(false);
                foodUsePanel.setVisible(false);
                foodGivePanel.setVisible(false);
                loadInfoScreen();
            }
            case "Yes" -> {
                INFO.save();
                savePanel.setVisible(false);
                INFO_SCREEN.setText("Progress saved;\nCome again!");
            }
            case "No" -> {
                savePanel.setVisible(false);
                INFO_SCREEN.setText("Progress not saved.\nCome again!");
            }
        }
    }
}
