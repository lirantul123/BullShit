import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetRockGui {

    private PetRock petRock = new PetRock("Rocky");
    private JLabel statusLabel;
    private JLabel quoteLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PetRockGui().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Pet Rock (with GUI)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        createButtons(frame.getContentPane());
        createStatusLabel(frame.getContentPane());
        createQuoteLabel(frame.getContentPane());

        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }

    private void createButtons(Container container) {
        JButton playButton = createButton("Play", this::playClicked);
        JButton feedButton = createButton("Feed", this::feedClicked);
        JButton talkButton = createButton("Talk", this::talkClicked);

        container.add(playButton);
        container.add(feedButton);
        container.add(talkButton);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void createStatusLabel(Container container) {
        statusLabel = new JLabel();
        container.add(statusLabel);
        updateStatusLabel();
    }

    private void createQuoteLabel(Container container) {
        quoteLabel = new JLabel();
        container.add(quoteLabel);
    }

    private void updateStatusLabel() {
        String status = "Happiness: " + petRock.getHappiness() +
                ", Hunger: " + petRock.getHunger();
        statusLabel.setText(status);
    }

    private void playClicked(ActionEvent event) {
        petRock.play();
        updateStatusLabel();
    }

    private void feedClicked(ActionEvent event) {
        petRock.feed();
        updateStatusLabel();
    }

    private void talkClicked(ActionEvent event) {
        petRock.talk();
        quoteLabel.setText("\n\n\n\nMessage: " + PetRock.resultedQuote);
    }
}

class PetRock {

    public static String resultedQuote = "";
    private String name;
    private int happiness;
    private int hunger;

    public PetRock(String name) {
        this.name = name;
        this.happiness = 50;
        this.hunger = 50;
    }

    public void play() {
        System.out.println(name + " is happy!");
        increaseHappiness(10);
    }

    public void feed() {
        System.out.println(name + " is full!");
        decreaseHunger(10);
    }

    public void talk() {
        String[] phrases = {"Hi there!", "Rock on!", "I'm a rolling stone!", "Let's rock and roll!"};
        int index = (int) (Math.random() * phrases.length);
        resultedQuote = phrases[index];
        System.out.println(name + ": " + phrases[index]);
    }
    public int getHappiness() {
        return happiness;
    }

    public int getHunger() {
        return hunger;
    }

    private void increaseHappiness(int amount) {
        happiness += amount;
        if (happiness > 100) happiness = 100;
    }

    private void decreaseHunger(int amount) {
        hunger -= amount;
        if (hunger < 0) hunger = 0;
    }
}
