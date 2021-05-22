package network;

import deliveries.Order;
import game.Game;
import tile.House;
import tile.Restaurant;
import utils.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
        System.out.println("Get bikers pos");
        this.writer.println("GETBIKERS|" + game.teamNumber);
        String[] bikersPos = this.reader.readLine().split("\\|");
        String[] pos0 = bikersPos[1].split(";");
        String[] pos1 = bikersPos[2].split(";");
        game.initBikers(Integer.parseInt(pos0[0]),Integer.parseInt(pos0[1]),Integer.parseInt(pos0[2]));
        game.initBikers(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]),Integer.parseInt(pos1[2]));

        this.getDeliveries();
    }

    public void checkResult(String res) {
        String resCode = res.split("\\|")[0];
        if(!resCode.equals("OK")) {
            System.err.println("ERROR: " + res);
        }
    }

    public void getDeliveries() throws IOException {
        System.out.println("Get deliveries");
        this.writer.println("GETDELIVERIES");
        String deliveriesRes = reader.readLine();
        this.checkResult(deliveriesRes);

        String[] splitedDel = deliveriesRes.split("\\|");
        //code; valeur ; coordonnées du restaurant ; coordonnées de la maison ; tour limite de livraison
        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 1; i < splitedDel.length; i++) {
            String[] order = splitedDel[i].split(";");
            Order current = new Order(
                    Integer.parseInt(order[0]),
                    Double.parseDouble(order[1]),
                    new Restaurant(new Position(Integer.parseInt(order[2]),Integer.parseInt(order[3]))),
                    new House(new Position(Integer.parseInt(order[4]),Integer.parseInt(order[5]))),
                    Integer.parseInt(order[6])
            );
            orders.add(current);
            game.setOrders(orders);
        }
    }

    public void getBikers() throws IOException {
        this.writer.println("GETBIKERS|" + game.teamNumber);
        String[] bikersPos = this.reader.readLine().split("\\|");
        String[] pos0 = bikersPos[1].split(";");
        String[] pos1 = bikersPos[2].split(";");
        game.initBikers(Integer.parseInt(pos0[0]),Integer.parseInt(pos0[1]),Integer.parseInt(pos0[2]));
        game.initBikers(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]),Integer.parseInt(pos1[2]));
    }

}
