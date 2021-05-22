package tile;

import utils.Position;

public class Empty extends Tile {

    public Empty(Position pos){
        super(pos);
        this.type = TileType.EMPTY;
    }
}
