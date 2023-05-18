import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 1234; // Port number for communication

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for a client...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // Open input and output streams for communication

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                double randomNumber = Math.random() * 1000000;
                int convertedNumber = (int) randomNumber;
                out.println(convertedNumber);

                String clientMessage2 = in.readLine();
                System.out.println("Recieved : " + clientMessage2);
                clientMessage2 += "'s";
                out.println(clientMessage2.toUpperCase());
                // Close the connections
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
