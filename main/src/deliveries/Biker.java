package deliveries;

import tile.Tile;
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
        Position current = this.pos;
        path.remove(0);
        this.pos = next;
        int dirX = next.x - current.x;
        int dirY = next.y - current.y;
        if(dirX == 0) {
            if(dirY == 1) return "R";
            else return "L";
        } else {
            if(dirX == 1) return "B";
            else return "T";
        }
    }

    public boolean isNear(Tile tile)
    {
        //Position[] directions = {new Position(0, 1), new Position(1, 0), new Position(0, -1), new Position(-1, 0)};
        return true;
    }

}
