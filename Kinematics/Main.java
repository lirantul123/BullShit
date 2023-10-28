package Kinematics;

import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static double v0y;
    public static double v0x;
    public static double v0 = 0, v = 0, y0 = 0, y = 0, t = 0, angle = 0;
    public static double time, horisantelDistance, maxHeight;

    public static void main(String[] args) {
        System.out.println("v0 = start velocity;\nv = end velocity;\ny0 = start height;\ny = end height;\nangle = angle;\nt = time;");
        System.out.println("Instance: v, t\n");

        System.out.print("WhAt dO yOu hAvA? ");
        String input = in.nextLine();

        input = input.replaceAll(" ", "");
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals("v0")) {
                System.out.print("Enter start velocity: ");
                v0 = in.nextInt();
            }
            if (elements[i].equals("v")) {
                System.out.print("Enter end velocity: ");
                v = in.nextInt();
            }
            if (elements[i].equals("y0")) {
                System.out.print("Enter start height: ");
                y0 = in.nextInt();
            }
            if (elements[i].equals("y")) {
                System.out.print("Enter end height: ");
                y = in.nextInt();
            }
            if (elements[i].equals("t")) {
                System.out.print("Enter time: ");
                t = in.nextInt();
            }
            if (elements[i].equals("angle")) {
                System.out.print("Enter angle: ");
                angle = in.nextInt();
            }
        }

        if (input.contains("v0") && input.contains("angle")) {
            v0x = v0 * Math.cos(Math.toRadians(angle));
            v0y = v0 * Math.sin(Math.toRadians(angle));
        }

        // y = y0 + v0t + 0.5at^2
        if (t == 0) {
            System.out.printf("-0.5*10*t^2 - %ft = %f - %f\n", v0y, y0, y);
            System.out.printf("5*t^2 - %ft = %f\n", v0y, (y0 - y));
            if (v0y < 2){
                System.out.printf("%ft*((5/%f)t - 1) = %f\n", v0y, v0y, (y0 - y));
                System.out.printf("(%ft - 1) = %f\n", (5/v0y), (y0 - y));
                System.out.printf("%ft = 1\n", (5/v0y));
                System.out.printf("t = (1/%f)\n", (5/v0y));
                System.out.printf("t = %f-seconds-\n\n", (1/(5/v0y)));
                time = (1/(5/v0y));


            }
            else{
                System.out.printf("5t*(t - (%f/5)) = %f\n", v0y, (y0 - y));
                System.out.printf("t = (%f/5)\n", v0y);
                System.out.printf("t = %f-seconds-\n\n", (v0y/5));
                time = (v0y/5);
            }
        }
        
        // x = x0 + v0*t
        // x0 will always be reset as 0
        System.out.printf("x = %ft\n", v0x);
        System.out.printf("x = %f*%f\n", v0x, time);
        System.out.printf("x = %f-meters-\n\n", (v0x*time));
        horisantelDistance = (v0x*time);

        // y = y0 + v0t + 0.5at^2
        // y0 will always be reset as 0
        // v = 0
        System.out.printf("y(max) = 0 + 0*%f + 5*%f^2\n", time, time);
        System.out.printf("y(max) = 5*%f^2\n", time);   
        System.out.printf("y(max) = %f-meters-\n\n", (5*(time*time)));
        maxHeight = (5*(time*time));
    }
}