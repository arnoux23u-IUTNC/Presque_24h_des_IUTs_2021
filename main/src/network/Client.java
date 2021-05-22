package network;

import game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static Client instance;
    private Game game;

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

        game = Game.getInstance();

        this.reader.readLine(); //read NAME
        //Sending team name
        this.writer.println("DNHess");

        game.teamNumber = Integer.parseInt(this.reader.readLine().substring(6)); //read START|0

        //MAP
        this.writer.println("GETMAP");
        String map = this.reader.readLine().substring(3); //getting the map
        game.parseMap(map);

        //Start pos
        this.writer.println("GETBIKERS|" + game.teamNumber);
        String[] bikersPos = this.reader.readLine().split("\\|");
        String[] pos0 = bikersPos[1].split(";");
        String[] pos1 = bikersPos[2].split(";");

    }
}
