package deliveries;

import game.Game;
import tile.Tile;
import tile.TileType;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Biker {

    public int id;

    public Position pos;

    public List<Order> order;

    public List<Position> path;

    public Biker(Position pos, int id) {
        this.id = id;
        this.pos = pos;
        this.order = new ArrayList<>();
        this.path = new ArrayList<>();
    }

    public boolean haveOrder() {
        return !this.order.isEmpty();
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
        Position[] directions = {new Position(0, 1), new Position(1, 0), new Position(0, -1), new Position(-1, 0)};
        for (Position direction : directions) {
            int x = this.pos.add(direction).x;
            int y = this.pos.add(direction).y;
            if (x >= 0 && x <= 30 && y >= 0 && y <= 30)
            {
                if (Game.getInstance().tiles[this.pos.add(direction).x][this.pos.add(direction).y].equals(tile))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNear(TileType tileType)
    {
        Position[] directions = {new Position(0, 1), new Position(1, 0), new Position(0, -1), new Position(-1, 0)};
        for (Position direction : directions) {
            int x = this.pos.add(direction).x;
            int y = this.pos.add(direction).y;
            if (x >= 0 && x <= 30 && y >= 0 && y <= 30)
            {
                if (Game.getInstance().tiles[this.pos.add(direction).x][this.pos.add(direction).y].type == tileType)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void addOrder(Order order) {
        if(this.order.size()<3) this.order.add(order);
    }

    public void removeOrder(Order order) {
        this.order.remove(order);
    }

}
