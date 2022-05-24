import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GUIActivities implements ActionListener { //has activity
    private JTextArea infoScreen;
    private Inventory info;
    private JFrame frame;
    private JPanel entry;
    private JPanel entry2;

    public GUIActivities(Inventory info) {
        infoScreen = new JTextArea(20, 30);
        entry2 = null;
        this.info = info;
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
        infoScreen.setText("inventory loading...");
        infoScreen.setFont(new Font("Courier", Font.PLAIN, 15));
        infoScreen.setWrapStyleWord(true);
        infoScreen.setLineWrap(true);
        inventoryPanel.add(infoScreen);

        JPanel entry = new JPanel();
        JButton begButton = new JButton("Beg");
        JButton scavengeButton = new JButton("Scavenge");
        JButton showerButton = new JButton("Shower");
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton saveButton = new JButton("Save");
        entry.add(begButton);
        entry.add(scavengeButton);
        entry.add(showerButton);
        entry.add(buyButton);
        entry.add(sellButton);
        entry.add(saveButton);
        this.entry = entry;

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
        if (entry2 != null) {
            entry2.setVisible(false);
        }
        String status = "Days Passed: " + info.getDaysPassed() + "\n";
        status += "Money: $" + info.getMoney() + "\n";
        status += "Appeal: " + info.getAppeal() + "\n";
        status += "Energy: " + info.getEnergy() + "\n";
        status += "Cat Energy: " + info.getCatEnergy() + "\n";
        status += "Actions Left: " + info.getActionCount();
        status += "\n\nWhat do you want to do? Choose an action.";
        infoScreen.setText(status);
    }

    public void save() {
        entry.setVisible(false);
        String ending = "";
        if ((info.getActionCount() == 0 && info.getEnergy() < 0)) {
            ending += "Congrats on surviving for " + info.getDaysPassed() + " days!\n";
        }
        ending += "Do you want to save your data?";
        infoScreen.setText(ending);
        JPanel entry2 = new JPanel();
        this.entry2 = entry2;
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        entry2.add(yesButton);
        entry2.add(noButton);
        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        frame.add(entry2, BorderLayout.SOUTH);
        entry2.setVisible(true);
    }

    public void transition() {
        infoScreen.setText("");
        if (info.getActionCount() == 0 && info.getEnergy() < 1) {
            save();
        } else if (info.getActionCount() < 1 && info.getEnergy() > 0) {
            info.addDaysPassed();
            info.setActionCount(3);
            info.setEnergy(info.getEnergy() - 2);
            loadInfoScreen();
        } else {
            loadInfoScreen();
        }
    }

    public void scavenge() {
        info.setEnergy(0);
        info.setActionCount(0);
        transition();
    }

    public void beg() {
        entry.setVisible(false);
        int earningsWhole = (int) (Math.random() * 100) + 1;
        DecimalFormat dF = new DecimalFormat("#.##0");
        String earningsString = 
        double earnings = new Double(df.format(earningsWhole / 100.0));
        info.setMoney(info.getMoney() + earnings);
        infoScreen.setText("You wait for a few hours...\nYou earned $" + earnings + "!");
        JPanel entry2 = new JPanel();
        this.entry2 = entry2;
        JButton continueButton = new JButton("Continue");
        entry2.add(continueButton);
        continueButton.addActionListener(this);
        frame.add(entry2, BorderLayout.SOUTH);
        entry2.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();
        if (text.equals("Beg")) {
            beg();
        } else if (text.equals("Scavenge")) {
            scavenge();
        } else if (text.equals("Save")) {
            save();
        } else if (text.equals("Continue")) {
            transition();
        } else if (text.equals("Yes")) {
            info.save();
            entry2.setVisible(false);
            infoScreen.setText("Progress saved;\nCome again!");
        } else if (text.equals("No")) {
            entry2.setVisible(false);
            infoScreen.setText("Progress unsaved.\nCome again!");
        }
    }
}
