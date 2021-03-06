package utils;

public class Position {

    public int x,y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position add(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    public boolean isBetween(int min, int max) {
        if(this.x < min || this.x > max) return false;
        return this.y >= min && this.y <= max;
    }

    @Override
    public String toString() {
        return "Position:" + this.x + ";" + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
