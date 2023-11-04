package Kinematics;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static double v0y;
    public static double v0x;
    public static double v0 = 0, y0 = 0, t = 0, angle = 30, y = 0;
    public static double time, horisantelDistance, maxHeight;

    public static void main(String[] args) {
        System.out.println("v0 = start velocity;\ny0 = start height;\nangle = angle;");
        System.out.println("Instance: v, t\n");

        whatGivenAndGetting();
        calculatingInfo();

        JFrame frame = new JFrame("Rocket Diagram");
        RocketDiagram rocketDiagram = new RocketDiagram(v0, angle, time, maxHeight, horisantelDistance, y0, y, v0x, v0y);
        frame.add(rocketDiagram);
        frame.setSize(850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void whatGivenAndGetting() {
        System.out.print("What do you have? ");
        String input = in.nextLine();

        input = input.replaceAll(" ", "");
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals("v0")) {
                System.out.print("Enter start velocity: ");
                v0 = in.nextDouble();
            }
            if (elements[i].equals("y0")) {
                System.out.print("Enter start height: ");
                y0 = in.nextDouble();
            }
            if (elements[i].equals("angle")) {
                System.out.print("Enter angle: ");
                angle = in.nextDouble();
            }
        }
        v0x = v0 * Math.cos(Math.toRadians(angle));
        v0y = v0 * Math.sin(Math.toRadians(angle));
    }

    public static void calculatingInfo() {
        // y = y0 + v0t + 0.5at^2
        if (t == 0) {
            System.out.printf("-0.5*10*t^2 - %ft = %f - %f\n", v0y, y0, y);
            System.out.printf("5*t^2 - %ft = %f\n", v0y, (y0 - y));
            if (v0y < 2) {
                System.out.printf("%ft*((5/%f)t - 1) = %f\n", v0y, v0y, (y0 - y));
                System.out.printf("(%ft - 1) = %f\n", (5 / v0y), (y0 - y));
                System.out.printf("%ft = 1\n", (5 / v0y));
                System.out.printf("t = (1/%f)\n", (5 / v0y));
                System.out.printf("t = %f-seconds-\n\n", (1 / (5 / v0y)));
                time = (1 / (5 / v0y));
            } else {
                System.out.printf("5t*(t - (%f/5)) = %f\n", v0y, (y0 - y));
                System.out.printf("t = (%f/5)\n", v0y);
                System.out.printf("t = %f-seconds-\n\n", (v0y / 5));
                time = (v0y / 5);
            }
        }

        // x = x0 + v0*t
        // x0 will always be reset as 0
        System.out.printf("x = %ft\n", v0x);
        System.out.printf("x = %f*%f\n", v0x, time);
        System.out.printf("x = %f-meters-\n\n", (v0x * time));
        horisantelDistance = (v0x * time);

        // y = y0 + v0t + 0.5at^2
        // y0 will always be reset as 0
        // v = 0
        System.out.printf("y(max) = 0 + 0*%f + 5*%f^2\n", time, time);
        System.out.printf("y(max) = 5*%f^2\n", time);
        System.out.printf("y(max) = %f-meters-\n\n\n", (5 * (time * time)));
        maxHeight = (5 * (time * time));

        System.out.println("Time of Flight: " + time + " seconds");
        System.out.println("Horizontal Distance: " + horisantelDistance + " meters");
        System.out.println("Maximum Height: " + maxHeight + " meters\n\n");
    }
}

class RocketDiagram extends JPanel {
    private double v0;
    private double angle;
    private double time;
    private double maxHeight;
    private double horisantelDistance;
    private double y0, y, v0x, v0y;

    public RocketDiagram(double v0, double angle, double time, double maxHeight, double horisantelDistance, double y0, double y, double v0x, double v0y) {
        this.v0 = v0;
        this.angle = angle;
        this.time = time;
        this.maxHeight = maxHeight;
        this.horisantelDistance = horisantelDistance;
        this.y0 = y0;
        this.y = y;
        this.v0x = v0x;
        this.v0y = v0y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw X and Y axes
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);  // X-axis
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());  // Y-axis

        double angleRadians = Math.toRadians(angle);
        // Each square is 5 units
        int x0 = 425;
        int y0 = getHeight() / 2;

        // Each horisantelDistance will be divided in 100, to scale
        int x1, x2;
        int y1, y2;
        boolean donwstars = false;

        for (double i = (horisantelDistance/100)/2 ; i <= horisantelDistance/100; i += 0.1) {
            if (i == (horisantelDistance/100)/2 || donwstars){
                x1 = (int) (x0 + v0 * Math.cos(angleRadians) * i);
                y1 = (int) (y0 - v0 * Math.sin(angleRadians) * i + 0.5 * (10) * i * i);
                donwstars = true;
            }
            else{
                x1 = (int) (x0 + v0 * Math.cos(angleRadians) * i);
                y1 = (int) (y0 - v0 * Math.sin(angleRadians) * i + 0.5 * (-10) * i * i);
            }
            g2d.drawLine(x0, y0, x1, y1);
            x0 = x1;
            y0 = y1;
        }
        
    }
}