package game;

import algo.AStar;
import deliveries.Biker;
import deliveries.Order;
import deliveries.OrderState;
import network.Client;
import tile.*;
import utils.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {

    private static Game game;

    public Tile[][] tiles;
    public int teamNumber;
    public Biker[] bikers;
    public List<Order> orders;
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

    public void setOrders(List<Order> orders) {
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
            List<Position> pathToRestau = findClosestPathToRestau(bikers[bikerID].pos, order.restaurant.position);
            List<Position> pathToHouse = findClosestPathToRestau(pathToRestau.get(pathToRestau.size()-1), order.house.position);

            int lengthPath = pathToRestau.size() + pathToHouse.size();

            int paToPath = (lengthPath + 2); // nb de pa pour arriver
            int turnToPath = (int)Math.ceil(paToPath/4f); // nb de tour pour arriver

            if(this.tour + turnToPath > order.tourLimite) break; // si pas le temps -> skip

            float score = (float)order.val / lengthPath; // calcul score = nb point par case

            if(maxScore < score) {
                maxScore = score;
                higthestScoreOrder = order;
            }
        }

        return higthestScoreOrder;
    }

    public void setOrderToBiker(Biker biker){
        Order o = findHigthestScoreOrder(biker.id);
        o.state = OrderState.AFFECTED;
        biker.path = findClosestPathToRestau(biker.pos, o.restaurant.position);
    }

    public void update() throws IOException {
        System.out.println("TOUR : " + this.tour + ", SCORE : " + Client.getInstance().getScore());

        // Les bikers ont-ils une commande ?
        for (Biker biker : this.bikers) {
            //Si le biker est arrive a destination
            if (biker.path.isEmpty()) {
                //on check si il arrive a une maison
                for (Order order : biker.order) {
                    if(biker.isNear(order.house)) {
                        Client.getInstance().deliver(biker,order);
                        biker.removeOrder(order);
                    }
                }
                //on check si il arrive a un restaurant
                for (Order order : this.orders) {
                    if(biker.isNear(order.restaurant)) {
                        Client.getInstance().take(biker,order);
                        biker.addOrder(order);
                        orders.remove(order);
                        biker.path = findClosestPathToRestau(biker.pos, order.house.position);
                    }
                }
                //Si il a rien && il bouge pas
                if(biker.order.isEmpty()) {
                    biker.path = findHigthestScoreOrder(biker.id);
                }
            }
            if(!biker.path.isEmpty()) { //Si le biker est en chemin
                System.out.println("Biker " + biker.id + " going to " + biker.path.get(biker.path.size()-1));
                for (int i = 0; i < this.pa; i++) {
                    String direction = biker.popNextDirection();
                    Client.getInstance().move(biker, direction);
                    //TODO: utiliser les PA restants
                    if (biker.path.size() == 0) break;
                }
            }
        }
        Client.getInstance().endTurn();
        this.tour++;
    }
}

















