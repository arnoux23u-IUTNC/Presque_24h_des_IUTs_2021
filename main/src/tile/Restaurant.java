package tile;

import utils.Position;

public class Restaurant extends Tile {

    public Restaurant(Position pos) {
        super(pos);
        this.type = TileType.RESTAURANT;
    }
}
