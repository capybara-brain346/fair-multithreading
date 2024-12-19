package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    private static void handleClient(Socket client) throws IOException {
        try {
            byte[] request = new byte[1024];

            client.getInputStream().read(request);
            System.out.println("Processign request");
            Thread.sleep(3000);

            System.out.println("processing complete....");

            String response = "Hello World!";
            client.getOutputStream().write(response.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            Socket client = serverSocket.accept();
            System.out.println("new client connected!");
            Thread clientThread = new Thread(() -> {
                try {
                    handleClient(client);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            clientThread.start();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            serverSocket.close();
        }
    }
}
