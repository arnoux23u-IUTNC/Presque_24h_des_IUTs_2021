package tile;

import utils.Position;

public class House extends Tile {

    public House(Position pos){
        super(pos);
        this.type = TileType.HOUSE;
    }
}
