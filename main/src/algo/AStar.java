package algo;
import tile.Tile;
import utils.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStar {   
   
   
                                 public static List<Position>                                   getClosestPath(Position from,
                        Position to,                                                                                       Tile[][] map){
                 List<Position>                                                                                                   positions =
            new ArrayList<>();                                                                                                     PathGrid pathGrid
         = new PathGrid();                                                                                                           pathGrid.createGrid(map);
       Node startNode =                                                                                                                  pathGrid.getNodeAt(from);
       Node endNode =                                                                                                                      pathGrid.getNodeAt(to);
      List<Node> openSet                                                                                                                      = new ArrayList<>();
      openSet.add(startNode);                                                                                                                   Set<Node> closeSet
      = new HashSet<>();                                                                                                                       while (openSet.
      size() > 0) {                                                                                                                           Node curNode =
      openSet.get(0);                     for (int i = 1;                                             i < openSet.size();                      i++) { Node n=
        openSet.get(i);                   if(n.getFCost()                                             < curNode.getFCost()                    || n.getFCost()
       == curNode.getFCost()              && n.getHCost()                                             < curNode.getHCost()) {                  curNode = n;}}
       openSet.remove(curNode);              closeSet                                                    .add(curNode);                     if(curNode.equals
         (endNode)) return                                                                                                              retracePath(startNode,
           endNode); List<Node>                                                                                                        neighbours = pathGrid
             .getNeighbours(curNode);                                                                                                 for (Node neighbour : 
               neighbours) {                                               if(!neighbour.                                           isWalkable() || closeSet
                 .contains(neighbour))                                  continue;int newCost                                    = curNode.getGCost() + Node.
                     calculHCost(curNode,                            neighbour);      if(newCost                             < neighbour.getGCost() || !
                       openSet.contains                        (neighbour)) {             neighbour                        .setParentNode(curNode);
                            neighbour.                   setGCost(newCost);                      neighbour.               setHCost(Node.calculHCost
                                   (neighbour, endNode));if                                         (!openSet.contains(neighbour)
                                   
                                   
                                   
                                   
                                   
) openSet.add(neighbour);                }            }         }        return positions;    }    private static <T> List<T> invertList(List<T> list) {        List<T> newList = new ArrayList<>();        list.forEach(t -> newList.add(0, t));        return newList;    }    private static List<Position> retracePath(Node startNode, Node endNode) {        List<Position> path = new ArrayList<>();        Node curNode = endNode;        while (!curNode.equals(startNode)) {            path.add(curNode.getPos());            curNode = curNode.getParentNode();        }        return invertList(path);    }}