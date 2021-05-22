package tile;

import utils.Position;

public abstract class Tile {

    public Position position;
    public TileType type;

    public Tile(Position position) {
        this.position = position;
    }
}
