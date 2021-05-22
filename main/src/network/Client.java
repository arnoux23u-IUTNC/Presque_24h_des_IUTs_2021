package network;

import game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        this.socket = new Socket(ip, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);

        this.reader.readLine(); //read NAME
        //Sending team name
        this.writer.println("DNHess");

        this.reader.readLine(); //read START|0

        this.writer.println("GETMAP");

        String map = this.reader.readLine().substring(3); //getting the map

        Game.parseMap(map);
    }
}
