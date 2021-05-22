package deliveries;

import utils.Position;

import java.util.ArrayList;

public class Biker {

    public int id;

    public Position pos;

    public ArrayList<Order> order;

    public ArrayList<Position> path;

    public Biker(Position pos, int id) {
        this.id = id;
        this.pos = pos;
        this.order = new ArrayList<>();
        this.path = new ArrayList<>();
    }

    public String popNextDirection() {
        Position next = path.get(0);
        path.remove(0);
        int dirX = next.x - pos.x;
        int dirY = next.y - pos.y;
        if(dirX == 0) {
            if(dirY == 1) return "R";
            else return "L";
        } else {
            if(dirX == 1) return "B";
            else return "T";
        }
    }

}
