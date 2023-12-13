import java.util.Scanner;

public class BarFilling {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        final int completeWindowSize = 100;
        String ans = "";
        int windowsNum = 0;
        String title = "title";

        while (true) {
            System.out.print("Add window or Remove window [a/R]? ");
            ans = in.nextLine();

            if (ans.equalsIgnoreCase("a")) {
                windowsNum++;
                int windowWidth = completeWindowSize / windowsNum;

                System.out.println(" _____________________________________________________________________________________________________");
                String midContent = "|";
                for (int i = 0; i < windowsNum; i++) {
                    midContent += String.format(" %-"+(windowWidth - 4)+"s |", title + (i + 1));
                }
                System.out.println(midContent);
                System.out.println("|_____________________________________________________________________________________________________|");
            } else if (ans.equalsIgnoreCase("r")) {
                if (windowsNum > 0) {
                    windowsNum--;
                    int windowWidth = completeWindowSize / windowsNum;

                    System.out.println(" _____________________________________________________________________________________________________");
                    String midContent = "|";
                    for (int i = 0; i < windowsNum; i++) {
                        midContent += String.format(" %-"+(windowWidth - 4)+"s |", title + (i + 1));
                    }
                    System.out.println(midContent);
                    System.out.println("|_____________________________________________________________________________________________________|");
                } else {
                    System.out.println("No windows to remove.");
                }
            }
            else
                System.out.println("Nope");
        }
    }
}
