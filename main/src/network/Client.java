package network;

import deliveries.Biker;
import deliveries.Order;
import game.Game;
import tile.House;
import tile.Restaurant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    // ---- Socket ---- //
    private static Client instance;
    public Socket socket;
    public BufferedReader reader;
    public PrintWriter writer;

    // ---- Game ---- //
    private Game game;

    private Client() {
    }

    // Singleton Pattern
    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public void connect(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);

        game = Game.getInstance();

        this.reader.readLine(); // NAME
        // Sending team name
        this.writer.println("DNHess");

        game.teamNumber = Integer.parseInt(this.reader.readLine().substring(6)); //read START|0

        // Getting Map
        this.writer.println("GETMAP");
        String map = this.reader.readLine().substring(3); //getting the map
        game.parseMap(map);

        this.getDeliveries();
        // Updating bikers position
        this.getBikers();

        //Test pour gps
        //while(!game.isEnd())
        game.update();
    }

    public void checkResult(String res) {
        String resCode = res.split("\\|")[0];
        System.out.println("recu: " + resCode);
    }

    public void getDeliveries() throws IOException {
        System.out.println("Get deliveries");
        this.writer.println("GETDELIVERIES");
        String deliveriesRes = reader.readLine();
        this.checkResult(deliveriesRes);

        String[] splitedDel = deliveriesRes.split("\\|");
        // code; valeur ; coordonnées du restaurant ; coordonnées de la maison ; tour limite de livraison
        CopyOnWriteArrayList<Order> orders = new CopyOnWriteArrayList<>();
        for (int i = 1; i < splitedDel.length; i++) {
            String[] order = splitedDel[i].split(";");
            Order current = new Order(
                    Integer.parseInt(order[0]),
                    Double.parseDouble(order[1]),
                    (Restaurant) game.tiles[Integer.parseInt(order[2])][Integer.parseInt(order[3])],
                    (House) game.tiles[Integer.parseInt(order[4])][Integer.parseInt(order[5])],
                    Integer.parseInt(order[6])
            );
            orders.add(current);
            game.setOrders(orders);
        }
    }

    public void getBikers() throws IOException {
        System.out.println("envoie: " + "GETBIKERS|" + game.teamNumber);
        this.writer.println("GETBIKERS|" + game.teamNumber);
        // log | code;posx;posy | code;posx;posy
        String[] bikersPos = this.reader.readLine().split("\\|");
        String[] pos0 = bikersPos[1].split(";");
        String[] pos1 = bikersPos[2].split(";");
        game.updateBiker(Integer.parseInt(pos0[0]), Integer.parseInt(pos0[1]), Integer.parseInt(pos0[2]));
        game.updateBiker(Integer.parseInt(pos1[0]), Integer.parseInt(pos1[1]), Integer.parseInt(pos1[2]));
    }

    public void endTurn() throws IOException {
        System.out.println("envoie: " + "ENDTURN");
        this.writer.println("ENDTURN");
        this.reader.readLine();
        String command = this.reader.readLine();
        if(command.contains("START")) {
            game.pa = 8;
            game.update();
        } else if(command.contains("ENDGAME")) {
            System.exit(0);
        }
    }

    public String getScore() throws IOException {
        this.writer.println("SCORE|" + game.teamNumber);
        String res = this.reader.readLine();
        this.checkResult(res);
        return res.substring(3);
    }

    public void move(Biker biker, String direction) throws IOException {
        //System.out.println("envoie: " + "MOVE|" + biker.id + "|" + direction);
        this.writer.println("MOVE|" + biker.id + "|" + direction);
        this.checkResult(this.reader.readLine());
    }

    public void take(Biker biker, Order order) throws IOException {
        //System.out.println("envoie: " + "TAKE|" + biker.id + "|" + order.id);
        this.writer.println("TAKE|" + biker.id + "|" + order.id);
        this.checkResult(this.reader.readLine());
    }

    public void deliver(Biker biker, Order order) throws IOException {
        //System.out.println("envoie: " + "DELIVER|" + biker.id + "|" + order.id);
        this.writer.println("DELIVER|" + biker.id + "|" + order.id);
        this.checkResult(this.reader.readLine());
    }
}
