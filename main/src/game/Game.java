package game;

import deliveries.Biker;
import deliveries.Order;
import tile.*;
import utils.Position;

import java.util.ArrayList;

public class Game {

    private static Game game;

    public Tile[][] tiles;
    public int teamNumber;
    public Biker[] bikers;
    public ArrayList<Order> orders;
    public int pa;

    private Game() {
        bikers = new Biker[2];
        pa = 8;
    }

    // Singleton Pattern
    public static Game getInstance() {
        if(game == null) game = new Game();
        return game;
    }

    public void parseMap(String mapStr) {
        Tile[][] tiles = new Tile[31][31];
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 31; j++) {
                char current = mapStr.charAt(i*31+j);
                switch (current) {
                    case 'R': tiles[i][j] = new Road(new Position(i,j)); break;
                    case 'E': tiles[i][j] = new Empty(new Position(i,j)); break;
                    case 'H': tiles[i][j] = new House(new Position(i,j)); break;
                    case 'S': tiles[i][j] = new Restaurant(new Position(i,j)); break;
                }
            }
        }
        Game.game.tiles = tiles;
    }

    public void updateBiker(int id, int x, int y) {
        if (bikers[id] == null)
        {
            new Biker(new Position(x,y));
        }
        else
        {
            bikers[id].pos.x = x;
            bikers[id].pos.y = y;
        }
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
        for (Order order : orders) {
            System.out.println(order);
        }
    }

}
