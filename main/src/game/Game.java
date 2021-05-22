package game;

import algo.AStar;
import deliveries.Biker;
import deliveries.Order;
import tile.*;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

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
            this.bikers[id] = new Biker(new Position(x,y), id);
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

    public List<Position> findClosestPathToRestau(Position pos, Position restau)
    {
        List <Position> res;
        int distance = 31*31+1;
        res = AStar.getClosestPath(pos, restau.add(new Position(1, 0)), game.tiles);
        List <Position> tmp;
        if (tiles[restau.x][restau.y+1].type==TileType.ROAD && distance > (tmp = AStar.getClosestPath(pos, restau.add(new Position(0, 1)), game.tiles)).size())
        {
            res = tmp;
            distance = res.size();
        }
        if (tiles[restau.x-1][restau.y].type==TileType.ROAD && distance > (tmp = AStar.getClosestPath(pos, restau.add(new Position(-1, 0)), game.tiles)).size())
        {
            res = tmp;
            distance = res.size();
        }
        if (tiles[restau.x][restau.y-1].type==TileType.ROAD && distance > (tmp = AStar.getClosestPath(pos, restau.add(new Position(0, -1)), game.tiles)).size())
        {
            res = tmp;
        }
        return res;
    }

    public List<Position> findNearestOrder(Position bikerID)
    {
        //AStar.getClosestPath(this.bikers[bikerID].pos, order.restaurant.position.add(new Position(0, -1)), game.tiles);
        return null;
    }

    public void update()
    {
        // Les bikers ont-ils une commande ?
        for (Biker biker : this.bikers) {
            if (biker.path.isEmpty())
            {
                //On assigne une commande

            }
        }
    }

}
