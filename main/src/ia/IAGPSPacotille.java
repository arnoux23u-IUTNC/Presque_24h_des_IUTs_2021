package ia;

import deliveries.Biker;
import deliveries.Order;
import game.Game;
import network.Client;
import tile.House;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IAGPSPacotille implements IA {

    private boolean isNextToResteau;
    private boolean isNextToHouse;
    private boolean aDepose;
    private boolean aPrisOrder;
    private boolean aPrisCommande;

    @Override
    public void thinking() throws IOException {
        Game game = Game.getInstance();
        Client client = Client.getInstance();
        Biker biker = game.bikers[0];
        if(!aPrisOrder) {
            System.out.println("pas d'order");
            aPrisOrder = true;
            Order order = game.findNearestOrder(biker.id);
            biker.path = game.findClosestPathToRestau(biker.pos, order.restaurant.position);
            biker.order.add(order);
        }
        int i = 0;
        while (!isNextToResteau && i < 8) {
            String nextDirection = biker.popNextDirection();
            client.move(biker, nextDirection);
            i++;
            if(biker.path.isEmpty()) isNextToResteau = true;
        }
        System.out.println("fin récup");
        if(i == 8) {
            System.out.println("first end tour");
            client.endTurn();
            return;
        }
        i = 0;
        if(!aPrisCommande) {
            client.take(biker, biker.order.get(0));
            i++;
            aPrisCommande = true;
        }
        if(biker.path.isEmpty()) {
            System.out.println("house empty");
            List<House> houses = game.getHouses();
            houses.remove(biker.order.get(0).house);
            houses.sort(Comparator.comparingInt(o -> game.findClosestPathToRestau(biker.pos, o.position).size()));
            biker.path = game.findClosestPathToRestau(biker.pos, houses.get(0).position);
            System.out.println(houses.get(0).position);
            System.out.println(biker.path);
        }
        while (!isNextToHouse && i < 8) {
            String nextDirection = biker.popNextDirection();
            client.move(biker, nextDirection);
            i++;
            if(biker.path.isEmpty()) isNextToHouse = true;
        }
        if(i == 8) {
            client.endTurn();
            return;
        }
        if(!aDepose) {
            System.out.println("on dépose");
            client.deliver(biker, biker.order.get(0));
            aDepose = true;
            game.end = true;
        }
    }
}
