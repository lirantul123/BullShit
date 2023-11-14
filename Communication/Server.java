package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server listening on port " + PORT + "...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with client");

            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Receive message
                String receivedMessage = in.nextLine();
                System.out.println("#: '" + receivedMessage + "'");

                // Send message
                System.out.print("!Enter a message to send: ");
                String messageToSend = scanner.nextLine();
                out.println(messageToSend);

                System.out.print("(WAITING FOR FEEDBACK...)\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
