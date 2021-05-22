package tile;

import utils.Position;

import java.util.Objects;

public abstract class Tile {

    public Position position;
    public TileType type;

    public Tile(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "{" +
                "position=" + position.x + "," + position.y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(position, tile.position) && type == tile.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, type);
    }
}
