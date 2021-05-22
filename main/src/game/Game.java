package game;

import algo.AStar;
import deliveries.Biker;
import deliveries.Order;
import network.Client;
import tile.*;
import utils.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game game;

    public Tile[][] tiles;
    public int teamNumber;
    public Biker[] bikers;
    public ArrayList<Order> orders;
    public int pa;
    public int tour;

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
        int minDist = Integer.MAX_VALUE;
        List<Position> closestPath = null;
        Position[] dirs = new Position[]{new Position(1,0), new Position(-1,0), new Position(0,1), new Position(0,-1)};

        for (Position dir : dirs) {
            Position newPos = restau.add(dir);

            if(tiles[newPos.x][newPos.y].type != TileType.ROAD) continue;

            List<Position> path = AStar.getClosestPath(pos, newPos, game.tiles);
            if(minDist > path.size()) {
                minDist = path.size();
                closestPath = path;
            }
        }

        return closestPath;
    }

    public Order findNearestOrder(int bikerID)
    {
        int minDist = Integer.MAX_VALUE;
        Order nearestOrder = null;
        for (Order order : orders) {
            List<Position> path = findClosestPathToRestau(bikers[bikerID].pos, order.restaurant.position);

            if(minDist > path.size()) {
                minDist = path.size();
                nearestOrder = order;
            }
        }

        return nearestOrder;
    }

    public void update() throws IOException {
        System.out.println("TOUR : " + this.tour + ", SCORE : " + Client.getInstance().getScore());

        // Les bikers ont-ils une commande ?
        for (Biker biker : this.bikers) {
            if (biker.path.isEmpty())
            {
                //Si a cote maison && check commandes à déposer
                    //déposer commande
                //Sinon (à cote resto) && verif tjr la commande
                    //récup commande
            } else {
                for (int i = 0; i < 4; i++) {
                    String direction = biker.popNextDirection();
                    Client.getInstance().move(biker, direction);
                    //TODO: utiliser les PA restants
                    if (biker.path.size() > 0) break;
                }
            }
        }
        Client.getInstance().endTurn();
        this.tour++;
    }
}

















