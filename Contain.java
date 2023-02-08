package FunktionellProgrammering.DatabaseHappyFeet;

public class Contain {
    private int orderId;
    private int shoesId;
    private int amount;

    public Contain(int orderId, int shoesId, int amount) {
        this.orderId = orderId;
        this.shoesId = shoesId;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getShoesId() {
        return shoesId;
    }

    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
