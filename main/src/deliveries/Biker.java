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

}
