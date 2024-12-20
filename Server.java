import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    private static void handleClient(Socket client) {
        try {
            byte[] request = new byte[1024];

            client.getInputStream().read(request);
            System.out.println("Processing request");
            Thread.sleep(3000);

            System.out.println("processing complete....");

            String response = "HTTP/1.1 200 OK\r\n\r\nHello World!\r\n";
            client.getOutputStream().write(response.getBytes());
        } catch (Exception e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("new client connected!");
                    Thread clientThread = new Thread(() -> handleClient(client));
                    clientThread.start();
                } catch (IOException e) {
                    System.out.println("Error accepting connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + PORT + ": " + e.getMessage());
        }
    }
}