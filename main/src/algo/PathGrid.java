package algo;

import tile.Tile;
import tile.TileType;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class PathGrid {

    private Node[][] nodes;

    public PathGrid() { this.nodes = new Node[31][31]; }

    public void createGrid(Tile[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                Tile tile = map[i][j];
                this.nodes[i][j] = new Node(tile.position, (tile.type == TileType.ROAD));
            }
        }
    }

    public Node getNodeAt(Position pos) {
        return this.nodes[pos.x][pos.y];
    }

    public List<Node> getNeighbours(Node node) {
        List<Position> neighboursPos = List.of(
                new Position(1, 0),
                new Position(0, 1),
                new Position(-1, 0),
                new Position(0, -1)
        );
        List<Node> neighboursNodes = new ArrayList<>();
        Position posNode = node.getPos();
        neighboursPos.forEach(position -> {
            Position newPos = posNode.add(position);
            if(!newPos.isBetween(0, 31)) return;
            Node curNode = nodes[newPos.x][newPos.y];
            if(!curNode.isWalkable()) return;
            neighboursNodes.add(curNode);
        });
        return neighboursNodes;
    }

}
