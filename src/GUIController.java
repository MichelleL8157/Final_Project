import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GUIController extends Activity implements ActionListener { //has activity
    private JTextArea infoScreen;
    private Inventory info;
    private Generator client;

    public GUIController(Inventory info) {
        super(info);
        infoScreen = new JTextArea(20, 30);
        this.info = info;
        setupGUI();
        loadInfoScreen();
    }

    public void setupGUI() {
        JFrame frame = new JFrame("Days Passed: " + info.getDaysPassed());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcome = new JLabel("  Days Passed: " + info.getDaysPassed());
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
        entry.add(begButton);
        entry.add(scavengeButton);
        entry.add(showerButton);
        entry.add(buyButton);
        entry.add(sellButton);

        frame.add(logoWelcome, BorderLayout.NORTH);
        frame.add(inventoryPanel,BorderLayout.CENTER);
        frame.add(entry, BorderLayout.SOUTH);

        begButton.addActionListener(this);
        scavengeButton.addActionListener(this);
        showerButton.addActionListener(this);
        buyButton.addActionListener(this);
        sellButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void loadInfoScreen() {
        String status = "";
        status += "Money: $" + info.getMoney() + "\n";
        status += "Appeal: " + info.getAppeal() + "\n";
        status += "Energy: $" + info.getEnergy() + "\n";
        status += "Cat Energy: $" + info.getCatEnergy() + "\n";
        status += "Actions Left: $" + info.getActionCount();
        status += "\n\nWhat do you want to do?";
        infoScreen.setText(status);
    }

    public void beg() {
        String text = "You wait for a few hours...";
        int earningsWhole = (int) (Math.random() * 100) + 1;
        double earnings = earningsWhole / 100.0;
        info.setMoney(info.getMoney() + earnings);
        System.out.println("You gained: $" + earnings + ".\nCurrent Savings: $" + info.getMoney());
        transition();
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();
        Activity act = new Activity(info);
        if (text.equals("Beg")) {
            act.feed();
        } else if (text.equals("Scavenge")) {

        }
    }
}
