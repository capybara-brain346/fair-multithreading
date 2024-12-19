package tcp;

import java.io.IOException;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;

    private static void handleClient(Socket client) {
        try {
            byte[] request = new byte[1024];

            client.getInputStream().read(request);
            System.out.println("Processign request");
            Thread.sleep(3000);

            System.out.println("processing complete....");

            String response = "Hello World!";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
