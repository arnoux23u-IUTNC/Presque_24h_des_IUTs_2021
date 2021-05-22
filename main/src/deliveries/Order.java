package deliveries;

import tile.*;

public class Order {

    public int id;
    public int val;
    public Restaurant restaurant;
    public House house;
    public int tourLimite;

    public Order(int id, int val, Restaurant restaurant, House house, int tourLimite) {
        this.id = id;
        this.val = val;
        this.restaurant = restaurant;
        this.house = house;
        this.tourLimite = tourLimite;
    }
}
