package SnakeGame;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame extends JFrame implements ActionListener, KeyListener {
    private BufferedImage snakeBodyImage, appleImage;  

    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 21;
    private static final int GAME_SPEED = 140;
    private static int currCount = 0;
    private static int recordCount = 0;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private LinkedList<Point> snake;
    private Point fruit;
    private Direction direction;

    private Clip eatingAppleSound, smashSnakeSound;

    private Timer timer;
    private boolean isGameOver;

    private GamePanel gamePanel;
    private Login login;

    private JLabel scoreLabel;
    private JLabel recordLabel;

    public SnakeGame() {
        login = new Login();
        login.checkVerfication();

        setTitle("Snake Game");
        setSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Loading figures and sound effects
        try {
            snakeBodyImage = ImageIO.read(new File("pixelSnake.png"));
            appleImage = ImageIO.read(new File("pixelApple.png"));

        } catch (IOException e) {
            e.printStackTrace(); 
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("eatingAppleSound.wav"));
            eatingAppleSound = AudioSystem.getClip();
            eatingAppleSound.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("smashSnake.wav"));
            smashSnakeSound = AudioSystem.getClip();
            smashSnakeSound.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }



        // Initialize components
        initUI();

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Organize components in a panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.add(scoreLabel);
        topPanel.add(recordLabel);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        timer = new Timer(GAME_SPEED, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    private void initUI() {
        snake = new LinkedList<>();
        direction = Direction.RIGHT;
        isGameOver = false;

        spawnSnake();
        spawnFruit();

        scoreLabel = new JLabel("Score: 0");
        recordLabel = new JLabel("Record: 0");

        // Set borders around the labels
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        recordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gamePanel = new GamePanel();
    }

    private void updateScoreLabels() {
        scoreLabel.setText("Score: " + currCount);
        recordLabel.setText("Record: " + recordCount);
    }

    private void spawnSnake() {
        int initialSize = 3;
        for (int i = 0; i < initialSize; i++) {
            snake.add(new Point(GRID_SIZE / 2 - i, GRID_SIZE / 2));
        }
    }

    private void spawnFruit() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(GRID_SIZE - 5);
            y = random.nextInt(GRID_SIZE - 5);
        } while (snake.contains(new Point(x, y)));

        fruit = new Point(x, y);
    }

    private void move() {
        if (!isGameOver) {
            Point head = snake.getFirst();
            Point newHead;

            switch (direction) {
                case UP:
                    newHead = new Point(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                    break;
                case DOWN:
                    newHead = new Point(head.x, (head.y + 1) % GRID_SIZE);
                    break;
                case LEFT:
                    newHead = new Point((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                    break;
                case RIGHT:
                    newHead = new Point((head.x + 1) % GRID_SIZE, head.y);
                    break;
                default:
                    newHead = head;
            }

            if (newHead.equals(fruit)) {
                // If the new head is at the fruit position, add it to the snake without removing the last part
                snake.addFirst(newHead);
                spawnFruit();
                currCount++;
                //  ---- 
                playEatingSound();
                System.out.println(GAME_SPEED);
                updateScoreLabels();
            } else {
                // Otherwise, add the new head to the snake and remove the last part
                snake.addFirst(newHead);
                snake.removeLast();
            }

            if (isCollisionWithItself() || isCollisionWithBoard()) {
                // Game Over condition
                isGameOver = true;
                if (currCount > recordCount)
                    recordCount = currCount;
                updateScoreLabels();

                playSmashingSound();
                try {
                    Thread.sleep(100); // 0.1 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); 
                }

                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            }

            gamePanel.repaint();
        }
    }

    private boolean isCollisionWithItself() {
        Point head = snake.getFirst();
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollisionWithBoard() {
        Point head = snake.getFirst();
        return head.x < 0 || head.x >= GRID_SIZE || head.y < 0 || head.y >= GRID_SIZE;
    }

    private void resetGame() {
        snake.clear();
        direction = Direction.RIGHT;
        isGameOver = false;
        spawnSnake();
        spawnFruit();
        currCount = 0;
        updateScoreLabels();
        gamePanel.repaint();
    }

    private void draw(Graphics g) {
        // Draw a line as the upper limit just below the scores
        g.setColor(Color.BLACK);
        g.drawLine(0, scoreLabel.getHeight() - 18, getWidth(), scoreLabel.getHeight() - 18);

        // Draw Snake
        for (Point point : snake) {
            // Draw the snake body image at the specified position
            g.drawImage(snakeBodyImage, point.x * CELL_SIZE, point.y * CELL_SIZE, CELL_SIZE+10, CELL_SIZE+10, null);
        }

        // Draw Fruit
        g.setColor(Color.RED);
        g.fillRect(fruit.x * CELL_SIZE, fruit.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (isGameOver) {
                    resetGame();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    
    // Implementing sound effects
    private void playEatingSound() {
        if (eatingAppleSound != null) {
            eatingAppleSound.setFramePosition(0);
            eatingAppleSound.start();
        }
    }
    private void playSmashingSound() {
        if (smashSnakeSound != null) {
            smashSnakeSound.setFramePosition(0);
            smashSnakeSound.start();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }
    }
}

class Login
{
    Scanner in = new Scanner(System.in);

    private String usernameField;
    private String passwordField;

    public Login() {
        //setLayout(new GridLayout(3, 2));

        // Add labels, input fields, and the login button
        System.out.print("Username: ");
        usernameField = in.nextLine();
        System.out.print("Password: ");
        passwordField = in.nextLine();

        // Add action listener for the login button
        //loginButton.addActionListener(this::loginAction);
    }

    public void checkVerfication(){
        if (!usernameField.equals(passwordField))
            System.exit(0);
    }
    private void loginAction(ActionEvent e) {
        // Handle login logic here
    }

}