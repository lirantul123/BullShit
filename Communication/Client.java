package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int PORT = 8080;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", PORT);
            System.out.println("Connected to server");

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Send message
                System.out.print("!Enter a message to send: ");
                String messageToSend = scanner.nextLine();
                out.println(messageToSend);

                System.out.print("(WAITING FOR FEEDBACK...)\n\n ");

                // Receive message
                String receivedMessage = in.nextLine();
                System.out.println("#: '" + receivedMessage + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
