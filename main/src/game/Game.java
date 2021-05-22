package game;

import algo.AStar;
import deliveries.Biker;
import deliveries.Order;
import deliveries.OrderState;
import ia.IA;
import network.Client;
import tile.*;
import utils.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private static Game game;

    public Tile[][] tiles;
    public int teamNumber;
    public Biker[] bikers;
    public List<Order> orders;
    public int pa;
    public int tour;
    public IA ia;
    public List<Integer> affected = new ArrayList<>();

    private Game() {
        bikers = new Biker[2];
        pa = 8;
        tour = 0;
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

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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

    public List<Position> findNearestOrder(int bikerID)
    {
        int minDist = Integer.MAX_VALUE;
        List<Position> nearestOrder = null;
        for (Order order : orders) {
            List<Position> path = findClosestPathToRestau(bikers[bikerID].pos, order.restaurant.position);

            if(minDist > path.size()) {
                minDist = path.size();
                nearestOrder = path;
            }
        }

        return nearestOrder;
    }

    public Order findHigthestScoreOrder(int bikerID)
    {
        float maxScore = 0;
        Order higthestScoreOrder = null;
        for (Order order : orders) {
            if(affected.contains(order.id)) continue;
            List<Position> pathToRestau = findClosestPathToRestau(bikers[bikerID].pos, order.restaurant.position);
            List<Position> pathToHouse;
            if(pathToRestau.size() != 0)
                 pathToHouse = findClosestPathToRestau(pathToRestau.get(pathToRestau.size()-1), order.house.position);
            else pathToHouse = new ArrayList<Position>();
            int lengthPath = pathToRestau.size() + pathToHouse.size();

            int paToPath = (lengthPath + 2); // nb de pa pour arriver
            int turnToPath = (int)Math.ceil(paToPath/4f); // nb de tour pour arriver

            if(this.tour + turnToPath > order.tourLimite || this.tour + turnToPath >= 50) {
                continue; // si pas le temps -> skip
            }

            float score = (float)order.val / lengthPath; // calcul score = nb point par case

            if(maxScore < score) {
                maxScore = score;
                higthestScoreOrder = order;
            }
        }
        return higthestScoreOrder;
    }

    public Order setOrderToBiker(Biker biker){
        Order o = findHigthestScoreOrder(biker.id);
        if(o != null) {
            o.state = OrderState.AFFECTED;
            affected.add(o.id);
            biker.path = findClosestPathToRestau(biker.pos, o.restaurant.position);
            biker.toTake = o;
            System.err.println("STATE ORDER" + o);
            return o;
        } else {
            System.err.println("No order found");
        }
        return null;
    }

    public void update() throws IOException {
        ia.thinking();
    }

    public void refreshOrders()
    {
        if (bikers[0].toTake != null && !this.orders.contains(this.bikers[0].toTake))
        {
            System.err.println("VOLEUR");
            this.setOrderToBiker(this.bikers[0]);
        }
        if (bikers[1].toTake != null && !this.orders.contains(this.bikers[1].toTake))
        {
            System.err.println("VOLEUR");
            this.setOrderToBiker(this.bikers[1]);
        }
    }
}

















