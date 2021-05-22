package deliveries;

import tile.*;

import java.util.Objects;

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
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
