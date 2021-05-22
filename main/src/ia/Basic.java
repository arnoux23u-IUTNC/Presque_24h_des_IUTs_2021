package ia;

import deliveries.Biker;
import deliveries.Order;
import game.Game;
import network.Client;

import java.io.IOException;

public class Basic implements IA{
    @Override
    public void thinking() throws IOException {
        Game game = Game.getInstance();
        Client.getInstance().getDeliveries();
        System.err.println("TOUR : " + game.tour + ", SCORE : " + Client.getInstance().getScore());

        if (game.tour != 1)
        {
            while (game.pa > 0)
            {
                // Les bikers ont-ils une commande ?
                for (Biker biker : game.bikers) {
                    //Si le biker est arrive a destination
                    if (biker.path.isEmpty()) {
                        //on check si il arrive a une maison
                        for (Order order : biker.order) {
                            if(biker.isNear(order.house)) {
                                System.err.println("Limite :" + order.tourLimite + " current : " + game.tour);
                                Client.getInstance().deliver(biker,order);
                                biker.removeOrder(order);
                            }
                        }
                        //on check si il arrive a un restaurant
                        for (Order order : game.orders) {
                            if(biker.isNear(order.restaurant) && order.equals(biker.toTake) && game.pa > 0) {
                                Client.getInstance().take(biker,order);
                                biker.addOrder(order);
                                game.orders.remove(order);
                                biker.path = game.findClosestPathToRestau(biker.pos, order.house.position);
                                biker.toTake = null;
                                break;
                            }
                        }
                        //Si il a rien && il bouge pas
                        if(biker.order.isEmpty() && game.pa > 0) {
                            game.setOrderToBiker(biker);
                        }
                    }
                    if(!biker.path.isEmpty()) { //Si le biker est en chemin
                        System.out.println("Biker " + biker.id + " going to " + biker.path.get(biker.path.size()-1));
                        for (int i = 0; (i < 4 && game.pa>0); i++) {
                            String direction = biker.popNextDirection();
                            Client.getInstance().move(biker, direction);
                            //TODO: utiliser les PA restants
                            if (biker.path.size() == 0) break;
                        }
                    }
                }
            }
        }

        game.tour += 1;

        Client.getInstance().endTurn();
    }
}
