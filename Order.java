package FunktionellProgrammering.DatabaseHappyFeet;

import java.util.Date;

public class Order {
    private int id;
    private int customerId;
    private Date orderDate;

    /*public Order() {}

    public Order(Long id, Long customerId, LocalDateTime orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /*@Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderDate='" + orderDate + '}';
    }*/
}