import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GUISimulationActivities implements ActionListener {
    private final JTextArea INFO_SCREEN;
    private final Inventory INFO;
    private JFrame frame;
    private JLabel defaultImage;
    private JLabel begImage;
    private JLabel eatOrFeedImage;
    private JLabel scavengeImage;
    private JLabel showerImage;
    private JLabel napImage;
    private JLabel daysPassed;
    private JPanel actionsPanel;
    private JPanel continuePanel;
    private JPanel savePanel;
    private JPanel foodBuyPanel;
    private JPanel foodUsePanel;
    private JPanel foodGivePanel;
    private JTextField choiceField;
    private int napCount;
    private JPanel inventoryPanel;

    public GUISimulationActivities(Inventory info) {
        INFO_SCREEN = new JTextArea(15, 25);
        choiceField = new JTextField();
        this.INFO = info;
        setUpUI();
        napCount = 0;
        loadInfoScreen();
    }

    public void setUpUI() {
        JFrame frame = new JFrame("Homeless Simulation");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel daysPassed = new JLabel("Days Passed: 0");
        daysPassed.setFont(new Font("Tempus Sans", Font.PLAIN, 30));
        daysPassed.setForeground(Color.black);
        JPanel logoWelcome = new JPanel();
        logoWelcome.add(daysPassed);
        this.daysPassed = daysPassed;

        JPanel inventoryPanel = new JPanel();
        INFO_SCREEN.setFont(new Font("Tempus Sans", Font.PLAIN, 25));
        INFO_SCREEN.setWrapStyleWord(true);
        INFO_SCREEN.setLineWrap(true);
        INFO_SCREEN.setBackground(new Color(242, 215, 213));
        inventoryPanel.add(INFO_SCREEN);

        ImageIcon infoIcon = new ImageIcon("src/Images/infoScreen.png");
        Image infoData = infoIcon.getImage();
        Image scaledInfoImage = infoData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        infoIcon = new ImageIcon(scaledInfoImage);
        defaultImage = new JLabel(infoIcon);
        inventoryPanel.add(defaultImage);
        this.inventoryPanel = inventoryPanel;

        JPanel actionsPanel = new JPanel();
        GUIButton begButton = new GUIButton();
        begButton.setText("Beg");
        GUIButton scavengeButton = new GUIButton();
        scavengeButton.setText("Scavenge");
        GUIButton showerButton = new GUIButton();
        showerButton.setText("Shower");
        GUIButton napButton = new GUIButton();
        napButton.setText("Nap");
        GUIButton shopButton = new GUIButton();
        shopButton.setText("Shop");
        GUIButton eatButton = new GUIButton();
        eatButton.setText("Eat");
        GUIButton feedCatButton = new GUIButton();
        feedCatButton.setText("Feed Cat");
        GUIButton saveButton = new GUIButton();
        saveButton.setText("Save");
        begButton.addActionListener(this);
        scavengeButton.addActionListener(this);
        showerButton.addActionListener(this);
        napButton.addActionListener(this);
        shopButton.addActionListener(this);
        eatButton.addActionListener(this);
        feedCatButton.addActionListener(this);
        saveButton.addActionListener(this);
        actionsPanel.add(begButton);
        actionsPanel.add(scavengeButton);
        actionsPanel.add(showerButton);
        actionsPanel.add(napButton);
        actionsPanel.add(shopButton);
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
        begImage = new JLabel();
        eatOrFeedImage = new JLabel();
        scavengeImage = new JLabel();
        showerImage = new JLabel();
        napImage = new JLabel();
        frame.pack();
        frame.setVisible(true);
    }

    public void continueOption() {
        JPanel continuePanel = new JPanel();
        GUIButton continueButton = new GUIButton();
        continueButton.setText("Continue");
        continuePanel.add(continueButton);
        continueButton.addActionListener(this);
        frame.add(continuePanel, BorderLayout.SOUTH);
        continuePanel.setVisible(true);
        this.continuePanel = continuePanel;
    }

    private void loadInfoScreen() {
        actionsPanel.setVisible(true);
        daysPassed.setText("Days Passed: " + INFO.getDaysPassed());
        String moneyString = (INFO.getMoney() * 100) + "";
        moneyString = moneyString.substring(0, moneyString.indexOf("."));
        if (moneyString.length() == 1) { moneyString = "0.0" + moneyString; }
        else if (moneyString.length() == 2) { moneyString = "0." + moneyString; }
        else {
            String cents = moneyString.substring(moneyString.length() - 2);
            String dollars = moneyString.substring(0, moneyString.length() - 2);
            moneyString = dollars + "." + cents;
        }
        Double money = Double.parseDouble(moneyString);
        INFO.setMoney(money);
        String status = "\n\n\nMoney:         $" + money + "\nAppeal:          " + INFO.getAppeal() +
                "\nEnergy:          " + INFO.getEnergy() + "\nCat Energy:   ";
        if (INFO.getCatEnergy() == -1) {
            status +=  "X";
        } else {
            status += INFO.getCatEnergy();
        }
        status += "\nActions Left:  " + INFO.getActionCount();
        status += "\n\nWhat do you want to do?";
        INFO_SCREEN.setText(status);
    }

    public void save() {
        actionsPanel.setVisible(false);
        boolean gameOver = false;
        String ending = "\n\n\n\n";
        if (napCount >= 2) {
            gameOver = true;
            ending += "You basically napped the whole day and find yourself sleeping, never waking up again...";
        }
        if (INFO.getActionCount() == 0 && INFO.getEnergy() <= 0) {
            gameOver = true;
            ending += "It's the end of the day, but you don't have enough energy to wake up the next day...";
        }
        if (gameOver) ending += "\n\nGame Over! You ended the day with " + INFO.getEnergy() + " energy.";
        else ending+= "\n";
        ending += "\nDo you want to save your data?";
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
        defaultImage.setVisible(true);
        begImage.setVisible(false);
        eatOrFeedImage.setVisible(false);
        scavengeImage.setVisible(false);
        showerImage.setVisible(false);
        napImage.setVisible(false);
        continuePanel.setVisible(false);
        INFO_SCREEN.setText("");
        if (INFO.getActionCount() == 0 && INFO.getEnergy() <= 0) { //game over
            save();
        } else if (INFO.getActionCount() == 0 && INFO.getEnergy() > 0) { //to next day
            if (napCount >= 2) { save(); }
            else {
                napCount = 0;
                INFO.addDaysPassed();
                INFO.setActionCount(3);
                INFO.changeCatEnergy(-1);
                String text = "\n\n\n\n\n\n";
                if (INFO.getCatEnergy() == 0) {
                    INFO.changeCatEnergy(-1);
                    String badMews = text + "It's the end of the day, and your cat doesn't have any energy left...\nBut you must go on.";
                    INFO_SCREEN.setText(badMews);
                    continueOption();
                } else {
                    INFO_SCREEN.setText(text + "You finished 1 whole day. Congrats!");
                    continueOption();
                }
            }
        } else loadInfoScreen();
    }

    public void beg() {
        ImageIcon begIcon = new ImageIcon("src/Images/beg.png");
        Image begData = begIcon.getImage();
        Image scaledBegImage = begData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        begIcon = new ImageIcon(scaledBegImage);
        begImage = new JLabel(begIcon);
        begImage.setVisible(true);
        inventoryPanel.add(begImage);
        defaultImage.setVisible(false);
        INFO.decreaseActionCount();
        INFO.changeEnergy(-2);
        INFO.changeAppeal(-1);
        actionsPanel.setVisible(false);
        int earningsWhole = (int) (Math.random() * 50) + 1;
        for (int i = 3; i < INFO.getAppeal(); i++) {
            int bonus = (int) (Math.random() * 10) + 1;
            earningsWhole += bonus;
        }
        DecimalFormat dF = new DecimalFormat("#.##");
        dF.setRoundingMode(RoundingMode.DOWN);
        String earningsString = dF.format(earningsWhole / 100.0);
        double earnings = Double.parseDouble(earningsString);
        INFO.changeMoney(earnings);
        INFO_SCREEN.setText("\n\n\n\n\nYou wait for a few hours holding up a cardboard paper...\nYou earned $" + earningsString + "!");
        continueOption();
    }

    public void scavenge() {
        ImageIcon scavengeIcon = new ImageIcon("src/Images/scavenge.png");
        Image scavengeData = scavengeIcon.getImage();
        Image scaledScavengeIcon = scavengeData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        scavengeIcon = new ImageIcon(scaledScavengeIcon);
        scavengeImage = new JLabel(scavengeIcon);
        scavengeImage.setVisible(true);
        inventoryPanel.add(scavengeImage);
        defaultImage.setVisible(false);
        INFO.decreaseActionCount();
        INFO.changeEnergy(-3);
        INFO.changeAppeal(-2);
        actionsPanel.setVisible(false);
        int amtFound = (int) (Math.random() * 3) + 1;
        ArrayList<Food> foodsFound = new ArrayList<Food>();
        for (int i = 0; i != amtFound; i++) {
            int foodFoundNum = (int) (Math.random() * 5);
            Food foodFound = INFO.getTRASH_PILE()[foodFoundNum];
            foodsFound.add(foodFound);
            INFO.addFood(foodFound);
        }
        String screenText = "\n\n\n\nYou found " + amtFound;
        if (amtFound == 1) screenText += " item.";
        else screenText += " items during your scavenge.";
        screenText += "\n\n#  name                      energy   price\n";
        for (int i = 0; i != foodsFound.size(); i++) {
            Food food = foodsFound.get(i);
            screenText += (i + 1) + "  " + food.getName() + food.getEnergy() + "           $" + food.getPrice() + "\n";
        }
        INFO_SCREEN.setText(screenText);
        continueOption();
    }

    public void shower() {
        ImageIcon showerIcon = new ImageIcon("src/Images/shower.png");
        Image showerData = showerIcon.getImage();
        Image scaledShowerIcon = showerData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        showerIcon = new ImageIcon(scaledShowerIcon);
        showerImage = new JLabel(showerIcon);
        showerImage.setVisible(true);
        inventoryPanel.add(showerImage);
        defaultImage.setVisible(false);
        INFO.decreaseActionCount();
        INFO.changeEnergy(-3);
        actionsPanel.setVisible(false);
        int appealGain = (int) (Math.random() * 2) + 1;
        INFO.changeAppeal(appealGain);
        String screenText = "\n\n\n\n\nYou take a shower in the park's public restroom...\nYou gained ";
        if (appealGain == 0) { screenText += "no appeal points..."; }
        else if (appealGain == 1) { screenText += "an appeal point."; }
        else { screenText += appealGain + " appeal points!"; }
        INFO_SCREEN.setText(screenText);
        continueOption();
    }

    public void shop() {
        actionsPanel.setVisible(false);
        String screenText = "Shop:\n#  name                      energy   price\n";
        for (int i = 0; i != INFO.getFOOD_SHOP().length; i++) {
            Food food = INFO.getFOOD_SHOP()[i];
            screenText += (i + 1) + "  " + food.getName() + food.getEnergy() + "           $" + food.getPrice() + "\n";
        }
        screenText += "\nWhich do you want to buy? You have $" + INFO.getMoney();
        INFO_SCREEN.setText(screenText);
        JLabel choiceBox = new JLabel("Choice #: ");
        JPanel foodBuyPanel = new JPanel();
        JTextField choiceField = new JTextField(3);
        choiceField.setPreferredSize(new Dimension(130, 40));
        this.choiceField = choiceField;
        GUIButton buyButton = new GUIButton();
        buyButton.setText("Buy");
        GUIButton stopButton = new GUIButton();
        stopButton.setText("Stop");
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
        if (foodChoice < 0 || foodChoice > INFO.getFOOD_SHOP().length) {
            INFO_SCREEN.setText("That's not an option.");
        } else {
            Food food = INFO.getFOOD_SHOP()[foodChoice - 1];
            String foodNameLong = food.getName();
            int index = foodNameLong.length() - 1;
            while (foodNameLong.substring(index).indexOf(" ") == 0) {
                index--;
            }
            String foodName = foodNameLong.substring(0, index + 1);
            String screenText = "\n\n\n\n\n\n";
            if (INFO.getMoney() < food.getPrice()) {
                screenText += "Purchase Denied:\nYou are short $" + (food.getPrice() - INFO.getMoney());
            } else {
                screenText += "Purchase Successful:\nYou spent $" + food.getPrice();
                INFO.addFood(food);
                INFO.changeMoney(-food.getPrice());
            }
            INFO_SCREEN.setText(screenText + " for a " + foodName.toLowerCase() + "." );

        }
        continueOption();
    }

    public void nap() {
        ImageIcon napIcon = new ImageIcon("src/Images/nap.png");
        Image napData = napIcon.getImage();
        Image scaledNapImage = napData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        napIcon = new ImageIcon(scaledNapImage);
        napImage = new JLabel(napIcon);
        napImage.setVisible(true);
        inventoryPanel.add(napImage);
        defaultImage.setVisible(false);
        INFO.decreaseActionCount();
        actionsPanel.setVisible(false);
        int energyGain = (int) (Math.random() * 4);
        INFO.changeEnergy(energyGain);
        String screenText = "\n\n\n\n\nYou go take a nap...\n\nYou gained ";
        if (energyGain == 0) { screenText += "no energy points..."; }
        else if (energyGain == 1) { screenText += "an energy point."; }
        else { screenText += energyGain + " energy points!"; }
        int moneyStolenWhole = (int) (Math.random() * (INFO.getMoney() + 1));
        for (int i = 3; i < INFO.getAppeal(); i++) {
            int bonus = (int) (Math.random() * 10) + 1;
            moneyStolenWhole += bonus;
        }
        if (moneyStolenWhole > INFO.getMoney() * 100) {
            moneyStolenWhole = (int) (INFO.getMoney() * 100);
        }
        DecimalFormat dF = new DecimalFormat("#.##");
        dF.setRoundingMode(RoundingMode.DOWN);
        String moneyStolenString = dF.format(moneyStolenWhole / 100.0);
        double moneyStolen = Double.parseDouble(moneyStolenString);
        if (moneyStolen == 0) { screenText +=  "\nLuckily you weren't robbed this time!"; }
        else { screenText += "\nAlso, while you were sleeping, you were robbed $" + moneyStolen + "!"; }
        INFO.changeMoney(-moneyStolen);
        INFO_SCREEN.setText(screenText);
        napCount++;
        continueOption();
    }

    public void eat() {
        ImageIcon eatFeedIcon = new ImageIcon("src/Images/eatOrFeed.png");
        Image eatFeedData = eatFeedIcon.getImage();
        Image scaledEatFeedIcon = eatFeedData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        eatFeedIcon = new ImageIcon(scaledEatFeedIcon);
        eatOrFeedImage = new JLabel(eatFeedIcon);
        eatOrFeedImage.setVisible(true);
        inventoryPanel.add(eatOrFeedImage);
        defaultImage.setVisible(false);
        actionsPanel.setVisible(false);
        String screenText = "Inventory:\n#  name                      energy   price\n";
        for (int i = 0; i != INFO.getFoods().size(); i++) {
            Food food = INFO.getFoods().get(i);
            screenText += (i + 1) + "  " + food.getName() + food.getEnergy() + "           $" + food.getPrice() + "\n";
        }
        screenText += "\nWhich do you want to eat? You have " + INFO.getEnergy() + " energy.";
        INFO_SCREEN.setText(screenText);
        JLabel choiceBox = new JLabel("Choice #: ");
        JTextField choiceField = new JTextField(3);
        choiceField.setPreferredSize(new Dimension(130, 40));
        this.choiceField = choiceField;
        GUIButton useButton = new GUIButton();
        useButton.setText("Use");
        GUIButton stopButton = new GUIButton();
        stopButton.setText("Stop");
        JPanel foodUsePanel = new JPanel();
        foodUsePanel.add(choiceBox);
        foodUsePanel.add(choiceField);
        foodUsePanel.add(useButton);
        foodUsePanel.add(stopButton);
        useButton.addActionListener(this);
        stopButton.addActionListener(this);
        frame.add(foodUsePanel, BorderLayout.SOUTH);
        foodUsePanel.setVisible(true);
        this.foodUsePanel = foodUsePanel;
    }

    public void use() {
        foodUsePanel.setVisible(false);
        String foodChoiceString = choiceField.getText();
        int foodChoice = Integer.parseInt(foodChoiceString);
        if (foodChoice < 0 || foodChoice > INFO.getFoods().size()) {
            INFO_SCREEN.setText("That's not an option.");
            continueOption();
        } else {
            Food food = INFO.getFoods().get(foodChoice - 1);
            INFO.changeEnergy(food.getEnergy());
            INFO.removeFood(foodChoice - 1);
            String foodNameLong = food.getName();
            int index = foodNameLong.length() - 1;
            while (foodNameLong.substring(index).indexOf(" ") == 0) {
                index--;
            }
            String foodName = foodNameLong.substring(0, index + 1);
            INFO_SCREEN.setText("\n\n\n\n\n\nYou used a " + foodName.toLowerCase() + " and gained " + food.getEnergy() + " energy!");
            continueOption();
        }
    }

    public void feedCat() {
        ImageIcon eatFeedIcon = new ImageIcon("src/Images/eatOrFeed.png");
        Image eatFeedData = eatFeedIcon.getImage();
        Image scaledEatFeedIcon = eatFeedData.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
        eatFeedIcon = new ImageIcon(scaledEatFeedIcon);
        eatOrFeedImage = new JLabel(eatFeedIcon);
        eatOrFeedImage.setVisible(true);
        inventoryPanel.add(eatOrFeedImage);
        defaultImage.setVisible(false);
        actionsPanel.setVisible(false);
        String screenText = "Inventory:\n#  name                      energy   price\n";
        for (int i = 0; i != INFO.getFoods().size(); i++) {
            Food food = INFO.getFoods().get(i);
            screenText += (i + 1) + "  " + food.getName() + food.getEnergy() + "           $" + food.getPrice() + "\n";
        }
        screenText += "\nWhich do you want to feed to your cat?\nThe cat has " + INFO.getCatEnergy() + " energy.";
        INFO_SCREEN.setText(screenText);
        JLabel choiceBox = new JLabel("Choice #: ");
        JTextField choiceField = new JTextField(3);
        choiceField.setPreferredSize(new Dimension(130, 40));
        this.choiceField = choiceField;
        GUIButton feedButton = new GUIButton();
        feedButton.setText("Feed");
        GUIButton stopButton = new GUIButton();
        stopButton.setText("Stop");
        JPanel foodGivePanel = new JPanel();
        foodGivePanel.add(choiceBox);
        foodGivePanel.add(choiceField);
        foodGivePanel.add(feedButton);
        foodGivePanel.add(stopButton);
        feedButton.addActionListener(this);
        stopButton.addActionListener(this);
        frame.add(foodGivePanel, BorderLayout.SOUTH);
        foodGivePanel.setVisible(true);
        this.foodGivePanel = foodGivePanel;
    }

    public void feed() {
        foodGivePanel.setVisible(false);
        String foodChoiceString = choiceField.getText();
        int foodChoice = Integer.parseInt(foodChoiceString);
        if (foodChoice < 0 || foodChoice > INFO.getFoods().size()) {
            INFO_SCREEN.setText("That's not an option.");
            continueOption();
        } else {
            Food food = INFO.getFoods().get(foodChoice - 1);
            INFO.changeCatEnergy(food.getEnergy());
            INFO.removeFood(foodChoice - 1);
            String foodNameLong = food.getName();
            int index = foodNameLong.length() - 1;
            while (foodNameLong.substring(index).indexOf(" ") == 0) {
                index--;
            }
            String foodName = foodNameLong.substring(0, index + 1);
            String screenText = "\n\n\n\n\n\nYou used a " + foodName.toLowerCase() + " to feed the cat.\nThe cat gained " + food.getEnergy() + " energy!";
            INFO_SCREEN.setText(screenText);
            continueOption();
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();
        switch (text) {
            case "Beg" -> beg();
            case "Scavenge" -> scavenge();
            case "Shower" -> shower();
            case "Shop" -> shop();
            case "Nap" -> nap();
            case "Eat" -> eat();
            case "Use" -> use();
            case "Feed Cat" -> {
                if (INFO.getCatEnergy() == -1) {
                    actionsPanel.setVisible(false);
                    INFO_SCREEN.setText("You're too late...");
                    continueOption();
                } else { feedCat(); }
            }
            case "Feed" -> feed();
            case "Save" -> save();
            case "Continue" -> transition();
            case "Buy" -> continuePurchase();
            case "Stop" -> {
                foodBuyPanel.setVisible(false);
                foodUsePanel.setVisible(false);
                foodGivePanel.setVisible(false);
                defaultImage.setVisible(true);
                eatOrFeedImage.setVisible(false);
                loadInfoScreen();
            }
            case "Yes" -> {
                INFO.save();
                savePanel.setVisible(false);
                INFO_SCREEN.setText("\n\n\n\n\n\nProgress saved;\nCome again!");
            }
            case "No" -> {
                savePanel.setVisible(false);
                INFO_SCREEN.setText("\n\n\n\n\n\nProgress not saved.\nCome again!");
            }
        }
    }
}
