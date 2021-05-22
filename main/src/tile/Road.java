package tile;

import utils.Position;

public class Road extends Tile {

    public Road(Position pos){
        super(pos);
        this.type = TileType.ROAD;
    }
}
