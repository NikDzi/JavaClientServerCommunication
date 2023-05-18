import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please provide exactly two words as command-line arguments.");
            return;
        }
        String serverAddress = "localhost"; // Server's IP address or hostname
        int port = 1234; // Port number for communication
        try {
            // Connect to the server
            Socket socket = new Socket(serverAddress, port);
            System.out.println("Connected to the server.");

            // Open input and output streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String word1 = args[0];
            String word2 = args[1];

            // Read server's response
            String serverResponse = in.readLine();
            System.out.println("Server sent me : " + serverResponse);

            int convertedString = Integer.parseInt(serverResponse);
            String response;
            if (convertedString % 2 == 0) {
                out.println(word1);
                response = word1;
            } else {
                out.println(word2);
                response = word2;

            }
            System.out.println("Sent: " + response + ", Recieved: " + in.readLine());
            // Close the connections
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
