package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// tic tac tow online
public class Client {
    public static final int PORT = 8080;
    public static tictactow t = new tictactow();

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", PORT);
            System.out.println("Connected to server");

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            System.out.println(t.print());

            while (true) {
                // Send message
                System.out.print("Enter position(e.g 00): ");
                String messageToSend = scanner.nextLine();
                t.setPlace(Integer.parseInt(messageToSend)/10, Integer.parseInt(messageToSend)%10, "X");
                System.out.println(t.print());

                out.println(messageToSend);

                System.out.print("(WAITING FOR FEEDBACK...)\n\n ");

                // Receive message
                String receivedMessage = in.nextLine();
                System.out.println("#: '" + receivedMessage + "'");
                System.out.println(t.print());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
