package network;

import java.net.*;
import java.io.*;

public class Client {

    private static Client instance;

    public Socket socket;
    public BufferedReader reader;
    public PrintWriter writer;

    private Client() {
    }

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public void connect(String ip, int port) throws IOException {
        this.socket = new Socket(ip,port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);

        this.writer.println("DNHess");
    }
}
