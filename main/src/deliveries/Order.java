package deliveries;

import tile.*;

public class Order {

    public int id;
    public double val;
    public Restaurant restaurant;
    public House house;
    public int tourLimite;
    public OrderState state;

    public Order(int id, double val, Restaurant restaurant, House house, int tourLimite) {
        this.id = id;
        this.val = val;
        this.restaurant = restaurant;
        this.house = house;
        this.tourLimite = tourLimite;
        this.state = OrderState.WAITING;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", val=" + val +
                ", restaurant=" + restaurant +
                ", house=" + house +
                ", tourLimite=" + tourLimite +
                '}';
    }
}
