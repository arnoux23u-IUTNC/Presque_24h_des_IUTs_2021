package algo;

import game.Game;
import tile.Tile;
import utils.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStar {

    /**
     * Méthode retournant le chemin le plus en partant d'une position a pour aller à une position b
     * @param from
     * @param to
     * @return liste de case formant le chemin le plus court
     */
    public List<Position> getClosestPath(Position from, Position to, Tile[][] map) {
        List<Position> positions = new ArrayList<>();
        //chocapic
        PathGrid pathGrid = new PathGrid();
        pathGrid.createGrid(map);
        Node startNode = pathGrid.getNodeAt(from);
        Node endNode = pathGrid.getNodeAt(to);
        //c'est fort en chocolat
        List<Node> openSet = new ArrayList<>();
        openSet.add(startNode);
        Set<Node> closeSet = new HashSet<>();
        while (openSet.size() > 0) {
            //ET PAAFFF !!!!
            Node curNode = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++) {
                Node n = openSet.get(i);
                if(n.getFCost() < curNode.getFCost() || n.getFCost() == curNode.getFCost() && n.getHCost() < curNode.getHCost()) {
                    curNode = n;
                }
            }
            openSet.remove(curNode);
            closeSet.add(curNode);
            if(curNode.equals(endNode)) return this.retracePath(startNode, endNode);
            List<Node> neighbours = pathGrid.getNeighbours(curNode);
            for (Node neighbour : neighbours) {
                if(!neighbour.isWalkable() || closeSet.contains(neighbour)) continue;
                int newCost = curNode.getGCost() + Node.calculHCost(curNode, neighbour);
                if(newCost < neighbour.getGCost() || !openSet.contains(neighbour)) {
                    neighbour.setParentNode(curNode);
                    neighbour.setGCost(newCost);
                    neighbour.setHCost(Node.calculHCost(neighbour, endNode));
                    if(!openSet.contains(neighbour)) openSet.add(neighbour);
                }
            }
            //CA FAIT DES CHOCAPICS
        }
        //Pas censé arriver
        return positions;
    }

    private <T> List<T> invertList(List<T> list) {
        List<T> newList = new ArrayList<>();
        list.forEach(t -> newList.add(0, t));
        return newList;
    }

    private List<Position> retracePath(Node startNode, Node endNode) {
        List<Position> path = new ArrayList<>();
        Node curNode = endNode;
        while (!curNode.equals(startNode)) {
            path.add(curNode.getPos());
            curNode = curNode.getParentNode();
        }
        return invertList(path);
    }

    public static void main(String[] args) {
        String map = "RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRREEEREREEERERSREREREEEREREEHHERRRRERRRERRRRRHRRRRRHRRRERRRRRERRERERHEEREREHERHRERERHRERSRHHHRRRRRRERERRRRRERRRRRRRERERRRRRHRRSRHREREEHEEREHERERERHREESRSRERRRRERSRRRERRRRRERERHRERRRERERRRRERHREREREEERHRHREREREREREEERERRERRRRREREEERERRRERERRRRRRRERERRHREREREREEESHEHREEEEERERERERERRRRRRERRREEERRRRRRRHEERRRRRRRRRRHRERHRHRHEEEEREEEEEEHREREHESERRRRHRERHREEERRRRRRRERHRRRSRRRERRESERHEEREEHEEEERERERERHRHRSHERRRREREEERRRRRRRERERRRRRERERERRRREREEEEHREREHERERHEEREREEEREHERRRRRRERRRRRERERRRHRRRERRRRRRRERREEHEEESRHREREREEEREHHRERESERERRERSEERRRRRRRERRRRRHRRRERRRERSRREREEERERERHRERHSERERHEEREHERERRRRHRERERRRRRRRRRERERERRRRRRRERRERERERHESREEEEERERERHREEEREHERRRRRRERRRRRERRRRRRRRRRRERRRRRRRREHEEEREEEREREHEREHHRSREREREEERREEEEERRRRRERRREREEERERERRRERRRRESSEERERHREEEEERHEERHREHEEEEERRRRRRRRRRERRRRRERHEERRRERERERRRRHRERSHHRERERERHEEEEHESSRERERERRERERRRRRHRRRRRRRRRHRRRRRRRERRRREEERERERHEESESHREEEEEHERERERERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR";
        Game game = Game.getInstance();
        game.parseMap(map);
        AStar aStar = new AStar();
        Position startPos = new Position(0, 0);
        Position endPos = new Position(0, 6);
        List<Position> path = aStar.getClosestPath(startPos, endPos, game.tiles);
        if(path.isEmpty()) System.out.println("Empty fdp");
        path.forEach(System.out::println);
    }

}
