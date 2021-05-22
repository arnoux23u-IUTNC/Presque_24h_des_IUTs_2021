package algo;

import utils.Position;

import java.util.Objects;

public class Node implements Comparable<Node> {

    private Node parentNode;
    private Position pos;
    private int gCost, hCost;
    private boolean walkable;

    public Node(Position pos, boolean walkable) {
        this.walkable = walkable;
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getHCost() {
        return hCost;
    }

    public int getFCost() {
        return this.gCost + hCost;
    }

    @Override
    public String toString() {
        return "Node[" + pos + "](g:" + gCost + ".h:" + hCost + ")";
    }

    @Override
    public int compareTo(Node o) {
        int result = Integer.compare(this.getFCost(), o.getFCost());
        if(result == 0) return Integer.compare(this.hCost, o.hCost);
        return result;
    }

    /**
     * Prend deux nodes en paramètres et calcule l'heuristique de la node 'from' à la node 'to'
     * @param from
     * @param to
     * @return heuristique de 'from' pour 'to'
     */
    public static int calculHCost(Node from, Node to) {
        int distX = Math.abs(from.pos.x - to.pos.x);
        int distY = Math.abs(from.pos.y - to.pos.y);
        if(distX > distY) return 14 * distY + 10 * (distX - distY);
        return 14 * distX + 10 * (distY - distX);
    }

    public boolean isWalkable() {
        return walkable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (gCost != node.gCost) return false;
        if (hCost != node.hCost) return false;
        if (walkable != node.walkable) return false;
        return Objects.equals(pos, node.pos);
    }

    @Override
    public int hashCode() {
        int result = pos != null ? pos.hashCode() : 0;
        result = 31 * result + gCost;
        result = 31 * result + hCost;
        result = 31 * result + (walkable ? 1 : 0);
        return result;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}
